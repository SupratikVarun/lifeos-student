package com.lifeos.student.service;

import com.lifeos.student.dto.AddCalenderEventDTO;
import com.lifeos.student.dto.CalenderEventResponseDTO;
import com.lifeos.student.dto.UpdateCalenderEventDTO;
import com.lifeos.student.entity.CalenderEvent;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.CalenderEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CalenderService {

    private final CalenderEventRepository calendarRepository;
    private final UserService userService;

    public CalenderService(CalenderEventRepository calendarRepository,
                            UserService userService) {
        this.calendarRepository = calendarRepository;
        this.userService = userService;
    }

    public CalenderEventResponseDTO addEvent(AddCalenderEventDTO request,
                                             String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        CalenderEvent event = CalenderEvent.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .eventDate(request.getEventDate())
                .eventType(request.getEventType())
                .user(user)
                .build();

        calendarRepository.save(event);

        return mapToDTO(event);
    }

    public List<CalenderEventResponseDTO> getAllEvents(String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        return calendarRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public CalenderEventResponseDTO getEvent(UUID id,
                                             String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        CalenderEvent event = calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return mapToDTO(event);
    }

    public CalenderEventResponseDTO updateEvent(UUID id,
                                                UpdateCalenderEventDTO request,
                                                String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        CalenderEvent event = calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setEventType(request.getEventType());

        calendarRepository.save(event);

        return mapToDTO(event);
    }

    public void deleteEvent(UUID id,
                            String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        CalenderEvent event = calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        calendarRepository.delete(event);
    }

    private CalenderEventResponseDTO mapToDTO(CalenderEvent event) {

        return CalenderEventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .eventType(event.getEventType())
                .build();
    }
}
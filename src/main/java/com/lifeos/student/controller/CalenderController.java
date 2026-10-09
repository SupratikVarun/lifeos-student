package com.lifeos.student.controller;

import com.lifeos.student.dto.AddCalenderEventDTO;
import com.lifeos.student.dto.CalenderEventResponseDTO;
import com.lifeos.student.dto.UpdateCalenderEventDTO;
import com.lifeos.student.service.CalenderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/calendar")
public class CalenderController {

    private final CalenderService calendarService;

    public CalenderController(CalenderService calendarService) {
        this.calendarService = calendarService;
    }

    // Add Event
    @PostMapping
    public CalenderEventResponseDTO addEvent(
            @RequestBody AddCalenderEventDTO request,
            Authentication authentication) {

        return calendarService.addEvent(
                request,
                authentication.getName()
        );
    }

    // Get All Events
    @GetMapping
    public List<CalenderEventResponseDTO> getAllEvents(
            Authentication authentication) {

        return calendarService.getAllEvents(
                authentication.getName()
        );
    }

    // Get Event By ID
    @GetMapping("/{id}")
    public CalenderEventResponseDTO getEvent(
            @PathVariable UUID id,
            Authentication authentication) {

        return calendarService.getEvent(
                id,
                authentication.getName()
        );
    }

    // Update Event
    @PutMapping("/{id}")
    public CalenderEventResponseDTO updateEvent(
            @PathVariable UUID id,
            @RequestBody UpdateCalenderEventDTO request,
            Authentication authentication) {

        return calendarService.updateEvent(
                id,
                request,
                authentication.getName()
        );
    }

    // Delete Event
    @DeleteMapping("/{id}")
    public String deleteEvent(
            @PathVariable UUID id,
            Authentication authentication) {

        calendarService.deleteEvent(
                id,
                authentication.getName()
        );

        return "Event deleted successfully.";
    }
}
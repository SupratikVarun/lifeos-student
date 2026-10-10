package com.lifeos.student.search.controller;

import com.lifeos.student.search.dto.SearchResultDTO;
import com.lifeos.student.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public List<SearchResultDTO> search(
            @RequestParam String keyword,
            Authentication authentication
    ) {

        return searchService.search(
                keyword,
                authentication.getName()
        );

    }

}
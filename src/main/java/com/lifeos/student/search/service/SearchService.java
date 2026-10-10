package com.lifeos.student.search.service;

import com.lifeos.student.search.dto.SearchResultDTO;

import java.util.List;

public interface SearchService {

    List<SearchResultDTO> search(
            String keyword,
            String email
    );

}
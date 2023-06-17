package com.service.search.controller;

import com.service.search.enums.ApiType;
import com.service.search.service.SearchService;
import com.service.search.service.SearchServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SearchController {

    final private SearchServiceFactory searchServiceFactory;

    public SearchController(SearchServiceFactory searchServiceFactory){
        this.searchServiceFactory = searchServiceFactory;

    }
    @GetMapping("/v1/place")
    public List<String> searchPlace(String keyword){
        return null;
    }
}

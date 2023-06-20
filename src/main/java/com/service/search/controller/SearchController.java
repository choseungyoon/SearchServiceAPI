package com.service.search.controller;
import com.service.search.service.response.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchController {

    final private SearchService searchService;

    public SearchController(SearchService searchService){
        this.searchService = searchService;

    }
    @GetMapping("/v1/place")
    public void searchPlace(String keyword){
        this.searchService.searchPlaces(keyword,5);
    }
}

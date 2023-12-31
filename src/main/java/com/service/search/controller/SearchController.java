package com.service.search.controller;
import com.service.search.dto.QueryCount;
import com.service.search.dto.Place;
import com.service.search.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class SearchController {

    final private SearchService searchService;


    public SearchController(SearchService searchService){
        this.searchService = searchService;

    }
    @GetMapping("/v1/place")
    public List<Place> searchPlace(String keyword){
        return this.searchService.searchPlaces(keyword,5);
    }

    @GetMapping("/v1/place/top")
    public List<Map.Entry<String, Integer>> topKeyword(){
        return this.searchService.getTop10Queries();
    }

    @GetMapping("/api/test")
    public String test(){
        return "Hello world";
    }
}

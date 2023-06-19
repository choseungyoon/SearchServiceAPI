package com.service.search.controller;
import com.service.search.service.response.Place;
import com.service.search.service.response.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
public class SearchController {

    final private SearchService searchService;

    public SearchController(SearchService searchService){
        this.searchService = searchService;

    }
    @GetMapping("/v1/place")
    public Mono<List<Place>> searchPlace(String keyword){
        return this.searchService.searchPlaces(keyword);
    }
}

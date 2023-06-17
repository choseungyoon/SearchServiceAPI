package com.service.search.service.api;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GogglePlaceSearchProvider implements SearchApiProvider {

    private final RestTemplate restTemplate;
    private final String kakaoApiUrl = ""; // Google 검색 API URL

    public GogglePlaceSearchProvider(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> searchPlaces(String keyword) {
        // Kakao 검색 API를 호출하여 장소 검색
        return null;
    }
}

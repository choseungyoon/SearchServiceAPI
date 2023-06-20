package com.service.search.service;

import com.service.search.enums.ApiType;
import com.service.search.service.response.NaverApiResponse;
import com.service.search.service.response.Place;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NaverSearchApi  implements SearchApi {

    private final RestTemplate restTemplate;

    @Override
    public List<Place> searchPlace(String query, int size) {
        String url = "https://openapi.naver.com/v1/search/local.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id",  "QImLMIExKX0_uMERzAXF");
        headers.set("X-Naver-Client-Secret",  "73kabI9dw4");

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<NaverApiResponse> responseEntity = restTemplate.exchange(
                url + "?query=" + query + "&display=" + size, HttpMethod.GET, requestEntity, NaverApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            NaverApiResponse kakaoApiResponse = responseEntity.getBody();
            List<Place> places = new ArrayList<>();
            kakaoApiResponse.getItems().forEach(document -> {
                places.add(new Place(document.getTitle().replaceAll("<[^>]+>", ""), document.getRoadAddress()));
            });
            return places;
        } else {
            throw new RuntimeException("API 호출에 실패하였습니다.");
        }
    }

    @Override
    public ApiType getApiType() {
        return ApiType.NAVER;
    }
}

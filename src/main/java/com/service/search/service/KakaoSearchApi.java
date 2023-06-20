package com.service.search.service;

import com.service.search.enums.ApiType;
import com.service.search.service.response.KakaoApiResponse;

import com.service.search.service.response.Place;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class KakaoSearchApi implements SearchApi{

    private final RestTemplate restTemplate;

    @Override
    public List<Place> searchPlace(String query , int size) {
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " +  "a063f349c96bcea82f626f2f9ae698de");

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<KakaoApiResponse> responseEntity = restTemplate.exchange(
                url + "?query=" + query + "&size=" + size, HttpMethod.GET, requestEntity, KakaoApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            KakaoApiResponse kakaoApiResponse = responseEntity.getBody();
            List<Place> places = new ArrayList<>();
            kakaoApiResponse.getDocuments().forEach(document -> {
                places.add(new Place(document.getPlace_name(), document.getRoad_address_name()));
            });
            return places;
        } else {
            throw new RuntimeException("API 호출에 실패하였습니다.");
        }
    }

    @Override
    public ApiType getApiType() {
        return ApiType.KAKAO;
    }
}

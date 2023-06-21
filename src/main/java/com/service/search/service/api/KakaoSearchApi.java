package com.service.search.service.api;

import com.service.search.enums.ApiType;
import com.service.search.service.response.KakaoApiResponse;

import com.service.search.dto.Place;
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
    private final int MAX_RETRIES = 3;

    @Override
    public List<Place> searchPlace(String query , int size) {
        List<Place> places = new ArrayList<>();
        int retries = 0;
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " +  "a063f349c96bcea82f626f2f9ae698de");

        while (retries < MAX_RETRIES) {
            try {
                HttpEntity<?> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<KakaoApiResponse> responseEntity = restTemplate.exchange(
                        url + "?query=" + query + "&size=" + size, HttpMethod.GET, requestEntity, KakaoApiResponse.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    KakaoApiResponse kakaoApiResponse = responseEntity.getBody();
                    kakaoApiResponse.getDocuments().forEach(document -> {
                        places.add(new Place(document.getPlace_name(), document.getRoad_address_name()));
                    });
                    break;
                } else {
                    retries++;
                    if (retries >= MAX_RETRIES) {
                        throw new RuntimeException("Fail call kakao API");
                    }
                }
            }
            catch (Exception e){
                retries++;
                if (retries >= MAX_RETRIES) {
                    throw e; // Throw the exception if maximum retries reached
                }
            }

        }

        return places;
    }

    @Override
    public ApiType getApiType() {
        return ApiType.KAKAO;
    }
}

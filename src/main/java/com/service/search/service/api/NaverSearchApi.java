package com.service.search.service.api;

import com.service.search.enums.ApiType;
import com.service.search.service.response.NaverApiResponse;
import com.service.search.dto.Place;
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
    private final int MAX_RETRIES = 3;

    @Override
    public List<Place> searchPlace(String query, int size) {
        List<Place> places = new ArrayList<>();
        int retries = 0;
        String url = "https://openapi.naver.com/v1/search/local.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id",  "QImLMIExKX0_uMERzAXF");
        headers.set("X-Naver-Client-Secret",  "73kabI9dw4");

        while (retries < MAX_RETRIES) {
            try{
                HttpEntity<?> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<NaverApiResponse> responseEntity = restTemplate.exchange(
                        url + "?query=" + query + "&display=" + size, HttpMethod.GET, requestEntity, NaverApiResponse.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {

                    NaverApiResponse kakaoApiResponse = responseEntity.getBody();

                    kakaoApiResponse.getItems().forEach(document -> {
                        places.add(new Place(document.getTitle().replaceAll("<[^>]+>", ""), document.getRoadAddress()));
                    });
                    break;

                } else {
                    retries++;
                    if (retries >= MAX_RETRIES) {
                        throw new RuntimeException("Fail call naver API");
                    }
                }
            }catch (Exception e){
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
        return ApiType.NAVER;
    }
}

package com.service.search.service;

import com.service.search.enums.ApiType;
import com.service.search.service.response.NaverApiResponse;
import com.service.search.service.response.Place;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class NaverSearchApi  {

    private final WebClient webClient;

    public NaverSearchApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://openapi.naver.com/v1").build();
    }

//    @Override
//    public List<Place> searchPlace(String keyword) {
//        List<Place> places = new ArrayList<>();
//
//        searchKeyword(keyword)
//                .subscribe(response -> {
//                    // API 응답 처리
//                    List<NaverApiResponse.Item> items = response.getItems();
//                    for (NaverApiResponse.Item item : items) {
//                        // 각 문서(document)에 대한 처리 로직 작성
//                        places.add(new Place(item.getTitle(), item.getRoadAddress()));
//                        System.out.println(item.getTitle() + " " + item.getRoadAddress());
//                    }
//                }, error -> {
//                    // API 호출 실패 처리
//                    throw new RuntimeException("API 호출에 실패하였습니다.", error);
//                });
//
//        return places;
//    }

    public Mono<NaverApiResponse> searchKeyword(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/local.json")
                        .queryParam("query", query)
                        .queryParam("display", 5)
                        .build())
                .header("X-Naver-Client-Id",  "QImLMIExKX0_uMERzAXF")
                .header("X-Naver-Client-Secret",  "73kabI9dw4")
                .retrieve()
                .bodyToMono(NaverApiResponse.class);
    }

//    @Override
//    public ApiType getApiType() {
//        return ApiType.NAVER;
//    }
}

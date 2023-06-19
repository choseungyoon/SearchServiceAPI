package com.service.search.service;

import com.service.search.enums.ApiType;
import com.service.search.service.response.KakaoApiResponse;
import com.service.search.service.response.Place;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class KakaoSearchApi {

    private final WebClient webClient;

    public KakaoSearchApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://dapi.kakao.com/v2/local").build();
    }

//    @Override
//    public List<Place> searchPlace(String keyword) {
//
//        List<Place> places = new ArrayList<>();
//
//        searchKeyword(keyword)
//                .subscribe(response -> {
//                    // API 응답 처리
//                    List<KakaoApiResponse.Document> documents = response.getDocuments();
//                    for (KakaoApiResponse.Document document : documents) {
//                        // 각 문서(document)에 대한 처리 로직 작성
//                        places.add(new Place(document.getPlace_name(), document.getRoad_address_name()));
//                        System.out.println(document.getPlace_name() + " " +document.getRoad_address_name());
//                    }
//                }, error -> {
//                    // API 호출 실패 처리
//                    throw new RuntimeException("API 호출에 실패하였습니다.", error);
//                });
//
//        return places;
//    }

    public Mono<KakaoApiResponse> searchKeyword(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/keyword.json")
                        .queryParam("query", query)
                        .queryParam("size", 5)
                        .build())
                .header("Authorization", "KakaoAK " + "a063f349c96bcea82f626f2f9ae698de")
                .retrieve()
                .bodyToMono(KakaoApiResponse.class);
    }

//    @Override
//    public ApiType getApiType() {
//        return ApiType.KAKAO;
//    }


}

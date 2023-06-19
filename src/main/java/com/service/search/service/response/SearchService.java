package com.service.search.service.response;

import com.service.search.service.KakaoSearchApi;
import com.service.search.service.NaverSearchApi;
import com.service.search.service.SearchServiceFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final KakaoSearchApi kakaoSearchApi;
    private final NaverSearchApi naverSearchApi;

    public SearchService(KakaoSearchApi kakaoSearchApi , NaverSearchApi naverSearchApi) {
        this.kakaoSearchApi = kakaoSearchApi;
        this.naverSearchApi = naverSearchApi;
    }

    public Mono<List<Place>> searchPlaces(String query) {
        Mono<KakaoApiResponse> kakaoApiResult = kakaoSearchApi.searchKeyword(query);
        Mono<NaverApiResponse> naverApiResult = naverSearchApi.searchKeyword(query);

        return Mono.zip(kakaoApiResult, naverApiResult)
                .map(result -> {
                    List<Place> places = new ArrayList<>();

                    List<KakaoApiResponse.Document> kakaoDocuments = result.getT1().getDocuments();
                    for (KakaoApiResponse.Document document : kakaoDocuments) {
                        Place place = new Place(document.getPlace_name(), document.getRoad_address_name());
                        places.add(place);
                    }

                    List<NaverApiResponse.Item> naverItems = result.getT2().getItems();
                    for (NaverApiResponse.Item item : naverItems) {
                        Place place = new Place(item.getTitle(), item.getRoadAddress());
                        places.add(place);
                    }

                    return places;
                });
    }

}

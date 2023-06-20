package com.service.search.service.response;

import com.service.search.enums.ApiType;
import com.service.search.service.SearchServiceFactory;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SearchService {

    private final SearchServiceFactory searchServiceFactory;

    public SearchService(SearchServiceFactory searchServiceFactory) {
        this.searchServiceFactory = searchServiceFactory;
    }

    public void searchPlaces(String query, int size) {

        List<Place> kakaoResult = searchServiceFactory.getServicePlaceApi(ApiType.KAKAO).searchPlace(query,size);
        List<Place> naverResult = searchServiceFactory.getServicePlaceApi(ApiType.NAVER).searchPlace(query,size);

    }

}

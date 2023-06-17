package com.service.search.service;

import com.service.search.enums.ApiType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SearchServiceFactory {

    private final Map<ApiType, SearchService> searchServiceMap = new HashMap<>();

    public SearchServiceFactory(List<SearchService> searchServices) {
        searchServices.forEach(s -> searchServiceMap.put(s.getApiType(), s));
    }

    public SearchService getServicePlaceApi(ApiType apiType) {
        return searchServiceMap.get(apiType);
    }

}

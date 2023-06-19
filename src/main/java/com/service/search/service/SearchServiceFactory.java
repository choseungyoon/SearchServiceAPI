package com.service.search.service;

import com.service.search.enums.ApiType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SearchServiceFactory {

    private final Map<ApiType, SearchApi> searchServiceMap = new HashMap<>();

    public SearchServiceFactory(List<SearchApi> searchApis) {
        searchApis.forEach(s -> searchServiceMap.put(s.getApiType(), s));
    }

    public SearchApi getServicePlaceApi(ApiType apiType) {
        return searchServiceMap.get(apiType);
    }

}

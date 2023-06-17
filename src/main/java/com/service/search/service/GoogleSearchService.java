package com.service.search.service;

import com.service.search.enums.ApiType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleSearchService implements SearchService{
    @Override
    public List<String> searchPlace(String keyWord) {
        return null;
    }

    @Override
    public ApiType getApiType() {
        return ApiType.GOOGLE;
    }
}

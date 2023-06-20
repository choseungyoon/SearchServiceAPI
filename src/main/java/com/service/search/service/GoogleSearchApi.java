package com.service.search.service;

import com.service.search.enums.ApiType;
import com.service.search.service.response.Place;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleSearchApi implements SearchApi {
    @Override
    public List<Place> searchPlace(String keyWord , int size) {
        return null;
    }

    @Override
    public ApiType getApiType() {
        return ApiType.GOOGLE;
    }
}

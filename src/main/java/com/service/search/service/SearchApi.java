package com.service.search.service;
import com.service.search.enums.ApiType;
import com.service.search.service.response.Place;

import java.util.List;


public interface SearchApi {
    List<Place> searchPlace(String keyWord);
    ApiType getApiType();
}

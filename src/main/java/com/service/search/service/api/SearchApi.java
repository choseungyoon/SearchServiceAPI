package com.service.search.service.api;
import com.service.search.enums.ApiType;
import com.service.search.dto.Place;

import java.util.List;


public interface SearchApi {
    List<Place> searchPlace(String query, int size);
    ApiType getApiType();
}

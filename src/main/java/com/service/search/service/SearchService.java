package com.service.search.service;
import com.service.search.enums.ApiType;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SearchService {
    List<String> searchPlace(String keyWord);
    ApiType getApiType();
}

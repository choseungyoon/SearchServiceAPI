package com.service.search.service.api;

import java.util.List;

public interface SearchApiProvider {
    List<String> searchPlaces(String keyword);
}


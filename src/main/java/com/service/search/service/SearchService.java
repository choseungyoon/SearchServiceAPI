package com.service.search.service;

import com.service.search.enums.ApiType;
import com.service.search.service.response.Place;
import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final SearchServiceFactory searchServiceFactory;

    private final ConcurrentHashMap<String, AtomicInteger> queryCountMap;


    public SearchService(SearchServiceFactory searchServiceFactory) {
        this.searchServiceFactory = searchServiceFactory;
        queryCountMap = new ConcurrentHashMap<>();
    }

    public List<Place> searchPlaces(String query, int size) {

        incrementQueryCount(query);

        List<Place> returnValue = new ArrayList<>();

        for (ApiType apiType : ApiType.values()) {
            // apiType 변수를 사용하여 enum의 모든 값에 액세스할 수 있습니다.
            List<Place> resultPlaces = searchServiceFactory.getServicePlaceApi(apiType).searchPlace(query,size);
            returnValue =  mergeResults(returnValue,resultPlaces);
        }

        return returnValue;
    }

    public List<Map.Entry<String, Integer>> getTop10Queries() {
        // Create a list of entries from the queryCountMap
        List<Map.Entry<String, AtomicInteger>> entries = new ArrayList<>(queryCountMap.entrySet());

        // Sort the entries based on the query count
        entries.sort(Comparator.comparingInt(entry -> -entry.getValue().get()));

        // Retrieve the top 10 queries (or less if there are fewer than 10)
        List<Map.Entry<String, Integer>> top10Queries = entries.stream()
                .limit(10)
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toList());

        return top10Queries;
    }


    private void incrementQueryCount(String query) {
        queryCountMap.computeIfAbsent(query, key -> new AtomicInteger(0)).incrementAndGet();
    }


    private List<Place> mergeResults(List<Place> targetResult, List<Place> newResult) {
        List<Place> mergedResults = new ArrayList<>();

        // 1. 기본적으로 Kakao 결과를 기준 순서로 사용
        mergedResults.addAll(targetResult);

        for (Place naverPlace : newResult) {
            boolean foundMatch = false;

            for (Place kakaoPlace : targetResult) {
                // 공백 제거 후 placeName 비교
                String kakaoPlaceName = kakaoPlace.getName().replaceAll("\\s+", "");
                String naverPlaceName = naverPlace.getName().replaceAll("\\s+", "");

                // Levenshtein 거리를 이용한 문자열 유사도 비교
                int nameDistance = LevenshteinDistance.getDefaultInstance().apply(kakaoPlaceName, naverPlaceName);

                // address 비교
                boolean addressMatch = kakaoPlace.getAddress().equals(naverPlace.getAddress());

                if (nameDistance <= 2 || addressMatch) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                mergedResults.add(naverPlace);
            }
        }

        // 2. 만약 카카오 결과 네이버 결과 모두 존재하는 경우 상위로 정렬
        mergedResults.sort(Comparator.comparingInt(place -> {
            if (targetResult.contains(place) && newResult.contains(place)) {
                return 0;  // 카카오와 네이버에 모두 존재하는 경우
            } else if (targetResult.contains(place)) {
                return -1; // 카카오에만 존재하는 경우
            } else {
                return 1;  // 네이버에만 존재하는 경우
            }
        }));

        return mergedResults;
    }

}

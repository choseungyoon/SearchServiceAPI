package com.service.search.service;

import com.service.search.enums.ApiType;
import com.service.search.dto.Place;
import com.service.search.dto.QueryCount;
import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

@Service
public class SearchService {

    private Jedis jedis; // Assume Jedis instance is initialized and connected to Redis
    private final SearchServiceFactory searchServiceFactory;

    public SearchService(SearchServiceFactory searchServiceFactory){
        this.searchServiceFactory = searchServiceFactory;
        jedis = new Jedis();
    }

    public List<Place> searchPlaces(String query, int size) {

        jedis.zincrby("queryCounts", 1, query);

        List<Place> returnValue = new ArrayList<>();

        for (ApiType apiType : ApiType.values()) {
            // apiType 변수를 사용하여 enum의 모든 값에 액세스
            List<Place> resultPlaces = searchServiceFactory.getServicePlaceApi(apiType).searchPlace(query,size);
            returnValue =  mergeResults(returnValue,resultPlaces);
        }

        return returnValue;
    }

    public List<QueryCount> getTop10Queries() {
        List<QueryCount> top10Queries = new ArrayList();

        try {
            // Retrieve the top 10 queries with their counts
            Set<Tuple> querySet = jedis.zrevrangeWithScores("queryCounts", 0, 9);

            // Process the querySet and extract the queries and counts
            for (Tuple tuple : querySet) {
                String query = tuple.getElement();
                double count = tuple.getScore();
                top10Queries.add(new QueryCount(query, count));
            }
        } catch (Exception e) {
            // Handle exceptions or logging as per your application's error handling strategy
        }

        return top10Queries;
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

package com.service.search.service.response;

import lombok.Getter;

import java.util.List;

@Getter
public class KakaoApiResponse {
    private List<Document> documents;
    private Meta meta;

    // getter and setter

    @Getter
    public static class Document {
        private String address_name;
        private String category_group_code;
        private String category_group_name;
        private String category_name;
        private String distance;
        private String id;
        private String phone;
        private String place_name;
        private String place_url;
        private String road_address_name;
        private String x;
        private String y;

        // getter and setter
    }

    public static class Meta {
        private boolean is_end;
        private int pageable_count;
        private SameName same_name;
        private int total_count;

        // getter and setter
    }

    public static class SameName {
        private String keyword;
        private List<String> region;
        private String selected_region;

        // getter and setter
    }
}

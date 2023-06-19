package com.service.search.service.response;

import lombok.Getter;
import java.util.List;

@Getter
public class NaverApiResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    // getter와 setter 생략
    @Getter
    public static class Item {
        private String title;
        private String link;
        private String category;
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private String mapx;
        private String mapy;

        // getter와 setter 생략
    }
}

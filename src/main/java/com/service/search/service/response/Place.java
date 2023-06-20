package com.service.search.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.text.similarity.LevenshteinDistance;

@Getter
@AllArgsConstructor
public class Place {
    private String name;
    private String address;

}

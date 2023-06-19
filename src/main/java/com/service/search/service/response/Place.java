package com.service.search.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.text.similarity.LevenshteinDistance;

@Getter
@AllArgsConstructor
public class Place {
    private String name;
    private String address;

    public boolean isSameAs(Place otherPlace) {
        // Remove whitespace and compare names
        String thisName = this.getName().replaceAll("\\s+", "").replaceAll("<[^>]*>", "");
        String otherName = otherPlace.getName().replaceAll("\\s+", "").replaceAll("<[^>]*>", "");

        if (thisName.equalsIgnoreCase(otherName)) {
            return true;
        }

        // Calculate string similarity using Levenshtein distance
        int similarity = LevenshteinDistance.getDefaultInstance().apply(thisName, otherName);
        double similarityRatio = (double) similarity / Math.max(thisName.length(), otherName.length());

        // Check if similarity ratio is above a threshold (e.g., 0.8)
        if (similarityRatio > 0.8) {
            return true;
        }

        // Compare location using coordinates (x, y)
        // You can implement the logic to compare the location here
        // For simplicity, let's assume they are different
        return false;
    }

}

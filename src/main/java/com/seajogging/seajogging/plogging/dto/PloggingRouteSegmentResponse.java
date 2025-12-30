package com.seajogging.seajogging.plogging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PloggingRouteSegmentResponse {
    private Integer sequenceOrder;
    private String destinationName;
    private Integer trashGrade;
    private String description;
    private String imageUrl1;
    private String imageUrl2;
}
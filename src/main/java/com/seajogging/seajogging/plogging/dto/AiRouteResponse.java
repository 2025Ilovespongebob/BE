package com.seajogging.seajogging.plogging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class AiRouteResponse {
    private String status;
    private Meta meta;
    private List<RouteDetail> routes;

    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private String weather;
        private Integer total_segments;
        private Double distance;
        private Integer spotCount;
    }

    @Getter @Builder
    @AllArgsConstructor
    public static class RouteDetail {
        private Integer segment_id;
        private String type;
        private String destination_name;
        private Integer trash_grade;
        private List<PloggingSaveRequest.PathPoint> path;
        private List<String> scrapedImages;
    }
}

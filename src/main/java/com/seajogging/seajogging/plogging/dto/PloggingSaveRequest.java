package com.seajogging.seajogging.plogging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PloggingSaveRequest {
    // 1. AI가 보내주는 최상위 status, meta 받기
    @JsonProperty("status")
    private String status;

    @JsonProperty("meta")
    private Map<String, Object> meta;

    // 2. routes 리스트 (여기가 핵심!)
    @JsonProperty("routes")
    private List<RouteSegmentRequest> routes;

    // 기존 저장용 필드들은 냅둬도 됨 (null로 들어와도 무관)
    private Double distance;
    private Integer duration;
    private Integer trashCount;
    private Double carbonReduction;

    @Getter
    @NoArgsConstructor
    public static class RouteSegmentRequest {
        @JsonProperty("segment_id")
        private Integer segment_id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("destination_name") // "destination_name" 매핑
        private String destination_name;

        @JsonProperty("trash_grade")
        private Integer trash_grade;

        @JsonProperty("description")
        private String description;

        @JsonProperty("path")
        private List<PathPoint> path;
    }

    @Getter
    @NoArgsConstructor
    public static class PathPoint {
        @JsonProperty("x")
        private Double x;

        @JsonProperty("y")
        private Double y;
    }
}
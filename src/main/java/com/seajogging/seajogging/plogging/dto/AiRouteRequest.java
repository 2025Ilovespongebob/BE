package com.seajogging.seajogging.plogging.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "AI 경로 생성을 위한 위치 정보 요청")
public class AiRouteRequest {
    @Getter
    @Setter
    @Schema(description = "현재 위치 위도", example = "37.123456")
    private Double latitude;

    @Getter
    @Setter
    @Schema(description = "현재 위치 경도", example = "127.123456")
    private Double longitude;
}
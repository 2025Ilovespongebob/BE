package com.seajogging.seajogging.plogging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "메인 리포트 응답 데이터")
public class DailyActivityReport {
    @Schema(description = "오늘 완료한 플로깅 횟수", example = "3")
    private Integer todayCount;

    @Schema(description = "오늘 총 이동 거리 (m 단위)", example = "5200")
    private Integer todayDistance;

    @Schema(description = "오늘 총 소요 시간 (HH:mm:ss)", example = "01:20:30")
    private String todayTime;

    @Schema(description = "오늘 주운 총 쓰레기 개수", example = "15")
    private Integer todayTrashCount;

    @Schema(description = "오늘의 탄소 저감량 (kg)", example = "12.5")
    private Double carbonReduction;

    @Schema(description = "최근 7일간의 주간 상태 기록")
    @JsonProperty("WeeklyRecords")
    private List<WeeklyStatusResponse> weeklyRecords;

    @Schema(description = "오늘 활동한 상세 경로 리스트")
    private List<PloggingRouteSegmentResponse> todayRoutes;
}
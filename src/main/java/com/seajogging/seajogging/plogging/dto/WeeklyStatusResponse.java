package com.seajogging.seajogging.plogging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeeklyStatusResponse {
    private String dayOfWeek; // 요일 정보
    private String status; // 상태 (완료 / 미완료 / None)
    private Integer trashCount; // 요일별 쓰레기 개수
}

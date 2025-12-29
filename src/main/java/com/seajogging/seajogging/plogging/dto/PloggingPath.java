package com.seajogging.seajogging.plogging.dto;

import lombok.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PathResponse {
    private Long pathId; // 경로 고유 ID
    private List<Coordinate> coordinates; // 추천 경로를 구성하는 위경도 좌표 리스트
    private Double trashDensityIndex; // 해당 지역의 쓰레기 밀집 예상 지수
    private Double totalDistance; // 추천 경로의 총 이동 거리
    private Integer estimatedTime; // 추천 경로 주행 시 예상 소요 시간
    private Coordinate destination; // 경로의 최종 목적지 좌표
    private List<Coordinate> trashHotspots; // 경로 중 쓰레기가 특히 많이 몰려있을 것으로 예상되는 주요 지점 리스트
    private String environmentalFactor; // AI가 분석한 환경 정보 요약
}
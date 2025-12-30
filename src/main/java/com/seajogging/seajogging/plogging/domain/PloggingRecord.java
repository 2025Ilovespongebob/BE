package com.seajogging.seajogging.plogging.domain;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "PLOGGING_RECORD")
public class PloggingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private Long userId; // 항상 1L로 저장
    private LocalDate activityDate; // 날짜
    private Double distance; // 거리 (km)
    private Integer duration; // 소요 시간 (분)
    private String status; // "COMPLETED", "FAILED"
    private Integer trashCount; // 주은 쓰레기 수
    private Double carbonReduction; // 탄소 절감량

    @OneToMany(mappedBy = "ploggingRecord", cascade = CascadeType.ALL)
    private List<PloggingRouteSegment> routeSegments;
}
package com.seajogging.seajogging.plogging.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "PLOGGING_ROUTE_SEGMENT")
public class PloggingRouteSegment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private PloggingRecord ploggingRecord;

    private Integer sequenceOrder; // segment_id 저장
    private String destinationName;
    private Integer trashGrade;
    private String description;

    private String imageUrl1;
    private String imageUrl2;

    // 연관관계 편의 메서드
    public void setPloggingRecord(PloggingRecord record) {
        this.ploggingRecord = record;
    }
}
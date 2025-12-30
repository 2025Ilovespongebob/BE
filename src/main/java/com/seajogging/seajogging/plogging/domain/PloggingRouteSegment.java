package com.seajogging.seajogging.plogging.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자는 필수!
@AllArgsConstructor
@Table(name = "PLOGGING_ROUTE_SEGMENT")
public class PloggingRouteSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 전체 기록(Record)과 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private PloggingRecord ploggingRecord;

    private Integer sequenceOrder;
    private String destinationName;
    private Integer trashGrade;
    private String description;

    private String imageUrl1;
    private String imageUrl2;
}
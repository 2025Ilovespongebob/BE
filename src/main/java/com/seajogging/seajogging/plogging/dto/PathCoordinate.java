package com.seajogging.seajogging.plogging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    private Double lat; // 위도
    private Double lng; // 경도
}
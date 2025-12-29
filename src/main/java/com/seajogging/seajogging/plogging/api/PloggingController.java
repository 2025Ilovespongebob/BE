package com.seajogging.seajogging.plogging.application;

import com.seajogging.seajogging.plogging.dto.PathResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plogging")
@RequiredArgsConstructor
public class PloggingController {

    private final PloggingService ploggingService;

    @GetMapping("/recommend-path")
    public ResponseEntity<PathResponse> getRecommendPath(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double distance) {

        PathResponse response = ploggingService.calculatePath(latitude, longitude, distance);
        return ResponseEntity.ok(response);
    }
}
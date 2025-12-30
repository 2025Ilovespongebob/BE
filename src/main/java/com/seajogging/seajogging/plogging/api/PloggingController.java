package com.seajogging.seajogging.plogging.api;

import com.seajogging.seajogging.plogging.application.PloggingService;
import com.seajogging.seajogging.plogging.dto.AiRouteRequest;
import com.seajogging.seajogging.plogging.dto.AiRouteResponse;
import com.seajogging.seajogging.plogging.dto.DailyActivityReport;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/plogging")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PloggingController {

    private final PloggingService ploggingService;

    @Operation(summary = "메인 리포트 조회", description = "오늘의 플로깅 활동 요약 데이터를 조회합니다.")
    @GetMapping("/main-report")
    public ResponseEntity<DailyActivityReport> getDailyActivityReport() {
        return ResponseEntity.ok(ploggingService.getMainReport());
    }

    @Operation(summary = "AI 경로 생성", description = "사용자의 현재 위도와 경도를 바탕으로 추천 경로를 생성합니다.")
    @PostMapping("/generate-course")
    public AiRouteResponse generateCourse(@RequestBody AiRouteRequest request) {
        return ploggingService.fetchAndEnrichAiRoute(request);
    }
}
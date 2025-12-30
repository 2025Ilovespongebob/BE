package com.seajogging.seajogging.plogging.application;

import com.seajogging.seajogging.plogging.domain.PloggingRecord;
import com.seajogging.seajogging.plogging.domain.PloggingRecordRepository;
import com.seajogging.seajogging.plogging.domain.PloggingRouteSegment;
import com.seajogging.seajogging.plogging.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PloggingService {

    private final PloggingRecordRepository ploggingRecordRepository;
    private final ScrapingService scrapingService;
    private final RestClient restClient;

    private final String AI_API_URL = "http://10.129.57.77:8000/api/v1/generate-course";

    public AiRouteResponse fetchAndEnrichAiRoute(AiRouteRequest request) {
        try {
            double lat = (request.getLatitude() != null) ? request.getLatitude() : 0.0;
            double lon = (request.getLongitude() != null) ? request.getLongitude() : 0.0;

            // 1. 주문서 작성 (3000, 2)
            Map<String, Object> aiRequestMap = Map.of(
                    "user_location", Map.of("x", lon, "y", lat),
                    "search_radius_meter", getRadius(),
                    "target_spot_count", getCount()
            );

            // 2. AI 서버 호출 (8000번 포트)
            PloggingSaveRequest aiRawData = restClient.post()
                    .uri(AI_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(aiRequestMap)
                    .retrieve()
                    .body(PloggingSaveRequest.class);

            // 3. AI가 응답한 데이터를 가공해서 반환 (이미지 추가 로직 포함)
            return getEnrichedRoutes(aiRawData);

        } catch (Exception e) {
            e.printStackTrace();
            return AiRouteResponse.builder()
                    .status("error")
                    .meta(AiRouteResponse.Meta.builder().weather("AI 서버 통신 실패").total_segments(0).build())
                    .routes(new ArrayList<>())
                    .build();
        }
    }

    public AiRouteResponse getEnrichedRoutes(PloggingSaveRequest aiRequest) { // 여기서 aiRequest를 받음!
        if (aiRequest == null || aiRequest.getRoutes() == null) {
            return AiRouteResponse.builder()
                    .status("error")
                    .meta(AiRouteResponse.Meta.builder().weather("데이터 없음").total_segments(0).build())
                    .routes(new ArrayList<>())
                    .build();
        }

        // 1. 경로 세그먼트 가공 (AI 데이터 기반)
        List<AiRouteResponse.RouteDetail> enrichedRoutes = aiRequest.getRoutes().stream()
                .map(route -> {
                    List<String> images = new ArrayList<>();
                    if (route.getDestination_name() != null && !route.getDestination_name().equals("복귀")) {
                        images = scrapingService.scrapeImages(route.getDestination_name());
                    }
                    return AiRouteResponse.RouteDetail.builder()
                            .segment_id(route.getSegment_id())
                            .type(route.getType())
                            .destination_name(route.getDestination_name())
                            .trash_grade(route.getTrash_grade())
                            .path(route.getPath())
                            .scrapedImages(images)
                            .build();
                }).collect(Collectors.toList());

        // 2. AI가 결정한 '진짜' 반경과 개수 꺼내기 (핵심 질문 해결!)
        // aiRequest에 해당 필드가 있다면 꺼내고, 없으면 0으로 처리
        Double aiDistance = (aiRequest.getDistance() != null) ? aiRequest.getDistance() : 0.0;
        Integer aiSpotCount = (aiRequest.getTrashCount() != null) ? aiRequest.getTrashCount() : 0;

        // 3. 최종 응답 조립
        String weather = (aiRequest.getMeta() != null && aiRequest.getMeta().containsKey("weather"))
                ? aiRequest.getMeta().get("weather").toString() : "정보 없음";

        return AiRouteResponse.builder()
                .status("success")
                .meta(AiRouteResponse.Meta.builder()
                        .weather(weather)
                        .total_segments(enrichedRoutes.size())
                        .distance(aiDistance)
                        .spotCount(aiSpotCount)
                        .build())
                .routes(enrichedRoutes)
                .build();
    }

    private int getRadius() { return 3000; }
    private int getCount() { return 2; }

    @Transactional(readOnly = true)
    public DailyActivityReport getMainReport() {
        // 현재 로그인 기능이 없으므로 임시로 1L 사용
        Long userId = 1L;
        java.time.LocalDate today = java.time.LocalDate.now();

        // 오늘 기록들 가져오기
        List<com.seajogging.seajogging.plogging.domain.PloggingRecord> todayRecords =
                ploggingRecordRepository.findByUserIdAndActivityDate(userId, today);

        // 통계 계산
        int totalCount = todayRecords.size();
        int totalDistance = todayRecords.stream().mapToInt(r -> (int)(r.getDistance() * 1000)).sum();
        int totalTrash = todayRecords.stream().mapToInt(r -> r.getTrashCount()).sum();
        double totalCarbon = todayRecords.stream().mapToDouble(r -> r.getCarbonReduction()).sum();

        // 시간(duration) 합산 및 포맷팅
        int totalSeconds = todayRecords.stream().mapToInt(r -> r.getDuration()).sum();

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        // DTO 반환
        return DailyActivityReport.builder()
                .todayCount(totalCount)
                .todayDistance(totalDistance)
                .todayTime(formattedTime)
                .todayTrashCount(totalTrash)
                .carbonReduction(totalCarbon)
                .weeklyRecords(new ArrayList<>())
                .todayRoutes(new ArrayList<>())
                .build();
    }

    @Transactional
    public void savePloggingResult(PloggingSaveRequest request) {
        PloggingRecord record = PloggingRecord.builder()
                .userId(1L)
                .activityDate(java.time.LocalDate.now())
                .distance(request.getDistance() != null ? request.getDistance() : 0.0)
                .duration(request.getDuration() != null ? request.getDuration() : 0)
                .trashCount(request.getTrashCount() != null ? request.getTrashCount() : 0)
                .carbonReduction(request.getCarbonReduction() != null ? request.getCarbonReduction() : 0.0)
                .status("COMPLETED")
                .build();

        if (request.getRoutes() != null) {
            List<PloggingRouteSegment> segments = request.getRoutes().stream()
                    .map(routeDto -> PloggingRouteSegment.builder()
                            .ploggingRecord(record)
                            .sequenceOrder(routeDto.getSegment_id())
                            .destinationName(routeDto.getDestination_name())
                            // 💡 8.13.50 에러 해결: String.valueOf 삭제하고 Integer 그대로 전달!
                            .trashGrade(routeDto.getTrash_grade())
                            .build())
                    .collect(Collectors.toList());

            record.setRouteSegments(segments);
        }

        ploggingRecordRepository.save(record);
    }
}
package com.seajogging.seajogging.plogging.application;
import java.util.List;

public interface ScrapingService {
    List<String> scrapeImages(String keyword); // 키워드로 이미지 URL 리스트 반환
}
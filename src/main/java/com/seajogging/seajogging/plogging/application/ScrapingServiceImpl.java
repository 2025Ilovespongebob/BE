package com.seajogging.seajogging.plogging.application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingServiceImpl implements ScrapingService {

    @Override
    public List<String> scrapeImages(String keyword) {
        List<String> imageUrls = new ArrayList<>();

        try {
            // 1. 검색어 인코딩 (한글 키워드 깨짐 방지)
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String searchUrl = "https://search.naver.com/search.naver?where=image&query=" + encodedKeyword;

            // 2. 네이버 HTML 가져오기
            Document doc = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
                    .timeout(5000)
                    .get();

            // 3. 이미지 태그들 찾기
            Elements imgTags = doc.select("img");

            for (Element img : imgTags) {
                String src = img.attr("src");

                // 주소가 http로 시작하고, 실제 데이터가 들어있는 이미지 주소만 필터링
                if (src.startsWith("http") && !src.contains("static.naver.net")) {
                    imageUrls.add(src);
                }

                // 이미지 2개만 수집하면 종료
                if (imageUrls.size() >= 2) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // 에러 발생 시 기본 이미지 제공
            imageUrls.add("https://via.placeholder.com/150?text=No+Image");
        }

        // 만약 검색 결과가 하나도 없다면 실패 메시지용 이미지 추가
        if (imageUrls.isEmpty()) {
            imageUrls.add("https://via.placeholder.com/150?text=Search+Failed");
        }

        return imageUrls;
    }
}
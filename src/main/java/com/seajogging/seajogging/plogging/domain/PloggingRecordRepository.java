package com.seajogging.seajogging.plogging.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PloggingRecordRepository extends JpaRepository<PloggingRecord, Long> {
    // 특정 사용자의 특정 기간 기록 조회
    List<PloggingRecord> findByUserIdAndActivityDateBetween(Long userId, LocalDate start, LocalDate end);

    // 특정 사용자의 오늘 기록 조회
    List<PloggingRecord> findByUserIdAndActivityDate(Long userId, LocalDate date);
}
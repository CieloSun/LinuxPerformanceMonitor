package com.cielo.performance.Domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceDataRepository extends JpaRepository<PerformanceData, Integer> {
    public List<PerformanceData> findPerformanceDataByTimeBetweenOrderByTime(long startTime,long endTime);
}

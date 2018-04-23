package com.cielo.performance.Scheduling;

import com.cielo.performance.Service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private PerformanceService performanceService;
    @Scheduled(fixedRate = 5000)
    public void recordData() throws Exception{
        performanceService.printAll();
    }
}

package com.cielo.performance.Controller;

import Utils.PerformanceStructure;
import com.cielo.performance.Domain.PerformanceData;
import com.cielo.performance.Domain.PerformanceDataRepository;
import com.cielo.performance.Service.PerformanceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PerformanceController {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PerformanceDataRepository performanceDataRepository;
    @ApiOperation("get cpu usage")
    @GetMapping("cpu")
    public PerformanceStructure getCpuUsage() throws Exception{
        return new PerformanceStructure(System.currentTimeMillis(),performanceService.getCpuUsage());
    }
    @ApiOperation("get memory usage")
    @GetMapping("memory")
    public PerformanceStructure getMemusage() throws Exception{
        return new PerformanceStructure(System.currentTimeMillis(),performanceService.getMemoryUsage());
    }
    @ApiOperation("get io usage")
    @GetMapping("io")
    public PerformanceStructure getIOUsage() throws Exception{
        return new PerformanceStructure(System.currentTimeMillis(),performanceService.getIOUsage());
    }
    @ApiOperation("get net usage")
    @GetMapping("net")
    public PerformanceStructure getNetUsage() throws Exception{
        return new PerformanceStructure(System.currentTimeMillis(),performanceService.getNetUsage());
    }
    @ApiOperation("get all performance usage, response a json of performance data")
    @GetMapping("all")
    public PerformanceData getPerformanceData() throws Exception{
        return performanceService.getPerformanceData();
    }
    @ApiOperation("get all performance usage by time with long-type, response a json of performance data list")
    @GetMapping("history")
    public List<PerformanceData> getHistory(long startTime, long endTime) throws Exception{
        return performanceDataRepository.findPerformanceDataByTimeBetweenOrderByTime(startTime,endTime);
    }
}

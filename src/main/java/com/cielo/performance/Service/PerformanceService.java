package com.cielo.performance.Service;

import com.cielo.performance.Domain.PerformanceData;
import com.cielo.performance.Domain.PerformanceDataRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PerformanceService {
    private final Log log = LogFactory.getLog(PerformanceService.class);
    @Autowired
    private PerformanceDataRepository performanceDataRepository;

    public PerformanceData getUsage() {
        float cpuUsage = 0;
        Process process1, process2;
        Runtime runtime = Runtime.getRuntime();
        try {
            String command = "cat /proc/stat";
            long startTime = System.currentTimeMillis();
            process1 = runtime.exec(command);
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(process1.getInputStream()));
            String line;
            long idleCpuTime1 = 0, totalCpuTime1 = 0;
            while ((line = bufferedReader1.readLine()) != null) {
                if (line.startsWith("cpu")) {
                    line = line.trim();
                    log.info(line);
                    String[] temp = line.split("\\s+");
                    idleCpuTime1 = Long.parseLong(temp[4]);
                    for (String s : temp) {
                        if (!s.equals("cpu")) {
                            totalCpuTime1 += Long.parseLong(s);
                        }
                    }
                    log.info("IdleCpuTime: " + idleCpuTime1 + "," + "TotalCpuTime" + totalCpuTime1);
                    break;
                }
            }
            bufferedReader1.close();
            process1.destroy();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                log.error("CpuUsage休眠时发生InterruptedException. " + e.getMessage());
                log.error(sw.toString());
            }
            //第二次采集CPU时间
            long endTime = System.currentTimeMillis();
            process2 = runtime.exec(command);
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            long idleCpuTime2 = 0, totalCpuTime2 = 0;   //分别为系统启动后空闲的CPU时间和总的CPU时间
            while ((line = bufferedReader2.readLine()) != null) {
                if (line.startsWith("cpu")) {
                    line = line.trim();
                    log.info(line);
                    String[] temp = line.split("\\s+");
                    idleCpuTime2 = Long.parseLong(temp[4]);
                    for (String s : temp) {
                        if (!s.equals("cpu")) {
                            totalCpuTime2 += Long.parseLong(s);
                        }
                    }
                    log.info("IdleCpuTime: " + idleCpuTime2 + ", " + "TotalCpuTime" + totalCpuTime2);
                    break;
                }
            }
            if (idleCpuTime1 != 0 && totalCpuTime1 != 0 && idleCpuTime2 != 0 && totalCpuTime2 != 0) {
                cpuUsage = 1 - (float) (idleCpuTime2 - idleCpuTime1) / (float) (totalCpuTime2 - totalCpuTime1);
                log.info("本节点CPU使用率为: " + cpuUsage);
            }
            bufferedReader2.close();
            process2.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            log.error("CpuUsage发生InstantiationException. " + e.getMessage());
            log.error(sw.toString());
        }
        //TODO
        return new PerformanceData();
    }
}

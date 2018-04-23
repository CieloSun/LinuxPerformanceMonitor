package com.cielo.performance.Service;

import Utils.GenericPair;
import com.cielo.performance.Domain.PerformanceDataRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PerformanceService {
    private final static int INTERVAL_TIME = 100;
    private final Log log = LogFactory.getLog(PerformanceService.class);

    @Autowired
    private PerformanceDataRepository performanceDataRepository;


    public GenericPair<Long, Long> calculateIdleAndTotal() throws IOException {
        //空闲时间，总时间
        long idleCpuTime = -1, totalCpuTime = -1;
        //执行命令
        Process process = Runtime.getRuntime().exec("cat /proc/stat");
        //获取stat文件
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            //选取以CPU字样开头的行
            if (line.startsWith("cpu")) {
                line = line.trim();
                log.info(line);
                //以空格为分隔符
                String[] temp = line.split("\\s+");
                //获取第四个数据：空闲时间
                idleCpuTime = Long.parseLong(temp[4]);
                for (String s : temp) {
                    //获取总时间
                    if (!s.equals("cpu")) {
                        totalCpuTime += Long.parseLong(s);
                    }
                }
                break;
            }
        }
        bufferedReader.close();
        process.destroy();
        return new GenericPair(idleCpuTime, totalCpuTime);
    }

    public float getCpuUsage() throws IOException {
        float cpuUsage = -1;
        GenericPair<Long, Long> firstPair = calculateIdleAndTotal();
        try {
            Thread.sleep(INTERVAL_TIME);
        } catch (InterruptedException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            log.error(e.getMessage());
            log.error(sw.toString());
        }
        GenericPair<Long, Long> secondPair = calculateIdleAndTotal();
        long idleCpuTime1 = firstPair.getFirst();
        long totalCpuTime1 = firstPair.getSecond();
        long idleCpuTime2 = secondPair.getFirst();
        long totalCpuTime2 = secondPair.getSecond();
        //计算CPU使用率
        if (idleCpuTime1 != -1 && totalCpuTime1 != -1 && idleCpuTime2 != -1 && totalCpuTime2 != -1) {
            cpuUsage = 1 - (float) (idleCpuTime2 - idleCpuTime1) / (float) (totalCpuTime2 - totalCpuTime1);
        }
        return cpuUsage;
    }

    public float getMemoryUsage() throws IOException {
        float memoryUsage = -1;
        Process process = Runtime.getRuntime().exec("cat /proc/meminfo");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        long totalMemory = 0, freeMemory = 0;
        for (int i = 0; (line = bufferedReader.readLine()) != null && i < 2; ++i) {
            String[] temp = line.split("\\s+");
            if (temp[0].startsWith("MemTotal")) totalMemory = Long.parseLong(temp[1]);
            else if (temp[0].startsWith("MemFree")) freeMemory = Long.parseLong(temp[1]);
            memoryUsage = 1 - (float) freeMemory / (float) totalMemory;
        }
        return memoryUsage;
    }

    public float getIOUsage() throws IOException{
        float ioUsage=-1;
        Process process=Runtime.getRuntime().exec("iostat -dx");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        for(int i=0;(line=bufferedReader.readLine())!=null;++i){
            //从第四行开始读
            if(i>=4){
                String[] temp=line.split("\\s+");
                if(temp.length>1){
                    float util=Float.parseFloat(temp[temp.length-1]);
                    ioUsage=(ioUsage>util)?ioUsage:util;
                }
            }
        }
    }


}

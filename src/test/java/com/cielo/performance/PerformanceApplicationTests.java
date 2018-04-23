package com.cielo.performance;

import com.cielo.performance.Service.PerformanceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceApplicationTests {
    public final Log log=LogFactory.getLog(PerformanceService.class);
    @Autowired
    private PerformanceService performanceService;

    @Test
    public void contextLoads() throws IOException {

    }

}

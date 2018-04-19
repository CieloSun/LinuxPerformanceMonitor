package com.cielo.performance.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class PerformanceData {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private float cpuUsage;
    @Column
    private float memoryUsage;
    @Column
    private float IOUsage;
    @Column
    private float NetUsage;
    @Column
    private LocalDateTime time;

    public PerformanceData() {
    }

    public PerformanceData(float cpuUsage, float memoryUsage, float IOUsage, float netUsage) {

        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.IOUsage = IOUsage;
        NetUsage = netUsage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(float cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public float getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(float memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public float getIOUsage() {
        return IOUsage;
    }

    public void setIOUsage(float IOUsage) {
        this.IOUsage = IOUsage;
    }

    public float getNetUsage() {
        return NetUsage;
    }

    public void setNetUsage(float netUsage) {
        NetUsage = netUsage;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

package com.metricssimulator.metricssimulator.controller;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VMMetrics {

    private List<Integer> systemUnreachableGauge = new ArrayList<>();
    private List<Integer> instanceUnreachableGauge = new ArrayList<>();
    private AtomicInteger cpuUtilization = new AtomicInteger(0);
    private AtomicInteger networkInBytes = new AtomicInteger(0);
    private AtomicInteger networkOutBytes = new AtomicInteger(0);

    public VMMetrics(String vmId, MeterRegistry meterRegistry) {
        Gauge.builder("vm-" + vmId +".system-reachability-failed", systemUnreachableGauge, Collection::size)
                .description("Virtual Machine [" + vmId + " System Reachability Status")
                .register(meterRegistry);

        Gauge.builder("vm-" + vmId +".instance-reachability-failed", instanceUnreachableGauge, Collection::size)
                .description("Virtual Machine [" + vmId + " Instance Reachability Status")
                .register(meterRegistry);

        Gauge.builder("vm-" + vmId + ".cpuUtilization-utilization", cpuUtilization, AtomicInteger::intValue)
                .description("Virtual Machine [" + vmId + " CPU Utilization")
                .register(meterRegistry);

        Gauge.builder("vm-" + vmId +".network-in", networkInBytes, AtomicInteger::intValue)
                .description("Virtual Machine [" + vmId + " NetworkIn")
                .register(meterRegistry);

        Gauge.builder("vm-" + vmId +".network-out", networkOutBytes, AtomicInteger::intValue)
                .description("Virtual Machine [" + vmId + " NetworkOut")
                .register(meterRegistry);

    }

    void setSystemReachable(boolean isHealthy) {
        if(isHealthy) {
            systemUnreachableGauge.clear();
        } else {
            if(systemUnreachableGauge.size() == 0) systemUnreachableGauge.add(1);
        }
    }

    void setInstanceReachable(boolean isHealthy) {
        if(isHealthy) {
            instanceUnreachableGauge.clear();
        } else {
            if(instanceUnreachableGauge.size() == 0) instanceUnreachableGauge.add(1);
        }
    }

    void setInstanceCpuUtilization(Integer percentage) {
        cpuUtilization.set(percentage);
    }

    void setNetworkIn(Integer bytes) {
        networkInBytes.set(bytes);
    }

    void setNetworkOut(Integer bytes) {
        networkOutBytes.set(bytes);
    }
}

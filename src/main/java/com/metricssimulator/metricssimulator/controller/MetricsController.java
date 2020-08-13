package com.metricssimulator.metricssimulator.controller;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private MeterRegistry meterRegistry;

    private List<Integer> transitGatewayStatusUnavailable = new ArrayList<>();
    private List<Integer> globalProtectStatusDisconnected = new ArrayList<>();

    public MetricsController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("transitgateway.unavailable", transitGatewayStatusUnavailable, Collection::size)
                .description("Transit Gateway Unavailable Status")
                .register(meterRegistry);

        Gauge.builder("globalprotect.disconnected", globalProtectStatusDisconnected, Collection::size)
                .description("Global Protect Disconnected Status")
                .register(meterRegistry);
    }

    @GetMapping("/transitgateway-unavailable")
    public String transitgatewayUnavailable() {
        if(transitGatewayStatusUnavailable.size() == 0) {
            transitGatewayStatusUnavailable.add(1);
        }
        return "Transit Gateway status set to unavailable";
    }

    @GetMapping("/transitgateway-available")
    public String transitgatewayAvailable() {
        transitGatewayStatusUnavailable.clear();
        return "Transit Gateway status set to available";
    }

    @GetMapping("/globalprotect-disconnected")
    public String globalProtectAttached() {
        if(globalProtectStatusDisconnected.size() == 0) {
            globalProtectStatusDisconnected.add(1);
        }
        return "Global Protect status set to disconnected";
    }

    @GetMapping("/globalprotect-attached")
    public String globalProtectDisconnected() {
        globalProtectStatusDisconnected.clear();
        return "Global Protect status set to attached";
    }
}

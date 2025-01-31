package com.gravitee.example.discovery;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class ConsulServiceDiscovery implements ServiceDiscovery {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${spring.cloud.consul.discovery.serviceName}")
    private String targetService;

    @Override
    public List<ServiceInstanceRecord> getServiceInstances() {
        return discoveryClient
                .getServices()
                .stream()
                .filter(service -> service.equalsIgnoreCase(targetService))
                .flatMap(service -> discoveryClient.getInstances(service).stream())
                .map(instance -> new ServiceInstanceRecord(
                        instance.getServiceId(),
                        instance.getInstanceId(),
                        instance.getHost(),
                        instance.getPort(),
                        instance.isSecure()
                ))
                .toList();
    }
}

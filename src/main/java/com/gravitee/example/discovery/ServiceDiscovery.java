package com.gravitee.example.discovery;

import java.util.List;

public interface ServiceDiscovery {

    List<ServiceInstanceRecord> getServiceInstances();

    record ServiceInstanceRecord(String serviceId,
                                 String instanceId,
                                 String host,
                                 int port,
                                 boolean secure) {
        @Override
        public String toString() {
            return String.format(
                    "%s://%s:%d",
                    secure ? "https" : "http",
                    host, port
            );
        }
    }
}

package com.gravitee.example.gravitee;

import com.gravitee.example.controller.ExternalRestController;
import com.gravitee.example.discovery.ServiceDiscovery;
import com.gravitee.example.discovery.ServiceDiscovery.ServiceInstanceRecord;
import com.gravitee.example.gravitee.GraviteeAPIService.GraviteeApiServiceException;
import com.gravitee.example.gravitee.dto.api.ApiDetailsDTO;
import com.gravitee.example.gravitee.dto.api.CreateApiDTO;
import com.gravitee.example.gravitee.dto.api.SearchApiDTO;
import com.gravitee.example.gravitee.dto.api.details.ApiListener;
import com.gravitee.example.gravitee.dto.api.details.ApiType;
import com.gravitee.example.gravitee.dto.api.details.DefinitionVersion;
import com.gravitee.example.gravitee.dto.api.details.Endpoint;
import com.gravitee.example.gravitee.dto.api.details.EndpointConfiguration;
import com.gravitee.example.gravitee.dto.api.details.EndpointGroup;
import com.gravitee.example.gravitee.dto.api.details.EndpointGroupSharedConfiguration;
import com.gravitee.example.gravitee.dto.api.details.EndpointServices;
import com.gravitee.example.gravitee.dto.api.details.HttpConfiguration;
import com.gravitee.example.gravitee.dto.api.details.HttpVersion;
import com.gravitee.example.gravitee.dto.api.details.ListenerEntrypoint;
import com.gravitee.example.gravitee.dto.api.details.ListenerEntrypointConfiguration;
import com.gravitee.example.gravitee.dto.api.details.ListenerType;
import com.gravitee.example.gravitee.dto.api.details.LoadBalancer;
import com.gravitee.example.gravitee.dto.api.details.LoadBalancerType;
import com.gravitee.example.gravitee.dto.api.details.Path;
import com.gravitee.example.gravitee.dto.api.details.ProxyConfiguration;
import com.gravitee.example.gravitee.dto.api.details.Visibility;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDetailsDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.details.PlanSecurity;
import com.gravitee.example.gravitee.dto.api.details.plan.details.PlanSecurityType;
import com.gravitee.example.gravitee.dto.api.details.plan.details.PlanValidation;
import com.gravitee.example.gravitee.dto.api.details.ssl.SslOptions;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GraviteeStartupRegistrator<T extends ExternalRestController> {

    private final ServiceDiscovery serviceDiscovery;
    private final List<T> externalRestControllers;
    private final GraviteeAPIService graviteeAPIService;

    @Autowired
    public GraviteeStartupRegistrator(ServiceDiscovery serviceDiscovery,
                                      List<T> externalRestControllers,
                                      GraviteeAPIService graviteeAPIService) {
        this.serviceDiscovery = serviceDiscovery;
        this.externalRestControllers = externalRestControllers;
        this.graviteeAPIService = graviteeAPIService;
    }

    @Scheduled(fixedRateString = "${gravitee.discovery.fixed-rate}")
    public void schedule() {
        log.debug("=== Run Gravitee registration ===");
        List<ServiceInstanceRecord> serviceInstances = serviceDiscovery.getServiceInstances();
        if (!serviceInstances.isEmpty()) {
            for (T controller : externalRestControllers) {
                try {
                    register(controller, serviceInstances);
                } catch (GraviteeApiServiceException e) {
                    log.error("Can't register controller {}", controller.getName(), e);
                }
            }
        }
    }

    public boolean register(T controller, List<ServiceInstanceRecord> instances) throws GraviteeApiServiceException {

        List<ApiDetailsDTO> currApi = graviteeAPIService.getAPi(new SearchApiDTO(controller.getName()));
        if (!currApi.isEmpty()) {
            log.debug("Controller {} was already registered", controller.getName());
            return false;
        }

        // create API
        ApiDetailsDTO api = createApi(controller, instances);
        // create Plan
        PlanDetailsDTO plan = createApiPlan(controller, api);
        // publish plan
        graviteeAPIService.publishPlan(plan);
        // start API
        graviteeAPIService.startApi(api.getId());
        return true;
    }

    private PlanDetailsDTO createApiPlan(T controller, ApiDetailsDTO api) throws GraviteeApiServiceException {
        return graviteeAPIService.createPlan(
                api.getId(),
                new PlanDTO(
                        String.format("%s: %s", controller.getName(), "Keyless Plan"),
                        "Plan without authentication",
                        DefinitionVersion.V4,
                        PlanValidation.AUTO,
                        new PlanSecurity(
                                PlanSecurityType.KEY_LESS,
                                null
                        )
                )
        );
    }

    private ApiDetailsDTO createApi(T controller, List<ServiceInstanceRecord> instances) throws GraviteeApiServiceException {
        return graviteeAPIService.createApi(CreateApiDTO
                .builder()
                .name(controller.getName())
                .apiVersion(controller.getVersion())
                .definitionVersion(DefinitionVersion.V4)
                .type(ApiType.PROXY)
                .tags(List.of())
                .visibility(Visibility.PUBLIC)
                .listeners(List.of(
                        new ApiListener(
                                null,
                                ListenerType.HTTP,
                                List.of(new ListenerEntrypoint(
                                        "http-proxy",
                                        null,
                                        null,
                                        new ListenerEntrypointConfiguration(
                                                "0.0.0.0",
                                                List.of(new Path(
                                                        null,
                                                        controller.getContextPath(),
                                                        null
                                                ))
                                        )
                                )),
                                null,
                                null,
                                null,
                                List.of(new Path(
                                        null,
                                        controller.getContextPath(),
                                        null
                                )),
                                null,
                                null
                        )
                ))
                .endpointGroups(List.of(
                        new EndpointGroup(
                                controller.getName() + "Endpoint",
                                "http-proxy",
                                new LoadBalancer(LoadBalancerType.ROUND_ROBIN),
                                new EndpointGroupSharedConfiguration(
                                        new ProxyConfiguration(false, false),
                                        new HttpConfiguration(
                                                30_000,
                                                true,
                                                false,
                                                10_000,
                                                60_000,
                                                3_000,
                                                true,
                                                20,
                                                HttpVersion.HTTP_1_1,
                                                false
                                        ),
                                        new SslOptions(
                                                true,
                                                true,
                                                null,
                                                null,
                                                List.of()
                                        )
                                ),
                                instances
                                        .stream()
                                        .map(instance -> new Endpoint(
                                                String.format("%s-%s", instance.serviceId(), instance.instanceId()),
                                                "http-proxy",
                                                1,
                                                true,
                                                new EndpointConfiguration(instance + controller.getContextPath()),
                                                null,
                                                null,
                                                false,
                                                List.of()

                                        ))
                                        .toList(),
                                new EndpointServices()
                        )
                ))
                .build());
    }


}

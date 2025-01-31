package com.gravitee.example.gravitee;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gravitee.example.gravitee.dto.api.ApiDetailsDTO;
import com.gravitee.example.gravitee.dto.api.CreateApiDTO;
import com.gravitee.example.gravitee.dto.api.SearchApiDTO;
import com.gravitee.example.gravitee.dto.api.SearchApiResultDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDetailsDTO;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GraviteeAPIService implements GraviteeAPI {

    private final GraviteeConfig graviteeConfig;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public GraviteeAPIService(GraviteeConfig graviteeConfig,
                              ObjectMapper objectMapper,
                              RestTemplate restTemplate) {
        this.graviteeConfig = graviteeConfig;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ApiDetailsDTO> getAPi(SearchApiDTO searchApiDTO) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                graviteeConfig.getSearchApiUrl(),
                searchApiDTO,
                body -> objectMapper.readValue(body, SearchApiResultDTO.class).getData(),
                "getAPi"
        );
    }

    @Override
    public ApiDetailsDTO createApi(CreateApiDTO createApi) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                graviteeConfig.getCreateApiUrl(),
                createApi,
                body -> objectMapper.readValue(body, ApiDetailsDTO.class),
                "createApi"
        );
    }

    @Override
    public PlanDetailsDTO createPlan(String apiId, PlanDTO planDTO) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                String.format(graviteeConfig.getCreateApiPlanUrlTemplate(), apiId),
                planDTO,
                body -> objectMapper.readValue(body, PlanDetailsDTO.class),
                "createPlan"
        );
    }

    @Override
    public PlanDetailsDTO publishPlan(PlanDetailsDTO planDetailsDTO) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                String.format(graviteeConfig.getPublishApiPlanUrlTemplate(), planDetailsDTO.getApiId(), planDetailsDTO.getId()),
                planDetailsDTO,
                body -> objectMapper.readValue(body, PlanDetailsDTO.class),
                "publishPlan"
        );
    }

    @Override
    public boolean startApi(String apiId) throws GraviteeApiServiceException {
        return Boolean.TRUE.equals(sendRequest2Gravitee(
                String.format(graviteeConfig.getStartApiUrlTemplate(), apiId),
                null,
                body -> true,
                "startApi"
        ));
    }

    @Override
    public boolean stopApi(String apiId) throws GraviteeApiServiceException {
        return false;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + graviteeConfig.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private <T, P> T sendRequest2Gravitee(String url,
                                          @Nullable P payload,
                                          JacksonResponseTransformer<String, T> transformer,
                                          String action) throws GraviteeApiServiceException{
        try {
            HttpEntity<String> request = new HttpEntity<>(
                    payload != null ? objectMapper.writeValueAsString(payload) : null,
                    createHttpHeaders()
            );

            // Отправляем запрос в Gravitee
            ResponseEntity<String> response = restTemplate.postForEntity(
                    url,
                    request,
                    String.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                return transformer.transform(response.getBody());
            }

            log.error("Gravitee API returns incorrect status for {}: {}", action, response);
            return null;
        } catch (Exception e) {
            throw new GraviteeApiServiceException(String.format("Can't execute %s sent to %s for %s", action, url, payload), e);
        }
    }

    public static class GraviteeApiServiceException extends Exception {
        public GraviteeApiServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @FunctionalInterface
    public interface JacksonResponseTransformer<T, R> {
        R transform(@Nullable T t) throws JacksonException;
    }

}

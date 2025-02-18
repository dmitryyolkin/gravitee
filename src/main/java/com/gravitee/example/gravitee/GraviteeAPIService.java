package com.gravitee.example.gravitee;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gravitee.example.gravitee.dto.api.ApiDTO;
import com.gravitee.example.gravitee.dto.api.ApiDetailsDTO;
import com.gravitee.example.gravitee.dto.api.SearchApiDTO;
import com.gravitee.example.gravitee.dto.api.SearchApiResultDTO;
import com.gravitee.example.gravitee.dto.api.details.pages.ListPageDocumentationDetailsDTO;
import com.gravitee.example.gravitee.dto.api.details.pages.PageDocumentationDTO;
import com.gravitee.example.gravitee.dto.api.details.pages.PageDetailsDocumentationDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDetailsDTO;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
    public ApiDetailsDTO createApi(ApiDTO api) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                graviteeConfig.getCreateApiUrl(),
                api,
                body -> objectMapper.readValue(body, ApiDetailsDTO.class),
                "createApi"
        );
    }

    @Override
    public void updateApi(String id, ApiDTO api) throws GraviteeApiServiceException {
        sendRequest2Gravitee(
                graviteeConfig.getUpdateApiUrl(),
                api,
                request -> {
                    restTemplate.put(
                            String.format(graviteeConfig.getUpdateApiUrl(), id),
                            request
                    );
                    return new ResponseEntity<>(
                            HttpStatus.OK
                    );
                },
                body -> body,
                "updateApi"
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
        throw new UnsupportedOperationException("Need to implement");
    }

    @Override
    public ListPageDocumentationDetailsDTO getApiPageDocumentation(String apiId) throws GraviteeApiServiceException {
        String url = String.format(graviteeConfig.getGetApiPagesUrlTemplate(), apiId);
        return sendRequest2Gravitee(
                url,
                null,
                request -> restTemplate
                        .exchange(
                                url,
                                HttpMethod.GET,
                                request,
                                String.class,
                                Map.of()
                        ),
                body -> objectMapper.readValue(body, ListPageDocumentationDetailsDTO.class),
                "getPageApi"
        );
    }

    @Override
    public PageDetailsDocumentationDTO createApiPageDocumentation(String apiId, PageDocumentationDTO pageDTO) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                String.format(graviteeConfig.getCreateApiPagesUrlTemplate(), apiId),
                pageDTO,
                body -> objectMapper.readValue(body, PageDetailsDocumentationDTO.class),
                "createApiPageDocumentation"
        );
    }

    @Override
    public void updateApiPageDocumentation(String apiId, String pageId, PageDocumentationDTO pageDTO) throws GraviteeApiServiceException {
        String url = String.format(graviteeConfig.getUpdateApiPagesUrlTemplate(), apiId, pageId);
        sendRequest2Gravitee(
                url,
                pageDTO,
                request -> {
                    restTemplate.put(url, request);
                    return new ResponseEntity<>(
                            HttpStatus.OK
                    );
                },
                body -> body,
                "updateApiPageDocumentation"
        );
    }

    @Override
    public PageDetailsDocumentationDTO publishApiPageDocumentation(String apiId, String pageId) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                String.format(graviteeConfig.getPublishApiPagesUrlTemplate(), apiId, pageId),
                null,
                body -> objectMapper.readValue(body, PageDetailsDocumentationDTO.class),
                "publishPageDocumentation"
        );
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
                                          String action) throws GraviteeApiServiceException {
        return sendRequest2Gravitee(
                url,
                payload,
                request -> restTemplate.postForEntity(url, request, String.class),
                transformer,
                action
        );
    }


    private <T, P> T sendRequest2Gravitee(String url,
                                          @Nullable P payload,
                                          Function<HttpEntity<String>, ResponseEntity<String>> sender,
                                          JacksonResponseTransformer<String, T> transformer,
                                          String action) throws GraviteeApiServiceException {
        try {
            HttpEntity<String> request = new HttpEntity<>(
                    payload != null ? objectMapper.writeValueAsString(payload) : null,
                    createHttpHeaders()
            );

            // Отправляем запрос в Gravitee
            ResponseEntity<String> response = sender.apply(request);
            if (response.getStatusCode().is2xxSuccessful()) {
                return transformer.transform(response.getBody());
            }

            log.error("Gravitee API returns incorrect status for {}: {}", action, response);
            return null;
        } catch (Exception e) {
            throw new GraviteeApiServiceException(String.format("Can't execute %s sent to %s for %s", action, url, payload), e);
        }
    }

    @FunctionalInterface
    public interface JacksonResponseTransformer<T, R> {
        R transform(@Nullable T t) throws JacksonException;
    }

    public static class GraviteeApiServiceException extends Exception {
        public GraviteeApiServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}

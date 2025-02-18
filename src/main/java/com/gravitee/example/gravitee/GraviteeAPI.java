package com.gravitee.example.gravitee;

import com.gravitee.example.gravitee.GraviteeAPIService.GraviteeApiServiceException;
import com.gravitee.example.gravitee.dto.api.ApiDTO;
import com.gravitee.example.gravitee.dto.api.ApiDetailsDTO;
import com.gravitee.example.gravitee.dto.api.SearchApiDTO;
import com.gravitee.example.gravitee.dto.api.details.pages.ListPageDocumentationDetailsDTO;
import com.gravitee.example.gravitee.dto.api.details.pages.PageDocumentationDTO;
import com.gravitee.example.gravitee.dto.api.details.pages.PageDetailsDocumentationDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDetailsDTO;
import java.util.List;

public interface GraviteeAPI {

    List<ApiDetailsDTO> getAPi(SearchApiDTO searchApiDTO) throws GraviteeApiServiceException;

    ApiDetailsDTO createApi(ApiDTO api) throws GraviteeApiServiceException;

    void updateApi(String id, ApiDTO api) throws GraviteeApiServiceException;

    PlanDetailsDTO createPlan(String apiId, PlanDTO planDTO) throws GraviteeApiServiceException;

    PlanDetailsDTO publishPlan(PlanDetailsDTO planDetailsDTO) throws GraviteeApiServiceException;

    boolean startApi(String apiId) throws GraviteeApiServiceException;

    boolean stopApi(String apiId) throws GraviteeApiServiceException;

    ListPageDocumentationDetailsDTO getApiPageDocumentation(String apiId) throws GraviteeApiServiceException;

    PageDetailsDocumentationDTO createApiPageDocumentation(String apiId, PageDocumentationDTO pageDTO) throws GraviteeApiServiceException;

    void updateApiPageDocumentation(String apiId, String pageId, PageDocumentationDTO pageDTO) throws GraviteeApiServiceException;

    PageDetailsDocumentationDTO publishApiPageDocumentation(String apiId, String pageId) throws GraviteeApiServiceException;
}

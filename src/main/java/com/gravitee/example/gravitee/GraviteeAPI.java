package com.gravitee.example.gravitee;

import com.gravitee.example.gravitee.GraviteeAPIService.GraviteeApiServiceException;
import com.gravitee.example.gravitee.dto.api.ApiDetailsDTO;
import com.gravitee.example.gravitee.dto.api.CreateApiDTO;
import com.gravitee.example.gravitee.dto.api.SearchApiDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDTO;
import com.gravitee.example.gravitee.dto.api.details.plan.PlanDetailsDTO;
import java.util.List;

public interface GraviteeAPI {

    List<ApiDetailsDTO> getAPi(SearchApiDTO searchApiDTO) throws GraviteeApiServiceException;

    ApiDetailsDTO createApi(CreateApiDTO createApi) throws GraviteeApiServiceException;

    PlanDetailsDTO createPlan(String apiId, PlanDTO planDTO) throws GraviteeApiServiceException;

    PlanDetailsDTO publishPlan(PlanDetailsDTO planDetailsDTO) throws GraviteeApiServiceException;

    boolean startApi(String apiId) throws GraviteeApiServiceException;

    boolean stopApi(String apiId) throws GraviteeApiServiceException;
}

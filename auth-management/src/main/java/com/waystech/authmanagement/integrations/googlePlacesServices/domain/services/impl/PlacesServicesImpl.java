package com.waystech.authmanagement.integrations.googlePlacesServices.domain.services.impl;

import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.response.GeoResponse;
import com.waystech.authmanagement.integrations.googlePlacesServices.domain.services.PlacesServices;
import com.waystech.authmanagement.integrations.googlePlacesServices.domain.services.apiConnection.WebClientService;
import com.waystech.authmanagement.user.dto.NovaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlacesServicesImpl implements PlacesServices {
    private final WebClientService webClientService;
    @Override
    public NovaResponse<GeoResponse> getNearByPlaces(NearbyPlacesRequest request){
        log.info("Request Body Obtained: {}", request);
        GeoResponse response = webClientService.getNearByPlaces(request);
        return new NovaResponse<>("Places Fetched Successfully", response);
    }
}

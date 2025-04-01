package org.partypal.thirdPartyService.googlePlacesServices.domain.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.response.GeoResponse;
import org.partypal.thirdPartyService.googlePlacesServices.domain.services.PlacesServices;
import org.partypal.thirdPartyService.googlePlacesServices.domain.services.apiConnection.WebClientService;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlacesServicesImpl implements PlacesServices {
    private final WebClientService webClientService;
    @Override
    public PartyPalResponse<GeoResponse> getNearByPlaces(NearbyPlacesRequest request){
        log.info("Request Body Obtained: {}", request);
        GeoResponse response = webClientService.getNearByPlaces(request);
        return new PartyPalResponse<>("Places Fetched Successfully", response);
    }
}

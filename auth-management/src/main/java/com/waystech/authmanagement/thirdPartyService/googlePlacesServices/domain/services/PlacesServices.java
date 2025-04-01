package org.partypal.thirdPartyService.googlePlacesServices.domain.services;

import org.partypal.thirdPartyService.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.response.GeoResponse;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.springframework.stereotype.Service;

@Service
public interface PlacesServices {
    PartyPalResponse<GeoResponse> getNearByPlaces(NearbyPlacesRequest request);
}

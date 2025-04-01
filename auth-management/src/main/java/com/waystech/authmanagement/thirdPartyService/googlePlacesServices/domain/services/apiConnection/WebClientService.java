package org.partypal.thirdPartyService.googlePlacesServices.domain.services.apiConnection;

import org.partypal.thirdPartyService.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import org.partypal.thirdPartyService.googlePlacesServices.application.dto.response.GeoResponse;
import org.springframework.stereotype.Service;

@Service
public interface WebClientService {
    GeoResponse getNearByPlaces(NearbyPlacesRequest request);
}

package com.waystech.authmanagement.integrations.googlePlacesServices.domain.services.apiConnection;


import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.response.GeoResponse;
import org.springframework.stereotype.Service;

@Service
public interface WebClientService {
    GeoResponse getNearByPlaces(NearbyPlacesRequest request);
}

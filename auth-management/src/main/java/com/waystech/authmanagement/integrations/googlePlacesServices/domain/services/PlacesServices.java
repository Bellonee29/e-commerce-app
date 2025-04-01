package com.waystech.authmanagement.integrations.googlePlacesServices.domain.services;

import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.request.NearbyPlacesRequest;
import com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.response.GeoResponse;
import com.waystech.authmanagement.user.dto.NovaResponse;
import org.springframework.stereotype.Service;

@Service
public interface PlacesServices {
    NovaResponse<GeoResponse> getNearByPlaces(NearbyPlacesRequest request);
}

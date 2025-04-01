package com.waystech.authmanagement.integrations.googlePlacesServices.application.dto.response;

import com.waystech.authmanagement.integrations.googlePlacesServices.domain.model.Places;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GeoResponse {
    private Places[] results;
}

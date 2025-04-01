package org.partypal.thirdPartyService.googlePlacesServices.application.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.partypal.thirdPartyService.googlePlacesServices.domain.model.Places;
@Getter
@Setter
public class GeoResponse {
    private Places[] results;
}

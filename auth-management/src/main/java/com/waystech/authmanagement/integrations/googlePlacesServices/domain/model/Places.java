package com.waystech.authmanagement.integrations.googlePlacesServices.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Places {
    private String place_id;
    private String name;
    private String formatted_address;
    private String vicinity;
    private String reference;
    private String[] types;
    private double rating;
    private Geometry geometry;
    private PlacesPhoto[] photos;

}

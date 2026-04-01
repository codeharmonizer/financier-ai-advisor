package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;
import com.neo.v1.product.catalogue.model.LocationInformation;
import org.springframework.stereotype.Component;


@Component
public class LocationInfoMapper {

    public LocationInformation map(CDAEntry cdaEntry) {
        return LocationInformation.builder()
                .addressLine(cdaEntry.getField("addressLine"))
                .buildingNumber(cdaEntry.getField("addressBuildingNumber"))
                .streetName(cdaEntry.getField("addressStreetName"))
                .townName(cdaEntry.getField("addressTownName"))
                .countrySubDivision(cdaEntry.getField("addressCountrySubDivision"))
                .country(cdaEntry.getField("addressCountry"))
                .locationLatitude(cdaEntry.getField("geoLocationLatitude"))
                .locationLongitude(cdaEntry.getField("geoLocationLongitude"))
                .build();
    }
}



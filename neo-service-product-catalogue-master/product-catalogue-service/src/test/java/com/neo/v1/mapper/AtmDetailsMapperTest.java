package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.AtmDetails;
import com.neo.v1.product.catalogue.model.LocationInformation;
import com.neo.v1.product.catalogue.model.ProductDetails;
import com.neo.v1.product.catalogue.model.ProductTnC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.neo.v1.constants.ProductCatalogueConstants.ACCESSIBILITY;
import static com.neo.v1.constants.ProductCatalogueConstants.ATM_SERVICES;
import static com.neo.v1.constants.ProductCatalogueConstants.IDENTIFICATION;
import static com.neo.v1.constants.ProductCatalogueConstants.LOCATION_CATEGORY;
import static com.neo.v1.constants.ProductCatalogueConstants.MINIMUM_AMOUNT;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_SUMMERY;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_TITLE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_URL;
import static com.neo.v1.constants.ProductCatalogueConstants.SUPPORTED_CURRENCIES;
import static com.neo.v1.constants.ProductCatalogueConstants.SUPPORTED_LANGUAGES;
import static com.neo.v1.constants.ProductCatalogueConstants.TIMING_FLAG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtmDetailsMapperTest {

    @InjectMocks
    private AtmDetailsMapper subject;

    @Mock
    private LocationInfoMapper locationInfoMapper;

    @Test
    void map_withCDAEntry_returnAtmDetails() throws Exception {

        LocationInformation locationInformation = LocationInformation.builder()
                .locationLatitude("Lat")
                .locationLongitude("Lon")
                .countrySubDivision("Cap")
                .townName("manama")
                .streetName("S1")
                .country("Bahrain")
                .buildingNumber("123")
                .addressLine("Line 1")
                .build();
        String identification = "id";
        String atmServices = "services";
        String accessibility = "accessibility";
        String timing = "24H";
        String currencies = "BHD";
        String languages = "en";
        BigDecimal minAmount = new BigDecimal(1);
        String locationCategory = "cat1";

        CDAEntry cdaEntry = mock(CDAEntry.class);
        CDAArray cdaArray = mock(CDAArray.class);
        CDAEntry cdaResource = mock(CDAEntry.class);
        cdaArray.items().add(cdaResource);
        List<CDAResource> cdaResourceList = Arrays.asList((CDAResource) cdaResource);
        List<AtmDetails> atmDetailsList = new ArrayList<>();
        AtmDetails atmDetails = AtmDetails.builder()
                .atmAddress(locationInformation)
                .atmLocationCategory(locationCategory)
                .atmSupportedLanguages(Arrays.asList(languages))
                .atmSupportedCurrencies(Arrays.asList(currencies))
                .atmTime24HIdentifier(timing)
                .atmAccessibility(Arrays.asList(accessibility))
                .atmId(identification)
                .atmMinimumAmount(minAmount)
                .atmServices(Arrays.asList(atmServices))
                .build();

        atmDetailsList.add(atmDetails);

        when(cdaArray.total()).thenReturn(1);
        when(cdaArray.items()).thenReturn(cdaResourceList);
        when(cdaResource.getField(any()))
                .thenReturn(identification)
                .thenReturn(Arrays.asList(atmServices))
                .thenReturn(Arrays.asList(accessibility))
                .thenReturn(timing)
                .thenReturn(Arrays.asList(currencies))
                .thenReturn(Arrays.asList(languages))
                .thenReturn(minAmount)
                .thenReturn(locationCategory);
        when(locationInfoMapper.map(any())).thenReturn(locationInformation);


        List<AtmDetails> result = subject.map(cdaArray);

        assertThat(result.get(0)).isEqualToComparingFieldByFieldRecursively(atmDetails);

        verify(locationInfoMapper).map(cdaResource);
    }
}

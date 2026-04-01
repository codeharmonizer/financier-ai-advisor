package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.AtmDetails;
import com.neo.v1.product.catalogue.model.BranchDetails;
import com.neo.v1.product.catalogue.model.ContactInfo;
import com.neo.v1.product.catalogue.model.LocationInformation;
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
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCH_NAME;
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCH_SERVICES_AND_FACILITIES;
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCH_TYPE;
import static com.neo.v1.constants.ProductCatalogueConstants.CUSTOMER_SEGMENT;
import static com.neo.v1.constants.ProductCatalogueConstants.IDENTIFICATION;
import static com.neo.v1.constants.ProductCatalogueConstants.SUPPORTED_LANGUAGES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchDetailsMapperTest {

    @InjectMocks
    private BranchDetailsMapper subject;

    @Mock
    private LocationInfoMapper locationInfoMapper;

    @Mock
    private ContactInfoMapper contactInfoMapper;

    @Test
    void map_withCDAEntry_returnBranchDetails() throws Exception {

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
        String services = "services";
        String accessibility = "accessibility";
        String branchType = "24H";
        String currencies = "BHD";
        String languages = "en";
        BigDecimal minAmount = new BigDecimal(1);
        String segments = "cat1";
        String branchName = "name";

        CDAEntry cdaEntry = mock(CDAEntry.class);
        CDAArray cdaArray = mock(CDAArray.class);
        CDAEntry cdaResource = mock(CDAEntry.class);
        cdaArray.items().add(cdaResource);
        List<CDAResource> cdaResourceList = Arrays.asList((CDAResource) cdaResource);

        List<BranchDetails> BranchDetailsList = new ArrayList<>();
        BranchDetails branchDetails = BranchDetails.builder()
                .branchAccessibility(Arrays.asList(accessibility))
                .branchSupportedLanguages(Arrays.asList(languages))
                .branchType(branchType)
                .branchServicesAndFacilities(Arrays.asList(services))
                .branchCustomerSegment(Arrays.asList(segments))
                .branchName(branchName)
                .branchId(identification)
                .branchContactInfo(new ArrayList<>())
                .branchAddress(locationInformation)
                .build();
        ContactInfo contactInfo = ContactInfo.builder().build();
        BranchDetailsList.add(branchDetails);
        when(cdaResource.getField(any()))
                .thenReturn(identification)
                .thenReturn(branchName)
                .thenReturn(branchType)
                .thenReturn(Arrays.asList(accessibility))
                .thenReturn(Arrays.asList(segments))
                .thenReturn(Arrays.asList(services))
                .thenReturn(Arrays.asList(languages))
                .thenReturn(Arrays.asList(contactInfo))
                .thenReturn(locationInformation);

        when(cdaArray.total()).thenReturn(1);
        when(cdaArray.items()).thenReturn(cdaResourceList);

//        when(locationInfoMapper.map(any())).thenReturn(locationInformation);
 //       when(contactInfoMapper.map(any())).thenReturn(new ArrayList<ContactInfo>());

        List<BranchDetails> result = subject.map(cdaArray);

        assertThat(result.get(0)).isEqualToIgnoringGivenFields(branchDetails,"branchAddress","branchContactInfo");

        verify(locationInfoMapper).map(cdaResource);
    }
}

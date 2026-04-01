package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.AppBranchDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_CASH_DEPOSIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith ( MockitoExtension.class )
class AppBranchDetailsMapperTest {
    public static final String CASH_DEPOSIT_YES = "yes";
    @InjectMocks
    private AppBranchDetailsMapper subject;

    @Test
    void map_withCDAArray_returnListOfAppBranchDetails() {
        CDAArray cdaArray = mock(CDAArray.class);
        CDAEntry cdaResource = mock(CDAEntry.class);
        List<CDAResource> cdaResourceList = Arrays.asList(cdaResource);
        when(cdaResource.getField(APP_BRANCHES_CASH_DEPOSIT)).thenReturn(true);
        when(cdaArray.items()).thenReturn(cdaResourceList);

        AppBranchDetails appBranchDetails = AppBranchDetails.builder()
                .cashDeposit(CASH_DEPOSIT_YES)
                .build();

        List<AppBranchDetails> result = subject.map(cdaArray);
        assertThat(result.get(0)).isEqualToComparingFieldByFieldRecursively(appBranchDetails);
    }
}
package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;
import org.junit.jupiter.api.Test;

import com.neo.v1.product.catalogue.model.ProductTnC;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_SUMMERY;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_TITLE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductTnCMapperTest {

    private ProductTnCMapper subject = new ProductTnCMapper();

    @Test
    void map_withCDAEntry_returnProductBenefits() throws Exception {
        CDAEntry cdaEntry = mock(CDAEntry.class);

        String tncSummery = "Test tnc summery";
        String tncTitle = "Test tnc title";
        String tncUrl = "Test tnc url";

        when(cdaEntry.getField(PRODUCT_TNC_TITLE)).thenReturn(tncTitle);
        when(cdaEntry.getField(PRODUCT_TNC_SUMMERY)).thenReturn(tncSummery);
        when(cdaEntry.getField(PRODUCT_TNC_URL)).thenReturn(tncUrl);

        ProductTnC expected = ProductTnC.builder()
                .tncSummery(tncSummery)
                .tncTitle(tncTitle)
                .tncUrl(tncUrl)
                .build();

        ProductTnC result = subject.map(cdaEntry);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(cdaEntry).getField(PRODUCT_TNC_TITLE);
        verify(cdaEntry).getField(PRODUCT_TNC_SUMMERY);
        verify(cdaEntry).getField(PRODUCT_TNC_URL);
    }
}

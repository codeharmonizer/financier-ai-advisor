package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import com.neo.v1.product.catalogue.model.ProductEligibility;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_CREDIT_SCORE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_EMPLOYMENT_STATUS;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_INCOME_RANGE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_MINIMUM_AGE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_REQUIRED_DOCUMENTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductEligibilityMapperTest {

    private ProductEligibilityMapper subject = new ProductEligibilityMapper();

    @Test
    public void map_withCDAEntry_returnProductEligibility() throws Exception {
        CDAEntry cdaEntry = mock(CDAEntry.class);

        String creditScore = "Test credit score";
        String incomeRange = "Test income range";
        String employmentStatus = "Test employment status";
        String requiredDocuments = "Test required documents";
        BigDecimal minimumAge = new BigDecimal("18.0");

        when(cdaEntry.getField(PRODUCT_CREDIT_SCORE)).thenReturn(creditScore);
        when(cdaEntry.getField(PRODUCT_INCOME_RANGE)).thenReturn(incomeRange);
        when(cdaEntry.getField(PRODUCT_EMPLOYMENT_STATUS)).thenReturn(employmentStatus);
        when(cdaEntry.getField(PRODUCT_REQUIRED_DOCUMENTS)).thenReturn(requiredDocuments);
        when(cdaEntry.getField(PRODUCT_MINIMUM_AGE)).thenReturn(minimumAge);

        ProductEligibility expected = ProductEligibility.builder()
                .creditScore(creditScore)
                .incomeRange(incomeRange)
                .employmentStatus(employmentStatus)
                .minimumAge(minimumAge)
                .requiredDocuments(requiredDocuments)
                .build();

        ProductEligibility result = subject.map(cdaEntry);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(cdaEntry).getField(PRODUCT_CREDIT_SCORE);
        verify(cdaEntry).getField(PRODUCT_INCOME_RANGE);
        verify(cdaEntry).getField(PRODUCT_EMPLOYMENT_STATUS);
        verify(cdaEntry).getField(PRODUCT_REQUIRED_DOCUMENTS);
        verify(cdaEntry).getField(PRODUCT_MINIMUM_AGE);
    }
}

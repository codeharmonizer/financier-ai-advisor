package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import com.neo.v1.product.catalogue.model.ProductFeatures;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_CURRENCY;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_DEPOSIT_RATE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_HANDLING_FEES;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_INTEREST_RATES;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_MIN_BALANCE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_MIN_DEPOSIT_AMOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductFeaturesMapperTest {

    private ProductFeaturesMapper subject = new ProductFeaturesMapper();

    @Test
    public void map_withCDAEntry_returnProductFeatures() throws Exception {
        CDAEntry cdaEntry = mock(CDAEntry.class);

        String productCurrency = "Test product currency";
        BigDecimal productDepositRate = new BigDecimal("1.0");
        BigDecimal productHandlingFees = new BigDecimal("2.0");
        BigDecimal productInterestRates = new BigDecimal("3.0");
        BigDecimal productMinBalance = new BigDecimal("4.0");
        BigDecimal productMinDepositAmount = new BigDecimal("5.0");

        when(cdaEntry.getField(PRODUCT_PRODUCT_CURRENCY)).thenReturn(productCurrency);
        when(cdaEntry.getField(PRODUCT_PRODUCT_DEPOSIT_RATE)).thenReturn(productDepositRate);
        when(cdaEntry.getField(PRODUCT_PRODUCT_HANDLING_FEES)).thenReturn(productHandlingFees);
        when(cdaEntry.getField(PRODUCT_PRODUCT_INTEREST_RATES)).thenReturn(productInterestRates);
        when(cdaEntry.getField(PRODUCT_PRODUCT_MIN_BALANCE)).thenReturn(productMinBalance);
        when(cdaEntry.getField(PRODUCT_PRODUCT_MIN_DEPOSIT_AMOUNT)).thenReturn(productMinDepositAmount);

        ProductFeatures expected = ProductFeatures.builder()
                .productCurrency(productCurrency)
                .productDepositRate(productDepositRate)
                .productHandlingFees(productHandlingFees)
                .productInterestRates(productInterestRates)
                .productMinBalance(productMinBalance)
                .productMinDepositAmount(productMinDepositAmount)
                .build();

        ProductFeatures result = subject.map(cdaEntry);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(cdaEntry).getField(PRODUCT_PRODUCT_CURRENCY);
        verify(cdaEntry).getField(PRODUCT_PRODUCT_DEPOSIT_RATE);
        verify(cdaEntry).getField(PRODUCT_PRODUCT_HANDLING_FEES);
        verify(cdaEntry).getField(PRODUCT_PRODUCT_INTEREST_RATES);
        verify(cdaEntry).getField(PRODUCT_PRODUCT_MIN_BALANCE);
        verify(cdaEntry).getField(PRODUCT_PRODUCT_MIN_DEPOSIT_AMOUNT);
    }
}

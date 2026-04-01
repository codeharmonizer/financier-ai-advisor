package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.ProductFeatures;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_CURRENCY;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_DEPOSIT_RATE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_HANDLING_FEES;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_INTEREST_RATES;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_MIN_BALANCE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_MIN_DEPOSIT_AMOUNT;


@Component
public class ProductFeaturesMapper {

    public ProductFeatures map(CDAEntry cdaEntry) {
        return ProductFeatures.builder()
                .productCurrency(cdaEntry.getField(PRODUCT_PRODUCT_CURRENCY))
                .productDepositRate(new BigDecimal(cdaEntry.getField(PRODUCT_PRODUCT_DEPOSIT_RATE).toString()))
                .productHandlingFees(new BigDecimal(cdaEntry.getField(PRODUCT_PRODUCT_HANDLING_FEES).toString()))
                .productInterestRates(new BigDecimal(cdaEntry.getField(PRODUCT_PRODUCT_INTEREST_RATES).toString()))
                .productMinBalance(new BigDecimal(cdaEntry.getField(PRODUCT_PRODUCT_MIN_BALANCE).toString()))
                .productMinDepositAmount(new BigDecimal(cdaEntry.getField(PRODUCT_PRODUCT_MIN_DEPOSIT_AMOUNT).toString()))
                .build();
    }
}



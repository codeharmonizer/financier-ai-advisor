package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.ProductEligibility;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_CREDIT_SCORE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_EMPLOYMENT_STATUS;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_INCOME_RANGE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_MINIMUM_AGE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_REQUIRED_DOCUMENTS;

@Component
public class ProductEligibilityMapper {

    public ProductEligibility map(CDAEntry cdaEntry) {
        return ProductEligibility.builder()
                .creditScore(cdaEntry.getField(PRODUCT_CREDIT_SCORE))
                .incomeRange(cdaEntry.getField(PRODUCT_INCOME_RANGE))
                .employmentStatus(cdaEntry.getField(PRODUCT_EMPLOYMENT_STATUS))
                .minimumAge(new BigDecimal(cdaEntry.getField(PRODUCT_MINIMUM_AGE).toString()))
                .requiredDocuments(cdaEntry.getField(PRODUCT_REQUIRED_DOCUMENTS))
                .build();
    }
}



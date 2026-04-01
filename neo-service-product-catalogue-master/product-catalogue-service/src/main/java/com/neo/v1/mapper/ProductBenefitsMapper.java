package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.ProductBenefits;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_AUTO_PAY_SETUP;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_E_CHEQUE_ALLOWED;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_MINIMUM_BALANCE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_MULTIPLE_DEBIT_CARDS;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_OTHER_BENEFITS;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_OVER_DRAFT_PROTECTION;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_RELATIONSHIP_MANAGER;


@Component
public class ProductBenefitsMapper {

    public ProductBenefits map(CDAEntry cdaEntry) {
        return ProductBenefits.builder()
                .autoPaySetup(cdaEntry.getField(PRODUCT_AUTO_PAY_SETUP))
                .eChequeAllowed(cdaEntry.getField(PRODUCT_E_CHEQUE_ALLOWED))
                .minimumBalance(new BigDecimal(cdaEntry.getField(PRODUCT_MINIMUM_BALANCE).toString()))
                .multipleDebitCards(cdaEntry.getField(PRODUCT_MULTIPLE_DEBIT_CARDS))
                .OtherBenefits(cdaEntry.getField(PRODUCT_OTHER_BENEFITS))
                .overDraftProtection(cdaEntry.getField(PRODUCT_OVER_DRAFT_PROTECTION))
                .relationshipManager(cdaEntry.getField(PRODUCT_RELATIONSHIP_MANAGER))
                .build();
    }
}



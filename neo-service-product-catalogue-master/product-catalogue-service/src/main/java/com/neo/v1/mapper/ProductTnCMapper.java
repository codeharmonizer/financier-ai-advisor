package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.ProductTnC;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_SUMMERY;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_TITLE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_TNC_URL;

@Component
public class ProductTnCMapper {

    public ProductTnC map(CDAEntry cdaEntry) {
        return ProductTnC.builder()
                .tncSummery(cdaEntry.getField(PRODUCT_TNC_SUMMERY))
                .tncTitle(cdaEntry.getField(PRODUCT_TNC_TITLE))
                .tncUrl(cdaEntry.getField(PRODUCT_TNC_URL))
                .build();
    }
}



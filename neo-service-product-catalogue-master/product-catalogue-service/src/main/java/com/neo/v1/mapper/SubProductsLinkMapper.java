package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_SUB_PRODUCT_TITLE_NAME;

import java.util.Map;

import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.SubProductsLink;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SubProductsLinkMapper {

    public SubProductsLink map(CDAResource cdaResource, Map<String, String> subProductsMap) {
        CDAEntry entry = (CDAEntry) cdaResource;
        String name = entry.getField(OFFER_SUB_PRODUCT_TITLE_NAME);
        return SubProductsLink.builder()
                .name(name)
                .subProductId(StringUtils.isNotBlank(name) ? subProductsMap.get(name) : null)
                .build();
    }

}

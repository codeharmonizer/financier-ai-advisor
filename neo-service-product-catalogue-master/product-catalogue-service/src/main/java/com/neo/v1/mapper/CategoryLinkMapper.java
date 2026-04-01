package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_CATEGORY_ID;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_CATEGORY_NAME;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.OfferCategoryLink;

import com.contentful.java.cda.CDAEntry;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryLinkMapper {

    public OfferCategoryLink map(CDAEntry entry) {
        return OfferCategoryLink.builder()
                .categoryId(entry.getAttribute(OFFERS_CATEGORY_ID))
                .name(entry.getField(OFFERS_CATEGORY_NAME))
                .build();
    }


}

package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.ENTRY_FIELD_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.HTTPS;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_SUB_PRODUCT_BASE_IMAGE_URL;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_SUB_PRODUCT_DISPLAY_ORDER;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_SUB_PRODUCT_ID;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_SUB_PRODUCT_NAME;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.SubProducts;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;

@Component
public class SubProductsMapper {

    public List<SubProducts> map(CDAArray cdaArray) {
        return cdaArray.items().stream().map(this::map).collect(Collectors.toList());
    }

    private SubProducts map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        Double order = entry.getField(OFFERS_SUB_PRODUCT_DISPLAY_ORDER);
        SubProducts offerCategory = SubProducts.builder()
                .subProductId(entry.getField(OFFERS_SUB_PRODUCT_ID))
                .name(entry.getField(OFFERS_SUB_PRODUCT_NAME))
                .displayOrder(order.longValue())
                .build();


        Optional<CDAEntry> subProductBaseImageUrl = Optional.ofNullable(entry.getField(OFFERS_SUB_PRODUCT_BASE_IMAGE_URL));
        subProductBaseImageUrl.ifPresent(image -> offerCategory.setSubProductBaseImageUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));
        return offerCategory;
    }


}

package com.neo.v1.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.OfferCategory;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;

import static com.neo.v1.constants.ProductCatalogueConstants.*;

@Component
public class OfferCategoriesMapper {

    public List<OfferCategory> map(CDAArray cdaArray) {
        return cdaArray.items().stream().map(this::map).collect(Collectors.toList());
    }

    private OfferCategory map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        Double order = entry.getField(OFFERS_CATEGORY_DISPLAY_ORDER);
        OfferCategory offerCategory = OfferCategory.builder()
                .categoryId(cdaResource.id())
                .name(entry.getField(OFFERS_CATEGORY_NAME))
                .startGradientColor(entry.getField(OFFERS_CATEGORY_START_GRADIENT_COLOR))
                .endGradientColor(entry.getField(OFFERS_CATEGORY_END_GRADIENT_COLOR))
                .displayOrder(Objects.nonNull(order) ? order.longValue() : null)
                .build();


        Optional<CDAEntry> offerBaseImageUrl = Optional.ofNullable(entry.getField(OFFERS_CATEGORY_BASE_IMAGE_URL));
        offerBaseImageUrl.ifPresent(image -> offerCategory.setCategoryBaseImageUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));
        return offerCategory;
    }


}

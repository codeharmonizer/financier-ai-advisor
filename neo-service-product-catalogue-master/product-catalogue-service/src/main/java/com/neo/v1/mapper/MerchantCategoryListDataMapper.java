package com.neo.v1.mapper;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.CategoryDetail;
import com.neo.v1.product.catalogue.model.CategoryListData;

import lombok.RequiredArgsConstructor;

import static com.neo.v1.constants.ProductCatalogueConstants.*;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class MerchantCategoryListDataMapper {

    public CategoryListData map(CDAArray merchants) {
        return CategoryListData
        		.builder()
        		.merchantCategories(mapCategoryList(merchants))
        		.build();
    }
    
    public List<CategoryDetail> mapCategoryList(CDAArray cdaArray) {
        return cdaArray.items().stream().map(cdaResource -> map(cdaResource)).collect(Collectors.toList());
    }

    public CategoryDetail map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        String id = entry.attrs().get(MERCHANTS_CATEGORY_ID).toString();
        Double displayOrder = entry.getField(MERCHANT_ORDER_PARAMETER);
        
        CategoryDetail categoryDetail = CategoryDetail.builder()
        		.id(Objects.nonNull(id) ? id : null)
        		.name(entry.getField(MERCHANTS_CATEGORY_NAME))
        		.color(entry.getField(MERCHANTS_CATEGORY_COLOR))
                .fontColor(entry.getField(MERCHANTS_FONT_COLOR))
        		.displayOrder(Objects.nonNull(displayOrder) ? displayOrder.longValue() : null)
        		.build();
        
        Optional<CDAEntry> merchantsIconLabelUrl = Optional.ofNullable(entry.getField(MERCHANTS_CATEGORY_ICONLABEL));
        merchantsIconLabelUrl.ifPresent(image -> categoryDetail.setIconLabelUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        Optional<CDAEntry> merchantsIcon = Optional.ofNullable(entry.getField(MERCHANTS_CATEGORY_ICON));
        merchantsIcon.ifPresent(image -> categoryDetail.setIcon(nonNull(image.getField(ENTRY_FIELD_IMAGE)) ? HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url() : null));


        return categoryDetail;
    }

}

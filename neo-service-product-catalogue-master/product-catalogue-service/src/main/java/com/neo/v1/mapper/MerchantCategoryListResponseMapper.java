package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_MESSAGE;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.CategoryListData;
import com.neo.v1.product.catalogue.model.CategoryListResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MerchantCategoryListResponseMapper {

    private final MetaMapper metaMapper;

    public CategoryListResponse map(CategoryListData categoryListData) {
        return CategoryListResponse.builder()
                .meta(metaMapper.map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE))
                .data(categoryListData)
                .build();
    }
}

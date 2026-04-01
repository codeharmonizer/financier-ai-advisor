package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.ProductListResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Component;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_PRODUCT_LIST_SUCCESS_MESSAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_PRODUCT_LIST_SUCCESS_CODE;

@RequiredArgsConstructor
@Component
public class ProductListResponseMapper {

    private final ProductListDataMapper productListDataMapper;
    private final MetaMapper metaMapper;

    public ProductListResponse map(CDAArray productArray, CDAArray atmArray, CDAArray branchArray) {
        return ProductListResponse.builder()
                .meta(metaMapper.map(GET_PRODUCT_LIST_SUCCESS_CODE, GET_PRODUCT_LIST_SUCCESS_MESSAGE))
                .data(productListDataMapper.map(productArray,atmArray,branchArray))
                .build();
    }
}

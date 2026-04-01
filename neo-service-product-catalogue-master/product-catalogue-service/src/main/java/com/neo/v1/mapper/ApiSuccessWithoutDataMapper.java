package com.neo.v1.mapper;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.ApiSuccessWithoutData;

import lombok.RequiredArgsConstructor;

import static com.neo.v1.constants.ProductCatalogueConstants.*;

@RequiredArgsConstructor
@Component
public class ApiSuccessWithoutDataMapper {

    private final MetaMapper metaMapper;

    public ApiSuccessWithoutData map() {
        return ApiSuccessWithoutData.builder()
                .meta(metaMapper.map(PURGE_OFFER_CACHE_SUCCESS_CODE, PURGE_OFFER_CACHE_SUCCESS_MESSAGE))
                .build();
    }

    public ApiSuccessWithoutData sucessmap() {
        return ApiSuccessWithoutData.builder()
                .meta(metaMapper.map(PURGE_GIFT_CARD_CACHE_SUCCESS_CODE,PURGE_GIFT_CARD_CACHE_SUCCESS_MESSAGE ))
                .build();
    }

}

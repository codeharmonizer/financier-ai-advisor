package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_MESSAGE;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.MerchantListData;
import com.neo.v1.product.catalogue.model.MerchantListResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MerchantListResponseMapper {

    private final MetaMapper metaMapper;

    public MerchantListResponse map(MerchantListData merchantListData) {
        return MerchantListResponse.builder()
                .meta(metaMapper.map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE))
                .data(merchantListData)
                .build();
    }
}

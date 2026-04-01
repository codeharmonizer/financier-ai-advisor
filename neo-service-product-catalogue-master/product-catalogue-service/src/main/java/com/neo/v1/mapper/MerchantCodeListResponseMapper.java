package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_MESSAGE;

import org.springframework.stereotype.Component;

import com.neo.v1.product.catalogue.model.MerchantCodeListData;
import com.neo.v1.product.catalogue.model.MerchantCodeListResponse;
import com.neo.v1.product.catalogue.model.MerchantListData;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MerchantCodeListResponseMapper {

    private final MetaMapper metaMapper;

    public MerchantCodeListResponse map(MerchantCodeListData merchantCodeListData) {
        return MerchantCodeListResponse.builder()
                .meta(metaMapper.map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE))
                .data(merchantCodeListData)
                .build();
    }
}

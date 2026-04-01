package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_OFFERS_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_OFFERS_SUCCESS_MESSAGE;

import java.util.List;

import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.OffersListData;
import com.neo.v1.product.catalogue.model.OffersListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OffersListResponseMapper {

    private final MetaMapper metaMapper;
    private final OffersListDataMapper offersListDataMapper;

    public OffersListResponse map(OffersListData offersListData, List<OfferDetails> offerDetailsList) {
        return OffersListResponse.builder()
                .meta(metaMapper.map(GET_OFFERS_SUCCESS_CODE, GET_OFFERS_SUCCESS_MESSAGE))
                .data(offersListDataMapper.map(offersListData, offerDetailsList))
                .build();
    }
}

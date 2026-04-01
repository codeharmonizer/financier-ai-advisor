package com.neo.v1.mapper;

import com.neo.v1.product.catalogue.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.neo.v1.constants.ProductCatalogueConstants.*;

@RequiredArgsConstructor
@Component
public class GiftCardResponseMapper {

    private final MetaMapper metaMapper;

    public GiftCardResponse map(PrizeoutGiftResponse prizeoutGiftResponse) {
        GiftCardResponse giftCardResponseData = GiftCardResponse.builder()
                .meta(metaMapper.map(GET_PRIZE_OUT_GIFT_SUCCESS_CODE,GET_PRIZE_OUT_GIFT_SUCCESS_MESSAGE))
                .data(prizeoutGiftResponse)
                .build();
        return giftCardResponseData;
    }
}

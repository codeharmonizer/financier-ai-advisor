package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.PrizeoutGiftResponse;
import com.neo.v1.product.catalogue.model.PrizeoutGiftResponsePrizeoutGiftCardsItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PrizeoutGiftResponseMapper {

    private final PrizeoutGiftResponsePrizeoutGiftCardsMapper prizeoutGiftResponsePrizeoutGiftCardsMapper;

    public PrizeoutGiftResponse map(CDAArray giftsCardsArray) {

        List<PrizeoutGiftResponsePrizeoutGiftCardsItem> prizeoutGiftResponsePrizeoutGiftCardsItem   =
                prizeoutGiftResponsePrizeoutGiftCardsMapper.map(giftsCardsArray);

        List<PrizeoutGiftResponsePrizeoutGiftCardsItem>   prizeoutGiftResponsePrizeoutGiftCardsItemData =
                prizeoutGiftResponsePrizeoutGiftCardsItem.stream()
                        .map(prizeoutGiftResponsePrizeoutGiftCardsMapper::mapPrizeoutGiftObject).collect(Collectors.toList());

            return PrizeoutGiftResponse.builder().
                    prizeoutGiftCards(prizeoutGiftResponsePrizeoutGiftCardsItemData).
                    build();
        }
}

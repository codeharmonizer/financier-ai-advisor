package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.PrizeoutGiftResponsePrizeoutGiftCardsItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.neo.v1.constants.ProductCatalogueConstants.*;
@RequiredArgsConstructor
@Component
@Slf4j
public class PrizeoutGiftResponsePrizeoutGiftCardsMapper {


    public List<PrizeoutGiftResponsePrizeoutGiftCardsItem> map(CDAArray cdaArray) {
        return cdaArray.items().stream().map(this::map).collect(Collectors.toList());
    }
    private PrizeoutGiftResponsePrizeoutGiftCardsItem map(CDAResource cdaResource) {

        CDAEntry entry = (CDAEntry) cdaResource;
        Double order = entry.getField(OFFERS_ORDER_PARAMETER);

       PrizeoutGiftResponsePrizeoutGiftCardsItem prizeoutGiftResponsePrizeoutGiftCardsItem = PrizeoutGiftResponsePrizeoutGiftCardsItem.builder()
                .name(entry.getField(PRIZE_OUT_GIFT_NAME))
                .order(Objects.nonNull(order) ? order.longValue() : null)
                .build();
        try {
       Optional<CDAAsset> region = Optional.ofNullable(entry.getField(PRIZE_OUT_GIFT_REGION));

       region.ifPresent(regionImage -> prizeoutGiftResponsePrizeoutGiftCardsItem.setRegion(HTTPS + regionImage.url()));

       Optional<CDAAsset> image = Optional.ofNullable(entry.getField(PRIZE_OUT_GIFT_IMAGE));

       image.ifPresent(giftimage -> prizeoutGiftResponsePrizeoutGiftCardsItem.setImage(HTTPS + giftimage.url()));
    }
   catch(Exception e)
    {
        log.error("Exception While Parsing in Prize outGiftCard Objects "+e);
    }
     return prizeoutGiftResponsePrizeoutGiftCardsItem;

    }

    public PrizeoutGiftResponsePrizeoutGiftCardsItem  mapPrizeoutGiftObject(PrizeoutGiftResponsePrizeoutGiftCardsItem prizeoutGiftResponsePrizeoutGiftCardsItemData)
    {

        PrizeoutGiftResponsePrizeoutGiftCardsItem prizeoutGiftResponsePrizeoutGiftCardsItem = PrizeoutGiftResponsePrizeoutGiftCardsItem.builder()
                .name(prizeoutGiftResponsePrizeoutGiftCardsItemData.getName())
                .order(prizeoutGiftResponsePrizeoutGiftCardsItemData.getOrder())
                .image(prizeoutGiftResponsePrizeoutGiftCardsItemData.getImage())
                .region(prizeoutGiftResponsePrizeoutGiftCardsItemData.getRegion())
                .build();
        return prizeoutGiftResponsePrizeoutGiftCardsItem;

    }


}

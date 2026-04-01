package com.neo.v1.service;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.mapper.PrizeoutGiftResponseMapper;
import com.neo.v1.product.catalogue.model.GiftCardResponse;
import com.neo.v1.product.catalogue.model.PrizeoutGiftResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GiftCardCachingService {

    private final UnitService unitService;
    private final LanguageService languageService;
    private final ContentfulService contentfulService;

    private final PrizeoutGiftResponseMapper prizeoutGiftResponseMapper;


    public PrizeoutGiftResponse getGiftCard(String unit,String language) {
        String contentFulUnit = unitService.getUnitEntityByCode(unit).getContentfulValue();
        String contentFulLanguage = languageService.getLanguageEntityByCode(language).getContentfulValue();
        CDAArray giftsCards = contentfulService.getGiftsCards(contentFulUnit,contentFulLanguage);
        return prizeoutGiftResponseMapper.map(giftsCards);
    }

}
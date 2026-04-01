package com.neo.v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.mapper.DirectusOffersListDataMapper;
import com.neo.v1.product.catalogue.model.OffersListData;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OffersCachingService {
    private final UnitService unitService;
    private final LanguageService languageService;
    private final DirectusOffersListDataMapper offersListDataMapper;
    private final DirectusService directusService;

    @Cacheable("offersByUnitAndLang")
    public OffersListData getOffersByUnitAndLanguage(String language, String unit) {
        String contentFulLanguage = languageService.getLanguageEntityByCode(language).getContentfulValue();
        String contentFulUnit = unitService.getUnitEntityByCode(unit).getContentfulValue();
        JsonNode offers = directusService.getOffers(contentFulLanguage, contentFulUnit);
        JsonNode offersCategories = directusService.getOffersCategories(contentFulLanguage, contentFulUnit);
        JsonNode offersSubProducts = directusService.getOffersSubProducts(contentFulLanguage, contentFulUnit);
        return offersListDataMapper.map(offers, offersCategories, offersSubProducts);
    }
}

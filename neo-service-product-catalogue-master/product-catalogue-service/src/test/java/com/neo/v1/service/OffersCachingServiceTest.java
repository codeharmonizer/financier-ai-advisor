package com.neo.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.entity.LanguageEntity;
import com.neo.v1.entity.UnitEntity;
import com.neo.v1.mapper.OffersListDataMapper;
import com.neo.v1.product.catalogue.model.OffersListData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OffersCachingServiceTest {


    @InjectMocks
    private OffersCachingService subject;

    @Mock
    private ContentfulService contentfulService;

    @Mock
    private UnitService unitService;

    @Mock
    private LanguageService languageService;

    @Mock
    private OffersListDataMapper offersListDataMapper;


    @Test
    void getOffersByUnitAndLanguage_withUnitAndLanguage_returnOffersListData() {
        String language = "en";
        String unit = "neo";
        CDAArray offers = new CDAArray();
        CDAArray offersCategories = new CDAArray();
        CDAArray offersSubProducts = new CDAArray();
        OffersListData expected = OffersListData.builder().build();
        UnitEntity unitEntity = UnitEntity.builder().contentfulValue(unit).build();
        LanguageEntity languageEntity = LanguageEntity.builder().contentfulValue(language).build();

        when(contentfulService.getOffers(language, unit)).thenReturn(offers);
        when(contentfulService.getOffersCategories(language, unit)).thenReturn(offersCategories);
        when(contentfulService.getOffersSubProducts(language, unit)).thenReturn(offersSubProducts);
        when(unitService.getUnitEntityByCode(unit)).thenReturn(unitEntity);
        when(languageService.getLanguageEntityByCode(language)).thenReturn(languageEntity);
        when(offersListDataMapper.map(offers, offersCategories, offersSubProducts)).thenReturn(expected);


        OffersListData result = subject.getOffersByUnitAndLanguage(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulService).getOffers(language, unit);
        verify(contentfulService).getOffersCategories(language, unit);
        verify(contentfulService).getOffersSubProducts(language, unit);
        verify(offersListDataMapper).map(offers, offersCategories, offersSubProducts);
        verify(unitService).getUnitEntityByCode(unit);
        verify(languageService).getLanguageEntityByCode(language);
    }

}

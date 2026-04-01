package com.neo.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.entity.LanguageEntity;
import com.neo.v1.entity.UnitEntity;
import com.neo.v1.mapper.MerchantCategoryListDataMapper;
import com.neo.v1.mapper.MerchantCodeListDataMapper;
import com.neo.v1.mapper.MerchantListDataMapper;
import com.neo.v1.product.catalogue.model.CategoryListData;
import com.neo.v1.product.catalogue.model.MerchantCodeListData;
import com.neo.v1.product.catalogue.model.MerchantListData;

@ExtendWith(MockitoExtension.class)
class MerchantsCachingServiceTest {
	
	@InjectMocks
    private MerchantsCachingService subject;

    @Mock
    private ContentfulService contentfulService;

    @Mock
    private UnitService unitService;

    @Mock
    private LanguageService languageService;

    @Mock
    private MerchantCategoryListDataMapper merchantCategoryListDataMapper;
    
    @Mock
    private MerchantListDataMapper merchantListDataMapper;
    
    @Mock
    private MerchantCodeListDataMapper merchantCodeListDataMapper;

    @Test
    void getMerchantCategoryByUnitAndLanguage_withUnitAndLanguage_returnCategoryListData() {
        String language = "en";
        String unit = "neo";
        CDAArray offers = new CDAArray();
        CategoryListData expected = CategoryListData.builder().build();
        UnitEntity unitEntity = UnitEntity.builder().contentfulValue(unit).build();
        LanguageEntity languageEntity = LanguageEntity.builder().contentfulValue(language).build();

        when(contentfulService.getMerchantCategory(language, unit)).thenReturn(offers);
        when(unitService.getUnitEntityByCode(unit)).thenReturn(unitEntity);
        when(languageService.getLanguageEntityByCode(language)).thenReturn(languageEntity);
        when(merchantCategoryListDataMapper.map(offers)).thenReturn(expected);


        CategoryListData result = subject.getMerchantCategoryByUnitAndLanguage(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulService).getMerchantCategory(language, unit);
        verify(merchantCategoryListDataMapper).map(offers);
        verify(unitService).getUnitEntityByCode(unit);
        verify(languageService).getLanguageEntityByCode(language);
    }
    
    @Test
    void getMerchantsByUnitAndLanguage_withUnitAndLanguage_returnMerchantListData() {
        String language = "en";
        String unit = "neo";
        CDAArray offers = new CDAArray();
        MerchantListData expected = MerchantListData.builder().build();
        UnitEntity unitEntity = UnitEntity.builder().contentfulValue(unit).build();
        LanguageEntity languageEntity = LanguageEntity.builder().contentfulValue(language).build();

        when(contentfulService.getMerchants(language, unit)).thenReturn(offers);
        when(unitService.getUnitEntityByCode(unit)).thenReturn(unitEntity);
        when(languageService.getLanguageEntityByCode(language)).thenReturn(languageEntity);
        when(merchantListDataMapper.map(offers)).thenReturn(expected);


        MerchantListData result = subject.getMerchantsByUnitAndLanguage(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulService).getMerchants(language, unit);
        verify(merchantListDataMapper).map(offers);
        verify(unitService).getUnitEntityByCode(unit);
        verify(languageService).getLanguageEntityByCode(language);
    }
    
    @Test
    void getMerchantCodeByUnitAndLanguage_withUnitAndLanguage_returnMerchantCodeListData() {
        String language = "en";
        String unit = "neo";
        CDAArray offers = new CDAArray();
        MerchantCodeListData expected = MerchantCodeListData.builder().build();
        UnitEntity unitEntity = UnitEntity.builder().contentfulValue(unit).build();
        LanguageEntity languageEntity = LanguageEntity.builder().contentfulValue(language).build();

        when(contentfulService.getMerchantCode(language, unit)).thenReturn(offers);
        when(unitService.getUnitEntityByCode(unit)).thenReturn(unitEntity);
        when(languageService.getLanguageEntityByCode(language)).thenReturn(languageEntity);
        when(merchantCodeListDataMapper.map(offers)).thenReturn(expected);


        MerchantCodeListData result = subject.getMerchantCodeByUnitAndLanguage(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulService).getMerchantCode(language, unit);
        verify(merchantCodeListDataMapper).map(offers);
        verify(unitService).getUnitEntityByCode(unit);
        verify(languageService).getLanguageEntityByCode(language);
    }

}

package com.neo.v1.service;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.entity.LanguageEntity;
import com.neo.v1.entity.UnitEntity;
import com.neo.v1.mapper.AppBranchesListDataMapper;
import com.neo.v1.product.catalogue.model.AppBranchesListData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith ( MockitoExtension.class )
class AppBranchesCachingServiceTest {

    @InjectMocks
    private AppBranchesCachingService subject;
    @Mock
    private ContentfulService contentfulService;
    @Mock
    private UnitService unitService;
    @Mock
    private LanguageService languageService;
    @Mock
    private AppBranchesListDataMapper appBranchesListDataMapper;

    @Test
    void getAppBranchesByUnitAndLanguage_withUnitAndLanguage_returnOffersListData() {
        String language = "en";
        String unit = "neo";
        CDAArray appBranches = new CDAArray();
        AppBranchesListData expected = AppBranchesListData.builder().build();
        UnitEntity unitEntity = UnitEntity.builder().contentfulValue(unit).build();
        LanguageEntity languageEntity = LanguageEntity.builder().contentfulValue(language).build();

        when(contentfulService.getAppBranches(language, unit)).thenReturn(appBranches);
        when(unitService.getUnitEntityByCode(unit)).thenReturn(unitEntity);
        when(languageService.getLanguageEntityByCode(language)).thenReturn(languageEntity);
        when(appBranchesListDataMapper.map(appBranches)).thenReturn(expected);

        AppBranchesListData result = subject.getAppBranchesByUnitAndLanguage(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
        verify(contentfulService).getAppBranches(language, unit);
        verify(appBranchesListDataMapper).map(appBranches);
        verify(unitService).getUnitEntityByCode(unit);
        verify(languageService).getLanguageEntityByCode(language);
    }

}

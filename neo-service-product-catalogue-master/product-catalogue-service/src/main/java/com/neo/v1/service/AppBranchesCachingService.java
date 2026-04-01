package com.neo.v1.service;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.mapper.AppBranchesListDataMapper;
import com.neo.v1.product.catalogue.model.AppBranchesListData;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppBranchesCachingService {
    private final UnitService unitService;
    private final LanguageService languageService;
    private final AppBranchesListDataMapper appBranchesListDataMapper;
    private final ContentfulService contentfulService;

    @Cacheable ( "appBranchesByUnitAndLang" )
    public AppBranchesListData getAppBranchesByUnitAndLanguage(String language, String unit) {
        String contentFulLanguage = languageService.getLanguageEntityByCode(language).getContentfulValue();
        String contentFulUnit = unitService.getUnitEntityByCode(unit).getContentfulValue();
        CDAArray appBranches = contentfulService.getAppBranches(contentFulLanguage, contentFulUnit);
        return appBranchesListDataMapper.map(appBranches);
    }
}

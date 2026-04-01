package com.neo.v1.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.mapper.MerchantCategoryListDataMapper;
import com.neo.v1.mapper.MerchantCodeListDataMapper;
import com.neo.v1.mapper.MerchantListDataMapper;
import com.neo.v1.product.catalogue.model.CategoryListData;
import com.neo.v1.product.catalogue.model.MerchantCodeListData;
import com.neo.v1.product.catalogue.model.MerchantListData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantsCachingService {
    private final UnitService unitService;
    private final LanguageService languageService;
    private final MerchantCategoryListDataMapper merchantCategoryListDataMapper;
    private final MerchantListDataMapper merchantListDataMapper;
    private final MerchantCodeListDataMapper merchantCodeListDataMapper;
    private final ContentfulService contentfulService;

    @Cacheable("merchantCategoryByUnitAndLang")
    public CategoryListData getMerchantCategoryByUnitAndLanguage(String language, String unit) {
        String contentFulLanguage = languageService.getLanguageEntityByCode(language).getContentfulValue();
        String contentFulUnit = unitService.getUnitEntityByCode(unit).getContentfulValue();
        CDAArray merchants = contentfulService.getMerchantCategory(contentFulLanguage, contentFulUnit);
        return merchantCategoryListDataMapper.map(merchants);
    }

    public MerchantListData getMerchantsByUnitAndLanguage(String language, String unit) {
        String contentFulLanguage = languageService.getLanguageEntityByCode(language).getContentfulValue();
        String contentFulUnit = unitService.getUnitEntityByCode(unit).getContentfulValue();
        CDAArray merchants = contentfulService.getMerchants(contentFulLanguage, contentFulUnit);
        return merchantListDataMapper.map(merchants);
    }
    
    @Cacheable("merchantCodeByUnitAndLang")
    public MerchantCodeListData getMerchantCodeByUnitAndLanguage(String language, String unit) {
        String contentFulLanguage = languageService.getLanguageEntityByCode(language).getContentfulValue();
        String contentFulUnit = unitService.getUnitEntityByCode(unit).getContentfulValue();
        CDAArray merchants = contentfulService.getMerchantCode(contentFulLanguage, contentFulUnit);
        return merchantCodeListDataMapper.map(merchants);
    }
}

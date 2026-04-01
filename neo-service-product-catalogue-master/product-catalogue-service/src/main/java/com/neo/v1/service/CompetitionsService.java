package com.neo.v1.service;


import com.contentful.java.cda.CDAArray;

import com.neo.v1.cache.LanguageService;
import com.neo.v1.cache.UnitService;
import com.neo.v1.mapper.CompetitionListDataMapper;
import com.neo.v1.mapper.CompetitionListResponseMapper;
import com.neo.v1.product.catalogue.model.CompetitionListData;
import com.neo.v1.product.catalogue.model.CompetitionListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionsService {

    private final ContentfulService contentfulService;
    private final LanguageService languageService;
    private final UnitService unitService;
    private final CompetitionListResponseMapper competitionListResponseMapper;
    private final CompetitionListDataMapper competitionListDataMapper;
    public CompetitionListResponse getProductCatalogueCompetition(String language, String unit, String customerId) {
        String contentFulLanguage = languageService.getLanguageEntityByCode(language).getContentfulValue();
        String contentFulUnit = unitService.getUnitEntityByCode(unit).getContentfulValue();
        CDAArray cdaArray = contentfulService.getCompetitions(contentFulLanguage, contentFulUnit);
        CompetitionListData competitionListData = competitionListDataMapper.map(cdaArray);
        return competitionListResponseMapper.map(competitionListData);
    }
}
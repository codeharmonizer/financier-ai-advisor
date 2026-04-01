package com.neo.v1.mapper;

import com.neo.v1.product.catalogue.model.CompetitionListData;
import com.neo.v1.product.catalogue.model.CompetitionListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_COMPETITION_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_COMPETITION_SUCCESS_MESSAGE;

@Component
@RequiredArgsConstructor
public class CompetitionListResponseMapper {

    private final MetaMapper metaMapper;

    public CompetitionListResponse map(CompetitionListData competitionListData) {
    return  CompetitionListResponse.builder()
            .data(competitionListData)
            .meta(metaMapper.map(GET_COMPETITION_SUCCESS_CODE, GET_COMPETITION_SUCCESS_MESSAGE))
            .build();
    }
}

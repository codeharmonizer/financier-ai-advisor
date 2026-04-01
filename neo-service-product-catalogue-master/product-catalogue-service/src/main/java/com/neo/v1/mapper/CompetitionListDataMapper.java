package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.CompetitionData;
import com.neo.v1.product.catalogue.model.CompetitionListData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompetitionListDataMapper {

    private final CompetitionDataMapper competitionDataMapper;

    public CompetitionListData map(CDAArray cdaArray) {
        List<CompetitionData>  competitionDataList = competitionDataMapper.map(cdaArray);
        return CompetitionListData.builder()
                .Competitions(competitionDataList)
                .build();
    }
}

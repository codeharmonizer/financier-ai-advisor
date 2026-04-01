package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.CompetitionData;
import com.neo.v1.product.catalogue.model.CompetitionListData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetitionListDataMapperTest {

    @InjectMocks
    private CompetitionListDataMapper subject;

    @Mock
    private CompetitionDataMapper competitionDataMapper;


    @Test
    void map_withCDAArray_returnCompetitionListData() {
        CDAArray competition = new CDAArray();
        List<CompetitionData> competitionDataList = new ArrayList<>();

        CompetitionListData expected = CompetitionListData.builder()
                .Competitions(competitionDataList)
                .build();

        when(competitionDataMapper.map(competition)).thenReturn(competitionDataList);

        CompetitionListData result = subject.map(competition);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(competitionDataMapper).map(competition);
    }
}

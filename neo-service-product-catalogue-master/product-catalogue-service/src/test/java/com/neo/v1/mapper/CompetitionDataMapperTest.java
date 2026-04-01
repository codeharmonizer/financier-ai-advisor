package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.CompetitionData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetitionDataMapperTest {
    @InjectMocks
    private CompetitionDataMapper subject;

    @Test
    void map_withCDAArray_returnListOfCompetitionData() throws Exception {
        List<CompetitionData> competitionDataList = new ArrayList<>();

        CDAArray cdaArray = mock(CDAArray.class);
        CDAEntry cdaResource = mock(CDAEntry.class);

        List<CDAResource> cdaResourceList = Arrays.asList((CDAResource) cdaResource);
        when(cdaArray.items()).thenReturn(cdaResourceList);

        CompetitionData competitionData = CompetitionData.builder()
                .build();


        competitionDataList.add(competitionData);

        List<CompetitionData> result = subject.map(cdaArray);
        assertThat(result.get(0)).isEqualToComparingFieldByFieldRecursively(competitionData);
    }
}
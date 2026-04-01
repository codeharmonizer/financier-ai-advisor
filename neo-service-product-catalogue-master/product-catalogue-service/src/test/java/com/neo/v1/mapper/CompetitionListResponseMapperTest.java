package com.neo.v1.mapper;

import com.neo.v1.product.catalogue.model.CompetitionListData;
import com.neo.v1.product.catalogue.model.CompetitionListResponse;
import com.neo.v1.product.catalogue.model.Meta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_COMPETITION_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_COMPETITION_SUCCESS_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetitionListResponseMapperTest {

    @InjectMocks
    private CompetitionListResponseMapper subject;

    @Mock
    private MetaMapper metaMapper;

    @Test
    void map_withCompetitionList_returnCompetitionListResponseMapper() {
        Meta meta = Meta.builder().build();
        CompetitionListData competitionListData = CompetitionListData.builder().build();
        CompetitionListResponse expected = CompetitionListResponse.builder()
                .meta(meta)
                .data(competitionListData)
                .build();

        when(metaMapper.map(GET_COMPETITION_SUCCESS_CODE, GET_COMPETITION_SUCCESS_MESSAGE)).thenReturn(meta);

        CompetitionListResponse result = subject.map(competitionListData);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(metaMapper).map(GET_COMPETITION_SUCCESS_CODE, GET_COMPETITION_SUCCESS_MESSAGE);
    }
}

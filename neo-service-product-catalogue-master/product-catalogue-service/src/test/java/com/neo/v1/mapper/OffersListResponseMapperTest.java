package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_OFFERS_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_OFFERS_SUCCESS_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.neo.v1.product.catalogue.model.Meta;
import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.OffersListData;
import com.neo.v1.product.catalogue.model.OffersListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OffersListResponseMapperTest {

    @InjectMocks
    private OffersListResponseMapper subject;

    @Mock
    private MetaMapper metaMapper;
    @Mock
    private OffersListDataMapper offersListDataMapper;

    @Test
    void map_withOffersListData_returnOffersListResponse() {
        OffersListData offersListData = new OffersListData();
        Meta meta = Meta.builder().build();
        List<OfferDetails> offerDetailsList = new ArrayList<>();
        OffersListResponse expected = OffersListResponse.builder()
                .meta(meta)
                .data(offersListData)
                .build();

        when(metaMapper.map(GET_OFFERS_SUCCESS_CODE, GET_OFFERS_SUCCESS_MESSAGE)).thenReturn(meta);
        when(offersListDataMapper.map(offersListData, offerDetailsList)).thenReturn(offersListData);

        OffersListResponse result = subject.map(offersListData, offerDetailsList);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(metaMapper).map(GET_OFFERS_SUCCESS_CODE, GET_OFFERS_SUCCESS_MESSAGE);
        verify(offersListDataMapper).map(offersListData, offerDetailsList);
    }
}

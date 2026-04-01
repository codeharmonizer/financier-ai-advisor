package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_MERCHANTS_SUCCESS_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neo.v1.product.catalogue.model.MerchantListData;
import com.neo.v1.product.catalogue.model.MerchantListResponse;
import com.neo.v1.product.catalogue.model.Meta;

@ExtendWith(MockitoExtension.class)
class MerchantListResponseMapperTest {

	@InjectMocks
	private MerchantListResponseMapper subject;

	@Mock
	private MetaMapper metaMapper;

	@Test
	void map_withMerchantListData_return() {
        Meta meta = Meta.builder().build();
        MerchantListData merchantListData = MerchantListData.builder().build();
        MerchantListResponse expected = MerchantListResponse.builder()
                .meta(meta)
                .data(merchantListData)
                .build();

        when(metaMapper.map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE)).thenReturn(meta);

        MerchantListResponse result = subject.map(merchantListData);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(metaMapper).map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE);
    }

}

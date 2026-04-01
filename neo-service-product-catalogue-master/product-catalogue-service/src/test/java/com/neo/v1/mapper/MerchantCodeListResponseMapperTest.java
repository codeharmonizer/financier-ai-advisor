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

import com.neo.v1.product.catalogue.model.CategoryListResponse;
import com.neo.v1.product.catalogue.model.MerchantCodeListData;
import com.neo.v1.product.catalogue.model.MerchantCodeListResponse;
import com.neo.v1.product.catalogue.model.Meta;

@ExtendWith(MockitoExtension.class)
class MerchantCodeListResponseMapperTest {

	@InjectMocks
	private MerchantCodeListResponseMapper subject;

	@Mock
	private MetaMapper metaMapper;

	@Test
	void map_withMerchantCodeListData_return() {
        Meta meta = Meta.builder().build();
        MerchantCodeListData merchantCodeListData = MerchantCodeListData.builder().build();
        MerchantCodeListResponse expected = MerchantCodeListResponse.builder()
                .meta(meta)
                .data(merchantCodeListData)
                .build();

        when(metaMapper.map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE)).thenReturn(meta);

        MerchantCodeListResponse result = subject.map(merchantCodeListData);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(metaMapper).map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE);
    }

}

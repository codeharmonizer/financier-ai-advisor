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

import com.neo.v1.product.catalogue.model.CategoryListData;
import com.neo.v1.product.catalogue.model.CategoryListResponse;
import com.neo.v1.product.catalogue.model.Meta;

@ExtendWith(MockitoExtension.class)
class MerchantCategoryListResponseMapperTest {

	@InjectMocks
	private MerchantCategoryListResponseMapper subject;

	@Mock
	private MetaMapper metaMapper;

	@Test
	void map_withMerchantListData_returnOffersListResponse() {
        Meta meta = Meta.builder().build();
        CategoryListData categoryListData = CategoryListData.builder().build();
        CategoryListResponse expected = CategoryListResponse.builder()
                .meta(meta)
                .data(categoryListData)
                .build();

        when(metaMapper.map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE)).thenReturn(meta);

        CategoryListResponse result = subject.map(categoryListData);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(metaMapper).map(GET_MERCHANTS_SUCCESS_CODE, GET_MERCHANTS_SUCCESS_MESSAGE);
    }

}

package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;

import com.neo.v1.product.catalogue.model.Meta;

import com.neo.v1.product.catalogue.model.ProductListData;
import com.neo.v1.product.catalogue.model.ProductListResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_PRODUCT_LIST_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_PRODUCT_LIST_SUCCESS_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductListResponseMapperTest {

    @InjectMocks
    private ProductListResponseMapper subject;

    @Mock
    private ProductListDataMapper productListDataMapper;

    @Mock
    private MetaMapper metaMapper;

    @Test
    void map_withCDAArray_returnProductListResponse() {
        CDAArray cdaArray = new CDAArray();
        ProductListData productListData = new ProductListData();
        Meta meta = Meta.builder().build();
        ProductListResponse expected = ProductListResponse.builder()
                .meta(meta)
                .data(productListData)
                .build();

        when(productListDataMapper.map(cdaArray,cdaArray,cdaArray)).thenReturn(productListData);
        when(metaMapper.map(GET_PRODUCT_LIST_SUCCESS_CODE, GET_PRODUCT_LIST_SUCCESS_MESSAGE)).thenReturn(meta);

        ProductListResponse result = subject.map(cdaArray,cdaArray,cdaArray);
        assertThat(result).isNotNull();
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(productListDataMapper).map(cdaArray,cdaArray,cdaArray);
        verify(metaMapper).map(GET_PRODUCT_LIST_SUCCESS_CODE, GET_PRODUCT_LIST_SUCCESS_MESSAGE);
    }
}

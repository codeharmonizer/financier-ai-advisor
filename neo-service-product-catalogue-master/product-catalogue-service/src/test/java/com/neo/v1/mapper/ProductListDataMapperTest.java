package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;

import com.neo.v1.product.catalogue.model.AtmDetails;
import com.neo.v1.product.catalogue.model.BranchDetails;
import com.neo.v1.product.catalogue.model.ProductDetails;
import com.neo.v1.product.catalogue.model.ProductListData;

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
class ProductListDataMapperTest {

    @InjectMocks
    private ProductListDataMapper subject;

    @Mock
    private ProductDetailsMapper productDetailsMapper;

    @Mock
    private AtmDetailsMapper atmDetailsMapper;

    @Mock
    private BranchDetailsMapper branchDetailsMapper;

    @Test
    void map_withCDAArray_returnProductListData() {
        CDAArray cdaArray = new CDAArray();
        List<ProductDetails> productDetailsList = new ArrayList<>();
        List<AtmDetails> atmDetailsList = new ArrayList<>();
        List<BranchDetails> branchDetailsList = new ArrayList<>();
        ProductListData expected = ProductListData.builder()
                .casaProducts(productDetailsList)
                .branchesList(branchDetailsList)
                .atmList(atmDetailsList)
                .build();

        when(productDetailsMapper.map(cdaArray)).thenReturn(productDetailsList);
        when(branchDetailsMapper.map(cdaArray)).thenReturn(branchDetailsList);
        when(atmDetailsMapper.map(cdaArray)).thenReturn(atmDetailsList);

        ProductListData result = subject.map(cdaArray,cdaArray,cdaArray);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(productDetailsMapper).map(cdaArray);
    }
}

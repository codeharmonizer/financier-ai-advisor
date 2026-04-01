package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;

import com.neo.v1.product.catalogue.model.ProductBenefits;
import com.neo.v1.product.catalogue.model.ProductDetails;
import com.neo.v1.product.catalogue.model.ProductEligibility;
import com.neo.v1.product.catalogue.model.ProductFeatures;
import com.neo.v1.product.catalogue.model.ProductTnC;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductDetailsMapperTest {

    @InjectMocks
    private ProductDetailsMapper subject;

    @Mock
    private ProductEligibilityMapper productEligibilityMapper;

    @Mock
    private ProductFeaturesMapper productFeaturesMapper;

    @Mock
    private ProductBenefitsMapper productBenefitsMapper;

    @Mock
    private ProductTnCMapper productTnCMapper;

    @Test
    void map_withCDAArray_returnListOfProductDetails() throws Exception {
        List<ProductDetails> productDetailsList = new ArrayList<>();
        ProductBenefits productBenefits = new ProductBenefits();
        ProductFeatures productFeatures = new ProductFeatures();
        ProductEligibility productEligibility = new ProductEligibility();
        ProductTnC productTnC = new ProductTnC();

        String productCategoryCode = "Test product category code";
        String productCategory = "Test product category";
        String productDescription = "Test product description";
        String productName = "Test product name";
        String subProductCode = "Test sub product code";
        String productSegment = "Test product segment";

        String productId = "test id";

        CDAArray cdaArray = mock(CDAArray.class);
        CDAEntry cdaResource = mock(CDAEntry.class);
        cdaArray.items().add(cdaResource);
        List<CDAResource> cdaResourceList = Arrays.asList((CDAResource) cdaResource);

        when(cdaArray.total()).thenReturn(1);
        when(cdaArray.items()).thenReturn(cdaResourceList);

        when(productBenefitsMapper.map(cdaResource)).thenReturn(productBenefits);
        when(productEligibilityMapper.map(cdaResource)).thenReturn(productEligibility);
        when(productFeaturesMapper.map(cdaResource)).thenReturn(productFeatures);
        when(productTnCMapper.map(cdaResource)).thenReturn(productTnC);

        when(cdaResource.getField(any()))
                .thenReturn(productCategoryCode)
                .thenReturn(productCategory)
                .thenReturn(productDescription)
                .thenReturn(productName)
                .thenReturn(productSegment)
                .thenReturn(subProductCode);

        when(cdaResource.id()).thenReturn(productId);

        ProductDetails productDetails = ProductDetails.builder()
                .productBenefits(productBenefits)
                .productEligibility(productEligibility)
                .productFeatures(productFeatures)
                .productTermsAndConditions(productTnC)
                .productCategoryCode(productCategoryCode)
                .productCategory(productCategory)
                .productDescription(productDescription)
                .productName(productName)
                .productSegment(productSegment)
                .subProductCode(subProductCode)
                .productId(productId)
                .build();

        productDetailsList.add(productDetails);

        List<ProductDetails> result = subject.map(cdaArray);
        assertThat(result.get(0)).isEqualToComparingFieldByFieldRecursively(productDetails);

        verify(productBenefitsMapper).map(cdaResource);
        verify(productEligibilityMapper).map(cdaResource);
        verify(productFeaturesMapper).map(cdaResource);
        verify(productTnCMapper).map(cdaResource);
        verify(cdaResource).id();
    }
}

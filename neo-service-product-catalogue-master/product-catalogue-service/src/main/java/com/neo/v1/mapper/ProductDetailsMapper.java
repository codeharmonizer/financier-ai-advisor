package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;

import com.contentful.java.cda.CDAResource;
import com.neo.core.exception.ServiceException;
import lombok.RequiredArgsConstructor;

import com.neo.v1.product.catalogue.model.ProductDetails;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_CATEGORY;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_CATEGORY_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_DESCRIPTION;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_NAME;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_PRODUCT_SEGMENT;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_SUB_PRODUCT_CODE;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.PRODUCTS_NOT_FOUND;


@RequiredArgsConstructor
@Component
public class ProductDetailsMapper {
    private final ProductEligibilityMapper productEligibilityMapper;
    private final ProductFeaturesMapper productFeaturesMapper;
    private final ProductBenefitsMapper productBenefitsMapper;
    private final ProductTnCMapper productTnCMapper;

    public List<ProductDetails> map(CDAArray cdaArray) {
        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(PRODUCTS_NOT_FOUND);
        }
        return cdaArray.items().stream().map(this::map).collect(Collectors.toList());
    }

    private ProductDetails map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        return ProductDetails.builder()
                .productBenefits(productBenefitsMapper.map(entry))
                .productEligibility(productEligibilityMapper.map(entry))
                .productFeatures(productFeaturesMapper.map(entry))
                .productTermsAndConditions(productTnCMapper.map(entry))
                .productCategoryCode(entry.getField(PRODUCT_PRODUCT_CATEGORY_CODE))
                .productCategory(entry.getField(PRODUCT_PRODUCT_CATEGORY))
                .productDescription(entry.getField(PRODUCT_PRODUCT_DESCRIPTION))
                .productId(cdaResource.id())
                .productName(entry.getField(PRODUCT_PRODUCT_NAME))
                .productSegment(entry.getField(PRODUCT_PRODUCT_SEGMENT))
                .subProductCode(entry.getField(PRODUCT_SUB_PRODUCT_CODE))
                .build();
    }
}

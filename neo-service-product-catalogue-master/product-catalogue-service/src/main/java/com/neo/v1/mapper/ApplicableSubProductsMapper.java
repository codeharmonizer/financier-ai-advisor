package com.neo.v1.mapper;

import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_SUB_PRODUCT_DISPLAY_ORDER;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_SUB_PRODUCT_MAIN_PRODUCT;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_SUB_PRODUCT_OFFERS_TAGS;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_SUB_PRODUCT_OFFER_BENEFITS;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.OfferSubProduct;
import com.neo.v1.product.catalogue.model.SubProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicableSubProductsMapper {

    private final SubProductsLinkMapper subProductsMapper;

    public List<OfferSubProduct> map(List<CDAEntry> cdaArray, List<SubProducts> subProductsList) {
        final Map<String, String> subProductsMap = subProductsList.stream()
                .collect(Collectors.toMap(SubProducts::getName, SubProducts::getSubProductId));
        return cdaArray.stream().map(cdaEntry -> map(cdaEntry, subProductsMap)).collect(Collectors.toList());
    }

    private OfferSubProduct map(CDAResource cdaResource, Map<String, String> subProductsMap) {
        CDAEntry entry = (CDAEntry) cdaResource;
        Double displayOrder = entry.getField(OFFER_SUB_PRODUCT_DISPLAY_ORDER);
        return OfferSubProduct.builder()
                .mainProduct(entry.getField(OFFER_SUB_PRODUCT_MAIN_PRODUCT))
                .displayOrder(Objects.nonNull(displayOrder) ? displayOrder.longValue() : null)
                .offerBenefits(entry.getField(OFFER_SUB_PRODUCT_OFFER_BENEFITS))
                .tag(entry.getField(OFFER_SUB_PRODUCT_OFFERS_TAGS))
                .subProduct(subProductsMapper.map(entry, subProductsMap))
                .build();
    }


}

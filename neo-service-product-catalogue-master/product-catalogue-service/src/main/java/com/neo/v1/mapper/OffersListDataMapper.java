package com.neo.v1.mapper;


import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.constants.ProductCatalogueConstants.ALBURAQ_CARD_NAME;
import static com.neo.v1.constants.ProductCatalogueConstants.ALBURAQ_UNIT;

import java.util.List;
import java.util.stream.Collectors;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.OffersListData;
import com.neo.v1.product.catalogue.model.SubProducts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OffersListDataMapper {

    private final OfferDetailsMapper offerDetailsMapper;
    private final OfferCategoriesMapper offerCategoriesMapper;
    private final SubProductsMapper subProductsMapper;

    public OffersListData map(CDAArray offers, CDAArray offersCategories, CDAArray offersSubProducts) {
        List<SubProducts> subProductsList = subProductsMapper.map(offersSubProducts);
        return OffersListData
                .builder()
                .offers(offerDetailsMapper.map(offers, subProductsList))
                .offerCategories(offerCategoriesMapper.map(offersCategories))
                .SubProducts(subProductsList)
                .build();
    }

    public OffersListData map(OffersListData offersListData, List<OfferDetails> offerDetailsList) {
        return OffersListData
                .builder()
                .offers(offerDetailsList)
                .offerCategories(offersListData.getOfferCategories())
                .SubProducts(StringUtils.equals(getContext().getUnit(), ALBURAQ_UNIT) ? offersListData.getSubProducts().stream().
                        filter(subProducts -> StringUtils.equals(subProducts.getSubProductId(), ALBURAQ_CARD_NAME)).collect(Collectors.toList()) : offersListData.getSubProducts().stream().
                        filter(subProducts -> !StringUtils.equals(subProducts.getSubProductId(), ALBURAQ_CARD_NAME)).collect(Collectors.toList()))
                .build();
    }
}

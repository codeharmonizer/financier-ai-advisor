package com.neo.v1.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.neo.v1.product.catalogue.model.OffersListData;
import com.neo.v1.product.catalogue.model.SubProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DirectusOffersListDataMapper {

    private final DirectusOfferDetailsMapper offerDetailsMapper;
    private final DirectusOfferCategoriesMapper offerCategoriesMapper;
    private final DirectusSubProductsMapper subProductsMapper;

    public OffersListData map(JsonNode offers, JsonNode offersCategories, JsonNode offersSubProducts) {
        List<SubProducts> subProductsList = subProductsMapper.map(offersSubProducts);
        return OffersListData.builder()
                .offers(offerDetailsMapper.map(offers, subProductsList))
                .offerCategories(offerCategoriesMapper.map(offersCategories))
                .SubProducts(subProductsList)
                .build();
    }
}

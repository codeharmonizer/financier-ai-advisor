package com.neo.v1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.contentful.java.cda.CDAArray;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.product.catalogue.model.OfferCategory;
import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.OffersListData;
import com.neo.v1.product.catalogue.model.SubProducts;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OffersListDataMapperTest {

    private static final String UNIT = "alburaq";
    private static final String LANGUAGE = "en";

    @InjectMocks
    private OffersListDataMapper subject;

    @Mock
    private OfferDetailsMapper offerDetailsMapper;

    @Mock
    private OfferCategoriesMapper offerCategoriesMapper;

    @Mock
    private SubProductsMapper subProductsMapper;
    @BeforeAll
    static void setupContext() {
        GenericRestParamDto genericRestParam = GenericRestParamDto.builder()
                .unit(UNIT)
                .locale(new Locale(LANGUAGE))
                .build();

        GenericRestParamContextHolder.setContext(genericRestParam);
    }
    @Test
    void map_withCDAArray_returnOffersListData() {
        CDAArray offers = new CDAArray();
        CDAArray offersCategories = new CDAArray();
        CDAArray offersSubProducts = new CDAArray();
        List<OfferDetails> offerDetailsList = new ArrayList<>();
        List<OfferCategory> offerCategories = new ArrayList<>();
        List<SubProducts> subProducts = new ArrayList<>();
        OffersListData expected = OffersListData.builder()
                .offers(offerDetailsList)
                .offerCategories(offerCategories)
                .SubProducts(subProducts)
                .build();

        when(offerDetailsMapper.map(offers,subProducts)).thenReturn(offerDetailsList);
        when(offerCategoriesMapper.map(offersCategories)).thenReturn(offerCategories);
        when(subProductsMapper.map(offersSubProducts)).thenReturn(subProducts);

        OffersListData result = subject.map(offers, offersCategories, offersSubProducts);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(offerDetailsMapper).map(offers,subProducts);
        verify(offerCategoriesMapper).map(offersCategories);
        verify(subProductsMapper).map(offersSubProducts);
    }


    @Test
    void map_withOffersListData_returnOffersListData() {

        List<OfferDetails> offerDetailsList = new ArrayList<>();
        List<OfferDetails> offerDetailsList1 = new ArrayList<>();
        List<OfferCategory> offerCategories = new ArrayList<>();
        List<SubProducts> subProducts = new ArrayList<>();
        SubProducts subProducts1 = SubProducts.builder().subProductId("AlburaqDebitClassic").build();
        subProducts.add(subProducts1);
        OffersListData offersListData = OffersListData.builder()
                .offers(offerDetailsList)
                .offerCategories(offerCategories)
                .SubProducts(subProducts)
                .build();
        OffersListData expected = OffersListData.builder()
                .offers(offerDetailsList1)
                .offerCategories(offerCategories)
                .SubProducts(subProducts)
                .build();


        OffersListData result = subject.map(offersListData, offerDetailsList1);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}

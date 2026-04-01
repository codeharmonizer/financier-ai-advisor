package com.neo.v1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.OfferSubProduct;
import com.neo.v1.product.catalogue.model.SubProducts;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfferDetailsMapperTest {
    private static final String UNIT = "alburaq";
    private static final String LANGUAGE = "en";

    @InjectMocks
    private OfferDetailsMapper subject;

    @Mock
    private ApplicableSubProductsMapper applicableSubProductsMapper;

    @Mock
    private CategoryLinkMapper categoryLinkMapper;

    @BeforeAll
    static void setupContext() {
        GenericRestParamDto genericRestParam = GenericRestParamDto.builder()
                .unit(UNIT)
                .locale(new Locale(LANGUAGE))
                .build();

        GenericRestParamContextHolder.setContext(genericRestParam);
    }

    @Test
    void map_withCDAArray_returnListOfOfferDetails() throws Exception {
        List<OfferDetails> offerDetailsArrayList = new ArrayList<>();

        CDAArray cdaArray = mock(CDAArray.class);
        CDAEntry cdaResource = mock(CDAEntry.class);
        CDAAsset cdaAsset = mock(CDAAsset.class);

        List<CDAResource> cdaResourceList = Arrays.asList((CDAResource) cdaResource);
        List<OfferSubProduct> subProducts = new ArrayList<>();
        List<SubProducts> subProductsList = new ArrayList<>();
        when(cdaArray.items()).thenReturn(cdaResourceList);

        OfferDetails offerDetails = OfferDetails.builder()
                .offerApplicableSubProducts(subProducts)
                .build();


        offerDetailsArrayList.add(offerDetails);

        List<OfferDetails> result = subject.map(cdaArray, subProductsList);
        assertThat(result.get(0)).isEqualToComparingFieldByFieldRecursively(offerDetails);
    }
}
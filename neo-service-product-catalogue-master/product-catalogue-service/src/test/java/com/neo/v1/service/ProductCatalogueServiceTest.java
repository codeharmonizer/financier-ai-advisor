package com.neo.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.contentful.java.cda.CDAArray;
import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.mapper.*;
import com.neo.v1.product.catalogue.model.*;
import com.neo.v1.mapper.ApiSuccessWithoutDataMapper;
import com.neo.v1.mapper.AppBranchesListResponseMapper;
import com.neo.v1.mapper.MerchantCategoryListResponseMapper;
import com.neo.v1.mapper.MerchantCodeListResponseMapper;
import com.neo.v1.mapper.MerchantListResponseMapper;
import com.neo.v1.mapper.OffersListResponseMapper;
import com.neo.v1.mapper.ProductListResponseMapper;
import com.neo.v1.product.catalogue.model.ApiSuccessWithoutData;
import com.neo.v1.product.catalogue.model.AppBranchDetails;
import com.neo.v1.product.catalogue.model.AppBranchesListData;
import com.neo.v1.product.catalogue.model.AppBranchesListResponse;
import com.neo.v1.product.catalogue.model.CategoryDetail;
import com.neo.v1.product.catalogue.model.CategoryListData;
import com.neo.v1.product.catalogue.model.CategoryListResponse;
import com.neo.v1.product.catalogue.model.MerchantCodeDetail;
import com.neo.v1.product.catalogue.model.MerchantCodeListData;
import com.neo.v1.product.catalogue.model.MerchantCodeListResponse;
import com.neo.v1.product.catalogue.model.MerchantDetail;
import com.neo.v1.product.catalogue.model.MerchantListData;
import com.neo.v1.product.catalogue.model.MerchantListResponse;
import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.OffersListData;
import com.neo.v1.product.catalogue.model.OffersListResponse;
import com.neo.v1.product.catalogue.model.ProductListResponse;
import com.neo.v1.util.OffersUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith ( MockitoExtension.class )
class ProductCatalogueServiceTest {

    private static final String UNIT = "neo";
    private static final String LANGUAGE = "en";

    @InjectMocks
    private ProductCatalogueService subject;
    @Mock
    private ContentfulService contentfulService;
    @Mock
    private OffersListResponseMapper offersListResponseMapper;
    @Mock
    private ProductListResponseMapper productListResponseMapper;
    @Mock
    private OffersUtil offersUtil;
    @Mock
    private ApiSuccessWithoutDataMapper apiSuccessWithoutDataMapper;
    @Mock
    private OffersCachingService offersCachingService;
    @Mock
    private AppBranchesCachingService appBranchesCachingService;
    @Mock
    private AppBranchesListResponseMapper appBranchesListResponseMapper;

    @Mock
    private GiftCardCachingService  giftCardCachingService;

    @Mock
    private  GiftCardResponseMapper giftCardResponseMapper;
    @Mock
    private MerchantsCachingService merchantsCachingService;

    @Mock
    private MerchantCategoryListResponseMapper merchantCategoryListResponseMapper;
    
    @Mock
    private MerchantListResponseMapper merchantListResponseMapper;
    
    @Mock
    private MerchantCodeListResponseMapper merchantCodeListResponseMapper;

    @BeforeAll
    static void setupContext() {
        GenericRestParamDto genericRestParam = GenericRestParamDto.builder()
                .unit(UNIT)
                .locale(new Locale(LANGUAGE))
                .build();

        GenericRestParamContextHolder.setContext(genericRestParam);
    }

    @Test
    void getProducts_returnProductListResponse() {

        CDAArray productArray = new CDAArray();
        CDAArray atmArray = new CDAArray();
        CDAArray branchArray = new CDAArray();

        final ProductListResponse expected = ProductListResponse.builder().build();

        when(contentfulService.getProducts()).thenReturn(productArray);
        when(contentfulService.getAtmsDetails()).thenReturn(atmArray);
        when(contentfulService.getBranchesDetails()).thenReturn(branchArray);
        when(productListResponseMapper.map(productArray, atmArray, branchArray)).thenReturn(expected);

        ProductListResponse result = subject.getProducts();

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulService).getProducts();
        verify(contentfulService).getAtmsDetails();
        verify(contentfulService).getBranchesDetails();
        verify(productListResponseMapper).map(productArray, atmArray, branchArray);
    }


    @Test
    void getOffers_returnOffersListResponse() {
        String offerCategory = " ";
        String offerProduct = " ";
        String offerSubProduct = " ";
        boolean isNewOfferOnly = false;
        List<OfferDetails> offerDetailsList = new ArrayList<>();
        OffersListData offersListData = OffersListData.builder().offers(offerDetailsList).build();
        final OffersListResponse expected = OffersListResponse.builder().build();

        when(offersCachingService.getOffersByUnitAndLanguage(LANGUAGE, UNIT)).thenReturn(offersListData);
        when(offersListResponseMapper.map(eq(offersListData), any())).thenReturn(expected);

        OffersListResponse result = subject.getOffers(offerCategory, offerProduct, offerSubProduct, isNewOfferOnly);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(offersCachingService).getOffersByUnitAndLanguage(LANGUAGE, UNIT);
        verify(offersListResponseMapper).map(eq(offersListData), any());
    }

    @Test
    void purgeData_withoutParameters_removeCache() {
        ApiSuccessWithoutData apiSuccessWithoutData = ApiSuccessWithoutData.builder().build();
        when(apiSuccessWithoutDataMapper.map()).thenReturn(apiSuccessWithoutData);

        ApiSuccessWithoutData result = subject.purgeData();

        assertThat(result).isEqualTo(apiSuccessWithoutData);
        verify(apiSuccessWithoutDataMapper).map();
    }

    @Test
    void getAppBranches_returnOffersListResponse() {
        List<AppBranchDetails> appBranchDetailsList = new ArrayList<>();
        AppBranchesListData appBranchesListData = AppBranchesListData.builder().branches(appBranchDetailsList).build();
        final AppBranchesListResponse expected = AppBranchesListResponse.builder().build();

        when(appBranchesCachingService.getAppBranchesByUnitAndLanguage(LANGUAGE, UNIT)).thenReturn(appBranchesListData);
        when(appBranchesListResponseMapper.map(eq(appBranchesListData))).thenReturn(expected);

        AppBranchesListResponse result = subject.getAppBranches();

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
        verify(appBranchesCachingService).getAppBranchesByUnitAndLanguage(LANGUAGE, UNIT);
        verify(appBranchesListResponseMapper).map(eq(appBranchesListData));
    }


    @Test
    void getPrizeOut_GiftCardResponse() {

        List<PrizeoutGiftResponsePrizeoutGiftCardsItem> prizeoutGiftCardsList = new ArrayList<>();

        PrizeoutGiftResponse prizeoutGiftResponseData = PrizeoutGiftResponse.builder().prizeoutGiftCards(prizeoutGiftCardsList).build();

        final GiftCardResponse expected = GiftCardResponse.builder().build();

        when(giftCardCachingService.getGiftCard( UNIT,LANGUAGE)).thenReturn(prizeoutGiftResponseData);

        when(giftCardResponseMapper.map(eq(prizeoutGiftResponseData))).thenReturn(expected);

        GiftCardResponse result = subject.getProductCataloguePrizeoutGiftCards();

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(giftCardCachingService).getGiftCard(UNIT,LANGUAGE);

        verify(giftCardResponseMapper).map(eq(prizeoutGiftResponseData));
    }


    @Test
    void getMerchantCategory_returnCategoryListResponse() {
        List<CategoryDetail> categoryDetailList = new ArrayList<>();
        CategoryListData categoryListData = CategoryListData.builder().merchantCategories(categoryDetailList).build();
        CategoryListResponse expected = CategoryListResponse.builder().build();

        when(merchantsCachingService.getMerchantCategoryByUnitAndLanguage(LANGUAGE, UNIT)).thenReturn(categoryListData);
        when(merchantCategoryListResponseMapper.map(categoryListData)).thenReturn(expected);

        CategoryListResponse result = subject.getMerchantCategory();

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(merchantsCachingService).getMerchantCategoryByUnitAndLanguage(LANGUAGE, UNIT);
        verify(merchantCategoryListResponseMapper).map(categoryListData);
    }
    
    @Test
    void getMerchants_returnMerchantListResponse() {
        List<MerchantDetail> merchantDetailList = new ArrayList<>();
        MerchantListData merchantListData = MerchantListData.builder().merchants(merchantDetailList).build();
        MerchantListResponse expected = MerchantListResponse.builder().build();
    
        when(merchantsCachingService.getMerchantsByUnitAndLanguage(LANGUAGE, UNIT)).thenReturn(merchantListData);
        when(merchantListResponseMapper.map(merchantListData)).thenReturn(expected);

        MerchantListResponse result = subject.getMerchants();

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(merchantsCachingService).getMerchantsByUnitAndLanguage(LANGUAGE, UNIT);
        verify(merchantListResponseMapper).map(merchantListData);
    }
    
    @Test
    void getMerchantCode_returnMerchantCodeListResponse() {
        List<MerchantCodeDetail> MerchantCodeDetailList = new ArrayList<>();
        MerchantCodeListData merchantListData = MerchantCodeListData.builder().merchantCodes(MerchantCodeDetailList).build();
        MerchantCodeListResponse expected = MerchantCodeListResponse.builder().build();
    
        when(merchantsCachingService.getMerchantCodeByUnitAndLanguage(LANGUAGE, UNIT)).thenReturn(merchantListData);
        when(merchantCodeListResponseMapper.map(merchantListData)).thenReturn(expected);

        MerchantCodeListResponse result = subject.getMerchantCode();

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(merchantsCachingService).getMerchantCodeByUnitAndLanguage(LANGUAGE, UNIT);
        verify(merchantCodeListResponseMapper).map(merchantListData);
    }
}

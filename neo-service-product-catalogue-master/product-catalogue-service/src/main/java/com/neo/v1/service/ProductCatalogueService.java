package com.neo.v1.service;


import com.contentful.java.cda.CDAArray;
import com.neo.v1.mapper.*;
import com.neo.v1.product.catalogue.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.util.OffersUtil.getFilteredOffers;

@Service
@RequiredArgsConstructor
public class ProductCatalogueService {

    private final ContentfulService contentfulService;
    private final OffersListResponseMapper offersListResponseMapper;
    private final ProductListResponseMapper productListResponseMapper;
    private final ApiSuccessWithoutDataMapper apiSuccessWithoutDataMapper;
    private final OffersCachingService offersCachingService;
    private final AppBranchesCachingService appBranchesCachingService;
    private final AppBranchesListResponseMapper appBranchesListResponseMapper;
    private final GiftCardCachingService giftCardCachingService;

    private final GiftCardResponseMapper giftCardResponseMapper;
    private final MerchantsCachingService merchantsCachingService;
    private final MerchantCategoryListResponseMapper merchantCategoryListResponseMapper;
    private final MerchantListResponseMapper merchantListResponseMapper;
    private final MerchantCodeListResponseMapper merchantCodeListResponseMapper;
    private final CompetitionsService competitionsService;

    public AppBranchesListResponse getAppBranches() {
        AppBranchesListData appBranchesListData = appBranchesCachingService.getAppBranchesByUnitAndLanguage(getContext().getLocale().getLanguage(), getContext().getUnit());
        return appBranchesListResponseMapper.map(appBranchesListData);
    }

    public OffersListResponse getOffers(String offerCategory, String offerProduct, String offerSubProduct, boolean isNewOfferOnly) {
        OffersListData offersListData = offersCachingService.getOffersByUnitAndLanguage(getContext().getLocale().getLanguage(), getContext().getUnit());
        List<OfferDetails> offerDetailsList = getFilteredOffers(offersListData, offerCategory, offerProduct, offerSubProduct, isNewOfferOnly);
        return offersListResponseMapper.map(offersListData, offerDetailsList);
    }

    public ProductListResponse getProducts() {
        CDAArray productArray;
        CDAArray atmArray;
        CDAArray branchesArray;
        try {
            productArray = contentfulService.getProducts();
        } catch (Exception e) {
            productArray = null;
        }
        try {
            atmArray = contentfulService.getAtmsDetails();
        } catch (Exception e) {
            atmArray = null;
        }
        try {
            branchesArray = contentfulService.getBranchesDetails();
        } catch (Exception e) {
            branchesArray = null;
        }
        return productListResponseMapper.map(productArray, atmArray, branchesArray);
    }

    @CacheEvict(allEntries = true, cacheNames = {"offersByUnitAndLang", "appBranchesByUnitAndLang"})
    public ApiSuccessWithoutData purgeData() {
        return apiSuccessWithoutDataMapper.map();
    }

    @CacheEvict(allEntries = true, cacheNames = {"prizeGiftCards"})
    public ApiSuccessWithoutData deletegiftcardpurgeData() {
        return apiSuccessWithoutDataMapper.sucessmap();
    }


    public GiftCardResponse getProductCataloguePrizeoutGiftCards() {
        PrizeoutGiftResponse prizeoutGiftResponseData = giftCardCachingService.getGiftCard(getContext().getUnit(), getContext().getLocale().getLanguage());
        return giftCardResponseMapper.map(prizeoutGiftResponseData);
    }

    public CompetitionListResponse getProductCatalogueCompetition(String language, String unit, String customerId) {
        return competitionsService.getProductCatalogueCompetition(language, unit, customerId);
    }

    public CategoryListResponse getMerchantCategory() {
        CategoryListData categoryListData = merchantsCachingService.getMerchantCategoryByUnitAndLanguage(getContext().getLocale().getLanguage(), getContext().getUnit());
        return merchantCategoryListResponseMapper.map(categoryListData);
    }

    public MerchantListResponse getMerchants() {
        MerchantListData merchantListData = merchantsCachingService.getMerchantsByUnitAndLanguage(getContext().getLocale().getLanguage(), getContext().getUnit());
        CategoryListData categoryListData = merchantsCachingService.getMerchantCategoryByUnitAndLanguage(getContext().getLocale().getLanguage(), getContext().getUnit());
        Map<String, CategoryDetail> mapCategoryData = Optional.ofNullable(categoryListData).map(CategoryListData::getMerchantCategories).map(list -> list.stream().collect(Collectors.toMap(CategoryDetail::getId, Function.identity()))).orElse(Collections.emptyMap());
        if (Objects.nonNull(merchantListData)) {
            List<MerchantDetail> merchantDetailList = merchantListData.getMerchants().stream()
                    .filter(x -> Objects.nonNull(x.getContentfulMerchantCategory()))
                    .collect(Collectors.toList());
            for (MerchantDetail merchantDetail : merchantDetailList) {
                merchantDetail.setContentfulMerchantCategory(mapCategoryData.get(merchantDetail.getContentfulMerchantCategory().getId()));
            }
        }
        return merchantListResponseMapper.map(merchantListData);
    }

    public MerchantCodeListResponse getMerchantCode() {
        MerchantCodeListData merchantCodeListData = merchantsCachingService.getMerchantCodeByUnitAndLanguage(getContext().getLocale().getLanguage(), getContext().getUnit());
        CategoryListData categoryListData = merchantsCachingService.getMerchantCategoryByUnitAndLanguage(getContext().getLocale().getLanguage(), getContext().getUnit());
        Map<String, CategoryDetail> mapCategoryData = Optional.ofNullable(categoryListData).map(CategoryListData::getMerchantCategories).map(list -> list.stream().collect(Collectors.toMap(CategoryDetail::getId, Function.identity()))).orElse(Collections.emptyMap());
        if (Objects.nonNull(merchantCodeListData)) {
            List<MerchantCodeDetail> merchantDetailList = merchantCodeListData.getMerchantCodes().stream()
                    .filter(x -> Objects.nonNull(x.getContentfulMerchantCategory()))
                    .collect(Collectors.toList());
            for (MerchantCodeDetail merchantDetail : merchantDetailList) {
                merchantDetail.setContentfulMerchantCategory(mapCategoryData.get(merchantDetail.getContentfulMerchantCategory().getId()));
            }
        }
        return merchantCodeListResponseMapper.map(merchantCodeListData);
    }

    @CacheEvict(allEntries = true, cacheNames = {"merchantCategoryByUnitAndLang", "merchantsByUnitAndLang", "merchantCodeByUnitAndLang"})
    public ApiSuccessWithoutData purgeMerchantData() {
        return apiSuccessWithoutDataMapper.map();
    }

}
package com.neo.v1.reader;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertyReader {
    @Value("${contentful.accessToken}")
    private String contentfulAccessToken;

    @Value("${contentful.spaceId}")
    private String contentfulSpaceId;

    @Value("${contentful.environmentId}")
    private String contentfulEnvironmentId;

    @Value("${contentful.ilabankspace.accessToken}")
    private String ilabankspacecontentfulAccessToken;

    @Value("${contentful.ilabankspace.spaceId}")
    private String ilabankspacecontentfulSpaceId;

    @Value("${contentful.ilabankspace.environmentId}")
    private String ilabankspacecontentfulEnviron;

    // Directus (optional, for migration from Contentful)
    @Value("${directus.baseUrl:http://localhost:8055}")
    private String directusBaseUrl;

    @Value("${directus.token:}")
    private String directusToken;

    @Value("${directus.collections.offers:cf_offers}")
    private String directusOffersCollection;

    @Value("${directus.collections.products:cf_product}")
    private String directusProductsCollection;

    @Value("${directus.collections.atm:cf_openbankingatminfo}")
    private String directusAtmCollection;

    @Value("${directus.collections.branches:cf_openbankingbranchinfo}")
    private String directusBranchesCollection;

    @Value("${directus.collections.offersCategory:cf_offercategory}")
    private String directusOffersCategoryCollection;

    @Value("${directus.collections.offersSubProducts:cf_subproduct}")
    private String directusOffersSubProductsCollection;

    @Value("${directus.collections.competitions:cf_ilacompetition}")
    private String directusCompetitionsCollection;

    @Value("${directus.collections.appBranches:cf_appbranches}")
    private String directusAppBranchesCollection;

    @Value("${directus.collections.merchantCategory:cf_merchantcategory}")
    private String directusMerchantCategoryCollection;

    @Value("${directus.collections.merchants:cf_merchants}")
    private String directusMerchantsCollection;

    @Value("${directus.collections.merchantCode:cf_mcccode}")
    private String directusMerchantCodeCollection;

}
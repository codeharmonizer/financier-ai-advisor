package com.neo.v1.service;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.neo.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_GOVERNORATES;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_LANGUAGE_PARAMETER;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_UNIT_SEARCH_PARAMETER;
import static com.neo.v1.constants.ProductCatalogueConstants.ATM;
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCHES;
import static com.neo.v1.constants.ProductCatalogueConstants.CATEGORY_ORDER_CONDITION_VALUE;
import static com.neo.v1.constants.ProductCatalogueConstants.ILA_COMPETITION;
import static com.neo.v1.constants.ProductCatalogueConstants.LANGUAGE_PARAMETER;
import static com.neo.v1.constants.ProductCatalogueConstants.MAX_LIMIT;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_CATEGORY;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_LANGUAGE_PARAMETER;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_ORDER_CONDITION_VALUE;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_ORDER_PARAMETER;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_SUB_PRODUCT;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS_UNIT_SEARCH_PARAMETER;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT;
import static com.neo.v1.constants.ProductCatalogueConstants.SUB_PRODUCT_ORDER_CONDITION_VALUE;
import static com.neo.v1.constants.ProductCatalogueConstants.UNIT_SEARCH_PARAMETER;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.COMPETITION_INTEGRATION_FAILING;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.COMPETITION_NOT_FOUND;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.APP_BRANCHES_INTEGRATION_FAILING;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.APP_BRANCHES_NOT_FOUND;
import static com.neo.v1.constants.ProductCatalogueConstants.MERCHANT_CATEGORY;
import static com.neo.v1.constants.ProductCatalogueConstants.MERCHANTS;
import static com.neo.v1.constants.ProductCatalogueConstants.MCC_CODE;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.OFFERS_INTEGRATION_FAILING;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.OFFERS_NOT_FOUND;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.PRODUCTS_INTEGRATION_FAILING;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.PRODUCTS_NOT_FOUND;
import static com.neo.v1.constants.ProductCatalogueConstants.*;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.*;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.MERCHANTS_NOT_FOUND;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.MERCHANTS_INTEGRATION_FAILING;

@Service
@Slf4j
public class ContentfulService {
    private final CDAClient contentfulCDAClient;

    private final CDAClient ilabankcdaClient;

    @Autowired
    public ContentfulService(CDAClient contentfulCDAClient, @Qualifier("giftCard") CDAClient ilabankcdaClient) {
        this.contentfulCDAClient = contentfulCDAClient;
        this.ilabankcdaClient = ilabankcdaClient;
    }

    public CDAArray getOffers(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class)
                    .where(OFFERS_UNIT_SEARCH_PARAMETER, unit)
                    .where(OFFERS_LANGUAGE_PARAMETER, language)
                    .where(OFFERS_ORDER_PARAMETER, OFFERS_ORDER_CONDITION_VALUE)
                    .limit(MAX_LIMIT)
                    .withContentType(OFFERS).all();
        } catch (Exception exception) {
            throw new ServiceException(OFFERS_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(OFFERS_NOT_FOUND);
        }
        log.info("Offers retrieved from contentful");
        return cdaArray;

    }


    public CDAArray getProducts() {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class).withContentType(PRODUCT).all();
        } catch (Exception exception) {
            throw new ServiceException(PRODUCTS_INTEGRATION_FAILING, exception);
        }
        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(PRODUCTS_NOT_FOUND);
        }
        return cdaArray;
    }

    public CDAArray getAtmsDetails() {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class).withContentType(ATM).all();
        } catch (Exception exception) {
            throw new ServiceException(PRODUCTS_INTEGRATION_FAILING, exception);
        }
        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(PRODUCTS_NOT_FOUND);
        }
        return cdaArray;
    }

    public CDAArray getBranchesDetails() {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class).withContentType(BRANCHES).all();
        } catch (Exception exception) {
            throw new ServiceException(PRODUCTS_INTEGRATION_FAILING, exception);
        }
        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(PRODUCTS_NOT_FOUND);
        }
        return cdaArray;
    }

    public CDAArray getOffersCategories(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class)
                    .where(OFFERS_LANGUAGE_PARAMETER, language)
                    .where(OFFERS_ORDER_PARAMETER, CATEGORY_ORDER_CONDITION_VALUE)
                    .withContentType(OFFERS_CATEGORY).all();
        } catch (Exception exception) {
            throw new ServiceException(OFFERS_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(OFFERS_NOT_FOUND);
        }
        return cdaArray;

    }

    public CDAArray getOffersSubProducts(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class)
                    //.where(UNIT_SEARCH_PARAMETER, unit)
                    .where(OFFERS_LANGUAGE_PARAMETER, language)
                    .where(OFFERS_ORDER_PARAMETER, SUB_PRODUCT_ORDER_CONDITION_VALUE)
                    .withContentType(OFFERS_SUB_PRODUCT).all();
        } catch (Exception exception) {
            throw new ServiceException(OFFERS_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(OFFERS_NOT_FOUND);
        }
        return cdaArray;

    }

    public CDAArray getCompetitions(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class)
                    .where(UNIT_SEARCH_PARAMETER, unit)
                    .where(LANGUAGE_PARAMETER, language)
                    .limit(MAX_LIMIT)
                    .withContentType(ILA_COMPETITION).all();
        } catch (Exception exception) {
            throw new ServiceException(COMPETITION_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(COMPETITION_NOT_FOUND);
        }
        log.info("Competition retrieved from contentful");
        return cdaArray;

    }

    public CDAArray getAppBranches(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = contentfulCDAClient.fetch(CDAEntry.class)
                    .where(APP_BRANCHES_UNIT_SEARCH_PARAMETER, unit)
                    .where(APP_BRANCHES_LANGUAGE_PARAMETER, language)
                    .limit(MAX_LIMIT)
                    .withContentType(APP_BRANCHES).all();
        } catch (Exception exception) {
            throw new ServiceException(APP_BRANCHES_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(APP_BRANCHES_NOT_FOUND);
        }
        log.info("Branches retrieved from contentful");
        return cdaArray;
    }

    public CDAArray getGiftsCards(String unit,String language) {
        CDAArray cdaArray = null;
        try {
            cdaArray = ilabankcdaClient.fetch(CDAEntry.class)
                     .where(PRIZE_OUT_GIFT_CARD_LANGUAGE_PARAMETER,language)
                     .where(PRIZE_GIFT_UNIT_SEARCH_PARAMETER, unit)
                     .limit(MAX_LIMIT)
                     .withContentType(GIFTS).all();
        } catch (Exception exception) {
            log.info(exception.getMessage());
            throw new ServiceException(OFFERS_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(OFFERS_NOT_FOUND);
        }
        log.info("Gift cards  retrieved from contentful");
        return cdaArray;

    }



    public CDAArray getMerchantCategory(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = ilabankcdaClient.fetch(CDAEntry.class)
                    .where(UNIT_SEARCH_PARAMETER, unit.toUpperCase())
                    .where(LANGUAGE_PARAMETER, language)
                    .where(MERCHANT_ORDER_PARAMETER, MERCHANT_ORDER_CONDITION_VALUE)
                    .limit(MAX_LIMIT)
                    .withContentType(MERCHANT_CATEGORY).all();
        } catch (Exception exception) {
            throw new ServiceException(MERCHANTS_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(MERCHANTS_NOT_FOUND);
        }
        log.info("Merchant Category retrieved from contentful");
        return cdaArray;

    }
    
    public CDAArray getMerchants(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = ilabankcdaClient.fetch(CDAEntry.class)
                    .where(UNIT_SEARCH_PARAMETER, unit.toUpperCase())
                    .where(LANGUAGE_PARAMETER, language)
                    .where(MERCHANT_ORDER_PARAMETER, MERCHANT_ORDER_CONDITION_VALUE)
                    .limit(MAX_LIMIT)
                    .withContentType(MERCHANTS).all();
        } catch (Exception exception) {
            throw new ServiceException(MERCHANTS_INTEGRATION_FAILING, exception);
        }

        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(MERCHANTS_NOT_FOUND);
        }
        log.info("Merchants retrieved from contentful");
        return cdaArray;

    }
    
    public CDAArray getMerchantCode(String language, String unit) {
        CDAArray cdaArray = null;
        try {
            cdaArray = ilabankcdaClient.fetch(CDAEntry.class)
                    .where(UNIT_SEARCH_PARAMETER, unit.toUpperCase())
                    .where(LANGUAGE_PARAMETER, language)
                    .where(MERCHANT_ORDER_PARAMETER, MERCHANT_ORDER_CONDITION_VALUE)
                    .limit(MAX_LIMIT)
                    .withContentType(MCC_CODE).all();
        } catch (Exception exception) {
            throw new ServiceException(MERCHANTS_INTEGRATION_FAILING, exception);
        }

        log.info("Merchants retrieved from contentful");
        return cdaArray;

    }

}

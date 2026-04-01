package com.neo.v1.constants;

import lombok.experimental.UtilityClass;
//import org.omg.CORBA.PUBLIC_MEMBER;

@UtilityClass
public class ProductCatalogueConstants {

    public static final String GET_OFFERS_SUCCESS_CODE = "PCOF-1001";

    public static final String GET_COMPETITION_SUCCESS_CODE = "PCOF-1002";
    public static final String GET_OFFERS_SUCCESS_MESSAGE = "com.neo.product.catalogue.offers.success.message";

    public static final String GET_COMPETITION_SUCCESS_MESSAGE = "com.neo.product.catalogue.competition.success.message";

    public static final String GET_PRODUCT_LIST_SUCCESS_CODE = "PCPL-1001";
    public static final String GET_PRODUCT_LIST_SUCCESS_MESSAGE = "com.neo.product.catalogue.offers.product.success.message";

    public static final String GET_APP_BRANCHES_SUCCESS_CODE = "PCOF-1001";
    public static final String GET_APP_BRANCHES_SUCCESS_MESSAGE = "com.neo.product.catalogue.branches.success.message";


    public static final String ENTRY_FIELD_IMAGE = "image";
    public static final String OFFERS = "offer";

    public static final String ILA_COMPETITION = "ilaCompetition";
    public static final String PRODUCT = "product";
    public static final String OFFERS_CATEGORY = "offerCategory";
    public static final String OFFERS_SUB_PRODUCT = "subProduct";

    public static final String HTTPS = "https:";
    public static final String NEW = "New";

    public static final String OFFER_BRAND_NAME = "offerBrandName";
    public static final String OFFER_CMS_LABEL = "cmsLabel";
    public static final String OFFER_CATEGORY = "offerCategory";
    public static final String OFFER_DESCRIPTION = "offerDescription";
    public static final String OFFER_PERCENTAGE = "offerDiscountRate";
    public static final String OFFER_TNC = "offerTermsAndConditions";
    public static final String OFFER_BACKGROUND_IMAGE = "BackgroundImage";
    public static final String OFFER_BRAND_IMAGE = "offerBrandImage";
    public static final String OFFER_LIST_IMAGE = "offerMediumImage";
    public static final String OFFER_VALUE = "offerValue";
    public static final String OFFER_IS_NEW = "isNew";
    public static final String OFFER_PAGE_URL = "offerPageUrl";
    public static final String OFFER_SMALL_LIST_IMAGE = "offerSmallImage";
    public static final String OFFER_DETAILS_IMAGE_URL = "offerDetailsImage";
    public static final String OFFER_TAG = "offerTag";
    public static final String OFFER_ORDER = "offerOrder";
    public static final String OFFER_ELIGIBILITY_TAGS = "eligibilityTags";
    public static final String OFFER_APPLICABLE_PRODUCTS = "offerApplicableProducts";
    public static final String OFFER_PRODUCT = "offerProduct";
    public static final String OFFER_SUB_PRODUCT_OFFERS_TAGS = "offerTags";
    public static final String OFFER_SUB_PRODUCT_MAIN_PRODUCT = "mainProduct";
    public static final String OFFER_SUB_PRODUCT_OFFER_BENEFITS = "offerValue";
    public static final String OFFER_SUB_PRODUCT_DISPLAY_ORDER = "offerOrder";
    public static final String OFFER_SUB_PRODUCT_TITLE_NAME = "offerApplicableProductTitle";
    public static final String OFFER_SUB_PRODUCT_SUB_PRODUCTS = "subProduct";
    public static final String OFFER_SUB_PRODUCT_SUB_PRODUCTS_NAME = "name";
    public static final String OFFER_SUB_PRODUCT_SUB_PRODUCTS_ID = "subProductId";


    public static final String PRODUCT_CREDIT_SCORE = "creditScore";
    public static final String PRODUCT_INCOME_RANGE = "incomeRange";
    public static final String PRODUCT_EMPLOYMENT_STATUS = "employmentStatus";
    public static final String PRODUCT_MINIMUM_AGE = "minimumAge";
    public static final String PRODUCT_REQUIRED_DOCUMENTS = "requiredDocuments";
    public static final String PRODUCT_PRODUCT_CURRENCY = "productCurrency";
    public static final String PRODUCT_PRODUCT_DEPOSIT_RATE = "productDepositRate";
    public static final String PRODUCT_PRODUCT_HANDLING_FEES = "productHandlingFees";
    public static final String PRODUCT_PRODUCT_INTEREST_RATES = "productInterestRates";
    public static final String PRODUCT_PRODUCT_MIN_BALANCE = "productMinBalance";
    public static final String PRODUCT_PRODUCT_MIN_DEPOSIT_AMOUNT = "productMinDepositAmount";
    public static final String PRODUCT_AUTO_PAY_SETUP = "autoPaySetup";
    public static final String PRODUCT_MULTIPLE_DEBIT_CARDS = "multipleDebitCards";
    public static final String PRODUCT_E_CHEQUE_ALLOWED = "eChequeAllowed";
    public static final String PRODUCT_OTHER_BENEFITS = "otherBenefits";
    public static final String PRODUCT_MINIMUM_BALANCE = "minimumBalance";
    public static final String PRODUCT_OVER_DRAFT_PROTECTION = "overDraftProtection";
    public static final String PRODUCT_RELATIONSHIP_MANAGER = "relationshipManager";
    public static final String PRODUCT_TNC_TITLE = "tncTitle";
    public static final String PRODUCT_TNC_SUMMERY = "tncSummery";
    public static final String PRODUCT_TNC_URL = "tncUrl";
    public static final String PRODUCT_PRODUCT_NAME = "productName";
    public static final String PRODUCT_PRODUCT_CATEGORY = "productCategory";
    public static final String PRODUCT_PRODUCT_CATEGORY_CODE = "productCategoryCode";
    public static final String PRODUCT_PRODUCT_DESCRIPTION = "productDescription";
    public static final String PRODUCT_SUB_PRODUCT_CODE = "subProductCode";
    public static final String PRODUCT_PRODUCT_SEGMENT = "productSegment";
    public static final int MAX_LIMIT = 1000;

    public static final String OFFERS_CATEGORY_NAME = "categoryName";
    public static final String OFFERS_CATEGORY_START_GRADIENT_COLOR = "startColor";
    public static final String OFFERS_CATEGORY_END_GRADIENT_COLOR = "endColor";
    public static final String OFFERS_CATEGORY_BASE_IMAGE_URL = "categoryImage";
    public static final String OFFERS_CATEGORY_DISPLAY_ORDER = "displayOrder";
    public static final String OFFERS_CATEGORY_ID = "id";
    public static final String OFFERS_SUB_PRODUCT_NAME = "subProductName";
    public static final String OFFERS_SUB_PRODUCT_BASE_IMAGE_URL = "productImage";
    public static final String OFFERS_SUB_PRODUCT_DISPLAY_ORDER = "displayOrder";
    public static final String OFFERS_SUB_PRODUCT_ID = "productIdentifier";
    public static final String OFFERS_LANGUAGE_PARAMETER = "locale";
    public static final String OFFERS_UNIT_SEARCH_PARAMETER ="fields.unit[in]";
    public static final String OFFERS_ORDER_PARAMETER ="order";
    public static final String OFFERS_ORDER_CONDITION_VALUE ="fields.offerOrder";
    public static final String SUB_PRODUCT_ORDER_CONDITION_VALUE ="fields.displayOrder";
    public static final String CATEGORY_ORDER_CONDITION_VALUE ="fields.displayOrder";


    public static final String PURGE_OFFER_CACHE_SUCCESS_CODE = "PCPL-1002";
    public static final String PURGE_OFFER_CACHE_SUCCESS_MESSAGE = "com.neo.product.catalogue.purge.offers.cache.success.message";

    public static final String ATM = "openBankingAtmInfo";
    public static final String BRANCHES = "openBankingBranchInfo";

    public static final String IDENTIFICATION = "identification";
    public static final String ACCESSIBILITY = "accessibility";
    public static final String TIMING_FLAG = "access24Hours";
    public static final String SUPPORTED_CURRENCIES = "supportedCurrencies";
    public static final String SUPPORTED_LANGUAGES = "supportedLanguages";
    public static final String MINIMUM_AMOUNT = "minimumAmount";
    public static final String LOCATION_CATEGORY = "locationCategory";
    public static final String BRANCH_NAME = "name";
    public static final String CUSTOMER_SEGMENT= "customerSegment";
    public static final String BRANCH_TYPE = "type";
    public static final String BRANCH_SERVICES_AND_FACILITIES = "servicesAndFacility";
    public static final String BRANCH_CONTACT_INFO = "contactInfo";

    public static final String ATM_SERVICES = "atmServices";

    public static final String CONTACT_TYPE = "contactType";
    public static final String CONTACT_VALUE = "contactContent";
    public static final String CONTACT_DESCRIPTION = "contactDescription";

    public static final String APP_BRANCHES_LANGUAGE_PARAMETER = "locale";
    public static final String APP_BRANCHES_UNIT_SEARCH_PARAMETER ="fields.unit[in]";
    public static final String APP_BRANCHES = "appBranchDetails";
    public static final String APP_BRANCHES_UNIT = "unit";
    public static final String APP_BRANCHES_ATM_ID = "atmId";
    public static final String APP_BRANCHES_GPS_COORDINATES = "gpsCoordinates";
    public static final String APP_BRANCHES_GOVERNORATE = "governorate";
    public static final String APP_BRANCHES_LOCATION_NAME = "locationName";
    public static final String APP_BRANCHES_WORKING_HOURS = "workingHours";
    public static final String APP_BRANCHES_CASH_DEPOSIT = "cashDeposit";
    public static final Boolean APP_BRANCHES_CASH_DEPOSIT_TRUE = true;
    public static final String APP_BRANCHES_CASH_DEPOSIT_YES = "yes";
    public static final String APP_BRANCHES_CASH_DEPOSIT_NO = "no";
    public static final String APP_BRANCHES_ADDRESS = "address";
    public static final String APP_CHANNEL_TYPE = "channelType";
    public static final String APP_CHANNEL_UNIT = "channelUnit";

    public static final String APP_BRANCHES_GOVERNORATES = "governorates";
    public static final String GIFTS = "prizeoutGiftCards";
    public static final String PRIZE_OUT_GIFT_REGION ="region";

    public static final String PRIZE_OUT_GIFT_NAME ="name";
    public static final String GET_PRIZE_OUT_GIFT_SUCCESS_CODE = "PCOF-1005";
    public static final String GET_PRIZE_OUT_GIFT_SUCCESS_MESSAGE = "com.neo.product.catalogue.prizeout-gift-cards.success.message";
    public static final String PRIZE_GIFT_UNIT_SEARCH_PARAMETER ="fields.unit[in]";
    public static final String PRIZE_OUT_GIFT_IMAGE ="image";
    public static final String PURGE_GIFT_CARD_CACHE_SUCCESS_CODE = "PCPL-1002";
    public static final String PURGE_GIFT_CARD_CACHE_SUCCESS_MESSAGE = "com.neo.product.catalogue.prizeout-gift-cards.cache.success.message";
    public static final String PRIZE_OUT_GIFT_CARD_LANGUAGE_PARAMETER = "locale";


    public static final String UNIT_SEARCH_PARAMETER ="fields.unit[in]";

    public static final String LANGUAGE_PARAMETER = "locale";

    public static final String COMPETITION_TITLE = "competitionTitle";

    public static final String COMPETITION_DESCRIPTION ="competitionDescription";

    public static final String COMPETITION_IMAGE_EN ="competitionImageEn";

    public static final String COMPETITION_IMAGE_AR ="competitionImageAr";

    public static final String COMPETITION_COVER_IMAGE ="competitionCoverImage";

    public static final String COMPETITION_BUTTON_VISIBILITY ="competitionButtonVisibility";

    public static final String COMPETITION_BUTTON_CAPTION ="buttonCaption";

    public static final String COMPETITION_BUTTON_NAVIGATION_ACTION ="buttonNavigationAction";

    public static final String COMPETITION_BUTTON_NAVIGATION_ACTION_LINK ="buttonNavigationActionLink";

    public static final String COMPETITION_START_DATE ="competitionStartDate";

    public static final String COMPETITION_END_DATE ="competitionEndDate";

    public static final String COMPETITION_ISKANZCOMPETITION ="isKanzCompetition";

    public static final String COMPETITION_UNIT ="unit";


    public static final String SORT_ORDER ="sortOrder";

    public static final String ALBURAQ_UNIT ="alburaq";

    public static final String ALBURAQ_CARD_NAME ="AlburaqDebitClassic";

    public static final String GET_MERCHANTS_SUCCESS_CODE = "PCOF-1000";
    public static final String GET_MERCHANTS_SUCCESS_MESSAGE = "com.neo.product.catalogue.merchants.success.message";

    public static final String MERCHANTS_CATEGORY_ID = "id";
    public static final String MERCHANTS_FILTER = "filter";
    public static final String MERCHANTS_CATEGORY_NAME = "name";
    public static final String MERCHANTS_CATEGORY_ICONLABEL = "iconLabel";
    public static final String MERCHANTS_CATEGORY_ICON = "icon";
    public static final String MERCHANTS_CATEGORY_COLOR = "color";
    public static final String MERCHANTS_REVISED_NAME = "revisedName";
    public static final String MERCHANTS_LOGO = "logo";
    public static final String MERCHANTS_CODE = "code";
    public static final String MERCHANT_CATEGORY = "merchantCategory";
    public static final String MERCHANTS = "merchant";
    public static final String MCC_CODE = "mccCode";

    public static final String MERCHANT_ORDER_PARAMETER ="order";
    public static final String MERCHANT_ORDER_CONDITION_VALUE ="fields.displayOrder";
    public static final String MERCHANTS_FONT_COLOR = "fontColor";




}



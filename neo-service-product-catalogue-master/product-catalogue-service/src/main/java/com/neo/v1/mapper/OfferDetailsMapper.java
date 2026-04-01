package com.neo.v1.mapper;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.constants.ProductCatalogueConstants.ALBURAQ_CARD_NAME;
import static com.neo.v1.constants.ProductCatalogueConstants.ALBURAQ_UNIT;
import static com.neo.v1.constants.ProductCatalogueConstants.ENTRY_FIELD_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.HTTPS;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_APPLICABLE_PRODUCTS;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_BACKGROUND_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_BRAND_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_BRAND_NAME;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_CATEGORY;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_CMS_LABEL;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_DESCRIPTION;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_DETAILS_IMAGE_URL;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_ELIGIBILITY_TAGS;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_IS_NEW;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_LIST_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_ORDER;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_PAGE_URL;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_PERCENTAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_PRODUCT;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_SMALL_LIST_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_TAG;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_TNC;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFER_VALUE;
import static com.neo.v1.util.OffersUtil.replaceHyperlink;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.SubProducts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OfferDetailsMapper {

    private final ApplicableSubProductsMapper applicableSubProductsMapper;
    private final CategoryLinkMapper categoryLinkMapper;

    public List<OfferDetails> map(CDAArray cdaArray, List<SubProducts> subProductsList) {
        return cdaArray.items().stream().map(cdaResource -> map(cdaResource, subProductsList)).collect(Collectors.toList());
    }

    private OfferDetails map(CDAResource cdaResource, List<SubProducts> subProductsList) {
        CDAEntry entry = (CDAEntry) cdaResource;
        List<CDAEntry> offerApplicableProducts = entry.getField(OFFER_APPLICABLE_PRODUCTS);
        CDAEntry offerCategory = entry.getField(OFFER_CATEGORY);
        Object offerValue = entry.getField(OFFER_VALUE);
        Double order = entry.getField(OFFER_ORDER);
        OfferDetails offerDetails = OfferDetails.builder()
                .offerId(cdaResource.id())
                .offerCategory(categoryLinkMapper.map(offerCategory))
                .offerTitle(entry.getField(OFFER_CMS_LABEL))
                .offerBrandName(entry.getField(OFFER_BRAND_NAME))
                .offerDescription(replaceHyperlink(entry.getField(OFFER_DESCRIPTION), cdaResource.id()))
                .offerDiscountRate(entry.getField(OFFER_PERCENTAGE))
                .offerTermsAndConditions(replaceHyperlink(entry.getField(OFFER_TNC) , cdaResource.id()))
                .offerValue(Objects.nonNull(offerValue) ? new BigDecimal(offerValue.toString()) : null)
                .isNewOffer(entry.getField(OFFER_IS_NEW))
                .offerPageUrl(entry.getField(OFFER_PAGE_URL))
                .offerTag(entry.getField(OFFER_TAG))
                .offerDisplayOrder(Objects.nonNull(order) ? order.longValue() : null)
                .offerEligibilityTags(entry.getField(OFFER_ELIGIBILITY_TAGS))
                .offerApplicableSubProducts(StringUtils.equals(getContext().getUnit(), ALBURAQ_UNIT) ?
                        applicableSubProductsMapper.map(offerApplicableProducts, subProductsList).stream().filter(asp -> StringUtils.equals(ALBURAQ_CARD_NAME, asp.getSubProduct().getSubProductId())).collect(Collectors.toList()) :
                        applicableSubProductsMapper.map(offerApplicableProducts, subProductsList).stream().filter(asp -> !StringUtils.equals(ALBURAQ_CARD_NAME, asp.getSubProduct().getSubProductId())).collect(Collectors.toList()))
                .offerProduct(entry.getField(OFFER_PRODUCT))
                .build();


        Optional<CDAEntry> offerImageUrl = Optional.ofNullable(entry.getField(OFFER_BACKGROUND_IMAGE));
        offerImageUrl.ifPresent(image -> offerDetails.setOfferImageUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        Optional<CDAEntry> offerBrandImageUrl = Optional.ofNullable(entry.getField(OFFER_BRAND_IMAGE));
        offerBrandImageUrl.ifPresent(image -> offerDetails.setOfferBrandImageUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        Optional<CDAEntry> offerListImageUrl = Optional.ofNullable(entry.getField(OFFER_LIST_IMAGE));
        offerListImageUrl.ifPresent(image -> offerDetails.setOfferListImageUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        Optional<CDAEntry> offerListSmallImageUrl = Optional.ofNullable(entry.getField(OFFER_SMALL_LIST_IMAGE));
        offerListSmallImageUrl.ifPresent(image -> offerDetails.setOfferListSmallImageUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        Optional<CDAEntry> offerDetailsImageUrl = Optional.ofNullable(entry.getField(OFFER_DETAILS_IMAGE_URL));
        offerDetailsImageUrl.ifPresent(image -> offerDetails.setOfferDetailsImageUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));
        return offerDetails;
    }


}

package com.neo.v1.controller;

import com.neo.core.model.ApiError;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.ResponseEntity;

import com.neo.v1.product.catalogue.api.NeoServiceV1Api;
import com.neo.v1.product.catalogue.model.*;
import com.neo.v1.product.catalogue.model.ApiSuccessWithoutData;
import com.neo.v1.product.catalogue.model.CompetitionListResponse;
import com.neo.v1.product.catalogue.model.AppBranchesListResponse;
import com.neo.v1.product.catalogue.model.CategoryListResponse;
import com.neo.v1.product.catalogue.model.OffersListResponse;
import com.neo.v1.product.catalogue.model.ProductListResponse;
import com.neo.v1.service.ProductCatalogueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neo.core.model.ApiError;
import com.neo.v1.product.catalogue.api.NeoServiceV1Api;
import com.neo.v1.product.catalogue.model.ApiSuccessWithoutData;
import com.neo.v1.product.catalogue.model.CategoryListResponse;
import com.neo.v1.product.catalogue.model.MerchantCodeListResponse;
import com.neo.v1.product.catalogue.model.MerchantListResponse;
import com.neo.v1.product.catalogue.model.OffersListResponse;
import com.neo.v1.product.catalogue.model.ProductListResponse;
import com.neo.v1.service.ProductCatalogueService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api
@Slf4j
@RestController
@RequestMapping ( "/api/v1/product-catalogue" )
@Validated
@AllArgsConstructor
@ApiResponses ( {
        @ApiResponse ( code = 400, message = "BadRequest", response = ApiError.class ),
        @ApiResponse ( code = 403, message = "Forbidden", response = ApiError.class ),
        @ApiResponse ( code = 500, message = "Internal Server Error", response = ApiError.class )
} )
public class ProductCatalogueController implements NeoServiceV1Api {

    private final ProductCatalogueService productCatalogueService;

    @Override
    @GetMapping ( "/products" )
    public ResponseEntity<ProductListResponse> getProductCatalogueProducts() {
        return status(OK).body(productCatalogueService.getProducts());
    }

    @Override
    @GetMapping ( "/offers" )
    public ResponseEntity<OffersListResponse> getProductCatalogueOffers(@RequestParam ( name = "language", required = false ) String language,
                                                                        @RequestParam ( name = "unit", required = false ) String unit,
                                                                        @RequestHeader ( required = false, name = "customerId" ) String customerId,
                                                                        @RequestHeader ( required = false, name = "isNewOfferOnly", defaultValue = "false" ) Boolean isNewOfferOnly,
                                                                        @RequestHeader ( required = false, name = "offerCategory" ) String offerCategory,
                                                                        @RequestHeader ( required = false, name = "offerProduct" ) String offerProduct,
                                                                        @RequestHeader ( required = false, name = "offerSubProduct" ) String offerSubProduct,
                                                                        @RequestHeader ( required = false, name = "onlyCustomerApplicableOffer", defaultValue = "false" ) Boolean onlyCustomerApplicableOffer) {
        return status(OK).body(productCatalogueService.getOffers(offerCategory, offerProduct, offerSubProduct, isNewOfferOnly));
    }

    @Override
    @GetMapping ( "/branches" )
    public ResponseEntity<AppBranchesListResponse> getProductCatalogueBranches(@RequestParam ( name = "language", required = false ) String language,
                                                                               @RequestParam ( name = "unit", required = false ) String unit,
                                                                               @RequestHeader ( name = "customerId", required = false ) String customerId) {
        return status(OK).body(productCatalogueService.getAppBranches());
    }

    @Override
    @DeleteMapping ()
    public ResponseEntity<ApiSuccessWithoutData> deleteProductCatalogueOffers() {
        return status(OK).body(productCatalogueService.purgeData());
    }

    @Override
    @GetMapping("/competition")
    public ResponseEntity<CompetitionListResponse> getProductCatalogueCompetition(String language, String unit, String customerId) {
        return status(OK).body(productCatalogueService.getProductCatalogueCompetition(language, unit, customerId));
    }

    @Override
    @GetMapping ( "/prizeout-gift-cards" )
    public ResponseEntity<GiftCardResponse> getProductCataloguePrizeoutGiftCards(@RequestParam ( name = "language", required = false ) String language,
                                                                                 @RequestParam ( name = "unit", required = false ) String unit,
                                                                                 @RequestHeader ( name = "customerId", required = false ) String customerId) {
        return status(OK).body(productCatalogueService.getProductCataloguePrizeoutGiftCards());
    }

    @Override
    @DeleteMapping ( "/prizeout-gift-cards" )
    public ResponseEntity<ApiSuccessWithoutData> deleteProductCataloguePrizeoutGiftCards() {
        return status(OK).body(productCatalogueService.deletegiftcardpurgeData());
    }

    @Override
    @GetMapping("/merchant/category")
    public ResponseEntity<CategoryListResponse> getProductCatalogueMerchantCategory() {
        return status(OK).body(productCatalogueService.getMerchantCategory());
    }

	@Override
	@GetMapping("/merchant")
	public ResponseEntity<MerchantListResponse> getProductCatalogueMerchant() {
		return status(OK).body(productCatalogueService.getMerchants());
	}

	@Override
	@GetMapping("/merchant/code")
	public ResponseEntity<MerchantCodeListResponse> getProductCatalogueMerchantCode() {
		return status(OK).body(productCatalogueService.getMerchantCode());
	}
	
	@Override
    @DeleteMapping("/merchant")
    public ResponseEntity<ApiSuccessWithoutData> deleteProductCatalogueMerchant(){
        return status(OK).body(productCatalogueService.purgeMerchantData());
    }

}
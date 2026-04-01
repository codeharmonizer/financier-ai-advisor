package com.neo.v1.enums;

import com.neo.core.provider.ServiceKeyMapping;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Getter
@AllArgsConstructor
public enum ProductCatalogueServiceKeyMapping implements ServiceKeyMapping {

    OFFERS_INTEGRATION_FAILING("PCOF-0002",
            BAD_GATEWAY,
            "com.neo.product.catalogue.offers.integration.failing.message",
            "com.neo.product.catalogue.offers.integration.failing.error.message"
    ),
    PRODUCTS_INTEGRATION_FAILING("PCPL-0002",
            BAD_GATEWAY,
            "com.neo.product.catalogue.offers.product.integration.failing.message",
            "com.neo.product.catalogue.offers.product.integration.failing.error.message"
    ),
    OFFERS_NOT_FOUND("PCOF-0003",
            NOT_FOUND,
            "com.neo.product.catalogue.offers.not.found.message",
            "com.neo.product.catalogue.offers.not.found.error.message"
    ),
    PRODUCTS_NOT_FOUND("PCPL-0003",
            NOT_FOUND,
            "com.neo.product.catalogue.offers.product.not.found.message",
            "com.neo.product.catalogue.offers.product.not.found.error.message"
    ),
    APP_BRANCHES_INTEGRATION_FAILING("PCOF-0002",
            BAD_GATEWAY,
            "com.neo.product.catalogue.branches.integration.failing.message",
            "com.neo.product.catalogue.branches.integration.failing.error.message"
    ),
    APP_BRANCHES_NOT_FOUND("PCOF-0003",
            NOT_FOUND,
            "com.neo.product.catalogue.branches.not.found.message",
            "com.neo.product.catalogue.branches.not.found.error.message"
    ),
    CUSTOMER_ID_INVALID("GENE-0002", FORBIDDEN,
            "com.neo.v1.customer.id.invalid.message",
            "com.neo.v1.customer.id.invalid.audit.message"),
    UNIT_INVALID("GENE-0006", BAD_REQUEST, "com.neo.v1.unit.invalid.message", "com.neo.v1.unit.invalid.audit.message"),
    LANGUAGE_INVALID("GENE-0008", BAD_REQUEST, "com.neo.v1.language.invalid.message", "com.neo.v1.language.invalid.audit.message"),
    MERCHANTS_INTEGRATION_FAILING("PCOF-0002",
            BAD_GATEWAY,
            "com.neo.product.catalogue.merchants.integration.failing.message",
            "com.neo.product.catalogue.merchants.integration.failing.error.message"
    ),
    MERCHANTS_NOT_FOUND("PCOF-0003",
            NOT_FOUND,
            "com.neo.product.catalogue.merchants.not.found.message",
            "com.neo.product.catalogue.merchants.not.found.error.message"
    ),
    COMPETITION_INTEGRATION_FAILING("PCOF-0009",
                               BAD_GATEWAY,
            "com.neo.product.catalogue.competition.integration.failing.message",
                                       "com.neo.product.catalogue.competition.integration.failing.error.message"
    ),
    COMPETITION_NOT_FOUND("PCOF-0010",
            NOT_FOUND,
            "com.neo.product.catalogue.competition.not.found.message",
            "com.neo.product.catalogue.competition.not.found.error.message"
    );
    private String code;
    private HttpStatus httpStatus;
    private String messageKey;
    private String errorMessageKey;
}
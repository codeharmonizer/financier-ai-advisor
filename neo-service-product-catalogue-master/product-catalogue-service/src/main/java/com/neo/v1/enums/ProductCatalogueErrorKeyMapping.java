package com.neo.v1.enums;

import com.neo.core.provider.ErrorKeyMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCatalogueErrorKeyMapping implements ErrorKeyMapping {

    ;

    private String code;
    private String messageKey;
    private String errorMessageKey;
}
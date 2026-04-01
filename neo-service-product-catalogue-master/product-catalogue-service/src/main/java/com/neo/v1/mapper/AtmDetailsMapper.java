package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.core.exception.ServiceException;
import com.neo.v1.product.catalogue.model.AtmDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.neo.v1.constants.ProductCatalogueConstants.ACCESSIBILITY;
import static com.neo.v1.constants.ProductCatalogueConstants.ATM_SERVICES;
import static com.neo.v1.constants.ProductCatalogueConstants.IDENTIFICATION;
import static com.neo.v1.constants.ProductCatalogueConstants.LOCATION_CATEGORY;
import static com.neo.v1.constants.ProductCatalogueConstants.MINIMUM_AMOUNT;
import static com.neo.v1.constants.ProductCatalogueConstants.SUPPORTED_CURRENCIES;
import static com.neo.v1.constants.ProductCatalogueConstants.SUPPORTED_LANGUAGES;
import static com.neo.v1.constants.ProductCatalogueConstants.TIMING_FLAG;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.PRODUCTS_NOT_FOUND;


@RequiredArgsConstructor
@Component
public class AtmDetailsMapper {
    private final LocationInfoMapper locationInfoMapper;

    public List<AtmDetails> map(CDAArray cdaArray) {
        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(PRODUCTS_NOT_FOUND);
        }
        return cdaArray.items().stream().map(this::map).collect(Collectors.toList());
    }

    private AtmDetails map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        return AtmDetails.builder()
                .atmId(entry.getField(IDENTIFICATION))
                .atmServices(entry.getField(ATM_SERVICES))
                .atmAccessibility(entry.getField(ACCESSIBILITY))
                .atmTime24HIdentifier(entry.getField(TIMING_FLAG))
                .atmSupportedCurrencies(entry.getField(SUPPORTED_CURRENCIES))
                .atmSupportedLanguages(entry.getField(SUPPORTED_LANGUAGES))
                .atmMinimumAmount(new BigDecimal(entry.getField(MINIMUM_AMOUNT).toString()))
                .atmLocationCategory(entry.getField(LOCATION_CATEGORY))
                .atmAddress(locationInfoMapper.map(entry))
                .build();
    }
}

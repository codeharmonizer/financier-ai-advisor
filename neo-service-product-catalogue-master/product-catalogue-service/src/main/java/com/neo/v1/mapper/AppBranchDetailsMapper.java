package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.AppBranchDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_ADDRESS;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_ATM_ID;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_CASH_DEPOSIT;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_CASH_DEPOSIT_NO;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_CASH_DEPOSIT_TRUE;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_CASH_DEPOSIT_YES;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_GOVERNORATE;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_GPS_COORDINATES;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_LOCATION_NAME;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_UNIT;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES_WORKING_HOURS;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_CHANNEL_TYPE;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_CHANNEL_UNIT;

@RequiredArgsConstructor
@Component
@Slf4j
public class AppBranchDetailsMapper {
    public List<AppBranchDetails> map(CDAArray cdaArray) {
        return cdaArray.items().stream()
                .filter(cdaResource -> ((CDAEntry) cdaResource).getField(APP_BRANCHES_CASH_DEPOSIT))
                .map(cdaResource -> map(cdaResource)).collect(Collectors.toList());
    }

    private AppBranchDetails map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        log.info("branch: [unit- {}, atmId- {}, governorate- {}, cashDeposit- {}, channelType - {}, channelUnit - {}]",
                entry.getField(APP_BRANCHES_UNIT), entry.getField(APP_BRANCHES_ATM_ID),
                entry.getField(APP_BRANCHES_GOVERNORATE), entry.getField(APP_BRANCHES_CASH_DEPOSIT),
                entry.getField(APP_CHANNEL_TYPE),entry.getField(APP_CHANNEL_UNIT));
        AppBranchDetails appBranchDetails = AppBranchDetails.builder()
                .unit(entry.getField(APP_BRANCHES_UNIT))
                .atmId(entry.getField(APP_BRANCHES_ATM_ID))
                .gpsCoordinates(entry.getField(APP_BRANCHES_GPS_COORDINATES))
                .governorate(entry.getField(APP_BRANCHES_GOVERNORATE))
                .locationName(entry.getField(APP_BRANCHES_LOCATION_NAME))
                .workingHours(entry.getField(APP_BRANCHES_WORKING_HOURS))
                .cashDeposit((entry.getField(APP_BRANCHES_CASH_DEPOSIT).equals(APP_BRANCHES_CASH_DEPOSIT_TRUE)
                        || entry.getField(APP_BRANCHES_CASH_DEPOSIT).equals(APP_BRANCHES_CASH_DEPOSIT_YES))
                        ? APP_BRANCHES_CASH_DEPOSIT_YES : APP_BRANCHES_CASH_DEPOSIT_NO)
                .address(entry.getField(APP_BRANCHES_ADDRESS))
                .channelType(entry.getField(APP_CHANNEL_TYPE))
                .channelUnit(entry.getField(APP_CHANNEL_UNIT))
                .build();

        return appBranchDetails;
    }

}

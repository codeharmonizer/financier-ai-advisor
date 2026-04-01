package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.core.exception.ServiceException;
import com.neo.v1.product.catalogue.model.BranchDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.neo.v1.constants.ProductCatalogueConstants.ACCESSIBILITY;
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCH_CONTACT_INFO;
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCH_NAME;
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCH_SERVICES_AND_FACILITIES;
import static com.neo.v1.constants.ProductCatalogueConstants.BRANCH_TYPE;
import static com.neo.v1.constants.ProductCatalogueConstants.CUSTOMER_SEGMENT;
import static com.neo.v1.constants.ProductCatalogueConstants.IDENTIFICATION;
import static com.neo.v1.constants.ProductCatalogueConstants.SUPPORTED_LANGUAGES;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.PRODUCTS_NOT_FOUND;

@RequiredArgsConstructor
@Component
public class BranchDetailsMapper {
    private final LocationInfoMapper locationInfoMapper;
    private final ContactInfoMapper contactInfoMapper;

    public List<BranchDetails> map(CDAArray cdaArray) {
        if (Objects.isNull(cdaArray) || cdaArray.total() <= 0) {
            throw new ServiceException(PRODUCTS_NOT_FOUND);
        }
        return cdaArray.items().stream().map(this::map).collect(Collectors.toList());
    }

    private BranchDetails map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        return BranchDetails.builder()
                .branchId(entry.getField(IDENTIFICATION))
                .branchName(entry.getField(BRANCH_NAME))
                .branchType(entry.getField(BRANCH_TYPE))
                .branchAccessibility(entry.getField(ACCESSIBILITY))
                .branchCustomerSegment(entry.getField(CUSTOMER_SEGMENT))
                .branchServicesAndFacilities(entry.getField(BRANCH_SERVICES_AND_FACILITIES))
                .branchSupportedLanguages(entry.getField(SUPPORTED_LANGUAGES))
                .branchContactInfo(contactInfoMapper.map(entry.getField(BRANCH_CONTACT_INFO)))
                .branchAddress(locationInfoMapper.map(entry))
                .build();
    }
}

package com.neo.v1.mapper;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.MerchantCodeDetail;
import com.neo.v1.product.catalogue.model.MerchantCodeListData;

import lombok.RequiredArgsConstructor;

import static com.neo.v1.constants.ProductCatalogueConstants.*;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
public class MerchantCodeListDataMapper {
	
	private final MerchantCategoryListDataMapper merchantCategoryListDataMapper;

    public MerchantCodeListData map(CDAArray merchants) {
        return MerchantCodeListData
        		.builder()
        		.merchantCodes(mapCodeList(merchants))
        		.build();
    }
    
    public List<MerchantCodeDetail> mapCodeList(CDAArray cdaArray) {
        return cdaArray.items().stream().map(cdaResource -> map(cdaResource)).collect(Collectors.toList());
    }

    private MerchantCodeDetail map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        String id = entry.attrs().get(MERCHANTS_CATEGORY_ID).toString();
        Double displayOrder = entry.getField(MERCHANT_ORDER_PARAMETER);
        CDAEntry contentfulMerchantCategory = entry.getField(MERCHANT_CATEGORY);
        
        MerchantCodeDetail merchantDetail = MerchantCodeDetail.builder()
        		.id(nonNull(id) ? id : null)
        		.code(entry.getField(MERCHANTS_CODE))
        		.revisedName(entry.getField(MERCHANTS_REVISED_NAME))
        		.contentfulMerchantCategory(merchantCategoryListDataMapper.map(contentfulMerchantCategory))
        		.displayOrder(nonNull(displayOrder) ? displayOrder.longValue() : null)
        		.build();
        
        Optional<CDAEntry> merchantsIconLabelUrl = Optional.ofNullable(entry.getField(MERCHANTS_LOGO));
        merchantsIconLabelUrl.ifPresent(image -> merchantDetail.setLogoUrl(nonNull(image.getField(ENTRY_FIELD_IMAGE)) ? HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url() : null));

        return merchantDetail;
    }

}

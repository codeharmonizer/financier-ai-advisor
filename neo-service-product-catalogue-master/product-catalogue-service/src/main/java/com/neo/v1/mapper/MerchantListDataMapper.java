package com.neo.v1.mapper;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.neo.v1.product.catalogue.type.MerchantDetailFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.CategoryDetail;
import com.neo.v1.product.catalogue.model.MerchantDetail;
import com.neo.v1.product.catalogue.model.MerchantListData;

import lombok.RequiredArgsConstructor;

import static com.neo.v1.constants.ProductCatalogueConstants.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class MerchantListDataMapper {
	
	private final MerchantCategoryListDataMapper merchantCategoryListDataMapper;

    public MerchantListData map(CDAArray merchants) {
        return MerchantListData
        		.builder()
        		.merchants(mapList(merchants))
        		.build();
    }
    
    public List<MerchantDetail> mapList(CDAArray cdaArray) {
        return cdaArray.items().stream().map(cdaResource -> map(cdaResource)).collect(Collectors.toList());
    }

    private MerchantDetail map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        String id = entry.attrs().get(MERCHANTS_CATEGORY_ID).toString();
        Double displayOrder = entry.getField(MERCHANT_ORDER_PARAMETER);
        CDAEntry contentfulMerchantCategory = entry.getField(MERCHANT_CATEGORY);
        String categoryId = contentfulMerchantCategory.attrs().get(MERCHANTS_CATEGORY_ID).toString();
        String filter = entry.getField(MERCHANTS_FILTER);
        
        MerchantDetail merchantDetail = MerchantDetail.builder()
        		.id(Objects.nonNull(id) ? id : null)
        		.name(entry.getField(MERCHANTS_CATEGORY_NAME))
        		.revisedName(entry.getField(MERCHANTS_REVISED_NAME))
                .filter(filter != null ? MerchantDetailFilter.valueOf(filter) : MerchantDetailFilter.DEFAULT)
        		.contentfulMerchantCategory(CategoryDetail.builder().id(Objects.nonNull(categoryId) ? categoryId : null).build())
        		.displayOrder(Objects.nonNull(displayOrder) ? displayOrder.longValue() : null)
        		.build();
        
        Optional<CDAEntry> merchantsIconLabelUrl = Optional.ofNullable(entry.getField(MERCHANTS_LOGO));
        try {
            merchantsIconLabelUrl.ifPresent(image -> merchantDetail.setLogoUrl(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));
        }catch (Exception e) {
            log.warn("Error in Enriching Transactions image", e);
        }

        return merchantDetail;
    }
    
}

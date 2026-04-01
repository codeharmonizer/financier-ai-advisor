package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;
import com.neo.v1.product.catalogue.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.neo.v1.constants.ProductCatalogueConstants.CONTACT_DESCRIPTION;
import static com.neo.v1.constants.ProductCatalogueConstants.CONTACT_TYPE;
import static com.neo.v1.constants.ProductCatalogueConstants.CONTACT_VALUE;

@RequiredArgsConstructor
@Component
public class ContactInfoMapper {

    public List<ContactInfo> map(List<CDAEntry> contacts) {
        if (contacts.size() <= 0) {
            return null;
        }
        List<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();
        for (CDAEntry entry : contacts) {
            contactInfoList.add(ContactInfo.builder()
                    .contactType(entry.getField(CONTACT_TYPE))
                    .contactValue(entry.getField(CONTACT_VALUE))
                    .contactDescription(entry.getField(CONTACT_DESCRIPTION))
                    .build());
        }
        return contactInfoList;
    }
}

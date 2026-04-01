package com.neo.v1.mapper;

import com.contentful.java.cda.CDAEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import com.neo.v1.product.catalogue.model.ProductBenefits;

import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_AUTO_PAY_SETUP;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_E_CHEQUE_ALLOWED;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_MINIMUM_BALANCE;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_MULTIPLE_DEBIT_CARDS;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_OTHER_BENEFITS;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_OVER_DRAFT_PROTECTION;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT_RELATIONSHIP_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductBenefitsMapperTest {

    private ProductBenefitsMapper subject = new ProductBenefitsMapper();

    @Test
    public void map_withCDAEntry_returnProductBenefits() throws Exception {
        CDAEntry cdaEntry = mock(CDAEntry.class);

        String autoPaySetup = "Test auto pay setup";
        String eChequeAllowed = "Test e-cheque allowed";
        String multipleDebitCards = "Test multiple debit cards";
        String otherBenefits = "Test other benefits";
        String overDraftProtection = "Test over draft protection";
        String relationshipManager = "Test relationship manager";
        BigDecimal minimumBalance = new BigDecimal("1.0");

        when(cdaEntry.getField(PRODUCT_AUTO_PAY_SETUP)).thenReturn(autoPaySetup);
        when(cdaEntry.getField(PRODUCT_E_CHEQUE_ALLOWED)).thenReturn(eChequeAllowed);
        when(cdaEntry.getField(PRODUCT_MULTIPLE_DEBIT_CARDS)).thenReturn(multipleDebitCards);
        when(cdaEntry.getField(PRODUCT_OTHER_BENEFITS)).thenReturn(otherBenefits);
        when(cdaEntry.getField(PRODUCT_OVER_DRAFT_PROTECTION)).thenReturn(overDraftProtection);
        when(cdaEntry.getField(PRODUCT_RELATIONSHIP_MANAGER)).thenReturn(relationshipManager);
        when(cdaEntry.getField(PRODUCT_MINIMUM_BALANCE)).thenReturn(minimumBalance);

        ProductBenefits expected = ProductBenefits.builder()
                .autoPaySetup(autoPaySetup)
                .eChequeAllowed(eChequeAllowed)
                .minimumBalance(minimumBalance)
                .multipleDebitCards(multipleDebitCards)
                .OtherBenefits(otherBenefits)
                .overDraftProtection(overDraftProtection)
                .relationshipManager(relationshipManager)
                .build();

        ProductBenefits result = subject.map(cdaEntry);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(cdaEntry).getField(PRODUCT_AUTO_PAY_SETUP);
        verify(cdaEntry).getField(PRODUCT_E_CHEQUE_ALLOWED);
        verify(cdaEntry).getField(PRODUCT_MULTIPLE_DEBIT_CARDS);
        verify(cdaEntry).getField(PRODUCT_OTHER_BENEFITS);
        verify(cdaEntry).getField(PRODUCT_OVER_DRAFT_PROTECTION);
        verify(cdaEntry).getField(PRODUCT_RELATIONSHIP_MANAGER);
        verify(cdaEntry).getField(PRODUCT_MINIMUM_BALANCE);
    }
}

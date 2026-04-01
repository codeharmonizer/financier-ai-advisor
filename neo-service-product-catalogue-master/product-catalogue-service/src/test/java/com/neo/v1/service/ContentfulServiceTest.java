package com.neo.v1.service;

import static com.neo.v1.constants.ProductCatalogueConstants.ILA_COMPETITION;
import static com.neo.v1.constants.ProductCatalogueConstants.APP_BRANCHES;
import static com.neo.v1.constants.ProductCatalogueConstants.MAX_LIMIT;
import static com.neo.v1.constants.ProductCatalogueConstants.OFFERS;
import static com.neo.v1.constants.ProductCatalogueConstants.PRODUCT;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.APP_BRANCHES_INTEGRATION_FAILING;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.APP_BRANCHES_NOT_FOUND;
import static com.neo.v1.constants.ProductCatalogueConstants.MERCHANT_CATEGORY;
import static com.neo.v1.constants.ProductCatalogueConstants.MERCHANTS;
import static com.neo.v1.constants.ProductCatalogueConstants.MCC_CODE;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.OFFERS_INTEGRATION_FAILING;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.OFFERS_NOT_FOUND;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.PRODUCTS_INTEGRATION_FAILING;
import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.PRODUCTS_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.FetchQuery;
import com.neo.core.exception.ServiceException;
import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentfulServiceTest {

    @InjectMocks
    private ContentfulService subject;

    @Mock
    private CDAClient contentfulCDAClient;

    @Test
    void getOffers_isCalled_returnCDAArray() {
        String language = "language";
        String unit = "unit";
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.limit(MAX_LIMIT)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(OFFERS)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(1);

        CDAArray result = subject.getOffers(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(OFFERS);
        verify(query).all();
        verify(expected).total();
    }

    @Test
    void getOffers_emptyList_throwServiceException() {
        String language = "language";
        String unit = "unit";
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.limit(MAX_LIMIT)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(OFFERS)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(0);

        ServiceException keyMapping = assertThrows(ServiceException.class, () -> subject.getOffers(language, unit));
        assertThat(keyMapping.getKeyMapping()).isEqualTo(OFFERS_NOT_FOUND);
        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(OFFERS);
        verify(query).all();
        verify(expected).total();
    }

    @Test
    void getOffers_exceptionFromContentful_throwServiceException() {
        String language = "language";
        String unit = "unit";
        RetryableException retryableException = mock(RetryableException.class);
        when(contentfulCDAClient.fetch(CDAEntry.class)).thenThrow(retryableException);
        ServiceException keyMapping = assertThrows(ServiceException.class, () -> subject.getOffers(language, unit));
        assertThat(keyMapping.getKeyMapping()).isEqualTo(OFFERS_INTEGRATION_FAILING);
        verify(contentfulCDAClient).fetch(CDAEntry.class);
    }


    @Test
    void getProducts_isCalled_returnCDAArray() {
        CDAArray expected = mock(CDAArray.class);
        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(PRODUCT)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(1);

        CDAArray result = subject.getProducts();

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(PRODUCT);
        verify(query).all();
        verify(expected).total();
    }


    @Test
    void getProduct_emptyList_throwServiceException() {
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(PRODUCT)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(0);

        ServiceException keyMapping = assertThrows(ServiceException.class, () -> subject.getProducts());
        assertThat(keyMapping.getKeyMapping()).isEqualTo(PRODUCTS_NOT_FOUND);
        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(PRODUCT);
        verify(query).all();
        verify(expected).total();
    }


    @Test
    void getProducts_exceptionFromContentful_throwServiceException() {
        RetryableException retryableException = mock(RetryableException.class);
        when(contentfulCDAClient.fetch(CDAEntry.class)).thenThrow(retryableException);
        ServiceException keyMapping = assertThrows(ServiceException.class, () -> subject.getProducts());
        assertThat(keyMapping.getKeyMapping()).isEqualTo(PRODUCTS_INTEGRATION_FAILING);
        verify(contentfulCDAClient).fetch(CDAEntry.class);
    }


    @Test
    void getAppBranches_isCalled_returnCDAArray() {
        String language = "language";
        String unit = "unit";
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.limit(MAX_LIMIT)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(APP_BRANCHES)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(1);

        CDAArray result = subject.getAppBranches(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(APP_BRANCHES);
        verify(query).all();
        verify(expected).total();
    }

    @Test
    void getAppBranches_emptyList_throwServiceException() {
        String language = "language";
        String unit = "unit";
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.limit(MAX_LIMIT)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(APP_BRANCHES)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(0);

        ServiceException keyMapping = assertThrows(ServiceException.class, () -> subject.getAppBranches(language, unit));

        assertThat(keyMapping.getKeyMapping()).isEqualTo(APP_BRANCHES_NOT_FOUND);
        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(APP_BRANCHES);
        verify(query).all();
        verify(expected).total();
    }

    @Test
    void getAppBranches_exceptionFromContentful_throwServiceException() {
        String language = "language";
        String unit = "unit";
        RetryableException retryableException = mock(RetryableException.class);
        when(contentfulCDAClient.fetch(CDAEntry.class)).thenThrow(retryableException);

        ServiceException keyMapping = assertThrows(ServiceException.class, () -> subject.getAppBranches(language, unit));

        assertThat(keyMapping.getKeyMapping()).isEqualTo(APP_BRANCHES_INTEGRATION_FAILING);
        verify(contentfulCDAClient).fetch(CDAEntry.class);
    }

//    @Test
//    void getCompetitions_isCalled_returnCDAArray() {
//        String language = "language";
//        String unit = "unit";
//        CDAArray expected = mock(CDAArray.class);
//
//        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
//        FetchQuery<CDAEntry> query = mock(FetchQuery.class);
//
//        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
//        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
//        when(cdaEntryFetchQuery.withContentType(ILA_COMPETITION)).thenReturn(query);
//        when(query.all()).thenReturn(expected);
//        when(expected.total()).thenReturn(1);
//
//        CDAArray result = subject.getCompetitions(language, unit);
//
//        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
//
//        verify(contentfulCDAClient).fetch(CDAEntry.class);
//        verify(cdaEntryFetchQuery).withContentType(ILA_COMPETITION);
//        verify(query).all();
//        verify(expected).total();
//    }


    @Test
    void getMerchantCategory_isCalled_returnCDAArray() {
        String language = "language";
        String unit = "unit";
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.limit(MAX_LIMIT)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(MERCHANT_CATEGORY)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(1);

        CDAArray result = subject.getMerchantCategory(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(MERCHANT_CATEGORY);
        verify(query).all();
        verify(expected).total();
    }
    
    @Test
    void getMerchants_isCalled_returnCDAArray() {
        String language = "language";
        String unit = "unit";
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.limit(MAX_LIMIT)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(MERCHANTS)).thenReturn(query);
        when(query.all()).thenReturn(expected);
        when(expected.total()).thenReturn(1);

        CDAArray result = subject.getMerchants(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(MERCHANTS);
        verify(query).all();
        verify(expected).total();
    }
    
    @Test
    void getMerchantCode_isCalled_returnCDAArray() {
        String language = "language";
        String unit = "unit";
        CDAArray expected = mock(CDAArray.class);

        FetchQuery<CDAEntry> cdaEntryFetchQuery = mock(FetchQuery.class);
        FetchQuery<CDAEntry> query = mock(FetchQuery.class);

        when(contentfulCDAClient.fetch(CDAEntry.class)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.where(anyString(), anyString())).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.limit(MAX_LIMIT)).thenReturn(cdaEntryFetchQuery);
        when(cdaEntryFetchQuery.withContentType(MCC_CODE)).thenReturn(query);
        when(query.all()).thenReturn(expected);

        CDAArray result = subject.getMerchantCode(language, unit);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(contentfulCDAClient).fetch(CDAEntry.class);
        verify(cdaEntryFetchQuery).withContentType(MCC_CODE);
        verify(query).all();
    }
}

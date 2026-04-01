package com.neo.v1.controller;

import com.neo.core.configuration.MessageSourceConfig;
import com.neo.core.message.GenericMessageSource;
import com.neo.v1.service.ProductCatalogueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductCatalogueController.class, useDefaultFilters = false)
@ContextConfiguration(classes = GenericMessageSource.class)
@Import(MessageSourceConfig.class)
class ProductCatalogueControllerTest {

    private static final String PRODUCT_CATALOGUE_ENDPOINT_URI = "/api/v1/product-catalogue";
    private static final String GET_PRODUCTS_URI = PRODUCT_CATALOGUE_ENDPOINT_URI + "/products";
    private static final String GET_OFFERS_URI = PRODUCT_CATALOGUE_ENDPOINT_URI + "/offers";
    private static final String GET_APP_BRANCHES_URI = PRODUCT_CATALOGUE_ENDPOINT_URI + "/branches";
    private static final String GET_MERCHANT_CATALOGUE_URI = PRODUCT_CATALOGUE_ENDPOINT_URI + "/merchant/category";
    private static final String GET_MERCHANTS_URI = PRODUCT_CATALOGUE_ENDPOINT_URI + "/merchant";
    private static final String GET_MERCHANTS_CODE_URI = PRODUCT_CATALOGUE_ENDPOINT_URI + "/merchant/code";

    private static final String GET_PRIZE_OUT_GIFT_CARDS = PRODUCT_CATALOGUE_ENDPOINT_URI + "/prizeout-gift-cards";

    private static final String GET_COMPETITION_URI = PRODUCT_CATALOGUE_ENDPOINT_URI + "/competition";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCatalogueService productCatalogueService;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = standaloneSetup(new ProductCatalogueController(productCatalogueService))
                .build();
    }

    @Test
    void getProducts_empty_successResponse() throws Exception {
        mockMvc.perform(get(GET_PRODUCTS_URI)
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

    @Test
    void deleteProductCatalogueOffers_empty_successResponse() throws Exception {
        mockMvc.perform(delete(PRODUCT_CATALOGUE_ENDPOINT_URI)
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
        verify(productCatalogueService).purgeData();
    }

    @Test
    void getOffers_empty_successResponse() throws Exception {
        mockMvc.perform(get(GET_OFFERS_URI)
                .param("language","en")
                .param("unit","neo")
                .header("customerId","customerId")
                .header("isNewOfferOnly","true")
                .header("offerCategory","offerCategory")
                .header("offerProduct","offerProduct")
                .header("offerSubProduct","offerSubProduct")
                .header("onlyCustomerApplicableOffer","true"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

    @Test
    void getcompetition_empty_successResponse() throws Exception {
        mockMvc.perform(get(GET_COMPETITION_URI)
                        .param("language","en")
                        .param("unit","neo")
                        .header("customerId","customerId"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

    @Test
    void getAppBranches_empty_successResponse() throws Exception {
        mockMvc.perform(get(GET_APP_BRANCHES_URI)
                        .param("language", "en")
                        .param("unit", "neo")
                        .header("customerId", "customerId"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

    @Test
    void getGiftCard_successResponse() throws Exception {
        mockMvc.perform(get(GET_PRIZE_OUT_GIFT_CARDS)
                        .param("language", "en")
                        .param("unit", "neo")
                        .header("customerId", "customerId"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }


    @Test
    void getProductCatalogueMerchantCategory_empty_successResponse() throws Exception {
        mockMvc.perform(get(GET_MERCHANT_CATALOGUE_URI)
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }
    
    @Test
    void getProductCatalogueMerchant_empty_successResponse() throws Exception {
        mockMvc.perform(get(GET_MERCHANTS_URI)
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }
    
    @Test
    void getProductCatalogueMerchantCode_empty_successResponse() throws Exception {
        mockMvc.perform(get(GET_MERCHANTS_CODE_URI)
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

}
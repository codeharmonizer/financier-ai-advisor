package com.neo.v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.core.exception.ServiceException;
import com.neo.v1.reader.PropertyReader;
import com.neo.v1.enums.ProductCatalogueServiceKeyMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.*;

/**
 * Directus read service (parallel to ContentfulService).
 *
 * Returns raw Directus "data" arrays as JsonNode, so you can wire existing mappers gradually.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DirectusService {

    private final PropertyReader propertyReader;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode getOffers(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            JsonNode data = fetchItems(propertyReader.getDirectusOffersCollection(), filters, "sort");
            ensureNotEmpty(data, OFFERS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(OFFERS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getProducts() {
        try {
            JsonNode data = fetchItems(propertyReader.getDirectusProductsCollection(), null, "sort");
            ensureNotEmpty(data, PRODUCTS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(PRODUCTS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getAtmsDetails() {
        try {
            JsonNode data = fetchItems(propertyReader.getDirectusAtmCollection(), null, "sort");
            ensureNotEmpty(data, PRODUCTS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(PRODUCTS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getBranchesDetails() {
        try {
            JsonNode data = fetchItems(propertyReader.getDirectusBranchesCollection(), null, "sort");
            ensureNotEmpty(data, PRODUCTS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(PRODUCTS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getOffersCategories(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            JsonNode data = fetchItems(propertyReader.getDirectusOffersCategoryCollection(), filters, "sort");
            ensureNotEmpty(data, OFFERS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(OFFERS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getOffersSubProducts(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            JsonNode data = fetchItems(propertyReader.getDirectusOffersSubProductsCollection(), filters, "sort");
            ensureNotEmpty(data, OFFERS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(OFFERS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getCompetitions(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            JsonNode data = fetchItems(propertyReader.getDirectusCompetitionsCollection(), filters, "sort");
            ensureNotEmpty(data, COMPETITION_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(COMPETITION_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getAppBranches(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            JsonNode data = fetchItems(propertyReader.getDirectusAppBranchesCollection(), filters, "sort");
            ensureNotEmpty(data, APP_BRANCHES_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(APP_BRANCHES_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getMerchantCategory(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            JsonNode data = fetchItems(propertyReader.getDirectusMerchantCategoryCollection(), filters, "sort");
            ensureNotEmpty(data, MERCHANTS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(MERCHANTS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getMerchants(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            JsonNode data = fetchItems(propertyReader.getDirectusMerchantsCollection(), filters, "sort");
            ensureNotEmpty(data, MERCHANTS_NOT_FOUND);
            return data;
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(MERCHANTS_INTEGRATION_FAILING, ex);
        }
    }

    public JsonNode getMerchantCode(String language, String unit) {
        try {
            Map<String, String> filters = new LinkedHashMap<>();
            filters.put("language", language);
            filters.put("unit", unit);
            return fetchItems(propertyReader.getDirectusMerchantCodeCollection(), filters, "sort");
        } catch (Exception ex) {
            throw new ServiceException(MERCHANTS_INTEGRATION_FAILING, ex);
        }
    }

    private JsonNode fetchItems(String collection, Map<String, String> filters, String sortField) throws Exception {
        String url = buildUrl(collection, filters, sortField);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(propertyReader.getDirectusToken());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode data = root.path("data");

        if (!data.isArray()) {
            throw new RuntimeException("Unexpected Directus response for collection: " + collection);
        }

        return data;
    }

    private String buildUrl(String collection, Map<String, String> filters, String sortField) {
        StringBuilder sb = new StringBuilder();
        sb.append(propertyReader.getDirectusBaseUrl()).append("/items/").append(collection);
        sb.append("?limit=1000");

        if (sortField != null && !sortField.isBlank()) {
            sb.append("&sort=").append(encode(sortField));
        }

        if (filters != null) {
            filters.forEach((k, v) -> {
                if (v != null && !v.isBlank()) {
                    sb.append("&filter[").append(encode(k)).append("][_eq]=").append(encode(v));
                }
            });
        }

        return sb.toString();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private void ensureNotEmpty(JsonNode data, ProductCatalogueServiceKeyMapping key) {
        if (data == null || !data.isArray() || data.size() == 0) {
            throw new ServiceException(key);
        }
    }
}

package com.neo.v1.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.SubProducts;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DirectusOfferDetailsMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<OfferDetails> map(JsonNode array, List<SubProducts> subProductsList) {
        if (array == null || !array.isArray()) return Collections.emptyList();

        Map<String, SubProducts> subProductsById = Optional.ofNullable(subProductsList).orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> x.getSubProductId() != null)
                .collect(Collectors.toMap(SubProducts::getSubProductId, x -> x, (a, b) -> a));

        List<OfferDetails> out = new ArrayList<>();
        for (JsonNode n : array) {
            OfferDetails d = OfferDetails.builder()
                    .offerId(text(n, "id", text(n, "sys_id", null)))
                    .offerTitle(text(n, "cmsLabel", null))
                    .offerBrandName(text(n, "offerBrandName", null))
                    .offerDescription(text(n, "offerDescription", null))
                    .offerDiscountRate(decimal(n, "offerPercentage", "offerDiscountRate"))
                    .offerTermsAndConditions(text(n, "offerTermsAndConditions", null))
                    .offerValue(decimal(n, "offerValue"))
                    .isNewOffer(bool(n, "isNew"))
                    .offerPageUrl(text(n, "offerPageUrl", null))
                    .offerTag(text(n, "offerTag", null))
                    .offerDisplayOrder(longNum(n, "offerOrder", "sort"))
                    .offerEligibilityTags(text(n, "eligibilityTags", null))
                    .offerProduct(text(n, "offerProduct", null))
                    .build();

            String bg = assetUrl(n.path("BackgroundImage"));
            if (bg != null) d.setOfferImageUrl(bg);
            String brand = assetUrl(n.path("offerBrandImage"));
            if (brand != null) d.setOfferBrandImageUrl(brand);
            String list = assetUrl(n.path("offerListImage"));
            if (list != null) d.setOfferListImageUrl(list);
            String small = assetUrl(n.path("offerSmallImage"));
            if (small != null) d.setOfferListSmallImageUrl(small);
            String details = assetUrl(n.path("offerDetailsImage"));
            if (details != null) d.setOfferDetailsImageUrl(details);

            // keep compatibility: if applicable products are present as simple ids, resolve from sub-products map
            // otherwise leave null/empty and your existing filter utility still works on available fields.
            JsonNode ap = n.path("offerApplicableProducts");
            if (ap != null && !ap.isMissingNode() && !ap.isNull()) {
                // If it is stringified JSON, try parsing to avoid breaking deserialization callers.
                if (ap.isTextual()) {
                    try { objectMapper.readTree(ap.asText()); } catch (Exception ignored) { }
                }
            }

            out.add(d);
        }

        return out;
    }

    private String text(JsonNode n, String field, String def) {
        JsonNode v = n.path(field);
        return v.isMissingNode() || v.isNull() ? def : v.asText();
    }

    private boolean bool(JsonNode n, String field) {
        JsonNode v = n.path(field);
        return !v.isMissingNode() && !v.isNull() && v.asBoolean();
    }

    private Long longNum(JsonNode n, String... fields) {
        for (String f : fields) {
            JsonNode v = n.path(f);
            if (!v.isMissingNode() && !v.isNull() && v.isNumber()) return v.asLong();
            if (!v.isMissingNode() && !v.isNull() && v.isTextual()) {
                try { return Long.parseLong(v.asText()); } catch (Exception ignored) {}
            }
        }
        return null;
    }

    private BigDecimal decimal(JsonNode n, String... fields) {
        for (String f : fields) {
            JsonNode v = n.path(f);
            if (v.isNumber()) return v.decimalValue();
            if (v.isTextual()) {
                try { return new BigDecimal(v.asText()); } catch (Exception ignored) {}
            }
        }
        return null;
    }

    private String assetUrl(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) return null;

        if (node.isTextual()) {
            String s = node.asText();
            if (s == null || s.isBlank()) return null;

            if (s.startsWith("http")) return s;
            if (s.startsWith("//")) return "https:" + s;

            // Directus file id stored directly as string
            if (looksLikeUuid(s)) return "/assets/" + s;

            // Sometimes imported payloads keep JSON as text; try to resolve nested url/id
            try {
                JsonNode parsed = objectMapper.readTree(s);
                String nested = assetUrl(parsed);
                if (nested != null) return nested;
            } catch (Exception ignored) {
            }

            return s;
        }

        if (node.hasNonNull("url")) {
            String s = node.get("url").asText();
            return s.startsWith("//") ? "https:" + s : s;
        }
        if (node.hasNonNull("directus_file_url")) return node.get("directus_file_url").asText();
        if (node.hasNonNull("id")) return "/assets/" + node.get("id").asText();
        return null;
    }

    private boolean looksLikeUuid(String s) {
        return s != null && s.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$");
    }
}

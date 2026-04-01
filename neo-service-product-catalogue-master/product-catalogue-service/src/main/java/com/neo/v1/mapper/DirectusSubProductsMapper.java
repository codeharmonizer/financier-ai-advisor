package com.neo.v1.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.neo.v1.product.catalogue.model.SubProducts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DirectusSubProductsMapper {

    public List<SubProducts> map(JsonNode array) {
        List<SubProducts> out = new ArrayList<>();
        if (array == null || !array.isArray()) return out;

        for (JsonNode n : array) {
            Long order = n.path("displayOrder").isNumber() ? n.path("displayOrder").asLong() : null;
            SubProducts sp = SubProducts.builder()
                    .subProductId(text(n, "productIdentifier", text(n, "subProductId", null)))
                    .name(text(n, "subProductName", text(n, "name", null)))
                    .displayOrder(order)
                    .build();

            String img = assetUrl(n.path("productImage"));
            if (img != null) sp.setSubProductBaseImageUrl(img);
            out.add(sp);
        }
        return out;
    }

    private String text(JsonNode n, String field, String def) {
        JsonNode v = n.path(field);
        return v.isMissingNode() || v.isNull() ? def : v.asText();
    }

    private String assetUrl(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) return null;

        if (node.isTextual()) {
            String s = node.asText();
            if (s == null || s.isBlank()) return null;
            if (s.startsWith("http")) return s;
            if (s.startsWith("//")) return "https:" + s;
            if (looksLikeUuid(s)) return "/assets/" + s;

            try {
                JsonNode parsed = new com.fasterxml.jackson.databind.ObjectMapper().readTree(s);
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

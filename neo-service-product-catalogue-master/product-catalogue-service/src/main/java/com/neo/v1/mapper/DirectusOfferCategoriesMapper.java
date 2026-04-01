package com.neo.v1.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.neo.v1.product.catalogue.model.OfferCategory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DirectusOfferCategoriesMapper {

    public List<OfferCategory> map(JsonNode array) {
        List<OfferCategory> out = new ArrayList<>();
        if (array == null || !array.isArray()) return out;

        for (JsonNode n : array) {
            Long order = n.path("displayOrder").isNumber() ? n.path("displayOrder").asLong() : null;
            OfferCategory c = OfferCategory.builder()
                    .categoryId(text(n, "id", null))
                    .name(text(n, "categoryName", text(n, "name", null)))
                    .startGradientColor(text(n, "startColor", null))
                    .endGradientColor(text(n, "endColor", null))
                    .displayOrder(order)
                    .build();
            String img = assetUrl(n.path("categoryImage"));
            if (img != null) c.setCategoryBaseImageUrl(img);
            out.add(c);
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

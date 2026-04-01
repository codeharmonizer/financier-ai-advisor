package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.neo.v1.product.catalogue.model.CompetitionData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_BUTTON_CAPTION;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_BUTTON_NAVIGATION_ACTION;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_BUTTON_NAVIGATION_ACTION_LINK;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_BUTTON_VISIBILITY;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_COVER_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_DESCRIPTION;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_END_DATE;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_IMAGE_AR;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_IMAGE_EN;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_ISKANZCOMPETITION;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_START_DATE;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_TITLE;
import static com.neo.v1.constants.ProductCatalogueConstants.COMPETITION_UNIT;
import static com.neo.v1.constants.ProductCatalogueConstants.ENTRY_FIELD_IMAGE;
import static com.neo.v1.constants.ProductCatalogueConstants.HTTPS;
import static com.neo.v1.constants.ProductCatalogueConstants.SORT_ORDER;

@Component
@RequiredArgsConstructor
public class CompetitionDataMapper {

    public List<CompetitionData> map(CDAArray cdaArray) {
        return cdaArray.items().stream().map(cdaResource -> map(cdaResource)).collect(Collectors.toList());
    }

    private CompetitionData map(CDAResource cdaResource) {
        CDAEntry entry = (CDAEntry) cdaResource;
        Double order = entry.getField(SORT_ORDER);
        CompetitionData competitionData = CompetitionData.builder()
                .competitionId(cdaResource.id())
                .competitionTitle(entry.getField(COMPETITION_TITLE))
                .competitionDescription(entry.getField(COMPETITION_DESCRIPTION))
                .competitionButtonVisibility(entry.getField(COMPETITION_BUTTON_VISIBILITY))
                .buttonCaption(entry.getField(COMPETITION_BUTTON_CAPTION))
                .buttonNavigationAction(entry.getField(COMPETITION_BUTTON_NAVIGATION_ACTION))
                .buttonNavigationActionLink(entry.getField(COMPETITION_BUTTON_NAVIGATION_ACTION_LINK))
                .competitionStartDate(entry.getField(COMPETITION_START_DATE))
                .competitionEndDate(entry.getField(COMPETITION_END_DATE))
                .isKanzCompetition(entry.getField(COMPETITION_ISKANZCOMPETITION))
                .unit(entry.getField(COMPETITION_UNIT))
                .sort(Objects.nonNull(order) ? order.longValue() : null)
                .build();


        Optional<CDAEntry> competitionImageEn = Optional.ofNullable(entry.getField(COMPETITION_IMAGE_EN));
        competitionImageEn.ifPresent(image -> competitionData.setCompetitionImageEn(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        Optional<CDAEntry> competitionImageAr = Optional.ofNullable(entry.getField(COMPETITION_IMAGE_AR));
        competitionImageAr.ifPresent(image -> competitionData.setCompetitionImageAr(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        Optional<CDAEntry> competitionCoverImage = Optional.ofNullable(entry.getField(COMPETITION_COVER_IMAGE));
        competitionCoverImage.ifPresent(image -> competitionData.setCompetitionCoverImage(HTTPS + ((CDAAsset) image.getField(ENTRY_FIELD_IMAGE)).url()));

        return competitionData;
    }
}

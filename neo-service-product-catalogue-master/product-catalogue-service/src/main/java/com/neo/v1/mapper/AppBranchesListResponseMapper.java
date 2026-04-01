package com.neo.v1.mapper;

import com.neo.v1.product.catalogue.model.AppBranchesListData;
import com.neo.v1.product.catalogue.model.AppBranchesListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_APP_BRANCHES_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_APP_BRANCHES_SUCCESS_MESSAGE;

@RequiredArgsConstructor
@Component
public class AppBranchesListResponseMapper {

    private final MetaMapper metaMapper;
    private final AppBranchesListDataMapper appBranchesListDataMapper;

    public AppBranchesListResponse map(AppBranchesListData appBranchesListData) {
        return AppBranchesListResponse.builder()
                .meta(metaMapper.map(GET_APP_BRANCHES_SUCCESS_CODE, GET_APP_BRANCHES_SUCCESS_MESSAGE))
                .data(appBranchesListDataMapper.map(appBranchesListData))
                .build();
    }
}

package com.neo.v1.mapper;

import com.neo.v1.product.catalogue.model.AppBranchesListData;
import com.neo.v1.product.catalogue.model.AppBranchesListResponse;
import com.neo.v1.product.catalogue.model.Meta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.neo.v1.constants.ProductCatalogueConstants.GET_APP_BRANCHES_SUCCESS_CODE;
import static com.neo.v1.constants.ProductCatalogueConstants.GET_APP_BRANCHES_SUCCESS_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith ( MockitoExtension.class )
class AppBranchesListResponseMapperTest {

    @InjectMocks
    private AppBranchesListResponseMapper subject;
    @Mock
    private MetaMapper metaMapper;
    @Mock
    private AppBranchesListDataMapper appBranchesListDataMapper;

    @Test
    void map_withAppBranchesListData_returnAppBranchesListResponse() {
        AppBranchesListData appBranchesListData = new AppBranchesListData();
        Meta meta = Meta.builder().build();
        AppBranchesListResponse expected = AppBranchesListResponse.builder()
                .meta(meta)
                .data(appBranchesListData).build();

        when(metaMapper.map(GET_APP_BRANCHES_SUCCESS_CODE, GET_APP_BRANCHES_SUCCESS_MESSAGE)).thenReturn(meta);
        when(appBranchesListDataMapper.map(appBranchesListData)).thenReturn(appBranchesListData);

        AppBranchesListResponse result = subject.map(appBranchesListData);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
        verify(metaMapper).map(GET_APP_BRANCHES_SUCCESS_CODE, GET_APP_BRANCHES_SUCCESS_MESSAGE);
        verify(appBranchesListDataMapper).map(appBranchesListData);
    }
}

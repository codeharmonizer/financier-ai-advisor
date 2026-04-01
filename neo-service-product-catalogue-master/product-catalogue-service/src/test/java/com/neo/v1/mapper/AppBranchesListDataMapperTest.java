package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.AppBranchDetails;
import com.neo.v1.product.catalogue.model.AppBranchesListData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith ( MockitoExtension.class )
class AppBranchesListDataMapperTest {

    @InjectMocks
    private AppBranchesListDataMapper subject;
    @Mock
    private AppBranchDetailsMapper appBranchDetailsMapper;

    @Test
    void map_withCDAArray_returnAppBranchesListData() {
        CDAArray appBranches = new CDAArray();
        List<AppBranchDetails> appBranchDetailsList = new ArrayList<>();
        List<String> governorates = new ArrayList<>();
        AppBranchesListData expected = AppBranchesListData.builder()
                .governorates(governorates)
                .branches(appBranchDetailsList)
                .build();
        when(appBranchDetailsMapper.map(appBranches)).thenReturn(appBranchDetailsList);

        AppBranchesListData result = subject.map(appBranches);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
        verify(appBranchDetailsMapper).map(appBranches);
    }

    @Test
    void map_withAppBranchesListData_returnAppBranchesListData() {
        List<AppBranchDetails> appBranchDetailsList1 = Arrays.asList(AppBranchDetails.builder()
                .unit(null).build());
        AppBranchesListData expected = AppBranchesListData.builder()
                .branches(appBranchDetailsList1)
                .build();

        List<AppBranchDetails> appBranchDetailsList2 = Arrays.asList(AppBranchDetails.builder().build());
        AppBranchesListData appBranchesListData = AppBranchesListData.builder()
                .branches(appBranchDetailsList2)
                .build();

        AppBranchesListData result = subject.map(appBranchesListData);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}

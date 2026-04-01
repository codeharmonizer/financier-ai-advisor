package com.neo.v1.mapper;


import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.AppBranchDetails;
import com.neo.v1.product.catalogue.model.AppBranchesListData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AppBranchesListDataMapper {

    private final AppBranchDetailsMapper appBranchDetailsMapper;

    public AppBranchesListData map(CDAArray appBranches) {
        List<AppBranchDetails> appBranchDetailsList = appBranchDetailsMapper.map(appBranches);
        List<String> governorates = appBranchDetailsList.stream().map(AppBranchDetails::getGovernorate).distinct().collect(Collectors.toList());
        return AppBranchesListData
                .builder()
                .governorates(governorates)
                .branches(appBranchDetailsList)
                .build();
    }

    public AppBranchesListData map(AppBranchesListData appBranchesListData) {
        List<AppBranchDetails> appBranchDetailsList = appBranchesListData.getBranches().stream()
                .map(branchDetail -> {
                    branchDetail.setUnit(null);
                    return branchDetail;
                }).collect(Collectors.toList());

        return AppBranchesListData
                .builder()
                .governorates(appBranchesListData.getGovernorates())
                .branches(appBranchDetailsList)
                .build();
    }
}

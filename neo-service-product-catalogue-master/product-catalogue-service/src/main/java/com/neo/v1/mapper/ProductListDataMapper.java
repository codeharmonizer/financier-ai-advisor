package com.neo.v1.mapper;

import com.contentful.java.cda.CDAArray;
import com.neo.v1.product.catalogue.model.ProductListData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductListDataMapper {

    private final ProductDetailsMapper productDetailsMapper;
    private final AtmDetailsMapper atmDetailsMapper;
    private final BranchDetailsMapper branchDetailsMapper;

    public ProductListData map (CDAArray productArray, CDAArray atmArray, CDAArray branchArray) {
        ProductListData productListData = ProductListData.builder().build();
        try{
            productListData.setCasaProducts(productDetailsMapper.map(productArray));
        }catch (Exception ex)
        {
            productListData.setCasaProducts(null);
        }
        try{
            productListData.setAtmList(atmDetailsMapper.map(atmArray));
        }catch (Exception ex)
        {
            productListData.setAtmList(null);
        }
        try{
            productListData.setBranchesList(branchDetailsMapper.map(branchArray));
        }catch (Exception ex)
        {
            productListData.setBranchesList(null);
        }
        return productListData;
    }
}

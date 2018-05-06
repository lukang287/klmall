package com.klmall.service;

import com.github.pagehelper.PageInfo;
import com.klmall.common.ServerResponse;
import com.klmall.pojo.Product;
import com.klmall.vo.ProductDetailVo;

/**
 * @program: klmall
 * @description:
 * @author: Mr.Wang
 * @create: 2018-05-01 22:17
 **/
public interface IProductService {

    //back end
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ServerResponse<ProductDetailVo> getDetail(Integer productId);

    ServerResponse<PageInfo> getList(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);
}

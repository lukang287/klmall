package com.klmall.service;

import com.klmall.common.ServerResponse;
import com.klmall.pojo.Product;

/**
 * @program: klmall
 * @description:
 * @author: Mr.Wang
 * @create: 2018-05-01 22:17
 **/
public interface IProductService {

    //back end
    ServerResponse saveOrUpdateProduct(Product product);


}

package com.klmall.service;

import com.github.pagehelper.PageInfo;
import com.klmall.common.ServerResponse;
import com.klmall.pojo.Shipping;

import java.util.Map;

/**
 * @program: klmall
 * @description: 地址接口
 * @author: Mr.Wang
 * @create: 2018-05-05 09:54
 **/
public interface IShippingService {

    ServerResponse<Map<String, Integer>> add(Integer userId, Shipping shipping);

    ServerResponse del(Integer shippingId, Integer userId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer shippingId, Integer userId);

    ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}

package com.klmall.service;

import com.github.pagehelper.PageInfo;
import com.klmall.common.ServerResponse;
import com.klmall.vo.OrderVo;

import java.util.Map;

/**
 * @program: klmall
 * @description:
 * @author: Mr.Wang
 * @create: 2018-05-06 01:19
 **/
public interface IOrderService {

    ServerResponse<Map<String, String>> pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    ServerResponse createOrder(Integer userId, Integer shippingId);

    ServerResponse cancelOrder(Integer userId, Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNum, Integer pageSize);


    /**************** BACK END *****************************/

    ServerResponse<PageInfo> manageList(Integer pageNum, Integer pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, Integer pageNum, Integer pageSize);

    ServerResponse<String> sendGoods(Long orderNo);
}

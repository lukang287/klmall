package com.klmall.service;

import com.klmall.common.ServerResponse;
import com.klmall.vo.CartVo;

/**
 * @program: klmall
 * @description: 购物车service
 * @author: Mr.Wang
 * @create: 2018-05-04 20:32
 **/
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> delete(Integer userId, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnselect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}

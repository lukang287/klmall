package com.klmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.klmall.common.ServerResponse;
import com.klmall.dao.ShippingMapper;
import com.klmall.pojo.Shipping;
import com.klmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: klmall
 * @description: 实现
 * @author: kang.lu
 * @create: 2018-05-05 09:54
 **/
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse<Map<String, Integer>> add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0){
            Map result = new HashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("添加地址成功", result);
        }
        return ServerResponse.createByErrorMessage("添加地址失败");
    }

    @Override
    public ServerResponse del(Integer shippingId, Integer userId){
        int rowCount = shippingMapper.deleteByShippingIdUserId(shippingId, userId);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    @Override
    public ServerResponse<Shipping> select(Integer shippingId, Integer userId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(shippingId, userId);
        if (shipping == null){
            return ServerResponse.createByErrorMessage("无法查到该地址");
        }
        return ServerResponse.createBySuccess("查找成功", shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess("查询列表成功", pageInfo);
    }
}

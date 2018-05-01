package com.klmall.service.impl;

import com.klmall.common.ServerResponse;
import com.klmall.dao.ProductMapper;
import com.klmall.pojo.Product;
import com.klmall.service.IProductService;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: klmall
 * @description: 实现类
 * @author: kang.lu
 * @create: 2018-05-01 22:17
 **/
@Service("iProductService")
public class IProductServiceImpl implements IProductService{

    @Autowired
    protected ProductMapper productMapper;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product){
        if (product == null){
            return ServerResponse.createByErrorMessage("产品信息不能为空");
        }
        //将子图的第一个当做主图
        if (StringUtils.isNotBlank(product.getSubImages())){
            String[] subImageArray = product.getSubImages().split(",");
            if (subImageArray.length > 0){
                product.setMainImage(subImageArray[0]);
            }
        }
        //更新or新建
        if (product.getId() == null){
            int rowCount = productMapper.insert(product);
            if (rowCount > 0){
                return ServerResponse.createBySuccess("新建产品成功");
            }
            return ServerResponse.createBySuccess("新建产品失败");
        }else {
            int rowCount = productMapper.updateByPrimaryKey(product);
            if (rowCount > 0){
                return ServerResponse.createBySuccess("更新产品成功");
            }
            return ServerResponse.createBySuccess("更新产品失败");
        }

    }
}

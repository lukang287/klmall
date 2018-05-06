package com.klmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.klmall.common.Const;
import com.klmall.common.ResponseCode;
import com.klmall.common.ServerResponse;
import com.klmall.dao.CartMapper;
import com.klmall.dao.ProductMapper;
import com.klmall.pojo.Cart;
import com.klmall.pojo.Product;
import com.klmall.service.ICartService;
import com.klmall.service.IFileService;
import com.klmall.util.BigDecimalUtil;
import com.klmall.util.PropertiesUtil;
import com.klmall.vo.CartProductVo;
import com.klmall.vo.CartVo;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: klmall
 * @description:
 * @author: kang.lu
 * @create: 2018-05-04 20:34
 **/
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count){
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null){
            //productId不在cart中，需要添加
            Cart cartItem = new Cart();
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartItem.setUserId(userId);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        }else {
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count){
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> delete(Integer userId, String productIds){
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId, productIdList);
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnselect(Integer userId, Integer productId, Integer checked){
        cartMapper.checkedOrUncheckedProduct(userId, productId, checked);
        return this.list(userId);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if (userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }

    //购物车核心方法
    private CartVo getCartVoLimit(Integer userId){
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(cartList)){
            for( Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null){
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductSubTitle(product.getSubtitle());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()){
                        cartProductVo.setLimitQuanlity(Const.Cart.LIMIT_NUM_SUCCESS);
                        buyLimitCount = cartItem.getQuantity();
                    }else{
                        cartProductVo.setLimitQuanlity(Const.Cart.LIMIT_NUM_FAIL);
                        buyLimitCount = product.getStock();
                        Cart updateCartQty = new Cart();
                        updateCartQty.setId(cartItem.getId());
                        updateCartQty.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(updateCartQty);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算该产品总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), buyLimitCount));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Const.Cart.CHECKED){
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }

        //封装返回
        CartVo cartVo = new CartVo();
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if (userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId)==0;
    }
}

package com.klmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @program: klmall
 * @description:常量类
 * @author: kang.lu
 * @create: 2018-04-24 22:36
 **/
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface ProductListOderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc", "price_desc");
    }

    public interface Role{
        //普通用户
        int ROLE_CUSTOMER = 0;
        //管理员
        int ROLE_AMDIN = 1;
    }

    public interface Cart{
        //普通用户
        int UN_CHECKED = 0;
        //管理员
        int CHECKED = 1;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public enum ProductStatusEnum{
        ON_SALE(1, "在线");

        int code;
        String value;
        ProductStatusEnum(int code, String value){
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static ProductStatusEnum codeOf(int code){
            for (ProductStatusEnum productStatusEnum : values()){
                if (productStatusEnum.getCode() == code){
                    return productStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public enum OrderStatusEnum{
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已支付"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");

        int code;
        String value;
        OrderStatusEnum(int code, String value){
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()){
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }

    }

    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝"),
        WX_PAY(2,"微信支付");

        int code;
        String value;
        PayPlatformEnum(int code, String value){
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static PayPlatformEnum codeOf(int code){
            for (PayPlatformEnum payPlatformEnum : values()){
                if (payPlatformEnum.getCode() == code){
                    return payPlatformEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public enum PayTypeEnum{
        ONLINE_PAY(1,"在线支付");

        int code;
        String value;
        PayTypeEnum(int code, String value){
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static PayTypeEnum codeOf(int code){
            for (PayTypeEnum payTypeEnum : values()){
                if (payTypeEnum.getCode() == code){
                    return payTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }
}

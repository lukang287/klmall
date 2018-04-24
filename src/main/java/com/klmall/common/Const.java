package com.klmall.common;

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

    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_AMDIN = 1;//管理员
    }
}

package com.klmall.service;

import com.klmall.common.ServerResponse;
import com.klmall.pojo.User;

/**
 * @program: klmall
 * @description: 用户service类
 * @author: kang.lu
 * @create: 2018-04-24 21:45
 **/
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);
}

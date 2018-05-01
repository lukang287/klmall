package com.klmall.controller.backend;

import com.klmall.common.Const;
import com.klmall.common.ResponseCode;
import com.klmall.common.ServerResponse;
import com.klmall.pojo.Product;
import com.klmall.pojo.User;
import com.klmall.service.IProductService;
import com.klmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @program: klmall
 * @description: 后台产品controller
 * @author: kang.lu
 * @create: 2018-05-01 22:15
 **/
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }

        return iProductService.saveOrUpdateProduct(product);
    }
}

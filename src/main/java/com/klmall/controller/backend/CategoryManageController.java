package com.klmall.controller.backend;

import com.klmall.common.Const;
import com.klmall.common.ResponseCode;
import com.klmall.common.ServerResponse;
import com.klmall.pojo.User;
import com.klmall.service.ICategoryService;
import com.klmall.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlAccessorOrder;

/**
 * @program: klmall
 * @description:
 * @author: kang.lu
 * @create: 2018-05-01 20:50
 **/
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        //添加品类的逻辑
        return iCategoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        //更新category name
        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "parentId", defaultValue = "0")Integer parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        //查询平级子节点
        return iCategoryService.getChildrenParallelCategory(parentId);
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0")Integer categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        //查询平级子节点和递归子节点的
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}

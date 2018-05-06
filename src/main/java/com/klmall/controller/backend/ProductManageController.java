package com.klmall.controller.backend;

import com.google.common.collect.Maps;
import com.klmall.common.Const;
import com.klmall.common.ResponseCode;
import com.klmall.common.ServerResponse;
import com.klmall.pojo.Product;
import com.klmall.pojo.User;
import com.klmall.service.IFileService;
import com.klmall.service.IProductService;
import com.klmall.service.IUserService;
import com.klmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @program: klmall
 * @description: 后台产品controller
 * @author: kang.lu
 * @create: 2018-05-01 22:15
 **/
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

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

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        return iProductService.setSaleStatus(productId, status);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession session, Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        return iProductService.manageProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value="pageNum", defaultValue = "1") Integer pageNum,@RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        return iProductService.getProductList(pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, String productName, Integer productId, @RequestParam(value="pageNum", defaultValue = "1") Integer pageNum,@RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员，无权限操作");
        }
        //业务逻辑
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFile = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFile);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        /*
        * 使用simditor插件，本接口是按插件要求编写
        * {
        *   success: , msg: , file_path:
        * }
        * */
        Map resultMap = Maps.newHashMap();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            resultMap.put("success", false);
            resultMap.put("msg", "用户未登录，需要登录");
            return resultMap;
        }
        //校验是否为管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            resultMap.put("success", false);
            resultMap.put("msg", "非管理员，无权限操作");
            return resultMap;
        }
        //业务逻辑
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFile = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFile)){
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;
    }
}

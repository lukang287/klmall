package com.klmall.service;

import com.klmall.common.ServerResponse;
import com.klmall.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: klmall
 * @description:
 * @author: Mr.Wang
 * @create: 2018-05-01 21:02
 **/
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId);

    ServerResponse selectCategoryAndChildrenById(Integer parentId);
}

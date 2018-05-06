package com.klmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.klmall.common.ServerResponse;
import com.klmall.dao.CategoryMapper;
import com.klmall.pojo.Category;
import com.klmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @program: klmall
 * @description:
 * @author: kang.lu
 * @create: 2018-05-01 21:03
 **/
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId){
        if (parentId == null && StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName){
        if (categoryId == null && StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("更新品类成功");
        }
        return ServerResponse.createByErrorMessage("更新品类失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer parentId){
        //guava方法初始化
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, parentId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (parentId != null){
            for (Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    //set 集合排重，普通类型自带的hashcode()和equal()即可，自定义类型的需要重写hashcode()和equal()
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        //查找子节点为空时退出递归
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList){
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}

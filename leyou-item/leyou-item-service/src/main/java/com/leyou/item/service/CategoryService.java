package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    /**
     * 根据父节点的id查询子节点
     * @param pid
     * */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }
    public List<String> queryNameByIds(List<Long> ids){
        List<Category> categories = categoryMapper.selectByIdList(ids);
        return categories.stream().map(category -> {
            return category.getName();
        }).collect(Collectors.toList());
    }
}

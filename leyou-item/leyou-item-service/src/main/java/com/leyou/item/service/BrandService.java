package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.aspectj.asm.internal.HandleProviderDelimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandService  {
    @Autowired
    private  BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria=example.createCriteria();
       if(StringUtils.isNotBlank(key)){
           criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
       }

       if(org.apache.commons.lang.StringUtils.isNotBlank(sortBy)){
           example.setOrderByClause(sortBy+"  "+(desc?"asc":"desc"));
       }
        PageHelper.startPage(page,rows);
        List<Brand> brands = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo=new PageInfo<>(brands);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());

    }
    /**
     *
     * 新增品牌
     * 事务管理
     * */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        boolean flag= brandMapper.insertSelective(brand)==1;
        if(flag){
            cids.forEach(cid->{
                Map<Integer,Brand> map=new HashMap<>();
                ArrayList<Brand> list=new ArrayList<>();

                brandMapper.insertCategoryAndBrand(cid,brand.getId());
            });
        }
    }

    public List<Brand> queryBrandByCids(Long cid) {
        return brandMapper.queryBrandByCids(cid);
    }

    public Brand queryBrandById(Long id) {

        return brandMapper.selectByPrimaryKey(id);
    }
}


package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    BrandService brandService;
    @GetMapping("page")//key=&page=1&rows=5&sortBy=id&desc=false
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "key" ,required = false) String key,
            @RequestParam(value = "page" ,required = false) Integer page,
            @RequestParam(value = "rows" ,required = false) Integer rows,
            @RequestParam(value = "sortBy" ,required = false) String sortBy,
            @RequestParam(value = "desc" ,required = false) Boolean desc
    ){
        PageResult<Brand> result=brandService.queryBrandByPage(key,page,rows,sortBy,desc);
        if(result==null|| CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return  ResponseEntity.ok(result);
    }
    @PostMapping

    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        this.brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCids(@PathVariable("cid")Long cid){
//        System.out.println("进入方法");
        List<Brand>brands= brandService.queryBrandByCids(cid);
        if(CollectionUtils.isEmpty(brands)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        Brand brand= brandService.queryBrandById(id);
        if(brand==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(brand);
    }
}

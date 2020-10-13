package com.leyou.controller;

import com.leyou.service.UploadService;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
//@RequestMapping("upload")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam(value = "file",required = false)MultipartFile file){
        System.out.println("进入 upload方法");
        ArrayList<Integer> list = new ArrayList<>();
        
        String  url= uploadService.uploadImage(file);
        if(StringUtils.isBlank(url)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }
}

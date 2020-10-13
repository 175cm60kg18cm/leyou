package com.leyou.service;

//import com.github.tobato.fastdfs.domain.fdfs.StorePath;
//import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {
    public static final List<String> CONTENT_TYPES= Arrays.asList("image/gif","image/jpeg");
    private static final Logger LOGGER= LoggerFactory.getLogger(UploadService.class);
//    @Autowired
//    private FastFileStorageClient fastFileStorageClient;
    public String uploadImage(MultipartFile file) {
        //校验文件类型
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        if(!CONTENT_TYPES.contains(contentType)){
            LOGGER.info("文件类型不合法:    "+originalFilename);
            return null;
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage==null){
                LOGGER.info("文件内容不合法：  "+originalFilename);
                return null;
            }
            //保存到服务器
            file.transferTo(new File("D:\\image\\"+originalFilename));
            //返回地址进行回显
            return "http://image.leyou.com:81/"+originalFilename;
//            String ext=StringUtils.substringAfterLast(originalFilename,".");
//            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
//            return "http://image.leyou.com/"+storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误："+originalFilename);
            e.printStackTrace();
        }
        return null;
        //校验文件内容
        //保存到服务器
        //返回URL进行回显
    }
}

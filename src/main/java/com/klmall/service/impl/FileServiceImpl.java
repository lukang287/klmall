package com.klmall.service.impl;

import com.google.common.collect.Lists;
import com.klmall.service.IFileService;
import com.klmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.MultiPixelPackedSampleModel;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: klmall
 * @description:
 * @author: kang.lu
 * @create: 2018-05-03 15:05
 **/
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+ fileExt;

        logger.info("开始上传文件，上传文件名为：{}，路径为：{}，新文件名：{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //todo 将targetFile上传FTP
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //todo 删除upload下的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
        }
        return targetFile.getName();
    }
}

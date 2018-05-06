package com.klmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: klmall
 * @description:
 * @author: Mr.Wang
 * @create: 2018-05-03 15:04
 **/
public interface IFileService {

    String upload(MultipartFile file, String path);
}

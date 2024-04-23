package com.example.springbootminio.controller;


import com.example.springbootminio.config.MinioHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

@RestController

public class MinioController {


    @Autowired
    MinioHelper minioHelper;


    @RequestMapping("/upload")
    public String uploadFile(String filename, MultipartFile file) throws Exception{
        try(InputStream is = file.getInputStream()) {
            minioHelper.upload(filename, is);
        }

        return "ok";
    }


//    @RequestMapping("/download")
//    public void uploadFile(String filename, HttpServletResponse response) throws Exception{
//        try(OutputStream os = response.getOutputStream()) {
//            minioHelper.download(filename, os);
//        }
//    }

    @RequestMapping("/download")
    public void uploadFile(String filename, OutputStream os) throws Exception{
        minioHelper.download(filename, os);

    }


    @RequestMapping("/exist")
    public boolean exist(String filename) throws Exception{
        return minioHelper.exists(filename);
    }



}

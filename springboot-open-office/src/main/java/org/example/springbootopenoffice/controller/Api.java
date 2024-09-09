package org.example.springbootopenoffice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springbootopenoffice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author booty
 */
@Controller
@Slf4j
@RequestMapping("/api")
public class Api {

    @Autowired
    private FileService fileService;
    
    @GetMapping("/onlinePreview/url")
    public void onlinePreview4urlFile(@RequestParam("url") String url, HttpServletResponse response) throws Exception{
        try (ServletOutputStream os = response.getOutputStream()){
            fileService.onlinePreview4urlFile(url,os);
        } catch (Exception e) {
            log.error("Preview failed",e);
        }
    }

    @GetMapping("/onlinePreview/path")
    public void onlinePreview4LocalFile(@RequestParam("path") String path, HttpServletResponse response) throws Exception{
        try (ServletOutputStream os = response.getOutputStream()){
            fileService.onlinePreview4LocalFile(path,os);
        } catch (Exception e) {
            log.error("Preview failed",e);
        }
    }
    
    
    
}

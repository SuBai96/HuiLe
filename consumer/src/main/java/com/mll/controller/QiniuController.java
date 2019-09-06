package com.mll.controller;

import com.mll.util.Qiniu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class QiniuController {

    @RequestMapping("/upload")
    public String upImg(MultipartFile files) throws IOException {
        System.out.println("up:"+files);
        byte[] imgBytes = files.getBytes();
        String imgUrl = Qiniu.upLoadImage(imgBytes);
        System.out.println("imgUrl:"+imgUrl);
        return "111";
    }
}

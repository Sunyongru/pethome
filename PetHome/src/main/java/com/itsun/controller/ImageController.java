package com.itsun.controller;

import com.itsun.redis.RedisUtil;
import com.itsun.result.Result;
import com.itsun.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@RequestMapping("/image")
@Controller
public class ImageController {

    public final static String UPUSERIMG_PATH="static/img/userImg/";

    public final static String UPFOSTERIMG_PATH="static/img/fosterImg/";


    @Autowired
    RedisUtil redisUtil;


    private String getUserAccount(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        String str=null;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                str=cookie.getValue();
            }
        }
        LoginVo loginVo=(LoginVo) redisUtil.get(str);
        String userAccount=loginVo.getUserAccount();
        return userAccount;
    }

    @RequestMapping("/imageUpload")
    @ResponseBody
    public Result<String> fileUpload(@RequestParam("image") MultipartFile image,
                                     Model model, HttpServletRequest request) {
        String userAccount=getUserAccount(request);
        if (image.isEmpty()) {
            System.out.println("文件为空");
        }
        String realPath=new String("src/main/resources/"+UPUSERIMG_PATH+userAccount+"/");
        File dest=new File(realPath);
        if (!dest.isDirectory()){
            dest.mkdirs();
        }
        String oldName=image.getOriginalFilename();
        String newName=UUID.randomUUID().toString().replaceAll("-","")+oldName.substring(oldName.lastIndexOf("."),oldName.length());
        File newFile=new File(dest.getAbsolutePath()+File.separator+newName);
        try {
            image.transferTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        String imagePath= "/img/userImg/"+userAccount+"/"+newName;
        model.addAttribute("imagePath",imagePath);
        return Result.success(imagePath);
    }

    @RequestMapping("/fosterImg")
    @ResponseBody
    public Result<String> fosterImgUpload(@RequestParam("image") MultipartFile image,
                                     Model model, HttpServletRequest request) {
        String userAccount=getUserAccount(request);
        if (image.isEmpty()) {
            System.out.println("文件为空");
        }
        String realPath=new String("src/main/resources/"+UPFOSTERIMG_PATH+userAccount+"/");
        File dest=new File(realPath);
        if (!dest.isDirectory()){
            dest.mkdirs();
        }
        String oldName=image.getOriginalFilename();
        String newName=UUID.randomUUID().toString().replaceAll("-","")+oldName.substring(oldName.lastIndexOf("."),oldName.length());
        File newFile=new File(dest.getAbsolutePath()+File.separator+newName);
        try {
            image.transferTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        String imagePath= "/img/fosterImg/"+userAccount+"/"+newName;
        model.addAttribute("imagePath",imagePath);
        return Result.success(imagePath);
    }


}

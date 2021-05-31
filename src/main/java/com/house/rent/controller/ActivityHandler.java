package com.house.rent.controller;

import com.house.rent.entity.Activity;
import com.house.rent.entity.Admin;
import com.house.rent.service.ActivityService;
import com.house.rent.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/activity")
public class ActivityHandler {

    @Autowired
    private ActivityService activityService;

    /*活动管理*/
    //查看所有活动公告
    @GetMapping("/Allactivity/{page}/{size}")
    public Page<Activity> findAllActivity(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return activityService.allActivity(pageable);//findAll方法中有按页查询方法
    }

    //查找活动公告（根据编号或活动名关键词）
    @GetMapping("/info/{id}")
    public Activity activityinfo(@PathVariable("id") Integer id){
        return activityService.info(id);
    }

    //添加活动公告
    @PostMapping("/saveactivity")
    public String save(@RequestBody Activity  activity){
        return activityService.add(activity);
    }

    //修改活动公告
    @PutMapping ("/updateactivity")
    public String update(@RequestBody Activity  activity){
        return activityService.update(activity);
    }

    //删除活动公告
    @GetMapping("/deleteactivityById/{id}")
    public String deleteactivityById(@PathVariable("id") Integer id){
        return activityService.delete(id);
    }


    //根据多条件查询
    @PostMapping("/findsearch")
    public Page<Activity> findSearch( @RequestBody Map searchMap){
        return activityService.findSearch(searchMap);
    }

    //上传房源图片
    @PostMapping("/upload")
    public Map<String,Object> fileupload(MultipartFile file, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        Map<String, Object> result = new HashMap<>();
        String realPath = "D:/bs program1/actpic/";//文件保存地址
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            file.transferTo(new File(folder, newName));
            String url = newName;//realPath +
            result.put("status", "OK");
            result.put("name", oldName);
            result.put("url", url);
        } catch (IOException e) {
            result.put("status", "ERROR");
            result.put("msg", e.getMessage());
        }
        return result;
    }
}

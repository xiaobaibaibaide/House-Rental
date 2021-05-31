package com.house.rent.controller;

import com.house.rent.VO.*;
import com.house.rent.entity.Admin;
import com.house.rent.entity.House;
import com.house.rent.entity.Info;
import com.house.rent.entity.Member;
import com.house.rent.repository.MemberRepository;
import com.house.rent.service.AdminService;
import com.house.rent.service.HouseService;
import com.house.rent.service.MemberService;
import com.house.rent.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private SystemService systemService;


    //根据账号密码登录系统 /已实现，但未结合Session
    @PostMapping("/login")
    public Object adminlogin(@RequestBody Admin admin){
        return adminService.login(admin.getAdmin_id(),admin.getPassword());
    }

    //查出所有房源、会员、发布房源等的信息统计数量给系统工作台显示可视化界面 实现
    @GetMapping("/userVO")
    public List<UserPieVO> userPieVO(){
        return systemService.userVOList();
    }
    @GetMapping("/houseVO")
    public List<HousePieVO> housePieVO(){
        return systemService.houseVOList();
    }
    @GetMapping("/areaVO")
    public List<AreaPieVO> areaPieVO(){
        return systemService.areaVOList();
    }
    @GetMapping("/orderVO")
    public OrderLineVO orderLineVO(){
        return systemService.orderLineVOList();
    }
    @GetMapping("/publishVO")
    public PublishLineVO publishLineVO(){
        return systemService.publishLineVOList();
    }


    /*管理员管理或权限管理*/
    //个人中心
    @GetMapping("/info/{id}")
    public Admin admininfo(@PathVariable("id") Integer id){
        return adminService.info(id);
    }

    //查看所有管理员（超级管理员权限）
    @GetMapping("/Alladmin/{page}/{size}")
    public Page<Admin> findAlladmin(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return adminService.Alladmin(pageable);//findAll方法中有按页查询方法
    }

    //添加普通管理员（超级管理员权限）
    @PostMapping("/saveadmin")
    public String save(@RequestBody Admin admin){
        return adminService.add(admin);
    }

    //修改管理员信息（超级管理员权限）
    @PutMapping ("/updateadmin")
    public String update(@RequestBody Admin admin){
        return adminService.update(admin);
    }



    //根据ID查找管理员
    @GetMapping("/findadminById/{id}")
    public Admin findadminById(@PathVariable("id") Integer id){
        return adminService.info(id);
    }

    //根据ID删除管理员
    @GetMapping("/deleteadminById/{id}")
    public String deleteadminById(@PathVariable("id") Integer id){
        return adminService.delete(id);
    }

    //根据多条件查询
    @PostMapping("/findsearch")
    public Page<Admin> findSearch( @RequestBody Map searchMap){
        return adminService.findSearch(searchMap);
    }

}

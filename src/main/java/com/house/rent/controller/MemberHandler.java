package com.house.rent.controller;


import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import com.house.rent.repository.MemberRepository;
import com.house.rent.service.HouseService;
import com.house.rent.service.MemberService;
import com.house.rent.service.TokenService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/member")
public class MemberHandler {

    @Autowired
    private MemberService memberService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HouseService houseService;

    //根据账号密码登录系统 /未实现Session
    @PostMapping("/login")
    public String memlogin(@RequestBody Member member){
       /* JSONObject jsonObject = new JSONObject();, HttpServletResponse response
        Member member1 = new Member();
        if(memberService.find(member.getMem_id())!=null){
            String token = tokenService.getToken(member1);
            jsonObject.put("token", token);
            Cookie cookie = new Cookie("token", token);
        }
*/
        //分成ID登录和手机号登录
        return memberService.login(member);
    }


    //根据用户ID查询  /未使用
    @GetMapping("/findById/{id}")
    public Member findById(@PathVariable("id") Integer id){
        return memberService.find(id);
    }
    //根据ID查找会员
    @GetMapping("/findmemById/{id}")
    public Member findmemById(@PathVariable("id") Integer id){
        return memberService.find(id);
    }

    //会员注册 已实现
    @PostMapping("/register")
    public String register(@RequestBody Member member , BindingResult bindingResult){
        for (ObjectError error : bindingResult.getAllErrors()) {
            return error.getDefaultMessage();
        }
       return memberService.register(member);
    }

    //会员实名认证
    @PostMapping("/recognize")
    public String recognize(@RequestBody Member member ){
        Member result = memberService.find(member.getMem_id());
        result.setRealname(member.getRealname());
        result.setIdcard(member.getIdcard());
        result.setStatus(2);
        return memberService.update(result);

    }

    /*用户管理模块*/
    //查看所有用户的信息  /已实现分页
    @GetMapping("/Allmember/{page}/{size}")
    public Page<Member> findAllmem(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return memberService.Allmem(pageable);//findAll方法中有按页查询方法
    }

    //审核会员实名认证信息


    //上传头像图片
    @PostMapping("/upload")
    public Map<String,Object> fileupload(MultipartFile file, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        Map<String, Object> result = new HashMap<>();
        String realPath = "D:/bs program1/avator/";//文件保存地址
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

    //添加会员
    @PostMapping("/savemem")
    public String save(@RequestBody Member member){
        return memberService.add(member);
    }

    //删除会员信息
    @GetMapping("/deletemem/{id}")
    public String delete(@PathVariable("id") Integer id){
        return memberService.delete(id);
    }

    //修改会员信息
    @PutMapping ("/updatemem")
    public String update(@RequestBody Member member){
        return memberService.update(member);
    }

    //根据会员状态查找
    @GetMapping("/findmemberByStatus/{status}")
    public List<Member> findmemberByStatus(@PathVariable("status") Integer status){
        return memberService.findByStatus(status);
    }


    //根据多条件查询
    @PostMapping("/findsearch")
    public List<Member> findSearch( @RequestBody Map searchMap){
        return memberService.findSearch(searchMap);
    }





}

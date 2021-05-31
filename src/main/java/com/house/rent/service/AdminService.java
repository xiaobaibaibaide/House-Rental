package com.house.rent.service;

import com.house.rent.entity.Admin;
import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminService {
    //声明在管理员ServiceImpl类中实现的方法
    Admin info(int id);
    Page<Admin> Alladmin(Pageable pageable);
    String login(int id,String password);//管理员登录
    String add(Admin admin);//添加普通管理员
    String update(Admin admin);//修改管理员信息
    Admin find(int id);
    String delete(int id);//删除管理员
    Page<Admin> findSearch(Map searchMap);
}

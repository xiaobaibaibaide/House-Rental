package com.house.rent.controller;

import com.house.rent.entity.Fitment;
import com.house.rent.entity.House;
import com.house.rent.entity.Order;
import com.house.rent.entity.Reserve;
import com.house.rent.service.MemberService;
import com.house.rent.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reserve")
public class ReserveHandler {
    @Autowired
    private ReserveService reserveService;

    //根据ID查找房源
    @GetMapping("/findreserveById/{id}")
    public Reserve findReserveById(@PathVariable("id") Integer id){
        return reserveService.info(id);
    }

    //根据会员ID查找房源
    @GetMapping("/mem/{id}")
    public List<Reserve> findReserveByMemId(@PathVariable("id") Integer id){
        return reserveService.memReserve(id);
    }

    //根据会员ID查找房源//分页

    //根据房东ID查找房源
    @GetMapping("/lord/{id}")
    public List<Reserve> findReserveByLordId(@PathVariable("id") Integer id){
        return reserveService.lordReserve(id);
    }



    //租客预约看房申请 添加
    @PostMapping("/save")
    public String save(@RequestBody Reserve reserve){
        System.out.println(reserve);
       return reserveService.add(reserve);
    }

    //租客取消预约看房请求
    @GetMapping("/deletereserveById/{id}")
    public String deleteReserveById(@PathVariable("id") Integer id){
        return reserveService.delete(id);
    }


    //房东拒绝租房请求
    @PutMapping("/rejectreserve")
    public String rejectReserveById(@RequestBody Reserve reserve){
        return reserveService.reject(reserve);
    }

    //根据多条件查询  分页
    @PostMapping("/findsearch")
    public Page<Reserve> findSearch(@RequestBody Map searchMap){
        return reserveService.findSearch(searchMap);
    }

}

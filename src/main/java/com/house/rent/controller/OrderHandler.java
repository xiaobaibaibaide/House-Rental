package com.house.rent.controller;

import com.house.rent.entity.Activity;
import com.house.rent.entity.Member;
import com.house.rent.entity.Order;
import com.house.rent.entity.Result;
import com.house.rent.service.HouseService;
import com.house.rent.service.MemberService;
import com.house.rent.service.OrderService;
import com.house.rent.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderHandler {
    @Autowired
    private OrderService orderService;


    /*租赁交易管理*/
    //查看所有订单  按订单ID倒叙分页
    @GetMapping("/Allorder/{page}/{size}")
    public Page<Order> findAllOrder(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC, "orderId");
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return orderService.allOrder(pageable);//findAll方法中有按页查询方法
    }

    //按订单ID查找订单
    @GetMapping("/findorder/{id}")
    public Result orderinfo(@PathVariable("id") Long id){
        if(id==null){
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
        Order order = orderService.info(id);
        Result result = new Result(ResultCode.SUCCESS,order);
        return result;
    }

    //按条件查找订单  //订单状态 0、1、2、3



    //查询个人订单
    @GetMapping("/memorder/{id}")
    public List<Order> memOrder(@PathVariable("id") Integer id){
        return orderService.memOrder(id);
    }

    //(分页)//未完成
    @GetMapping("/order/{id}/{page}/{size}")
    public Page<Order> memOrder(@PathVariable("id") Integer id, @PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return orderService.memOrder(id,pageable);
    }

    //根据房东ID查询 租客提交的租房订单请求
    @GetMapping("/lord/{id}")
    public List<Order> lordOrder(@PathVariable("id") Integer id){
        return orderService.lordOrder(id);
    }


    //发起租房订单
    @PostMapping("/saveorder")
    public String save(@RequestBody Order order){
        return orderService.add(order);
    }

    //修改订单信息 （房东和租客
    @PostMapping ("/updateorder")
    public String update(@RequestBody Order order){
        return orderService.update(order);
    }

    //删除订单
    @GetMapping("/deleteorderById/{id}")
    public String deleteactivityById(@PathVariable("id") Integer id){
        return orderService.delete(id);
    }

    //根据多条件查询 分页
    @PostMapping("/findsearch")
    public Page<Order> findSearch(@RequestBody Map searchMap){
        return orderService.findSearch(searchMap);
    }



}

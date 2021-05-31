package com.house.rent.service;

import com.house.rent.entity.Activity;
import com.house.rent.entity.Member;
import com.house.rent.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Page<Order> allOrder(Pageable pageable);
    Page<Order> memOrder(int id,Pageable pageable);
    List<Order> memOrder(int id);
    List<Order> lordOrder(int id);
    Order info(long id);//查找订单 //按订单ID
    String add(Order order);//添加订单
    String update(Order order);//修改订单信息
    String delete(long id);//删除订单
    Page<Order> findSearch(Map searchMap);
}

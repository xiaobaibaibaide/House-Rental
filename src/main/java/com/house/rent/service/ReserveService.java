package com.house.rent.service;

import com.house.rent.entity.Order;
import com.house.rent.entity.Reserve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ReserveService {
    Page<Reserve> allReserve(Pageable pageable);
    Page<Reserve> memReserve(int id,Pageable pageable);
    List<Reserve> memReserve(int id);
    List<Reserve> lordReserve(int id);
    Reserve info(int id);//查找预约 //按订单ID
    String add(Reserve reserve);//添加预约
    String delete(int id);//删除预约
    String reject(Reserve reserve);//拒绝预约
    Page<Reserve> findSearch(Map searchMap);
}

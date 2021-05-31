package com.house.rent.service;

import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HouseService {
    //声明在房源服务实现类中的方法
    Page<House> Allhouse(Pageable pageable);
    String add(House house);
    House find(long id);
    String update(House house);
    //List<House> findByArea_id(int area_id);
    List<House> findByLord(int id);
    List<House> findByRentkind(int kind);
    List<House> findByRentkindAndStatus(int kind);

    List<House> findByCtime(Date date);
    List<House> findByStatus(int status);
    Page<House> findByStatus(int status,Pageable pageable);
    Page<House> findSearch(Map searchMap);
    String delete(long id);

}

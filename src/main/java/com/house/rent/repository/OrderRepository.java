package com.house.rent.repository;

import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import com.house.rent.entity.Order;
import com.house.rent.entity.Reserve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    public List<Order> findByMemId(Integer id);
    public List<Order> findByLordId(Integer id);
    public List<Order> findAllByCtimeBetweenAndStatus(Date date, Date stop,Integer status);
    public List<Order> findAllByCtimeBetween(Date date, Date stop);
}

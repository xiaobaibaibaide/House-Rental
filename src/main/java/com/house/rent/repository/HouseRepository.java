package com.house.rent.repository;

import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface HouseRepository extends JpaRepository<House,Long>, JpaSpecificationExecutor<House> {
    //声明JPA不常用不普遍的高级SQL操作
    public List<House> findByLordId(int id);

    public List<House> findByStatus(int status);
    public Page<House> findByStatus(int status,Pageable pageable);

    public List<House> findByCtime(Date date);
    public List<House> findAllByCtimeBetween(Date date,Date stop);
    public List<House> findByAreaidAndStatusNot(int areaid,int status);
    public List<House> findByRentkind(int kind);
    public List<House> findByRentkindAndStatus(int kind,int status);
    //public List<House> findByLord_id(int Lord_id);


}

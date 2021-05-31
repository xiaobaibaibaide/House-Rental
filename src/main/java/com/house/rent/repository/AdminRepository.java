package com.house.rent.repository;
import com.house.rent.entity.Admin;
import com.house.rent.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRepository extends JpaRepository<Admin,Integer>, JpaSpecificationExecutor<Admin> {
    //如需额外添加方法，（按非ID查询、联合查询等），则在此处创建
    //无则默认用JPA中提供的默认DAO方法
}

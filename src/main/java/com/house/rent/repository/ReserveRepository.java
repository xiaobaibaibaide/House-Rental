package com.house.rent.repository;

import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import com.house.rent.entity.Order;
import com.house.rent.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve,Integer>, JpaSpecificationExecutor<Reserve> {
    //public List<Reserve> findByLord_id(int Lord_id);
    public List<Reserve> findByMemId(int id);
    public List<Reserve> findByLordId(int id);
}

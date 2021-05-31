package com.house.rent.repository;

import com.house.rent.entity.Lord;
import com.house.rent.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LordRepository extends JpaRepository<Lord,Integer>, JpaSpecificationExecutor<Lord> {
    public Lord findByMemId(int id);
    public Lord findById(int id);
}

package com.house.rent.repository;

import com.house.rent.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegRepository extends JpaRepository<Member,String> {
    public List<Member> findByPhone(String phone);
}

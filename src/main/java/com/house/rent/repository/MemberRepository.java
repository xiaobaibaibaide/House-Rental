package com.house.rent.repository;

import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer>, JpaSpecificationExecutor<Member> {
    public Member findByPhone(String phone);
    public boolean existsByPhone(String phone);
    public List<Member> findByStatus(Integer status);

}

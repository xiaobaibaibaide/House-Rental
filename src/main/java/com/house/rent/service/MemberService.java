package com.house.rent.service;

import com.house.rent.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MemberService {
    //声明在网站会员实现类中的方法
    String login(Member member);
    String register(Member member);
    Page<Member> Allmem(Pageable pageable);
    String add(Member member);
    Member find(int id);
    String update(Member member);
    String delete(int id);
    List<Member> findByStatus(int status);
    List<Member> findSearch(Map searchMap);

}

package com.house.rent.serviceImpl;

import com.house.rent.entity.House;
import com.house.rent.entity.Member;
import com.house.rent.repository.HouseRepository;
import com.house.rent.repository.MemberRepository;
import com.house.rent.repository.RegRepository;
import com.house.rent.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.print.Book;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RegRepository regRepository;
    @Autowired
    private HouseRepository houseRepository;

    //会员登录
    @Override
    public String login(Member member) {
        String phone = member.getPhone();
        String password = member.getMem_password();
        if(member.getMem_id()==null){//ID为空手机号不为空
            if (memberRepository.existsByPhone(phone)){//查找手机号是否注册存在
                Member mem = memberRepository.findByPhone(phone);
                if(mem.getMem_password().equals(password)){
                    return mem.getMem_id()+"";
                }
                else {
                    return "error";
                }
            }
            else {
                return "null";
            }
        }
        else {//按输入的ID查找
            int id = member.getMem_id();
            if(memberRepository.existsById(id)){
                Member mem = memberRepository.findById(id).get();
                if(mem.getMem_password().equals(password)){
                    return mem.getNickname();
                }
                else {
                    return "error";
                }
            }
            else{
                return "null";
            }
        }
    }

    //会员注册方法，，然后才能添加
    @Override
    public String register(Member member) {
        String str = member.getPhone();
        List<Member> l = regRepository.findByPhone(str);
        //在表中查找手机号
        if(l.size() >= 1){//应加上手机号唯一的验证
            return "exist";
        }
        else{
            Member result = memberRepository.save(member);
            System.out.println(result);
            if(result!=null){
                return "success";
            }else{
                return "error";
            }
        }
    }

    //管理员查看所有会员信息
    public Page<Member> Allmem(Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    //管理员添加会员
    @Override
    public String add(Member member) {
        Member result = memberRepository.save(member);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //根据ID查找会员
    @Override
    public Member find(int id) {
        return memberRepository.findById(id).get();
    }

    //修改会员信息
    @Override
    public String update(Member member) {
        Member result = memberRepository.save(member);
        System.out.println(result);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //删除会员信息
    @Override
    public String delete(int id) {
        memberRepository.deleteById(id);
        return "success";
    }

    //按状态查找会员
    @Override
    public List<Member> findByStatus(int status) {
        return memberRepository.findByStatus(status);
    }


    //多条件查询
    @Override
    public List<Member> findSearch(Map searchMap) {
        System.out.println(searchMap);
        Specification<Member> specification = createSpecification(searchMap);
        return memberRepository.findAll(specification);
    }


    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Member> createSpecification(Map searchMap) {

        return new Specification<Member>() {

            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 会员ID
                if (searchMap.get("mem_id")!=null && !"".equals(searchMap.get("mem_id"))) {
                    predicateList.add(cb.equal(root.get("mem_id").as(String.class), (String)searchMap.get("mem_id")));
                }
                // 会员真实姓名
                if (searchMap.get("realname")!=null && !"".equals(searchMap.get("realname"))) {
                    predicateList.add(cb.equal(root.get("realname").as(String.class), (String)searchMap.get("realname")));
                }
                // 手机号
                if (searchMap.get("phone")!=null && !"".equals(searchMap.get("phone"))) {
                    predicateList.add(cb.equal(root.get("phone").as(String.class), (String)searchMap.get("phone")));
                }
                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}

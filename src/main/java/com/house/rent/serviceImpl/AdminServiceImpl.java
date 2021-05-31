package com.house.rent.serviceImpl;

import com.house.rent.entity.Admin;
import com.house.rent.entity.Collection;
import com.house.rent.entity.Member;
import com.house.rent.repository.AdminRepository;
import com.house.rent.repository.HouseRepository;
import com.house.rent.repository.MemberRepository;
import com.house.rent.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    //管理员业务管理管理员自身，不要干扰其它功能
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HouseRepository houseRepository;



    //管理员登录系统
    @Override
    public String login(int id,String password) {
        if(adminRepository.existsById(id)){//判断账号是否存在
            Admin admin = adminRepository.findById(id).get();
            if(admin.getPassword().equals(password)){
                return admin.getAdmin_id().toString();
            }
            else {
                return "iserror";
            }
        }
        else{
            return "isnull";
        }
    }

    //管理员查看个人信息
    @Override
    public Admin info(int id) {
        return adminRepository.findById(id).get();
    }

    @Override
    public Admin find(int id) {
        return adminRepository.findById(id).get();
    }

    //超级管理员查看所有管理员信息
    @Override
    public Page<Admin> Alladmin(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    //超级管理员添加管理员
    @Override
    public String add(Admin admin) {//
        Admin result = adminRepository.save(admin);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @Override
    public String update(Admin admin) {//修改管理员信息
        Admin result = adminRepository.save(admin);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @Override
    public String delete(int id) {//删除管理员
        adminRepository.deleteById(id);
        return "success";
    }

    @Override
    public Page<Admin> findSearch(Map searchMap) {
        System.out.println(searchMap);
        int page = Integer.parseInt(searchMap.get("page").toString());
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,10,sort);
        Specification<Admin> specification = createSpecification(searchMap);
        return adminRepository.findAll(specification,pageable);
    }
    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Admin> createSpecification(Map searchMap) {
        return new Specification<Admin>() {

            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //房源ID
                if (searchMap.get("admin_id")!=null && !"".equals(searchMap.get("admin_id"))) {
                    predicateList.add(cb.equal(root.get("admin_id").as(String.class), (String)searchMap.get("admin_id")));
                }
                //租客
                if (searchMap.get("authority")!=null && !"".equals(searchMap.get("authority"))) {
                    predicateList.add(cb.equal(root.get("authority").as(Integer.class), (Integer)searchMap.get("authority")));
                }
                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }



}

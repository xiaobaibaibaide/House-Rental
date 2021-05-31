package com.house.rent.serviceImpl;

import com.house.rent.entity.*;
import com.house.rent.repository.*;
import com.house.rent.service.LordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LordServiceImpl implements LordService {

    @Autowired
    private LordRepository lordRepository;

    //管理员查看所有的房东
    @Override
    public Page<Lord> Alllord(Pageable pageable) {
        return lordRepository.findAll(pageable);
    }

    //添加新房东
    @Override
    public String add(Lord lord) {
        Lord result = lordRepository.save(lord);
        System.out.println(result);
        if(result!=null){
            return result.getLord_id()+"";
        }else{
            return "error";
        }
    }

    //按房东ID查找房东
    @Override
    public Lord find(int id) {
        return lordRepository.findById(id);
    }

    //按会员ID查找  判断身份
    @Override
    public Lord findByMem(int id) {
        return  lordRepository.findByMemId(id);

    }

    //修改房东信息
    @Override
    public String update(Lord lord) {
        Lord result = lordRepository.save(lord);
        System.out.println(result);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @Override
    public String delete(int id) {
        return null;
    }

    //多条件查询
    @Override
    public List<Lord> findSearch(Map searchMap) {
        Specification<Lord> specification = createSpecification(searchMap);
        return lordRepository.findAll(specification);
    }

    private Specification<Lord> createSpecification(Map searchMap) {

        return new Specification<Lord>() {

            @Override
            public Predicate toPredicate(Root<Lord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("lord_id")!=null && !"".equals(searchMap.get("lord_id"))) {
                    predicateList.add(cb.equal(root.get("lord_id").as(String.class), (String)searchMap.get("lord_id")));
                }
                // 姓名
                if (searchMap.get("lord_name")!=null && !"".equals(searchMap.get("lord_name"))) {
                    predicateList.add(cb.equal(root.get("lord_name").as(String.class), (String)searchMap.get("lord_name")));
                }
                // 手机号
                if (searchMap.get("phone")!=null && !"".equals(searchMap.get("phone"))) {
                    predicateList.add(cb.equal(root.get("phone").as(String.class), (String)searchMap.get("phone")));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }



    /*//发布新房源
    @Override
    public String addHouse(House house) {
        House result = houseRepository.save(house);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //查看自己对应的房源，按房东的id
    @Override
    public List<House> findHouse(int id) {
        List<House> houses = houseRepository.findByLord_id(id);
        return houses;
    }*/

    //修改发布的房源情况
     /*@Override
    public String updateHouse(House house) {
        House result = houseRepository.save(house);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //查看租客的预约看房申请,按预约的房东ID查找，状态为待同意
   @Override
    public List<Reserve> findReserve(int id) {
        List<Reserve> reserves = reserveRepository.findByLord_id(id);
        return reserves;
    }

    //处理租客的预约看房申请
    @Override
    public String updateReserve(Reserve res) {
        Reserve result =  reserveRepository.save(res);
        if(result!=null){
            return "agree";
        }else{
            return "refuse";
        }
    }*/

    //查看租客的租房申请,按订单中目标房源的Id和房东的ID
    /*@Override
    public List<Order> findOrder(int id) {
        List<Order> orders = orderRepository.findByLord_id(id);
        return orders;
    }

    //处理租客的租房申请
    @Override
    public String updateOrder(Order order) {
        Order result =  orderRepository.save(order);
        if(result!=null){
            return "agree";
        }else{
            return "refuse";
        }
    }*/



    //
}

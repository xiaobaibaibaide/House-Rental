package com.house.rent.serviceImpl;

import com.house.rent.entity.*;
import com.house.rent.repository.*;
import com.house.rent.service.OrderService;
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
import javax.xml.crypto.Data;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LordRepository lordRepository;
    @Autowired
    private ContractRepository contractRepository;


    //分页查看所有的订单信息
    @Override
    public Page<Order> allOrder(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }


    //按订单ID查找订单
    @Override
    public Order info(long id) {
        return orderRepository.findById(id).get();
    }

    //按会员ID查找订单****  findBymemId
    @Override
    public List<Order> memOrder(int id) {
        return orderRepository.findByMemId(id);
    }

    //按房东ID查找订单****  findBylordId
    @Override
    public List<Order> lordOrder(int id) {
        return orderRepository.findByLordId(id);
    }

    //待完成(分页)
    @Override
    public Page<Order> memOrder(int id,Pageable pageable) {
        return null;
    }

    //添加订单   //租客发起订单  //若为合租订单要将
    @Override
    public String add(Order order) {
        Order result = orderRepository.save(order);
        if(result!=null){
            return ""+result.getOrderId();
        }else{
            return "error";
        }
    }

    //修改订单信息    //修改订单信息（含状态）
    @Override
    public String update(Order order) {
        Order result = orderRepository.save(order);
        if(result!=null){
            if(result.getStatus()==3){//修改订单为同意租房  生成租房合同
                House house = houseRepository.findById(result.getHouse_id()).get();
                //如果是整租房源
                if(house.getRentkind()==1){
                    house.setStatus(2);
                    houseRepository.save(house);
                }
                else{
                    System.out.println("111111");
                    house.setLeftrooms(house.getLeftrooms()-order.getRooms());
                    if (house.getLeftrooms()==0){//合租房源房间全租完将房源的状态改为 2 已租
                        house.setStatus(2);
                    }
                    houseRepository.save(house);
                }
                Contract contract = new Contract();
                Member member=memberRepository.findById(order.getMemId()).get();
                Lord lord = lordRepository.findById(order.getLordId()).get();
                contract.setMem_id(member.getMem_id());
                contract.setMem_realname(member.getRealname());
                contract.setLord_id(house.getLordId());
                contract.setLord_name(lord.getLord_name());
                contract.setHouse_id(house.getHouse_id());
                contract.setOrder_id(result.getOrderId());
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ParsePosition pos = new ParsePosition(0);
                String ctime=f.format(new Date());
                contract.setCtime(ctime);
                contract.setBegtime(order.getBegin());
                Date date = f.parse(order.getBegin(),pos);//订单开始时间
                Calendar cal=Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MONTH,order.getRent_month());
                System.out.println(cal);
                Date date1 = cal.getTime();
                String extime = f.format(date1);
                System.out.println(date1);
                contract.setExtime(extime);
                Contract res = contractRepository.save(contract);
                result.setContract_id(res.getContract_id());
                Order result1 = orderRepository.save(result);
            }
            return "success";
        }else{
            return "error";
        }
    }

    @Override
    public String delete(long id) {
        orderRepository.deleteById(id);
        return "success";
    }

    //多条件查询
    @Override
    public Page<Order> findSearch(Map searchMap) {
        System.out.println(searchMap);
        int page = Integer.parseInt(searchMap.get("page").toString());
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,10,sort);
        Specification<Order> specification = createSpecification(searchMap);
        return orderRepository.findAll(specification,pageable);
    }

    private Specification<Order> createSpecification(Map searchMap) {

        return new Specification<Order>() {

            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //房源ID
                if (searchMap.get("house_id")!=null && !"".equals(searchMap.get("house_id"))) {
                    predicateList.add(cb.equal(root.get("house_id").as(String.class), (String)searchMap.get("house_id")));
                }
                //订单ID
                if (searchMap.get("orderId")!=null && !"".equals(searchMap.get("orderId"))) {
                    predicateList.add(cb.equal(root.get("orderId").as(String.class), (String)searchMap.get("orderId")));
                }
                //订单状态
                if (searchMap.get("status")!=null && !"".equals(searchMap.get("status"))) {
                    predicateList.add(cb.equal(root.get("status").as(Integer.class), (Integer)searchMap.get("status")));
                }
                //会员ID
                if (searchMap.get("memId")!=null && !"".equals(searchMap.get("memId"))) {
                    predicateList.add(cb.equal(root.get("memId").as(String.class), (String)searchMap.get("memId")));
                }
                //房东ID
                if (searchMap.get("lordId")!=null && !"".equals(searchMap.get("lordId"))) {
                    predicateList.add(cb.equal(root.get("lordId").as(String.class), (String)searchMap.get("lordId")));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    //查询近期的订单成交情况（周/月）//多条件

}

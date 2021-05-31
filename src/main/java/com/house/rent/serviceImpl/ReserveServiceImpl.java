package com.house.rent.serviceImpl;

import com.house.rent.entity.House;
import com.house.rent.entity.Reserve;
import com.house.rent.repository.HouseRepository;
import com.house.rent.repository.ReserveRepository;
import com.house.rent.service.ReserveService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;
    @Override
    public Page<Reserve> allReserve(Pageable pageable) {
        return null;
    }

    //根据会员ID查找房源  //分页
    @Override
    public Page<Reserve> memReserve(int id, Pageable pageable) {
        return null;
    }

    //根据 会员ID 查找房源
    @Override
    public List<Reserve> memReserve(int id) {
        return reserveRepository.findByMemId(id);
    }

    //根据 房东ID 查找房源
    @Override
    public List<Reserve> lordReserve(int id) {
        return reserveRepository.findByLordId(id);
    }

    //根据预约ID查看预约信息
    @Override
    public Reserve info(int id) {
        return reserveRepository.findById(id).get();
    }

    //添加预约
    @Override
    public String add(Reserve reserve) {

        Reserve result = reserveRepository.save(reserve);
        System.out.println(result);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //删除预约
    @Override
    public String delete(int id) {
        reserveRepository.deleteById(id);
        return "success";
    }

    //拒绝预约请求
    @Override
    public String reject(Reserve reserve) {
        Reserve result = reserveRepository.save(reserve);
        System.out.println(result);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //多条件查询
    @Override
    public Page<Reserve> findSearch(Map searchMap) {
        System.out.println(searchMap);
        int page = Integer.parseInt(searchMap.get("page").toString());
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,10,sort);
        Specification<Reserve> specification = createSpecification(searchMap);
        return reserveRepository.findAll(specification,pageable);

    }
    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Reserve> createSpecification(Map searchMap) {

        return new Specification<Reserve>() {

            @Override
            public Predicate toPredicate(Root<Reserve> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //房源ID
                if (searchMap.get("houseId")!=null && !"".equals(searchMap.get("houseId"))) {
                    predicateList.add(cb.equal(root.get("houseId").as(String.class), (String)searchMap.get("houseId")));
                }
                //房东
                if (searchMap.get("lordId")!=null && !"".equals(searchMap.get("lordId"))) {
                    predicateList.add(cb.equal(root.get("lordId").as(String.class), (String)searchMap.get("lordId")));
                }
                //租客
                if (searchMap.get("memId")!=null && !"".equals(searchMap.get("memId"))) {
                    predicateList.add(cb.equal(root.get("memId").as(String.class), (String)searchMap.get("memId")));
                }
                //状态
                if (searchMap.get("status")!=null && !"".equals(searchMap.get("status"))) {
                    predicateList.add(cb.equal(root.get("status").as(Integer.class), (Integer)searchMap.get("status")));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}

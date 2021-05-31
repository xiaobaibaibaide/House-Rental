package com.house.rent.serviceImpl;

import com.house.rent.entity.House;
import com.house.rent.entity.Lord;
import com.house.rent.entity.Member;
import com.house.rent.repository.AreaRepository;
import com.house.rent.repository.HouseRepository;
import com.house.rent.repository.LordRepository;
import com.house.rent.repository.MemberRepository;
import com.house.rent.service.HouseService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private LordRepository lordRepository;
    @Autowired
    private MemberRepository memberRepository;

    //查看所有房屋信息
    public Page<House> Allhouse(Pageable pageable){
        return houseRepository.findAll(pageable);
    }

    //发布新房源
    @Override
    public String add(House house) {
        House result = houseRepository.save(house);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //根据房源ID查找房源
    @Override
    public House find(long id) {
        return houseRepository.findById(id).get();
    }

    //根据房东ID查找房源
    @Override
    public List<House> findByLord(int id) {
        return houseRepository.findByLordId(id);
    }

    //修改房源信息
    @Override
    public String update(House house) {
        System.out.println(house);
        House result1 = houseRepository.save(house);
        if(result1!=null){
            return "success";
        }else{
            return "error";
        }
    }


    //删除房源
    @Override
    public String delete(long id) {
        if(houseRepository.existsById(id)) {//要判断是否存在
            houseRepository.deleteById(id);
            return "success";
        }
        else
            return "error";
    }

    //根据地区查找房源 area
    /*@Override
    public List<House> findByArea_id(int area_id) {
        return houseRepository.findByArea_id(area_id);
    }*/

    //根据租赁方式查找房源 rentkind
    @Override
    public List<House> findByRentkind(int kind) {
        return houseRepository.findByRentkind(kind);
    }

   //根据房源状态查找房源 status
   @Override
   public Page<House> findByStatus(int status,Pageable pageable) {
       return houseRepository.findByStatus(status,pageable);
   }
    @Override
    public List<House> findByStatus(int status) {
        return houseRepository.findByStatus(status);
    }

    //根据房源状态和租赁方式查找房源 status rentkind 分页
    public List<House> findByRentkindAndStatus(int kind){
        return houseRepository.findByRentkindAndStatus(kind,1);
    }

    //根据创建时间查找房源 ctime
    @Override
    public List<House> findByCtime(Date date) {
        return houseRepository.findByCtime(date);
    }

    //根据房间数量查找房源 rooms


    //根据多条件查找
    @Override
    public Page<House> findSearch(Map searchMap){
        System.out.println(searchMap);
        int page = Integer.parseInt(searchMap.get("page").toString());
        int sort1 = Integer.parseInt(searchMap.get("sort").toString());
        if(sort1==0){
            Sort sort = Sort.by(Sort.Direction.ASC, "ctime");
            Pageable pageable = PageRequest.of(page-1,10,sort);
            Specification<House> specification = createSpecification(searchMap);
            return houseRepository.findAll(specification,pageable);
        }
        if(sort1==1){
            Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
            Pageable pageable = PageRequest.of(page-1,10,sort);
            Specification<House> specification = createSpecification(searchMap);
            return houseRepository.findAll(specification,pageable);
        }
        if(sort1==2){
            Sort sort = Sort.by(Sort.Direction.DESC, "price");
            Pageable pageable = PageRequest.of(page-1,10,sort);
            Specification<House> specification = createSpecification(searchMap);
            return houseRepository.findAll(specification,pageable);
        }
        if(sort1==3){
            Sort sort = Sort.by(Sort.Direction.DESC, "acreage");
            Pageable pageable = PageRequest.of(page-1,10,sort);
            Specification<House> specification = createSpecification(searchMap);
            return houseRepository.findAll(specification,pageable);
        }
        return null;
    }
    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<House> createSpecification(Map searchMap) {

        return new Specification<House>() {

            @Override
            public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //房源ID
                if (searchMap.get("house_id")!=null && !"".equals(searchMap.get("house_id"))) {
                    predicateList.add(cb.equal(root.get("house_id").as(String.class), (String)searchMap.get("house_id")));
                }
                // 地区
                if (searchMap.get("region")!=null && !"".equals(searchMap.get("region"))) {
                    predicateList.add(cb.equal(root.get("areaid").as(Integer.class), (Integer)searchMap.get("region")));
                }
                // 输入框关键词
                if (searchMap.get("searchContent")!=null && !"".equals(searchMap.get("searchContent"))) {
                    predicateList.add(cb.like(root.get("remark").as(String.class), "%"+(String)searchMap.get("searchContent")+"%"));
                }
                // 出租类型
                if (searchMap.get("rentkind")!=null && !"".equals(searchMap.get("rentkind"))) {
                    predicateList.add(cb.equal(root.get("rentkind").as(Integer.class), (Integer)searchMap.get("rentkind")));
                }
                // 价格
                if (searchMap.get("price")!=null && !"".equals(searchMap.get("price"))) {
                    if (searchMap.get("price").equals(0)){
                        predicateList.add(cb.lessThanOrEqualTo(root.get("price").as(Double.class), 1500.00));
                    }
                    if (searchMap.get("price").equals(1)){
                        predicateList.add(cb.between(root.get("price").as(Double.class), 1500.00, 3000.00));
                    }
                    if (searchMap.get("price").equals(2)){
                        predicateList.add(cb.between(root.get("price").as(Double.class), 3000.00, 5000.00));
                    }
                    if (searchMap.get("price").equals(3)){
                        predicateList.add(cb.greaterThanOrEqualTo(root.get("price").as(Double.class), 5000.00));
                    }
                }
                //面积
                if (searchMap.get("acreage")!=null && !"".equals(searchMap.get("acreage"))) {
                    if (searchMap.get("acreage").equals(0)){
                        predicateList.add(cb.lessThanOrEqualTo(root.get("acreage").as(Integer.class), 50));
                    }
                    if (searchMap.get("acreage").equals(1)){
                        predicateList.add(cb.between(root.get("acreage").as(Integer.class), 50, 100));
                    }
                    if (searchMap.get("acreage").equals(2)){
                        predicateList.add(cb.between(root.get("acreage").as(Integer.class), 100, 150));
                    }
                    if (searchMap.get("acreage").equals(3)){
                        predicateList.add(cb.greaterThanOrEqualTo(root.get("acreage").as(Integer.class), 150));
                    }
                }
                //房型
                if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
                    predicateList.add(cb.equal(root.get("type").as(Integer.class), (Integer)searchMap.get("type")));
                }
                //发布者
                if (searchMap.get("publisher")!=null && !"".equals(searchMap.get("publisher"))) {
                    predicateList.add(cb.equal(root.get("publisher").as(String.class), (String)searchMap.get("publisher")));
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

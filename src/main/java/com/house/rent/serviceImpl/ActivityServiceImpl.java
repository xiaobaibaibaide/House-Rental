package com.house.rent.serviceImpl;

import com.house.rent.entity.Activity;
import com.house.rent.entity.Admin;
import com.house.rent.entity.Member;
import com.house.rent.repository.ActivityRepository;
import com.house.rent.service.ActivityService;

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
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    //查看所有活动信息
    @Override
    public Page<Activity> allActivity(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    //查看具体活动信息  参数为活动ID
    @Override
    public Activity info(int id) {
        return activityRepository.findById(id).get();
    }

    //添加活动 参数为活动实体
    @Override
    public String add(Activity activity) {
        Activity result = activityRepository.save(activity);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //修改活动  参数为活动实体
    @Override
    public String update(Activity activity) {
        Activity result = activityRepository.save(activity);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    //删除活动
    @Override
    public String delete(int id) {
        activityRepository.deleteById(id);
        return "success";
    }

    @Override
    public Page<Activity> findSearch(Map searchMap) {
        System.out.println(searchMap);
        int page = Integer.parseInt(searchMap.get("page").toString());
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,10,sort);
        Specification<Activity> specification = createSpecification(searchMap);
        return activityRepository.findAll(specification,pageable);
    }
    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Activity> createSpecification(Map searchMap) {

        return new Specification<Activity>() {

            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //活动ID
                if (searchMap.get("activity_id")!=null && !"".equals(searchMap.get("activity_id"))) {
                    predicateList.add(cb.equal(root.get("activity_id").as(String.class), (String)searchMap.get("activity_id")));
                }
                //活动标题
                if (searchMap.get("activity_name")!=null && !"".equals(searchMap.get("activity_name"))) {
                    predicateList.add(cb.like(root.get("activity_name").as(String.class), "%"+(String)searchMap.get("activity_name")+"%"));
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

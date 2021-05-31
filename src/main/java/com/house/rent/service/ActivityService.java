package com.house.rent.service;

import com.house.rent.entity.Activity;
import com.house.rent.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ActivityService {
    Page<Activity> allActivity(Pageable pageable);
    Activity info(int id);//查找活动公告
    String add(Activity activity);//添加活动
    String update(Activity activity);//修改活动信息
    String delete(int id);//删除活动

    Page<Activity> findSearch(Map searchMap);
}

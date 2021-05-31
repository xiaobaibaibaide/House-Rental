package com.house.rent.service;

import com.house.rent.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface LordService {
    //声明在房东服务实现类中的方法
    Page<Lord> Alllord(Pageable pageable);
    String add(Lord lord);//新增房东
    Lord find(int id);//根据房东ID查找房东
    Lord findByMem(int id);//根据会员ID查找房东
    String update(Lord lord);//更新房东信息
    String delete(int id);//删除房东
    List<Lord> findSearch(Map searchMap);
}

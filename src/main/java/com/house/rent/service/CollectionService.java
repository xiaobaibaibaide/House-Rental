package com.house.rent.service;

import com.house.rent.entity.Collection;
import com.house.rent.entity.Reserve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CollectionService {
    Collection find(int id);
    String exist(Collection collection);//是否已有
    String add(Collection collection);//添加
    String update(Collection collection);//修改
    String delete(long houseId,int memId);//删除
    List<Collection> findByMem(Pageable pageable, int id);
    List<Collection> findByHouse(long id);
    Page<Collection> findSearch(Map searchMap);
}

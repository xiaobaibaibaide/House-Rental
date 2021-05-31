package com.house.rent.controller;

import com.house.rent.entity.Admin;
import com.house.rent.entity.Collection;
import com.house.rent.entity.House;
import com.house.rent.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection")
public class CollectionHandler {
    @Autowired
    private CollectionService collectionService;

    //查出某房源对应的所有收藏  //条件查询
    @GetMapping("/housecollections/{houseId}")
    public List<Collection> findCollections(@PathVariable("houseId") Long houseId){
        return collectionService.findByHouse(houseId);//findAll方法中有按页查询方法
    }


    //查出某会员所有收藏  //条件查询
    @GetMapping("/memcollections/{page}/{size}/{memid}")
    public List<Collection> findCollections(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("memid") Integer memid){
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return collectionService.findByMem(pageable,memid);//findAll方法中有按页查询方法
    }

    //查看某房源会员是否收藏
    @PostMapping("/iscollected")
    public String isCollected(@RequestBody Collection collection){
        return collectionService.exist(collection);
    }



    //添加收藏
    @PostMapping("/save")
    public String saveCollection(@RequestBody Collection collection){
        return collectionService.add(collection);
    }


    //取消收藏
    @GetMapping("/deleteById/{houseId}/{memId}")
    public String deleteCollectionById(@PathVariable("houseId") Long houseId,@PathVariable("memId") Integer memId){
        System.out.println("1111111");
        return collectionService.delete(houseId,memId);
    }

    //按收藏编号查找收藏记录
    @GetMapping("/findById/{id}")
    public Collection findCollectionById(@PathVariable("id") Integer id){
        return collectionService.find(id);
    }

    //根据多条件查询
    @PostMapping("/findsearch")
    public Page<Collection> findSearch( @RequestBody Map searchMap){
        return collectionService.findSearch(searchMap);
    }

}

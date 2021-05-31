package com.house.rent.controller;


import com.house.rent.entity.House;
import com.house.rent.entity.Lord;
import com.house.rent.entity.Member;
import com.house.rent.service.HouseService;
import com.house.rent.service.LordService;
import com.house.rent.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lord")
public class LordHandler {
    @Autowired
    private LordService lordService;

    //查看所有房东
    @GetMapping("/Alllord/{page}/{size}")
    public Page<Lord> findAlllord(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return lordService.Alllord(pageable);//findAll方法中有按页查询方法
    }

    //根据房东ID查找
    @GetMapping("/findlordById/{id}")
    public Lord findLordById(@PathVariable("id") Integer id){
        return lordService.find(id);
    }


    //根据会员ID判断是否为房东
    @GetMapping("/findlordBymemId/{id}")
    public Lord findLordByMemId(@PathVariable("id") Integer id){
        return lordService.findByMem(id);
    }

    //修改房东信息（状态）
    @PutMapping("/updatelord")
    public String update(@RequestBody Lord lord){
        return lordService.update(lord);
    }

    //添加房东
    @PostMapping("/savelord")
    public String save(@RequestBody Lord lord){
        return lordService.add(lord);
    }

    //根据多条件查询
    @PostMapping("/findsearch")
    public List<Lord> findSearch(@RequestBody Map searchMap){
        return lordService.findSearch(searchMap);
    }

}

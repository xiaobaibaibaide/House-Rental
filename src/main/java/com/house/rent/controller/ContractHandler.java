package com.house.rent.controller;

import com.house.rent.entity.Admin;
import com.house.rent.entity.Contract;
import com.house.rent.entity.Image;
import com.house.rent.service.AdminService;
import com.house.rent.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/contract")
public class ContractHandler {
    @Autowired
    private ContractService contractService;

    /*合同管理模块*/
    //查看所有合同


    //查找合同

    //修改合同信息
    @PostMapping("/findsearch")
    public Page<Contract> findSearch(@RequestBody Map searchMap){
        return contractService.findSearch(searchMap);
    }

    //添加合同
    @PostMapping("/savecontract")
    public String save(@RequestBody Contract contract){
        Contract result = contractService.add(contract);
        if (result!=null){
            return result.getContract_id()+"";
        }
        else
            return "error";

    }


}

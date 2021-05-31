package com.house.rent.service;

import com.house.rent.entity.Admin;
import com.house.rent.entity.Contract;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ContractService {

    Contract add(Contract contract);
    Page<Contract> findSearch(Map searchMap);
}

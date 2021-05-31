package com.house.rent.repository;

import com.house.rent.entity.Area;
import com.house.rent.entity.Collection;
import com.house.rent.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractRepository extends JpaRepository<Contract,Integer>, JpaSpecificationExecutor<Contract> {
}

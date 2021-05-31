package com.house.rent.repository;

import com.house.rent.entity.Activity;
import com.house.rent.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityRepository extends JpaRepository<Activity,Integer>, JpaSpecificationExecutor<Activity> {
}

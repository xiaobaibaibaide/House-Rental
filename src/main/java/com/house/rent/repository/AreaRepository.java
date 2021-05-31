package com.house.rent.repository;

import com.house.rent.entity.Admin;
import com.house.rent.entity.Area;
import com.house.rent.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area,Integer> {
}

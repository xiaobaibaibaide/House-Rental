package com.house.rent.repository;

import com.house.rent.entity.Contract;
import com.house.rent.entity.Fitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FitmentRepository extends JpaRepository<Fitment,Integer> {
}

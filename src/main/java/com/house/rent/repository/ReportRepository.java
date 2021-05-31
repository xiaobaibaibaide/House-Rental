package com.house.rent.repository;

import com.house.rent.entity.Message;
import com.house.rent.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Integer> {
}

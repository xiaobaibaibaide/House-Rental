package com.house.rent.repository;

import com.house.rent.entity.Area;
import com.house.rent.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}

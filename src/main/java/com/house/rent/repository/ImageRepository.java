package com.house.rent.repository;

import com.house.rent.entity.House;
import com.house.rent.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}

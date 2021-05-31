package com.house.rent.repository;

import com.house.rent.entity.Collection;
import com.house.rent.entity.Contract;
import com.house.rent.entity.Reserve;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection,Integer>, JpaSpecificationExecutor<Collection> {
    Collection findByHouseIdAndMemId(Long house_id,Integer mem_id);
    void deleteByHouseIdAndMemId(Long house_id,Integer mem_id);
    List<Collection> findAllByMemId(Integer mem_id);
    List<Collection> findAllByHouseId(Long houseId);
}

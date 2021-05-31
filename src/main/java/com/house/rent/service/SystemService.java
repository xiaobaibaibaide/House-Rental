package com.house.rent.service;

import com.house.rent.VO.*;
import com.house.rent.entity.Info;

import java.util.List;

public interface SystemService {
    List<HousePieVO> houseVOList();
    List<UserPieVO> userVOList();
    List<AreaPieVO> areaVOList();
    PublishLineVO publishLineVOList();
    OrderLineVO orderLineVOList();

}

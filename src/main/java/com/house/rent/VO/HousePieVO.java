package com.house.rent.VO;

import lombok.Data;

import java.util.Map;

@Data
public class HousePieVO {
    public Integer value;
    public Map<String,String> itemStyle;
    public String name;
}

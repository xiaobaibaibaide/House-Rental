package com.house.rent.util;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {
    public static Map<String,String> createItemStyle(Integer num){
        String color = "";
        if(num<3){
            color = "#c4ebad";
        }
        if(num>3 && num<6){
            color = "#6be6c1";
        }
        if(num>6 && num<10){
            color = "#3fb1e3";
        }
        if(num>10 && num<13){
            color = "#a0a7e6";
        }
        Map<String,String> map = new HashMap<>();
        map.put("color", color);
        return map;
    }
}

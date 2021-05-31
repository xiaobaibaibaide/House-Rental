package com.house.rent.VO;

import lombok.Data;

import java.util.List;

@Data
public class PublishLineVO {
    private List<String> dates;
    private List<DataVO> values;
}

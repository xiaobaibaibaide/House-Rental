package com.house.rent.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "`area`")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer area_id;
    private String province;
    private String city;
    private String region;
}

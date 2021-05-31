package com.house.rent.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "`fitment`")
public class Fitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fitment_id;
    private Integer desk;
    private Integer bed;
    private Integer chair;
    private Integer wash;
    private Integer bath;
    private Integer balcony;
    private Integer aircondition;
    private Integer net;
    private Integer fridge;
    private Integer tv;
    private Integer cabinet;
}

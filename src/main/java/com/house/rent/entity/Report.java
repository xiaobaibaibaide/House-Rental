package com.house.rent.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "`report`")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer report_id;



}

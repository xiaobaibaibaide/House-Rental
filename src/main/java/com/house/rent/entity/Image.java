package com.house.rent.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "`image`")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;
    private String image1_url;
    private String image2_url;
    private String image3_url;
    private String image4_url;
    private String image5_url;
    private String image6_url;
}

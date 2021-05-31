package com.house.rent.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "`message`")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer message_id;
    private String context;
}

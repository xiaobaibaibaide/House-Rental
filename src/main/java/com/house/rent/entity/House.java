package com.house.rent.entity;

import lombok.Data;
import org.springframework.expression.ParseException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "`house`")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long house_id;
    @Column(name ="lord_id")
    private Integer lordId;
    @Column(name ="p_id")
    private Integer publisher;
    @Column(name ="area_id")
    private Integer areaid;
    private String address;
    private Integer type;
    private Integer rentkind;
    private Integer rooms;
    private Integer leftrooms;
    private Integer fitment_id;
    private Integer acreage;
    private String orientation;
    private BigDecimal price;
    private BigDecimal deposit;
    private Integer status;
    private Date ctime;
    private Date utime;
    private Integer image_id;
    private String image1;
    private String remark;

    //ctime的set和get方法
    public String getCtime() {
        String value = null;
        //将Date类型的时间转换成指定格式的字符串
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        value = dateFormat.format(ctime);
        return value;
    }
    public void setCtime(String ctime) {
        //将字符串类型的日期转换成Date类型的指定格式的日期
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);//从第一个字符开始解析
        try {
            this.ctime = f.parse(ctime,pos);/*对参数msg_create_date（String类型）从第一个字符开始解析（由pos），转换成java.util.Date类型，
		而这个Date的格式为"yyyy-MM-dd"（因为SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");）*/
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //utime的set和get方法
    public String getUtime() {
        String value = null;
        //将Date类型的时间转换成指定格式的字符串
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        value = dateFormat.format(utime);
        return value;
    }
    public void setUtime(String utime) {
        //将字符串类型的日期转换成Date类型的指定格式的日期
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);//从第一个字符开始解析
        try {
            this.utime = f.parse(utime,pos);/*对参数msg_create_date（String类型）从第一个字符开始解析（由pos），转换成java.util.Date类型，
            /*而这个Date的格式为"yyyy-MM-dd"（因为SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");）*/
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

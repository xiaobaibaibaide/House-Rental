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
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    private Long house_id;
    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "lord_id")
    private Integer lordId;
    @Column(name = "begin_date")
    private Date begin;
    private Integer rooms;
    private Integer rent_month;
    private BigDecimal month_price;
    private Long contract_id;
    private BigDecimal price;
    private Date ctime;
    private Integer status;

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
    public String getBegin() {
        String value = null;
        //将Date类型的时间转换成指定格式的字符串
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        value = dateFormat.format(begin);
        return value;
    }
    public void setBegin(String begin) {
        //将字符串类型的日期转换成Date类型的指定格式的日期
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);//从第一个字符开始解析
        try {
            this.begin = f.parse(begin,pos);/*对参数msg_create_date（String类型）从第一个字符开始解析（由pos），转换成java.util.Date类型，
		而这个Date的格式为"yyyy-MM-dd"（因为SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");）*/
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

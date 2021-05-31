package com.house.rent.serviceImpl;

import com.house.rent.VO.*;
import com.house.rent.entity.House;
import com.house.rent.entity.Info;
import com.house.rent.entity.Lord;
import com.house.rent.entity.Member;
import com.house.rent.repository.HouseRepository;
import com.house.rent.repository.LordRepository;
import com.house.rent.repository.MemberRepository;
import com.house.rent.repository.OrderRepository;
import com.house.rent.service.SystemService;
import com.house.rent.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private LordRepository lordRepository;
    @Autowired
    private OrderRepository orderRepository;

    //根据前端选择的时间将近期发布的房源，会员，成交量等信息包装成类发送到前端显示可视化面板

    //发布房源情况的统计 折线图
    //按发布时间区分
    @Override
    public PublishLineVO publishLineVOList() {
        PublishLineVO pubVO = new PublishLineVO(); //封装的VO
        List<String> dates = new ArrayList<>(); //发布日期
        List<DataVO> houses = new ArrayList<>(); //房源数this.houseRepository.findByStatus(1)
        SimpleDateFormat f =  new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        f.setLenient(false);
        for(int i=0;i<7;i++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -i);
            DataVO data = new DataVO();
            Date date = c.getTime();//Fri May 14 20:46:45 CST 2021
            String str = f.format(date);//2021-05-14
            dates.add(c.get(Calendar.MONTH)+1+"-"+c.get(Calendar.DAY_OF_MONTH)+"");
            houses.add(data);
            try{
                SimpleDateFormat f1 =  new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = f1.parse(str);
                System.out.println(date1);
                c.add(Calendar.DAY_OF_MONTH,1);//当天的结束时间
                Date date2 =c.getTime();
                String str1 = f1.format(date2);
                Date date3 = f1.parse(str1);
                System.out.println(date3);
                data.setValue(houseRepository.findAllByCtimeBetween(date1,date3).size());//houseRepository.findAllByCtimeLike(date1).size())
            }catch (ParseException | java.text.ParseException e) {e.printStackTrace();}

        }
        pubVO.setDates(dates);
        pubVO.setValues(houses);
        return pubVO;
    }


    //租房订单的统计 折线图 //未完成
    @Override
    public OrderLineVO orderLineVOList() {
        OrderLineVO ordVO = new OrderLineVO(); //封装的VO
        List<String> dates = new ArrayList<>(); //发布日期
        List<DataVO> houses = new ArrayList<>(); //房源数this.houseRepository.findByStatus(1)
        SimpleDateFormat f =  new SimpleDateFormat("yyyy-MM-dd");
        f.setLenient(false);
        for(int i=0;i<7;i++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -i);
            DataVO data = new DataVO();
            Date date = c.getTime();//Fri May 14 20:46:45 CST 2021
            String str = f.format(date);//2021-05-14
            dates.add(c.get(Calendar.MONTH)+1+"-"+c.get(Calendar.DAY_OF_MONTH)+"");
            houses.add(data);
            try{
                SimpleDateFormat f1 =  new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = f1.parse(str);
                System.out.println(date1);
                c.add(Calendar.DAY_OF_MONTH,1);//当天的结束时间
                Date date2 =c.getTime();
                String str1 = f1.format(date2);
                Date date3 = f1.parse(str1);
                System.out.println(date3);
                data.setValue(orderRepository.findAllByCtimeBetween(date1,date3).size());
                //data.setValue(orderRepository.findAllByCtimeBetweenAndStatus(date1,date3,3).size());
            }catch (ParseException | java.text.ParseException e) {e.printStackTrace();}
        }
        ordVO.setDates(dates);
        ordVO.setValues(houses);
        return ordVO;
    }

    //会员和房东情况的统计 饼图 返回的是Pie集合
    @Override
    public List<UserPieVO> userVOList() {
        List<UserPieVO> userPieVOList = new ArrayList<>();
        List<Member> members = memberRepository.findAll();
        List<Lord> lords = lordRepository.findAll();
        UserPieVO userPieVO =new UserPieVO();
        UserPieVO lordPieVO =new UserPieVO();
        lordPieVO.setName("房东");
        lordPieVO.setValue(lords.size());
        lordPieVO.setItemStyle(DataUtil.createItemStyle(lords.size()));
        userPieVOList.add(lordPieVO);

        userPieVO.setValue(members.size()-lords.size());
        userPieVO.setName("会员");
        userPieVO.setItemStyle(DataUtil.createItemStyle(members.size()-lords.size()));
        userPieVOList.add(userPieVO);

        return userPieVOList;
    }

    //房源待租和已租情况的统计 饼图
    @Override
    public List<HousePieVO> houseVOList() {
        List<HousePieVO> housePieVOList = new ArrayList<>();
        List<House> fin = houseRepository.findByStatus(2);//已租的状态是2
        List<House> wait = houseRepository.findByStatus(1);//未租的状态是1
        List<House> check = houseRepository.findByStatus(0);//发布待审核
        List<House> cancel = houseRepository.findByStatus(4);//审核不通过
        HousePieVO finPieVO =new HousePieVO();
        HousePieVO waitPieVO =new HousePieVO();
        HousePieVO checkPieVO =new HousePieVO();
        HousePieVO cancelPieVO =new HousePieVO();
        finPieVO.setName("已租");
        finPieVO.setValue(fin.size());
        finPieVO.setItemStyle(DataUtil.createItemStyle(fin.size()));
        housePieVOList.add(finPieVO);

        waitPieVO.setValue(wait.size());
        waitPieVO.setName("待租");
        waitPieVO.setItemStyle(DataUtil.createItemStyle(wait.size()));
        housePieVOList.add(waitPieVO);

        checkPieVO.setValue(check.size());
        checkPieVO.setName("待审核");
        checkPieVO.setItemStyle(DataUtil.createItemStyle(check.size()));
        housePieVOList.add(checkPieVO);
        cancelPieVO.setValue(cancel.size());
        cancelPieVO.setName("审核不通过");
        cancelPieVO.setItemStyle(DataUtil.createItemStyle(cancel.size()));
        housePieVOList.add(cancelPieVO);
        return housePieVOList;
    }

    //房源来源地区情况的统计 饼图
    @Override
    public List<AreaPieVO> areaVOList() {
        List<AreaPieVO> areaPieVOList = new ArrayList<>();
        //先查出有多少种area的区，然后区号找出对应的地区有多少房源数
        //初步实现 需完善 遍历读
        List<House> jn = houseRepository.findByAreaidAndStatusNot(320115,4);
        List<House> pk = houseRepository.findByAreaidAndStatusNot(320111,4);
        List<House> xw = houseRepository.findByAreaidAndStatusNot(320102,4);
        List<House> qh = houseRepository.findByAreaidAndStatusNot(320104,4);
        List<House> jy = houseRepository.findByAreaidAndStatusNot(320105,4);
        List<House> gl = houseRepository.findByAreaidAndStatusNot(320106,4);
        List<House> qx = houseRepository.findByAreaidAndStatusNot(320113,4);
        List<House> yht = houseRepository.findByAreaidAndStatusNot(320114,4);
        List<House> lh = houseRepository.findByAreaidAndStatusNot(320116,4);
        List<House> ls = houseRepository.findByAreaidAndStatusNot(320117,4);
        List<House> gc = houseRepository.findByAreaidAndStatusNot(320118,4);
        AreaPieVO jnPieVO =new AreaPieVO();
        AreaPieVO pkPieVO =new AreaPieVO();
        AreaPieVO xwPieVO =new AreaPieVO();
        AreaPieVO qhPieVO =new AreaPieVO();
        AreaPieVO jyPieVO =new AreaPieVO();
        AreaPieVO glPieVO =new AreaPieVO();
        AreaPieVO qxPieVO =new AreaPieVO();
        AreaPieVO yhtPieVO =new AreaPieVO();
        AreaPieVO lhPieVO =new AreaPieVO();
        AreaPieVO lsPieVO =new AreaPieVO();
        AreaPieVO gcPieVO =new AreaPieVO();
        jnPieVO.setValue(jn.size());
        jnPieVO.setName("江宁区");
        jnPieVO.setItemStyle(DataUtil.createItemStyle(jn.size()));
        areaPieVOList.add(jnPieVO);
        pkPieVO.setValue(pk.size());
        pkPieVO.setName("浦口区");
        pkPieVO.setItemStyle(DataUtil.createItemStyle(pk.size()));
        areaPieVOList.add(pkPieVO);
        xwPieVO.setValue(xw.size());
        xwPieVO.setName("玄武区");
        xwPieVO.setItemStyle(DataUtil.createItemStyle(xw.size()));
        areaPieVOList.add(xwPieVO);
        qhPieVO.setValue(qh.size());
        qhPieVO.setName("秦淮区");
        qhPieVO.setItemStyle(DataUtil.createItemStyle(qh.size()));
        areaPieVOList.add(qhPieVO);
        jyPieVO.setValue(jy.size());
        jyPieVO.setName("建邺区");
        jyPieVO.setItemStyle(DataUtil.createItemStyle(jy.size()));
        areaPieVOList.add(jyPieVO);
        glPieVO.setValue(gl.size());
        glPieVO.setName("鼓楼区");
        glPieVO.setItemStyle(DataUtil.createItemStyle(gl.size()));
        areaPieVOList.add(glPieVO);
        qxPieVO.setValue(qx.size());
        qxPieVO.setName("栖霞区");
        qxPieVO.setItemStyle(DataUtil.createItemStyle(qx.size()));
        areaPieVOList.add(qxPieVO);
        yhtPieVO.setValue(yht.size());
        yhtPieVO.setName("雨花台区");
        yhtPieVO.setItemStyle(DataUtil.createItemStyle(yht.size()));
        areaPieVOList.add(yhtPieVO);
        lhPieVO.setValue(lh.size());
        lhPieVO.setName("六合区");
        lhPieVO.setItemStyle(DataUtil.createItemStyle(lh.size()));
        areaPieVOList.add(lhPieVO);
        lsPieVO.setValue(ls.size());
        lsPieVO.setName("溧水区");
        lsPieVO.setItemStyle(DataUtil.createItemStyle(ls.size()));
        areaPieVOList.add(lsPieVO);
        gcPieVO.setValue(gc.size());
        gcPieVO.setName("高淳区");
        gcPieVO.setItemStyle(DataUtil.createItemStyle(gc.size()));
        areaPieVOList.add(gcPieVO);
        return areaPieVOList;
    }

}

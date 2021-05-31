package com.house.rent.controller;

import com.house.rent.entity.*;
import com.house.rent.repository.AreaRepository;
import com.house.rent.repository.FitmentRepository;
import com.house.rent.repository.ImageRepository;
import com.house.rent.service.HouseService;
import com.house.rent.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/house")
public class HouseHandler {
    @Autowired
    private MemberService memberService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private FitmentRepository fitmentRepository;
    @Autowired
    private ImageRepository imageRepository;


    /*房源管理模块*/
    //首页 查看所有待租房源信息 待租1
    @GetMapping("/findhouseByStatus/{status}")
    public List<House> findhouseByStatus(@PathVariable("status") Integer status){
        return houseService.findByStatus(status);
    }
    //分页
    @GetMapping("/findhouseByStatus/{status}/{page}/{size}")
    public Page<House> findhouseByStatus(@PathVariable("status") Integer status,@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return houseService.findByStatus(status,pageable);
    }

    //首页 根据租赁方式查找待租房源 租赁方式+待租 分页 排序
    @GetMapping("/findhouseByKindAndStatus/{kind}/{page}/{size}")
    public List<House> findhouseByKindAndStatus(@PathVariable("kind") Integer kind,@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        return houseService.findByRentkindAndStatus(kind);
    }

    //后台分页查看系统中所有房源
    @GetMapping("/Allhouse/{page}/{size}")
    public Page<House> findAllhouse(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return houseService.Allhouse(pageable);
    }

    //查看房源对应的地址
    @GetMapping("/findareaById/{id}")
    public String findareaById(@PathVariable("id") Integer id){
        Area result = areaRepository.findById(id).get();
        return result.getProvince()+result.getCity()+result.getRegion();
    }

    @GetMapping("/findarea/{id}")
    public Area findarea(@PathVariable("id") Integer id){
        Area result = areaRepository.findById(id).get();
        return result;
    }

    //上传房源图片
    @PostMapping("/upload")
    public Map<String,Object> fileupload(MultipartFile file, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        Map<String, Object> result = new HashMap<>();
        String realPath = "D:/bs program1/housepic/";//文件保存地址
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            file.transferTo(new File(folder, newName));
            String url = newName;//realPath +
            result.put("status", "OK");
            result.put("name", oldName);
            result.put("url", url);
        } catch (IOException e) {
            result.put("status", "ERROR");
            result.put("msg", e.getMessage());
        }
        return result;
    }

    //查看对应的房源图片 参数时房源图片ID
    @GetMapping("/findimageById/{id}")
    public Image findimageById(@PathVariable("id") Long id){
        return imageRepository.findById(id).get();
    }

    //添加房源图片对象
    @PostMapping("/saveimage")
    public String save(@RequestBody Image image){
        Image result = imageRepository.save(image);
        if (result!=null){
            return result.getImage_id()+"";
        }
        else
            return "error";

    }

    //修改房源图片
    @PutMapping ("/updateimage")
    public String update(@RequestBody Image image){
        Image result = imageRepository.save(image);
        if (result!=null){
            return "success";
        }
        else{
            return "error";
        }

    }

    //修改房源信息
    @PutMapping ("/updatehouse")
    public String update(@RequestBody House house){
        return houseService.update(house);
    }
    //处理提交房源的审核请求 //添加修改房源状态为3


    //添加房源
    @PostMapping("/savehouse")
    public String save(@RequestBody House house){
        return houseService.add(house);
    }

    //删除房源
    @GetMapping("/deletehouse/{id}")
    public String deletehouse(@PathVariable("id") Integer id){
        return houseService.delete(id);
    }

    //根据ID查找房源
    @GetMapping("/findhouseById/{id}")
    public House findHouseById(@PathVariable("id") Long id){
        return houseService.find(id);
    }

    //根据房东ID查找房源
    @GetMapping("/lord/{id}")
    public List<House> findHouseByLordId(@PathVariable("id") Integer id){
        return houseService.findByLord(id);
    }





    //根据创建时间查找房源 ctime
    @GetMapping("/findhouseByCtime/{ctime}")
    public List<House> findhouseByCtime(@PathVariable("ctime") Date ctime){
        return houseService.findByCtime(ctime);
    }

    //根据地区查找房源 area
  /*  @GetMapping("/findhouseByArea/{area}")
    public List<House> findhouseByArea(@PathVariable("area") Integer area){
        return houseService.findByArea_id(area);
    }*/

    //根据多条件查询
    @PostMapping("/findsearch")
    public Page<House> findSearch( @RequestBody Map searchMap){
        return houseService.findSearch(searchMap);
    }


    /*家具管理*/
    //添加家具
    @PostMapping("/savefitment")
    public String save(@RequestBody Fitment fitment){
        Fitment result = fitmentRepository.save(fitment);
        if (result!= null){
            System.out.println(result);
            return result.getFitment_id()+"";
        }
        else{
            return "error";
        }

    }


    //根据家具ID查找家具
    @GetMapping("/findfitmentById/{id}")
    public Fitment findfitmentById(@PathVariable("id") Integer id){
        Fitment result = fitmentRepository.findById(id).get();
        if (result!=null)
            return result;
        else
            return null;
    }

    //修改家具信息
    @PutMapping("/updatefitment")
    public String update(@RequestBody Fitment fitment){
        Fitment result = fitmentRepository.save(fitment);
        if (result!= null){
            return "success";
        }
        else{
            return "error";
        }

    }

}

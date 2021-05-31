package com.house.rent.serviceImpl;

import com.house.rent.entity.Collection;
import com.house.rent.entity.Reserve;
import com.house.rent.repository.CollectionRepository;
import com.house.rent.repository.HouseRepository;
import com.house.rent.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private HouseRepository houseRepository;

    //查找收藏
    @Override
    public Collection find(int id) {
        return collectionRepository.findById(id).get();
    }

    //是否已收藏
    @Override
    public String exist(Collection collection) {
        Collection result = collectionRepository.findByHouseIdAndMemId(collection.getHouseId(),collection.getMemId());
        if(result!=null)
            return "exist";
        else
            return "null";
    }

    //添加房源到收藏夹  //参数是收藏实体  //条件是用户收藏夹中无该收藏记录
    @Override
    public String add(Collection collection) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        collection.setCtime(dateFormat.format(date));
        if(exist(collection).equals("null")){
            Collection result = collectionRepository.save(collection);
            if(result!=null)
                return "success";
            else
                return "error";
        }
        else
            return "exist";

    }

    //修改收藏 //可不用
    @Override
    public String update(Collection collection) {
        return null;
    }


    //取消收藏
    @Override
    public String delete(long houseId,int memId) {
        collectionRepository.deleteByHouseIdAndMemId(houseId,memId);
        return "success";
    }
    //批量删除收藏

    //查看会员对应的收藏夹  //参数是会员ID
    @Override
    public List<Collection> findByMem(Pageable pageable, int id) {
        return collectionRepository.findAllByMemId(id);
    }

    //查看房源对应的收藏记录
    @Override
    public List<Collection> findByHouse(long id) {
        return collectionRepository.findAllByHouseId(id);
    }

    //多条件查询
    @Override
    public Page<Collection> findSearch(Map searchMap) {
        System.out.println(searchMap);
        int page = Integer.parseInt(searchMap.get("page").toString());
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,10,sort);
        Specification<Collection> specification = createSpecification(searchMap);
        return collectionRepository.findAll(specification,pageable);

    }
    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Collection> createSpecification(Map searchMap) {

        return new Specification<Collection>() {

            @Override
            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //房源ID
                if (searchMap.get("houseId")!=null && !"".equals(searchMap.get("houseId"))) {
                    predicateList.add(cb.equal(root.get("houseId").as(String.class), (String)searchMap.get("houseId")));
                }
                //租客
                if (searchMap.get("memId")!=null && !"".equals(searchMap.get("memId"))) {
                    predicateList.add(cb.equal(root.get("memId").as(String.class), (String)searchMap.get("memId")));
                }
                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}

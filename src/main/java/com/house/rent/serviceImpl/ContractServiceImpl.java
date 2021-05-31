package com.house.rent.serviceImpl;

import com.house.rent.entity.Admin;
import com.house.rent.entity.Contract;
import com.house.rent.repository.CollectionRepository;
import com.house.rent.repository.ContractRepository;
import com.house.rent.service.ContractService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;


    //添加合同  //参数是合同实体
    @Override
    public Contract add(Contract contract) {
        return contractRepository.save(contract);
    }
    //查找合同  //参数是合同ID

    //修改合同      //参数是合同实体


    //删除合同      //参数是合同ID



    //多条件查询
    @Override
    public Page<Contract> findSearch(Map searchMap) {
        System.out.println(searchMap);
        int page = Integer.parseInt(searchMap.get("page").toString());
        Sort sort = Sort.by(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(page-1,10,sort);
        Specification<Contract> specification = createSpecification(searchMap);
        return contractRepository.findAll(specification,pageable);
    }
    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Contract> createSpecification(Map searchMap) {

        return new Specification<Contract>() {

            @Override
            public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //合同ID
                if (searchMap.get("contract_id")!=null && !"".equals(searchMap.get("contract_id"))) {
                    predicateList.add(cb.equal(root.get("contract_id").as(String.class), (String)searchMap.get("contract_id")));
                }
                //房东ID
                if (searchMap.get("mem_id")!=null && !"".equals(searchMap.get("mem_id"))) {
                    predicateList.add(cb.equal(root.get("mem_id").as(String.class), (String)searchMap.get("mem_id")));
                }
                //租客ID
                if (searchMap.get("lord_id")!=null && !"".equals(searchMap.get("lord_id"))) {
                    predicateList.add(cb.equal(root.get("lord_id").as(String.class), (String)searchMap.get("lord_id")));
                }
                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }


}

package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.dao.WarningRepository;
import com.fym.electrichousekeeper.entiry.po.Device;
import com.fym.electrichousekeeper.entiry.po.Warning;
import com.fym.electrichousekeeper.entiry.vo.WarningOverview;
import com.fym.electrichousekeeper.util.DateComputeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/warning")
public class WarningController {

    @Autowired
    private WarningRepository repository;

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/list")
    public ApiResponse getWarning(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                  @RequestParam(required = false,value = "startDate") Date start,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd")
                                  @RequestParam(value = "endDate",required = false) Date end,
                                  @RequestParam(value = "warningType",required = false)String warningType,
                                  @RequestParam(value = "status",required = false)Integer status,
                                  @RequestParam(value = "deviceCodes",required = false)String [] devices){
        ApiResponse response = ApiResponse.OK();
        List<Warning> all = repository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (start != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("time"), start));
            }
            if (end != null) {
                Date tomorrow = DateComputeUtil.add(end, DateComputeUtil.TYPE_DATE, 1);
                predicates.add(cb.lessThan(root.get("time"), tomorrow));
            }
            if (!StringUtils.isEmpty(warningType)) {
                predicates.add(cb.equal(root.get("warningType"), warningType));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (devices != null && devices.length > 0) {
                CriteriaBuilder.In<Object> in = cb.in(root.get("deviceCode"));
                for (String device : devices) {
                    in.value(device);
                }
                predicates.add(in);
            }
            query.where(predicates.toArray(new Predicate[]{}));
            return null;
        });
        response.setRows(all);
        return response;
    }

    @GetMapping("/overview")
    public ApiResponse getOverView(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                   @RequestParam(value = "date") Date date){
        ApiResponse response = ApiResponse.OK();
        Date tomorrow = DateComputeUtil.add(date,DateComputeUtil.TYPE_DATE,1);
        List<Warning> result = repository.findByTimeGreaterThanEqualAndTimeLessThan(date, tomorrow);
        Map<String,Integer> resultMap = new HashMap<>();
        List<WarningOverview> overviews = new ArrayList<>();
        result.forEach(item->{
            Integer count = resultMap.get(item.getDeviceCode());
            count = count == null ? 0 : count;
            resultMap.put(item.getDeviceCode(),count + 1);
        });
        resultMap.forEach((k,v)->overviews.add(new WarningOverview(k,v)));
        response.setRows(overviews);
        return response;
    }

    @GetMapping("/deviceWarningRank")
    public ApiResponse warningRank(@RequestParam(name = "top",required = false,defaultValue = "5") Integer top){
        ApiResponse response = ApiResponse.OK();
        Date today = DateComputeUtil.todayZero();
        List warningRank = repository.findWarningRank(today);
        Map<String,Set<String>> deviceWarningType = new HashMap<>();
        Map<String,Integer> deviceWarningCount = new HashMap<>();
        List<Map<String,Object>> result = new ArrayList<>();
        for (Object obj : warningRank) {
            Object [] objArr = (Object [])obj;
            String code = objArr[0].toString();
            String warningType = objArr[1].toString();
            Integer count = Integer.valueOf(objArr[2].toString());
            Set<String> typeSet = deviceWarningType.getOrDefault(code, new HashSet<>());
            typeSet.add(warningType);
            deviceWarningType.put(code,typeSet);
            Integer defaultCount = deviceWarningCount.getOrDefault(code, 0);
            defaultCount += count;
            deviceWarningCount.put(code,defaultCount);
        }
        for (String deviceCode : deviceWarningType.keySet()) {
            Map<String,Object> item = new HashMap<>();
            item.put("code",deviceCode);
            item.put("warningTypeCount",deviceWarningType.get(deviceCode).size());
            item.put("warningCount",deviceWarningCount.get(deviceCode));
            result.add(item);
        }
        result.sort((left,right)->{
            Integer warningCountLeft = (Integer) left.get("warningCount");
            Integer warningTypeCountLeft = (Integer) left.get("warningTypeCount");
            Integer warningCountRight = (Integer) right.get("warningCount");
            Integer warningTypeCountRight = (Integer) right.get("warningTypeCount");
            if(warningCountLeft == warningCountRight){
                return warningTypeCountLeft > warningTypeCountRight ? 1 : (warningTypeCountLeft == warningTypeCountRight ? 0 : -1);
            }else{
                return warningCountLeft > warningCountRight ? 1 : (warningCountLeft == warningCountRight ? 0 : -1);
            }
        });
        if(result.size() > top){
            result = result.subList(result.size() - top,result.size());
        }
        result.forEach(item->{
            String code = item.get("code").toString();
            Optional<Device> byId = deviceRepository.findById(code);
            item.put("name",byId.isPresent() ? byId.get().getName() : code);
        });
        response.setRows(result);
        return response;
    }

}

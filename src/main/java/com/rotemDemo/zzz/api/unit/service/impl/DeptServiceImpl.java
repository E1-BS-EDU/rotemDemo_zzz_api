package com.rotemDemo.zzz.api.unit.service.impl;

import com.rotemDemo.zzz.api.login.service.impl.LoginServiceImpl;
import com.rotemDemo.zzz.api.unit.mapper.DeptMapper;
import com.rotemDemo.zzz.api.unit.service.DeptService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service 
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;

    public DeptServiceImpl(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    @Override
    public List<Map<String, Object>> selectDeptList(Map<String, Object> data) {
    	
        return deptMapper.selectDeptList(data);
    }
    
    /** 하위부서 포함 */
    @Override
    public List<Map<String, Object>> selectDeptIncludeSubList(Map<String, Object> data) {
        return deptMapper.selectDeptIncludeSubList(data);
    }
    

}
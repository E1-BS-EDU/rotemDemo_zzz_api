package com.rotemDemo.zzz.api.unit.service;

import java.util.List;
import java.util.Map;

/**
 */
public interface DeptService {

    /**
     * 부서 목록 전체 조회
     */
	List<Map<String, Object>> selectDeptList(Map<String, Object> data);
    
    /**
     * 부서 하위부서 만 목록 전체 조회
     */
	List<Map<String, Object>> selectDeptIncludeSubList(Map<String, Object> data);

}
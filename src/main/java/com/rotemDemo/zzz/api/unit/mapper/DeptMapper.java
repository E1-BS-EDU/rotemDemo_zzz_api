package com.rotemDemo.zzz.api.unit.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptMapper {
	/** 부서 전체 **/
	List<Map<String, Object>> selectDeptList(Map<String, Object> data);
	
	/** 하위부서를 포함하는 부서 */
	List<Map<String, Object>> selectDeptIncludeSubList(Map<String, Object> data);

	
}


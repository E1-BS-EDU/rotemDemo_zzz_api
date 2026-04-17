package com.rotemDemo.zzz.api.unit.service;

import java.util.List;
import java.util.Map;

public interface EmplService {

	List<Map<String, Object>> selectEmplList(Map<String, Object> data);
	
	List<Map<String, Object>> selectEmplInDeptList(Map<String, Object> data);

	Map<String, Object> selectEmpDeptHistByDate(Map<String, Object> data);

	List<Map<String, Object>> selectEmpDeptHistList(Map<String, Object> data);

	List<Map<String, Object>> selectWorkEmpInfoList(Map<String, Object> data);

	List<Map<String, Object>> selectWorkdaySummaryEmpTree(Map<String, Object> data);
}

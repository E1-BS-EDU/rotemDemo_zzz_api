package com.rotemDemo.zzz.api.unit.service.impl;

import com.rotemDemo.zzz.api.unit.mapper.EmplMapper;
import com.rotemDemo.zzz.api.unit.service.EmplService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class EmplServiceImpl implements EmplService {

	private final EmplMapper emplMapper;

	public EmplServiceImpl(EmplMapper emplMapper) {
		this.emplMapper = emplMapper;
	}

	@Override
	public List<Map<String, Object>> selectEmplList(Map<String, Object> data) {
		return emplMapper.selectEmplList(data);
	}

	@Override
	public List<Map<String, Object>> selectEmplInDeptList(Map<String, Object> data) {
		return emplMapper.selectEmplInDeptList(data);
	}

	@Override
	public Map<String, Object> selectEmpDeptHistByDate(Map<String, Object> data) {
		return emplMapper.selectEmpDeptHistByDate(data);
	}
	
	@Override
	public List<Map<String, Object>> selectEmpDeptHistList(Map<String, Object> data) {
		return emplMapper.selectEmpDeptHistList(data);
	}

	@Override
	public List<Map<String, Object>> selectWorkEmpInfoList(Map<String, Object> data) {
		List<?> empIdList = (data.get("empIdList") instanceof List<?> list) ? list : Collections.emptyList();

		if(empIdList.isEmpty()) return Collections.emptyList();
		
		return emplMapper.selectWorkEmpInfoList(data);
	}

	@Override
	public List<Map<String, Object>> selectWorkdaySummaryEmpTree(Map<String, Object> data) {
		return emplMapper.selectWorkdaySummaryEmpTree(data);
	}

}

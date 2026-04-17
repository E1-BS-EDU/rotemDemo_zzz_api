package com.rotemDemo.zzz.api.unit.service.impl;

import com.rotemDemo.zzz.api.unit.mapper.CommMapper;
import com.rotemDemo.zzz.api.unit.service.CommService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CommServiceImpl implements CommService {
	private final CommMapper commMapper;

	// 이 코드가 없으면 deptMapper는 null 상태로 남습니다.
	public CommServiceImpl(CommMapper commMapper) {
		this.commMapper = commMapper;
	}

	@Override
	public List<Map<String, Object>> selectCommList(Map<String, Object> data) {
		// 이제 deptMapper가 null이 아니므로 쿼리가 실행됩니다.
		Object val = data.get("supiComCd");

		if (val instanceof String) {
			return commMapper.selectCommList(data);
		} else {
			return commMapper.selectCommMultiList(data);

		}
	}

	@Override
	public List<Map<String, Object>> selectSystemList(Map<String, Object> data) {
		return commMapper.selectSystemList(data);
	}

}

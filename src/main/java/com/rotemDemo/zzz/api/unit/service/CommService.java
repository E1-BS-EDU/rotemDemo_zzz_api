package com.rotemDemo.zzz.api.unit.service;

import java.util.List;
import java.util.Map;

public interface CommService {
    /**
     * 공통 목록 전체 조회
     */
	List<Map<String, Object>> selectCommList(Map<String, Object> data);

	List<Map<String, Object>> selectSystemList(Map<String, Object> data);
}

package com.rotemDemo.zzz.api.unit.service;

import java.util.List;
import java.util.Map;

public interface OblnService {
    /**
     * 다국어 목록 전체 조회
     */
	List<Map<String, Object>> selectOblnList(Map<String, Object> data);
}

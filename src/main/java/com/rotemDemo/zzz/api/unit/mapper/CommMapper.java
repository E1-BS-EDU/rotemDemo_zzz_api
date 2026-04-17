package com.rotemDemo.zzz.api.unit.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommMapper {
	List<Map<String, Object>> selectCommList(Map<String, Object> data);

	List<Map<String, Object>> selectCommMultiList(Map<String, Object> data);

	List<Map<String, Object>> selectSystemList(Map<String, Object> data);
}

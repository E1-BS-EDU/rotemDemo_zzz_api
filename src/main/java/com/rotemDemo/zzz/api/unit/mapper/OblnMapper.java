package com.rotemDemo.zzz.api.unit.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OblnMapper {

	List<Map<String, Object>> selectOblnList(Map<String, Object> data);
	
}

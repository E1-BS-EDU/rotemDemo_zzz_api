package com.rotemDemo.zzz.api.auth.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

	Map<String, Object> selectBtnAuth(Map<String, Object> data);

}

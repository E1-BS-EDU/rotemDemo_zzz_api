package com.rotemDemo.zzz.api.login.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

@Mapper
public interface LoginMapper {
	Map<String, Object> selectMemberByEmail(@Param("empEmail") String empEmail);

	int reSetPassword(Map<String, Object> data);

	int insertRefreshToken(Map<String, Object> data);
	int selectRefreshToken(Map<String, Object> data);
	int updateRefreshTokenExpiry(Map<String, Object> data);
	int deleteRefreshToken(Map<String, Object> data);
	int deleteAllTokensByUserId(Map<String, Object> data);
	int deleteExpiredTokens(Map<String, Object> data);
	
	
}

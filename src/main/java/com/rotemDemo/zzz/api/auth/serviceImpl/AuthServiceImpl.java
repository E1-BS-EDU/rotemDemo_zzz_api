package com.rotemDemo.zzz.api.auth.serviceImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.rotemDemo.zzz.api.auth.service.AuthService;
import com.rotemDemo.zzz.api.auth.mapper.AuthMapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService{

	private final AuthMapper authMapper;
	
    //초기화
	public AuthServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }
    
	@Override
	public Map<String, Object> selectBtnAuth(Map<String, Object> data) {
		return authMapper.selectBtnAuth(data);
		
	}	
}

package com.rotemDemo.zzz.api.unit.service.impl;

import com.rotemDemo.zzz.api.unit.mapper.OblnMapper;
import com.rotemDemo.zzz.api.unit.service.OblnService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service 
public class OblnServiceImpl implements OblnService{

	 private final OblnMapper oblnMapper;

	    public OblnServiceImpl(OblnMapper oblnMapper) {
	        this.oblnMapper = oblnMapper;
	    }
	    
	    @Override
	    public List<Map<String, Object>> selectOblnList(Map<String, Object> data) {
	        return oblnMapper.selectOblnList(data);
	    }
	
}

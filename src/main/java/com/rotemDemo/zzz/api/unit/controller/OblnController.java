package com.rotemDemo.zzz.api.unit.controller;

import com.rotemDemo.zzz.api.dto.ApiResult;
import com.rotemDemo.zzz.api.unit.service.OblnService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/unit")
@RequiredArgsConstructor 
public class OblnController {

	
    private final OblnService oblnService;

    /** 다국어 조회 
     * 전체를 조회한다.
     * */
    
    @PostMapping("/if-zzz-obln-001")
    public ResponseEntity<ApiResult<List<Map<String, Object>>>> getOblnList(@RequestBody Map<String, Object> data) {
    	List<Map<String, Object>> list = oblnService.selectOblnList(data);
        return ResponseEntity.ok(ApiResult.ok(list));
    }
}

package com.rotemDemo.zzz.api.controller;

import com.rotemDemo.zzz.api.dto.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    // 1. 조회 API
    @GetMapping("/info")
    public ResponseEntity<ApiResult<Map<String, Object>>> getInfo() {
    	System.out.println(" info: " );
    	
        Map<String, Object> data = new HashMap<>();
        data.put("system", "MSA API Server");
        data.put("version", "1.0.0");
        data.put("status", "Running");

        return ResponseEntity.ok(ApiResult.ok(data));
    }

    // 2. 등록 API
    @PostMapping("/save")
    public ResponseEntity<ApiResult<String>> saveData(@RequestBody Map<String, String> params) {
        System.out.println("수신 데이터: " + params);
        
        return ResponseEntity.ok(ApiResult.ok("저장 성공!"));
    }
}
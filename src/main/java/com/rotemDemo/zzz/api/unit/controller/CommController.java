package com.rotemDemo.zzz.api.unit.controller;

import com.rotemDemo.zzz.api.dto.ApiResult;
import com.rotemDemo.zzz.api.unit.service.CommService;

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
public class CommController {
	
    private final CommService commService;

    @PostMapping("/if-zzz-comm-001")
    public ResponseEntity<ApiResult<List<Map<String, Object>>>> getCommList(@RequestBody Map<String, Object> data) {
    	List<Map<String, Object>> list = commService.selectCommList(data);
        return ResponseEntity.ok(ApiResult.ok(list));
    }

    @PostMapping("/if-zzz-comm-002")
    public ResponseEntity<ApiResult<List<Map<String, Object>>>> getSystemList(@RequestBody Map<String, Object> data) {
    	List<Map<String, Object>> list = commService.selectSystemList(data);
        return ResponseEntity.ok(ApiResult.ok(list));
    }
}

package com.rotemDemo.zzz.api.unit.controller;

import com.rotemDemo.zzz.api.dto.ApiResult;
import com.rotemDemo.zzz.api.unit.service.DeptService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/unit")
@RequiredArgsConstructor 
public class DeptController {

    private final DeptService deptService;
    
    @PostMapping("/if-zzz-dept-001")
    public ResponseEntity<ApiResult<List<Map<String, Object>>>> getDeptList(@RequestBody Map<String, Object> data) {
    	List<Map<String, Object>> list = deptService.selectDeptList(data);
        return ResponseEntity.ok(ApiResult.ok(list));
    }

    @PostMapping("/if-zzz-dept-002")
    public ResponseEntity<ApiResult<List<Map<String, Object>>>> selectDeptIncludeSubList(@RequestBody Map<String, Object> data) {
    	List<Map<String, Object>> list = deptService.selectDeptIncludeSubList(data);
        return ResponseEntity.ok(ApiResult.ok(list));
    }    
}
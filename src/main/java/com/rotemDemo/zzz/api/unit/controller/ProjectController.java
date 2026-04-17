package com.rotemDemo.zzz.api.unit.controller;

import com.rotemDemo.zzz.api.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rotemDemo.zzz.api.unit.service.ProjectService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/unit")
@RequiredArgsConstructor 
public class ProjectController {

    private final ProjectService projectService;
    
    @PostMapping("/if-zzz-project-001")
    public ResponseEntity<ApiResult<List<Map<String, Object>>>> selectProjectList(@RequestBody Map<String, Object> data) {
    	List<Map<String, Object>> list = projectService.selectProjectList(data);
        return ResponseEntity.ok(ApiResult.ok(list));
    }    
}
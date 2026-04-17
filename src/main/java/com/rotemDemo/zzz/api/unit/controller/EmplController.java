package com.rotemDemo.zzz.api.unit.controller;

import com.rotemDemo.zzz.api.dto.ApiResult;
import com.rotemDemo.zzz.api.unit.service.EmplService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/unit")
@RequiredArgsConstructor
public class EmplController {

	private final EmplService emplService;

	@PostMapping("/if-zzz-empl-001")
	public ResponseEntity<ApiResult<List<Map<String, Object>>>> getEmplList(@RequestBody Map<String, Object> data) {
		List<Map<String, Object>> list = emplService.selectEmplList(data);
		return ResponseEntity.ok(ApiResult.ok(list));
	}

	@PostMapping("/if-zzz-empl-002")
	public ResponseEntity<ApiResult<List<Map<String, Object>>>> getEmplInDeptList(@RequestBody Map<String, Object> data) {
		List<Map<String, Object>> list = emplService.selectEmplInDeptList(data);
		return ResponseEntity.ok(ApiResult.ok(list));
	}

	@PostMapping("/if-zzz-empl-003")
	public ResponseEntity<ApiResult<Map<String, Object>>> getEmpDeptHistByDate(@RequestBody Map<String, Object> data) {
		Map<String, Object> row = emplService.selectEmpDeptHistByDate(data);
		return ResponseEntity.ok(ApiResult.ok(row));
	}

	@PostMapping("/if-zzz-empl-004")
	public ResponseEntity<ApiResult<List<Map<String, Object>>>> getEmpDeptHistList(@RequestBody Map<String, Object> data) {
		List<Map<String, Object>> list = emplService.selectEmpDeptHistList(data);
		return ResponseEntity.ok(ApiResult.ok(list));
	}
	
	@PostMapping("/if-zzz-empl-005")
	public ResponseEntity<ApiResult<List<Map<String, Object>>>> getWorkEmpInfoList(@RequestBody Map<String, Object> data) {
		List<Map<String, Object>> list = emplService.selectWorkEmpInfoList(data);
		return ResponseEntity.ok(ApiResult.ok(list));
	}
	
	@PostMapping("/if-zzz-empl-006")
	public ResponseEntity<ApiResult<List<Map<String, Object>>>> getWorkdaySummaryEmpTree(@RequestBody Map<String, Object> data) {
		List<Map<String, Object>> list = emplService.selectWorkdaySummaryEmpTree(data);
		return ResponseEntity.ok(ApiResult.ok(list));
	}

}

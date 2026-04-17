package com.rotemDemo.zzz.api.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rotemDemo.zzz.api.auth.service.AuthService;
import com.rotemDemo.zzz.api.dto.ApiResult;
import com.rotemDemo.zzz.api.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
	
    @PostMapping("/if-zzz-auth-001")
    public ResponseEntity<?> getBtnAuth (
            @RequestBody Map<String, Object> param, 
            @CookieValue(value = "Authorization", required = false) String token, // 쿠키에서 바로 추출
            HttpServletRequest request) {

    	log.info("권한조회 파라메터  :: {}", param);  	
    	log.info("권한조회 쿠키에서 찾은 Token :: {}", token);  	
    	
    	
    	// 1. 토큰 없으면 401 반환 (Access Token 만료 → 클라이언트 reissue 후 재시도)
    	if (token == null) {
    	    return ResponseEntity.status(401).body(ApiResult.error("401", "세션이 만료되었습니다."));
    	}

    	// 2. 토큰에서 사용자 ID 추출
    	//String token = jwtTokenProvider.resolveToken(request);
    	String userId = jwtTokenProvider.getUserIdFromToken(token);
    	
    	log.info("권한조회 아이디 :: {}", userId);
    	
        param.put("empId", userId);

        // 2. 권한 조회
        Map<String, Object> authMap = authService.selectBtnAuth(param);
        
        log.info("권한조회 :: {}", authMap);
        // 권한이 아예 없는 경우 방어 코드
        if (authMap == null) {
            authMap = new HashMap<>(); // 기본 빈 객체 리턴
        }
        
        return ResponseEntity.ok(ApiResult.ok(authMap));
    }
	
}



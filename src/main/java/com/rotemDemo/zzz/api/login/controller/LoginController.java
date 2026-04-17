package com.rotemDemo.zzz.api.login.controller;

import com.rotemDemo.zzz.api.dto.ApiResult;
import com.rotemDemo.zzz.api.login.dto.LoginRequestDto;
import com.rotemDemo.zzz.api.login.dto.LoginResponseDto;
import com.rotemDemo.zzz.api.login.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Value("${cookie.secure:false}")
    private boolean cookieSecure;

    private static final int ACCESS_TOKEN_AGE  = 60 * 60;       // 1시간
    private static final int REFRESH_TOKEN_AGE = 60 * 60 * 8;   // 8시간

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        try {
            log.info("로그인 요청: {}", requestDto.getEmpEmail());
            LoginResponseDto loginResponse = loginService.login(requestDto);

            addCookie(response, "Authorization", loginResponse.getToken(), ACCESS_TOKEN_AGE);
            addCookie(response, "RefreshToken",  loginResponse.getRefreshToken(), REFRESH_TOKEN_AGE);

            log.info("로그인 성공: {}", loginResponse);

            return ResponseEntity.ok(LoginResponseDto.builder()
                    .token(loginResponse.getToken())
                    .refreshToken(loginResponse.getRefreshToken())
                    .userId(loginResponse.getUserId())
                    .userNm(loginResponse.getUserNm())
                    .lang(loginResponse.getLang())
                    .deptNm(loginResponse.getDeptNm())
                    .deptCd(loginResponse.getDeptCd())
                    .positionNm(loginResponse.getPositionNm())
                    .empDeptAuth(loginResponse.getEmpDeptAuth())
                    .build());

        } catch (IllegalArgumentException e) {
            log.error("로그인 실패: {}", e.getMessage());
            return ResponseEntity.status(401).body(ApiResult.error("401", e.getMessage()));
        } catch (Exception e) {
            log.error("로그인 시스템 오류", e);
            return ResponseEntity.status(500).body(ApiResult.error("500", "서버 오류가 발생했습니다."));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = getCookieValue(request, "RefreshToken");
            loginService.logOut(refreshToken);

            deleteCookie(response, "Authorization");
            deleteCookie(response, "RefreshToken");

            return ResponseEntity.ok(ApiResult.info(true, "200", "로그아웃 되었습니다."));

        } catch (Exception e) {
            log.error("로그아웃 시스템 오류", e);
            return ResponseEntity.status(500).body(ApiResult.error("500", "서버 오류가 발생했습니다."));
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("토큰 재발급 요청");

            String refreshToken = getCookieValue(request, "RefreshToken");
            if (refreshToken == null || refreshToken.trim().isEmpty()) {
                refreshToken = request.getHeader("X-Refresh-Token");
            }
            if (refreshToken == null || refreshToken.trim().isEmpty()) {
                refreshToken = request.getParameter("refreshToken");
            }
            LoginResponseDto result = loginService.reissue(refreshToken);

            addCookie(response, "Authorization", result.getToken(), ACCESS_TOKEN_AGE);
            addCookie(response, "RefreshToken", refreshToken, REFRESH_TOKEN_AGE);

            return ResponseEntity.ok(ApiResult.info(true, "200", "토큰 재발급 완료"));

        } catch (IllegalArgumentException e) {
            log.error("토큰 재발급 실패: {}", e.getMessage());
            return ResponseEntity.status(401).body(ApiResult.error("401", e.getMessage()));
        } catch (Exception e) {
            log.error("토큰 재발급 시스템 오류", e);
            return ResponseEntity.status(500).body(ApiResult.error("500", "서버 오류가 발생했습니다."));
        }
    }

    @PostMapping("/pwReset")
    public ResponseEntity<?> pwReset(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        try {
            log.info("비밀번호 초기화 요청: {}", requestDto.getEmpEmail());
            LoginResponseDto loginResponse = loginService.pwReset(requestDto);

            if (loginResponse.getResult() > 0) {
                deleteCookie(response, "Authorization");
                deleteCookie(response, "RefreshToken");
                return ResponseEntity.ok(ApiResult.info(true, "200", "비밀번호 초기화 되었습니다."));
            } else {
                return ResponseEntity.status(401).body(ApiResult.error("401", "비밀번호 초기화에 실패했습니다."));
            }

        } catch (IllegalArgumentException e) {
            log.error("비밀번호 초기화 실패: {}", e.getMessage());
            return ResponseEntity.status(401).body(ApiResult.error("401", e.getMessage()));
        } catch (Exception e) {
            log.error("비밀번호 초기화 시스템 오류", e);
            return ResponseEntity.status(500).body(ApiResult.error("500", "서버 오류가 발생했습니다."));
        }
    }

    @PostMapping({"/change-password", "/pwChange", "/pwChagne"})
    public ResponseEntity<?> pwChange(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        try {
            log.info("비밀번호 변경 요청: {}", requestDto.getEmpEmail());
            LoginResponseDto loginResponse = loginService.pwChange(requestDto);

            if (loginResponse.getResult() > 0) {
                deleteCookie(response, "Authorization");
                deleteCookie(response, "RefreshToken");
                return ResponseEntity.ok(ApiResult.info(true, "200", "비밀번호 변경이 되었습니다."));
            } else {
                return ResponseEntity.status(401).body(ApiResult.error("401", "비밀번호 변경에 실패했습니다."));
            }

        } catch (IllegalArgumentException e) {
            log.error("비밀번호 변경 실패: {}", e.getMessage());
            return ResponseEntity.status(401).body(ApiResult.error("401", e.getMessage()));
        } catch (Exception e) {
            log.error("비밀번호 변경 시스템 오류", e);
            return ResponseEntity.status(500).body(ApiResult.error("500", "서버 오류가 발생했습니다."));
        }
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (name.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
}

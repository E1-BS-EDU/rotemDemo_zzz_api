package com.rotemDemo.zzz.api.login.service.impl;

import com.rotemDemo.zzz.api.login.dto.LoginRequestDto;
import com.rotemDemo.zzz.api.login.dto.LoginResponseDto;
import com.rotemDemo.zzz.api.login.mapper.LoginMapper;
import com.rotemDemo.zzz.api.login.service.LoginService;
import com.rotemDemo.zzz.api.login.util.JwtTokenProviderLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final JwtTokenProviderLogin jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.refresh-expiration:28800000}")
    private long refreshExpirationMs;

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("요청 값을 확인해주세요.");
        }
        if (requestDto.getEmpEmail() == null || requestDto.getEmpEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }
        if (requestDto.getEmpPassword() == null || requestDto.getEmpPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        Map<String, Object> memberMap = loginMapper.selectMemberByEmail(requestDto.getEmpEmail());
        if (memberMap == null) {
            throw new IllegalArgumentException("이메일을 확인해주세요.");
        }

        if (!passwordEncoder.matches(requestDto.getEmpPassword(), String.valueOf(memberMap.get("emp_password")))) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String userId = String.valueOf(memberMap.get("emp_id"));
        String userNm = String.valueOf(memberMap.get("emp_nm"));
        Object langObj = memberMap.get("langCd");
        String lang = (langObj != null && !String.valueOf(langObj).trim().isEmpty()) ? String.valueOf(langObj).trim() : "KO";
        String deptNm = String.valueOf(memberMap.get("dept_nm"));
        String deptCd = String.valueOf(memberMap.get("dept_cd"));
        String positionNm = String.valueOf(memberMap.get("position_nm"));
        String empDeptAuth = String.valueOf(memberMap.get("emp_dept_auth"));
        String accessToken = jwtTokenProvider.createToken(userId, userNm, lang);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, userNm, lang);

        Map<String, Object> deleteTokenDto = new HashMap<>();
        deleteTokenDto.put("empId", userId);
        loginMapper.deleteAllTokensByUserId(deleteTokenDto);

        Map<String, Object> tokenDto = new HashMap<>();
        tokenDto.put("empId", userId);
        tokenDto.put("tokenVal", refreshToken);
        tokenDto.put("deviceInfo", null);
        tokenDto.put("expDt", buildRefreshExpDt());
        loginMapper.insertRefreshToken(tokenDto);

        log.info("로그인 성공 - userId: {}", userId);

        return LoginResponseDto.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .userNm(userNm)
                .lang(lang)
                .deptNm(deptNm)
                .deptCd(deptCd)
                .positionNm(positionNm)
                .empDeptAuth(empDeptAuth)
                .build();
    }

    @Override
    public void logOut(String refreshToken) {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            return;
        }

        Claims claims;
        try {
            claims = jwtTokenProvider.getClaims(refreshToken);
        } catch (JwtException e) {
            return;
        }

        String userId = claims.getSubject();
        Map<String, Object> tokenDto = new HashMap<>();
        tokenDto.put("empId", userId);
        tokenDto.put("tokenVal", refreshToken);
        int delResult = loginMapper.deleteRefreshToken(tokenDto);

        log.info("로그아웃 완료 - userId: {}, Refresh Token 삭제 {}건", userId, delResult);
    }

    @Override
    public LoginResponseDto pwReset(LoginRequestDto requestDto) {
        log.info("비밀번호 초기화 요청: {}", requestDto.getEmpEmail());

        if (requestDto.getEmpEmail() == null || requestDto.getEmpEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }

        Map<String, Object> memberMap = loginMapper.selectMemberByEmail(requestDto.getEmpEmail());
        if (memberMap == null) {
            throw new IllegalArgumentException("이메일을 확인해주세요.");
        }

        String empId = String.valueOf(memberMap.get("emp_id"));
        String empAlias = String.valueOf(memberMap.get("emp_alias"));

        String newPw = passwordEncoder.encode("e1" + empId);
        Map<String, Object> pwResetDto = new HashMap<>();
        pwResetDto.put("empId", empAlias);
        pwResetDto.put("newPw", newPw);
        pwResetDto.put("empEmail", requestDto.getEmpEmail());
        int resetResult = loginMapper.reSetPassword(pwResetDto);

        return LoginResponseDto.builder()
                .userId(empAlias)
                .result(resetResult)
                .build();
    }

    @Override
    public LoginResponseDto pwChange(LoginRequestDto requestDto) {
        if (requestDto.getEmpEmail() == null || requestDto.getEmpEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }

        Map<String, Object> memberMap = loginMapper.selectMemberByEmail(requestDto.getEmpEmail());
        if (memberMap == null) {
            throw new IllegalArgumentException("이메일을 확인해주세요.");
        }

        String empAlias = String.valueOf(memberMap.get("emp_alias"));
        String empDbPw = String.valueOf(memberMap.get("emp_password"));

        if (!passwordEncoder.matches(requestDto.getEmpPassword(), empDbPw)) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        String newPw = passwordEncoder.encode(requestDto.getNewEmpPassword());
        Map<String, Object> pwResetDto = new HashMap<>();
        pwResetDto.put("empId", empAlias);
        pwResetDto.put("newPw", newPw);
        pwResetDto.put("empEmail", requestDto.getEmpEmail());
        int resetResult = loginMapper.reSetPassword(pwResetDto);

        return LoginResponseDto.builder()
                .userId(empAlias)
                .result(resetResult)
                .build();
    }

    @Override
    public LoginResponseDto reissue(String refreshToken) {
        log.info("Access Token 재발급 요청");

        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Refresh Token이 없습니다.");
        }
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("만료되거나 유효하지 않은 Refresh Token입니다.");
        }

        Claims claims;
        try {
            claims = jwtTokenProvider.getClaims(refreshToken);
        } catch (JwtException e) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token입니다.");
        }

        String userId = claims.getSubject();
        String userNm = (String) claims.get("userNm");
        Object langObj = claims.get("lang");
        String lang = (langObj != null && !String.valueOf(langObj).trim().isEmpty())
                ? String.valueOf(langObj) : "KO";

        Map<String, Object> tokenCheck = new HashMap<>();
        tokenCheck.put("empId", userId);
        tokenCheck.put("tokenVal", refreshToken);

        if (loginMapper.selectRefreshToken(tokenCheck) == 0) {
            throw new IllegalArgumentException("만료되거나 유효하지 않은 Refresh Token입니다.");
        }

        String newAccessToken = jwtTokenProvider.createToken(userId, userNm, lang);

        Map<String, Object> updateDto = new HashMap<>();
        updateDto.put("empId", userId);
        updateDto.put("tokenVal", refreshToken);
        updateDto.put("expDt", buildRefreshExpDt());
        loginMapper.updateRefreshTokenExpiry(updateDto);

        log.info("Access Token 재발급 완료 - userId: {}", userId);

        return LoginResponseDto.builder()
                .token(newAccessToken)
                .userId(userId)
                .userNm(userNm)
                .lang(lang)
                .build();
    }

    private LocalDateTime buildRefreshExpDt() {
        long normalizedMs = refreshExpirationMs > 0 ? refreshExpirationMs : 28800000L;
        long seconds = Math.max(1L, normalizedMs / 1000L);
        return LocalDateTime.now().plusSeconds(seconds);
    }
}

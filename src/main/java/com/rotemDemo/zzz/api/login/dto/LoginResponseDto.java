package com.rotemDemo.zzz.api.login.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String token;           // Access Token (JWT, 1시간)
    private String refreshToken;    // Refresh Token (JWT, 2주)
    private String userId;          // emp_alias
    private String userNm;         // emp_nm
    private String lang;          // "ko"
    private String empId;         // emp_id
    private String positionNm;         // 직책
    private String deptNm;       // 부서명
    private String deptCd;       // 부서코드
    private String empDeptAuth;  // 부서권한
    
    private int result;   // "삭제여부"
    private int delResult;   // "삭제여부"
}
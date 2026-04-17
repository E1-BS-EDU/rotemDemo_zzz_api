package com.rotemDemo.zzz.api.login.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String empAlias;    // 아이디 (emp_alias 사용)
    private String empEmail;    // 이메일
    private String empPassword; // 비밀번호
    private String newEmpPassword; // 새로운 비밀번호
    private String langCd;      // 언어모델
}
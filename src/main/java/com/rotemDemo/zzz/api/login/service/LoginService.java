package com.rotemDemo.zzz.api.login.service;

import com.rotemDemo.zzz.api.login.dto.LoginRequestDto;
import com.rotemDemo.zzz.api.login.dto.LoginResponseDto;

public interface LoginService {

    LoginResponseDto login(LoginRequestDto requestDto);
    void logOut(String refreshToken);
    LoginResponseDto reissue(String refreshToken);

    LoginResponseDto pwReset(LoginRequestDto requestDto);
    LoginResponseDto pwChange(LoginRequestDto requestDto);
}

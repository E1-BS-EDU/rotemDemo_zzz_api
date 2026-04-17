package com.rotemDemo.zzz.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> {
    private boolean success;  // 성공 여부
    private String code;      // 응답 코드 (200, 401, 500 ...)
    private String message;   // 메시지
    private T data;           // 실제 데이터

    // 성공 시 정적 메서드
    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.<T>builder()
                .success(true)
                .code("200")
                .message("Success")
                .data(data)
                .build();
    }

    // 실패 시 정적 메서드
    public static <T> ApiResult<T> error(String code, String message) {
        return ApiResult.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .data(null)
                .build();
    }
    // 
    public static <T> ApiResult<T> info( boolean success, String code, String message) {
        return ApiResult.<T>builder()
                .success(success)
                .code(code)
                .message(message)
                .data(null)
                .build();
    }    
}
package com.popcorntalk.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto<T> {

    /**
     * status 응답 상태코드
     * msg 응답메시지
     * data 응답 데이터
     */
    private int status;
    private String msg;
    private T data;

    /**
     * 성공시 상태코드만 전달
     * @param status 상태코드
     * @return CommonResponseDto
     * @param <T> 응답 데이터 타입
     */
    public static <T> CommonResponseDto<T> success(int status){
        return new CommonResponseDto<>(status,null,null);
    }

    /**
     * 성공시 상태코드와 데이터를 전달
     * @param status 상태코드
     * @param data 데이터
     * @return CommonResponseDto
     * @param <T> 응답 데이터 타입
     */
    public static <T> CommonResponseDto<T> success(int status, T data ){
        return new CommonResponseDto<>(status,null,data);
    }

    /**
     * 실패시 상태코드와 메세지만 전달
     * @param status 상태코드
     * @param msg 메세지
     * @return CommonResponseDto
     */
    public static CommonResponseDto fail(int status, String msg){
        return new CommonResponseDto<>(status,msg,null);
    }

    /**
     *  실패시 상태코드와 데이터를 전달
     * @param status 상태코드
     * @param data 데이터
     * @return CommonResponseDto
     * @param <T> 응답 데이터 타입
     */
    public static <T> CommonResponseDto<T> fail(int status, T data){
        return new CommonResponseDto<>(status,null,data);
    }
}

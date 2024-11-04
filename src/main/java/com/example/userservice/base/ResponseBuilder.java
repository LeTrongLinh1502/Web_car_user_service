package com.example.userservice.base;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseBuilder {
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setCode(200);
        response.setMessage("Thành công!");
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> error(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setCode(500);
        response.setMessage("Thất bại!");
        response.setData(data);
        return response;
    }
    public static <T> BaseResponse<T> error(int code, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
    public static <T> BaseResponse<T> success(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

}

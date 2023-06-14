package com.gte.myjavadaggerhilt.domain;

import com.gte.myjavadaggerhilt.core.ApiEnum;

public class ApiResponse<T> {
    private T data;
    private ApiEnum status;
    private String message;
    public ApiResponse(T data, ApiEnum status, String message){
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public ApiEnum getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

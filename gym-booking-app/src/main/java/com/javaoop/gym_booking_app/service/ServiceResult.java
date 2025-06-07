package com.javaoop.gym_booking_app.service;

/**
 * 通用服務回傳物件。
 * @param <T> 真正的資料（DTO / Entity / List…）
 */
public class ServiceResult<T> {

    private boolean success;
    private String message;
    private T data;

    /* ---------- 建構子 ---------- */
    private ServiceResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /* ---------- 工具方法 ---------- */
    public static <T> ServiceResult<T> ok(T data) {
        return new ServiceResult<>(true, null, data);
    }

    public static <T> ServiceResult<T> fail(String message) {
        return new ServiceResult<>(false, message, null);
    }

    /* ---------- Getter ---------- */
    public boolean isSuccess()   { return success; }
    public String  getMessage()  { return message; }
    public T       getData()     { return data; }
}
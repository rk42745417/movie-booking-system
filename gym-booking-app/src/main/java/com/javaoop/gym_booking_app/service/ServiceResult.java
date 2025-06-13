package com.javaoop.gym_booking_app.service;

/**
 * A generic service result object.
 * @param <T> The type of the data.
 */
public class ServiceResult<T> {

    private boolean success;
    private String message;
    private T data;

    /**
     * Constructs a ServiceResult.
     * @param success Whether the operation was successful.
     * @param message A message about the operation.
     * @param data The data returned by the operation.
     */
    private ServiceResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Creates a successful ServiceResult.
     * @param data The data returned by the operation.
     * @param <T> The type of the data.
     * @return A successful ServiceResult.
     */
    public static <T> ServiceResult<T> ok(T data) {
        return new ServiceResult<>(true, null, data);
    }

    /**
     * Creates a failed ServiceResult.
     * @param message A message about the failure.
     * @param <T> The type of the data.
     * @return A failed ServiceResult.
     */
    public static <T> ServiceResult<T> fail(String message) {
        return new ServiceResult<>(false, message, null);
    }

    /**
     * Gets whether the operation was successful.
     * @return Whether the operation was successful.
     */
    public boolean isSuccess()   { return success; }

    /**
     * Gets the message about the operation.
     * @return The message about the operation.
     */
    public String  getMessage()  { return message; }

    /**
     * Gets the data returned by the operation.
     * @return The data returned by the operation.
     */
    public T       getData()     { return data; }

    /**
     * Creates a ServiceResult for a created resource.
     * @param data The data of the created resource.
     * @param <T> The type of the data.
     * @return A ServiceResult for a created resource.
     */
    public static <T> ServiceResult<T> created(T data) {
        return new ServiceResult<>(true, null, data);
    }

    /**
     * Creates a ServiceResult for an unauthorized operation.
     * @param message A message about the failure.
     * @param <T> The type of the data.
     * @return A ServiceResult for an unauthorized operation.
     */
    public static <T> ServiceResult<T> unauthorized(String message) {
        return new ServiceResult<>(false, message, null);
    }
}
package com.course.miniapp.response;

import lombok.Data;

/**
 * 统一返回值
 */
@Data
public class ResultData<T> {

    public static final String SUCCESS = "SUCCESS";

    /**
     * 结果状态
     */
    private int status;

    private String message;

    private T data;

    private long timestamp;

    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(200);
        resultData.setMessage(SUCCESS);
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }
}

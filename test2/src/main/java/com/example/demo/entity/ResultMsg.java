package com.example.demo.entity;

import com.example.demo.constant.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.slf4j.MDC;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@ToString
public class ResultMsg<T> implements Serializable {
    /**
     * json返回值
     *   {
     *      "code": 0,
     *      "flag": true,
     *      "message": "string",
     *      "data": [
     *          {
     *          "id": "string",
     *          "labelname": "string",
     *          "state": "string",
     *          "count": 0,
     *          "recommend": "string"
     *          }
     *       ]
     *   }
     * */
    @JsonProperty("编码")
    private String code;
    @JsonProperty("信息")
    private String message;
    @JsonProperty("状态")
    private String status;
    @JsonProperty("data")
    private T data;

    public ResultMsg(StatusCode statusCode1,T data)
    {
        this.code = statusCode1.getCode();
        this.status=statusCode1.getStatus();
        this.message = statusCode1.getMsg();
        this.data = data;
    }

    public ResultMsg(ResponseStatus responseStatus, T data) {
        this.status = responseStatus.getStatus();
        this.message = responseStatus.getMsg();
        this.code = responseStatus.getCode();
        this.data = data;
    }

    public ResultMsg(ResponseStatus responseStatus, String message, T data) {
        this.status = responseStatus.getStatus();
        this.code = responseStatus.getCode();
        this.message = message;
        this.data = data;
    }

    public ResultMsg(String status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
    public ResultMsg(String status, String message, String code, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public static ResultMsg<String> serverError(String message) {
        return new ResultMsg<>(ResponseStatus.INTERNAL_SERVER_ERROR, message, null);
    }

    public static ResultMsg<?> ok(Object data) {
        return new ResultMsg<>(StatusCode.SUCCESS, data);
    }

    public static ResultMsg<?> ok1(Object data) {
        return new ResultMsg<>(ResponseStatus.SUCCESS, data);
    }


}

package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    // 统一返回信息
    SUCCESS("true", "00000", "成功!", "一切正常"),
    USER_NAME_ALREADY_EXISTS("false", "A0111", "用户名已存在!", "用户名已存在"),
    REQUEST_METHOD_NOT_SUPPORT("false", "A0154", "访问方法不支持!", "接口不允许其他方式访问"),
    USER_AUTHENTICATION_EXCEPTION("false", "A0220", "用户名或密码有误!", "用户身份校验异常"),
    USER_AUTHENTICATION_EXPIRED("false", "A0230", "登录信息已经过期或已经退出登录，请重新登录", "TOKEN已过期"),
    CAPTCHA_FAILED("false", "A0240", "验证码错误", "用户验证码错误"),
    USER_ACCESS_UNAUTHORIZED("false", "A0301", "越权访问", "访问未被授权"),
    NO_ACCESS_TO_API("false", "A0312", "权限不足", "无权使用API"),
    USER_ACCESS_DENIED("false", "A0320", "拒绝访问", "未登录用户访问被拦截!"),
    SIGNATURE_NOT_MATCH("false", "A0340", "用户签名异常", "请求的数字签名不匹配"),
    REQUEST_PARAMETER_EXCEED_ALLOW("false", "A0420", "请求参数值超出允许的范围", "请求参数值超出允许的范围"),
    PARAMETER_NOT_MATCH("false", "A0421", "请求参数格式不符", "参数格式不匹配"),
    JSON_PARSE_FAILED("false", "A0427", "参数解析失败", "JSON解析失败"),
    RPC_SERVICE_ERROR("false", "C0110", "远端服务出错 ", "RPC服务出错"),
    SQL_SYNTAX_ERROR("false", "C0310", "语句无返回记录 ", "查询表不存在"),
    TABLE_NOT_EXISTS("false", "C0311", "表无返回记录 ", "查询表不存在"),
    COLUMN_NOT_EXISTS("false", "C0312", "字段无返回记录 ", "查询列不存在"),
    ROW_NOT_EXISTS("false", "C0313", "无返回记录 ", "对应行记录不存在"),
    NOT_FOUND("false", "404", "未找到该资源", ""),
    INTERNAL_SERVER_ERROR("false", "500", "服务器内部错误", ""),
    SERVER_BUSY("false", "503", "服务器正忙，请稍后再试", ""),

    ;

    /**
     * 错误码
     */
    private final String status;
    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String msg;

    /**
     * 错误描述
     */
    private final String description;
}

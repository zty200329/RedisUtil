package com.redis.enums;

import lombok.Getter;

/**
 * @author hobo
 * @description
 */
@Getter
public enum ResultEnum {
    PARAMETER_ERROR(100,"项目名，上传时间，项目类型，学号为必填项！"),
    PROJECT_EXIST(101,"项目存在"),
    PROJECT_NOT_EXISI(102,"项目不存在"),
    PRO_UPLOAD_ERROR(103,"文件上传出错"),
    PRO_UPLOAD_SUCCESS(104,"上传成功"),
    DELETE_ERROR(105,"文件删除有误！"),
    DELETE_SUCCESS(106,"文件删除有误！"),
    FILE_IS_NOT_EXIST(107,"文件不存在"),
    FILE_NOT_EXIST(108,"文件为空"),
    AUTHENTICATION_ERROR(401, "用户认证失败,请重新登录"),
    PERMISSION_DENNY(403, "权限不足"),
    NOT_FOUND(404, "url错误,请求路径未找到"),
    SERVER_ERROR(500, "服务器未知错误:%s"),
    BIND_ERROR(511, "参数校验错误:%s"),
    REQUEST_METHOD_ERROR(550, "不支持%s的请求方式"),
    USER_NOT_EXIST(1,"用户不存在" ), PASSWORD_ERROR(2,"密码错误" );

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

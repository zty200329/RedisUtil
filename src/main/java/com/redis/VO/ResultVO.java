package com.redis.VO;

import lombok.Data;

/**
 * @author: zty
 * @date 2019/9/19 下午1:59
 * @description:
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回具体内容
     */
    private T data;
}

package com.redis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ServiceLog
 * @Author hobo
 * @Date 19-4-22 下午8:38
 * @Description
 **/
@Aspect
@Slf4j
@Component
public class ServiceLog {

    private long startTime;
    @Pointcut("execution(public * com.redis.service.*.*(..))")
    public void service() {

    }


    @Before("service()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        this.startTime = System.currentTimeMillis();
        log.info("-----------------------------------------------------");
        log.info("当前执行的service方法:" + method);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("参数:" + arg);
        }
    }

    @AfterReturning(pointcut = "service()", returning = "ret")
    public void after(Object ret) {
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long endTime = System.currentTimeMillis();
        log.info(request.getMethod()+"方法执行了" +(endTime - this.startTime) + "ms");
        log.info("service返回参数:" + ret);
        log.info("-----------------------------------------------------");

    }


}

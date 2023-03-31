package com.wise.gemmes.processroute.util;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
    @Pointcut("within(com.wise.gemmes.processroute.util.UserServiceImpl)")
    public void excudeService() {
    }
    @Before("excudeService()")
    public void befor() {
        System.out.println("开启事务");
    }
    @After("excudeService()")
    public void after() {
        System.out.println("事务提交");
    }
}

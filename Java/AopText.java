package com.wise.gemmes.processroute.util;

import org.junit.Test;

public class AopText {
    @Test
    public void demo(){
        Long start = System.currentTimeMillis();
        UserService userService = MyBeanFactory.CreateUserService();
        userService.addUser();
        userService.deleteUser();
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}

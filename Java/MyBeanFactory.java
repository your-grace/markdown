package com.wise.gemmes.processroute.util;

import java.lang.reflect.Proxy;

//工厂类
public class MyBeanFactory {

    public static UserService CreateUserService() {
//		目标类
//		addUser() <--开辟了一个实现类的堆给他
        UserService userService = new UserServiceImpl();
//		切面类Aspect
        MyAspect myAspect = new MyAspect();
//      代理类：将目标类（切入点）和切面类（通知）结合-->切面
//		Proxy.newProxyInstance
//		参数1：loader,类加载器,动态代理类，运行时创建，如何类都需要类加载器将其加载到内存
//		一般情况：当前类.class.getClassLoader();
//				目标类实例.getClass().getClassLoader();
//		参数2：Class[] interfaces 代理类需要实现的所有接口
//			方式1：目标类实例.getClass.getInterfaces();注意：只能获得自己的接口，不能获得父类元素接口
//			方式2：new class[]{UserService.class}
//		参数3：InvocationHandler() 处理类，接口，必须进行实现类，一般采用匿名内部
//		提供invoke方法，代理类的每一个方法执行时，都将调用一次invoke
//			参数31：Object proxy:代理对象
//			参数32：Method method:代理对象当前执行的方法的描述对象（反射）
//			参数33:Object[] args:方法实际参数
//        UserService proxySvice=(UserService) Proxy.newProxyInstance(
//                MyBeanFactory.class.getClassLoader(),
//                userService.getClass().getInterfaces(),
//                new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        myAspect.befor();//事务开启
//                        Object obj=method.invoke(userService, args);
//                        myAspect.after();//事务提交
//                        return obj;
//                    }
//                });
        UserService proxySvice = (UserService) Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader(),
                userService.getClass().getInterfaces(), (proxy, method, args) -> {
                    myAspect.befor();
                    Object object = method.invoke(userService, args);
                    myAspect.after();
                    return object;
                });
        return proxySvice;
    }
}


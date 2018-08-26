package org.litespring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring.aop.aspectj.AspectJBeforeAdvice;
import org.litespring.aop.framework.ReflectiveMethodInvocation;
import org.litespring.service.v5.PetStoreService;
import org.litespring.tx.TransactionManager;
import org.litespring.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveMethodInvocationTest {

    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterAdvice = null;
    //private AspectJAfterThrowingAdvice  afterThrowingAdvice = null;
    private PetStoreService petStoreService = null;
    private TransactionManager tx;
    @Before
    public void setUp() throws Exception {
        petStoreService = new PetStoreService();
        tx = new TransactionManager();
        MessageTracker.clearMsgs();
        /**
         * 創建需要 3個參數
         * 反射 将字符串变为对象
         *
         */
        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),
                null,
                tx);
        afterAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),
                null,
                tx);

    }

    @Test
    public void testMethodInvocation() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
        List<MethodInterceptor> methodInterceptors = new ArrayList<MethodInterceptor>();
        methodInterceptors.add(beforeAdvice);
        methodInterceptors.add(afterAdvice);

        ReflectiveMethodInvocation methodInvocation = new ReflectiveMethodInvocation(petStoreService,targetMethod,new Object[0],methodInterceptors);
        //MethodInvocation 定义
        //存在递归
        methodInvocation.proceed();

        List<String> msgs = MessageTracker.getMsgs();

        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }

    @Test
    public void testMethodInvocation2() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
        List<MethodInterceptor> methodInterceptors = new ArrayList<MethodInterceptor>();
        methodInterceptors.add(afterAdvice);
        methodInterceptors.add(beforeAdvice);

        ReflectiveMethodInvocation methodInvocation = new ReflectiveMethodInvocation(petStoreService,targetMethod,new Object[0],methodInterceptors);
        //MethodInvocation 定义
        //存在递归
        methodInvocation.proceed();

        List<String> msgs = MessageTracker.getMsgs();

        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }
}

package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice implements Advice{
    private Method adviceMethod;
    private Pointcut pc;
    private Object adviceObject;

    public AspectJBeforeAdvice(Method adviceMethod, Pointcut pc, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pc = pc;
        this.adviceObject = adviceObject;
    }

    public Pointcut getPointcut() {
        return this.pc;
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //调用TransactionManager 的 start 方法
        adviceMethod.invoke(adviceObject);
        Object o = methodInvocation.proceed();
        return o;
    }
}

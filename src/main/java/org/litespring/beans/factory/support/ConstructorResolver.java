package org.litespring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeMissmatchException;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ConstructorResolver {
    protected final Log logger =  LogFactory.getLog(getClass());

    private final ConfigurableBeanFactory configurableBeanFactory;

    /**
     * 构造函数 传入一个beanFactory
     * @param beanFactory
     */
    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.configurableBeanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition bd) {
        Constructor<?> constructorToUse = null;

        Object[] argsToUse = null;

        Class<?> beanClass = null;
        try {
            //构建字节码类
            beanClass = this.configurableBeanFactory.getBeanClassLoader().loadClass(bd.getBeanName());//?
        } catch (ClassNotFoundException e) {
            throw  new BeanCreationException(bd.getID(),"Instantiation of bean failed, can't resolve class",e);
        }
        Constructor<?>[] candidates = beanClass.getConstructors();//返回所有的构造方法
        //构建属性解析类
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.configurableBeanFactory);
        //构建构造属性类
        ConstructorArgument constructorArgument = bd.getConstructorArgument();
        //类型转化类
        SimpleTypeConverter converter = new SimpleTypeConverter();
        int length = candidates.length;
        for (int i = 0;i<length;i++){
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();//获取构造函数中的参数
            int paramsLength = parameterTypes.length;
            if(paramsLength!=constructorArgument.getArgumentCount()){
                continue;
            }
            argsToUse = new Object[paramsLength];
            boolean result = this.valuesMatchTypes(parameterTypes,constructorArgument.getArgumentValues(),argsToUse,valueResolver,converter);
            if (result){
                //满足判断进行 构造函数赋值
                constructorToUse = candidates[i];
            }
        }
        //如果找不到一个合适的构造函数
        if(constructorToUse==null){
            throw new BeanCreationException(bd.getID(), "can't find a apporiate constructor");
        }
        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException( bd.getID(), "can't find a create instance using "+constructorToUse);
        }
    }

    /**
     * 判断 构造方法是否与配置文件内容匹配
     * @param parameterTypes
     * @param
     * @param argsToUse
     * @param valueResolver
     * @param
     * @return
     */
    private boolean valuesMatchTypes(Class<?>[] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter ) {


        for (int i = 0,length = parameterTypes.length;i<length;i++){
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            //获取的参数可能是TypeStringValue 也可能是RuntimeBeanReference
            Object originalValue  = valueHolder.getValue();
            try {
                //获取正真的值
                Object resolvedValue  = valueResolver.resolveValueIfNecessary(originalValue );
                //如果参数类型是 int "3" 需要进行转换
                //如果类型转换失败,则抛出异常... 说明这个构造函数不可以用
                Object convertedValue  = typeConverter.convertIfNecessaray(resolvedValue, parameterTypes[i]);
                //转型成功 记录下来
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }


        return true;
    }

}

package com.qlqn.common.dataSwitch;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源切换注解
 * @classname DataSourceChange
 * @version
 */
//自定义注解相关设置
@Target({ElementType.METHOD})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented 
public @interface DataSourceChange {

	//自定义注解的属性，default是设置默认值
    String value() default " ";   
}


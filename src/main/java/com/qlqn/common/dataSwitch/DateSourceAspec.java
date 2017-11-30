package com.qlqn.common.dataSwitch;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 数据源切面
 * @classname DateSourceAspec
 * @version
 */
@Component
@Aspect
@Order(0)
public class DateSourceAspec {

	 //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* com.ccx..service..*.*(..))")
    public void aspect(){
    }
    
    /**
     *  根据注解切换数据源.
     * @param method
     * @param arg1
     * @param arg2
     */
    
    @After("@annotation(ds)")
    public void after(DataSourceChange ds){
        	DataSourceSwitch.setDatasource("dtsDB");
    }
    
    @Before("@annotation(ds)")
    public void around(DataSourceChange ds)
    		throws Throwable {
    	final String datasource = ds.value();
    	if ("wxCreditDB".equals(datasource)){
    		DataSourceSwitch.setDatasource("wxCreditDB");
    	}else{
    		DataSourceSwitch.setDatasource("dtsDB");
    	}
    	
    }
}

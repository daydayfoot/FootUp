/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.qlqn.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Http与Servlet工具类.
 */
public class ServletsUtils {

	
	// 静态文件后缀
	
	private final static String staticFile=".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.htm,.html,.crx,.xpi,.exe,.ipa,.apk";
	
	private final static String[] staticFiles = StringUtils.split(staticFile, ",");
	
	// 动态映射URL后缀
	private final static String urlSuffix = ".html" ;

	/**
	 * 获取当前请求对象
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		try{
			return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}catch(Exception e){
			return null;
		}
	}
	/**
     * 判断访问URI是否是静态文件请求
	 * @throws Exception 
     */
    public static boolean isStaticFile(String uri){
		if (Utils.isNullString(uri) || uri.indexOf("resources")!=-1 ){
			return true;
		}
		return false;
    }
}

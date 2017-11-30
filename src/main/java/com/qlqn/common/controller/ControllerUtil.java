package com.qlqn.common.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import com.qlqn.bean.SysUserBean;
import com.qlqn.utils.Utils;



/**
 * 获取用户信息和请求参数
 * @classname ControllerUtil
 * @version
 */
public class ControllerUtil {

	/**
	 * 获取登陆用户
	 * @return
	 */
	public static SysUserBean getSessionUser(){
		SysUserBean operator = (SysUserBean)SecurityUtils.getSubject().getPrincipal();
    	return operator;
	}
	
	/**
	 * 获取请求参数
	 * @param request
	 * @author lilin
	 * @date 2016年11月9日19:12:21
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static  Map<String,Object> requestMap(HttpServletRequest request){
		Enumeration emu = request.getParameterNames();
		Map<String,Object> requestMap = new HashMap<String,Object>();
		if(!Utils.isNullString(emu)){
			while(emu.hasMoreElements()){
				String name = (String)emu.nextElement();
				String value = request.getParameter(name);
				if(!Utils.isNullString(value)){
					requestMap.put(name, value.trim());
				}			
			}
		}
		return requestMap;
	}
	
	
}

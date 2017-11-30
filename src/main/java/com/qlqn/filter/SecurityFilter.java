package com.qlqn.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlqn.utils.Utils;


/**
 * 过滤所有请求
 * 判断当前用户是否有效经过一下步骤.
 * @classname SecurityFilter
 * @author lilin
 * @date  2016年12月28日13:42:29
 * @version
 */
public class SecurityFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	/**
	 * 免登陆接口白名单
	 */
	private String apply = "";
	/**
	 * 免登陆的白名单
	 */
	private String whiteList = "";
	/**
	 * 默认页面
	 */
	private String loginPage = "/login";
	
	
	
	public SecurityFilter() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.apply = fConfig.getInitParameter("apply");
		this.whiteList = fConfig.getInitParameter("whiteList");
		this.loginPage = fConfig.getInitParameter("loginPage");
		
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("utf-8");
		
		//获取工程名称
		String path = req.getContextPath();
		//获取工程基础路径
		String basePath = req.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		//获取工程名之后路径
		String servlet = req.getServletPath();
		
		if (!Utils.isNullString(servlet)) {
			servlet = servlet.substring(1);
		}
		
		boolean doFilter = true;
		if(servlet.contains("resources")) {
			doFilter = false ;
		}
		
		// 验证是否需要登录
//		if (doFilter && !servlet.contains(apply) && (this.whiteList.indexOf(servlet) == -1 )) {
//			//需要登录
//			String gopage = loginPage;
//			logger.debug("重定向URL:" + basePath + gopage);
//			res.sendRedirect(basePath + gopage);
//			return;
//		} 
		
		chain.doFilter(  request , response);
	}
	 

}

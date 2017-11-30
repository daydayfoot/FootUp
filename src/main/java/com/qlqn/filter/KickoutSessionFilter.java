package com.qlqn.filter;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlqn.bean.SysUserBean;
import com.qlqn.shiro.ShiroSessionRepository;
import com.qlqn.utils.RedisCacheUtil;

/**
 * 在线用户  踢出状态
 * 
 */
@SuppressWarnings({"unchecked","static-access"})
public class KickoutSessionFilter extends AccessControlFilter {
	private static final Logger logger = LoggerFactory.getLogger(KickoutSessionFilter.class);
	//静态注入
	private static String kickoutUrl;
	//在线用户
	private final static String ONLINE_USER ="_ccx_antifarud_web_online_user";
	//踢出状态，true标示踢出
	private final static String KICKOUT_STATUS ="_ccx_antifarud_web_kickout_status";
	//session 存活时间
	private final static int STATUS_TIME =1800;
	//session获取
	private static ShiroSessionRepository shiroSessionRepository;
	
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		Subject subject = getSubject(request, response);
		//如果没有登录 就直接return true
		if(!subject.isAuthenticated() && !subject.isRemembered()){
			return Boolean.TRUE;
		}
		Session session = subject.getSession();
		Serializable sessionId = session.getId();
		/**
		 * 判断是否已经踢出
		 * 直接跳转到登录页
		 */
		Session oldSession33 = shiroSessionRepository.getSession(sessionId);
		if (null ==oldSession33) {
			return  Boolean.FALSE;
		}
		Boolean marker =(Boolean) oldSession33.getAttribute(KICKOUT_STATUS);
		if (null != marker && marker) {
			return  Boolean.FALSE;
		}
		//从缓存获取用户-Session信息 <UserId,SessionId>
		LinkedHashMap<Long, Serializable> infoMap = RedisCacheUtil.get(ONLINE_USER, LinkedHashMap.class);
		//如果不存在，创建一个新的
		infoMap = null == infoMap ? new LinkedHashMap<Long, Serializable>() : infoMap;
		//获取tokenId
		SysUserBean token = (SysUserBean)SecurityUtils.getSubject().getPrincipal();
		Long userId=token.getId();
		/**
		 * 如果用户Id不相同,Session不相同 新用户登录
		 */
		if(!infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)){
			infoMap.put(userId, sessionId);
			//存储到缓存半个小时（这个时间最好和session的有效期一致或者大于session的有效期）
			RedisCacheUtil.setex(ONLINE_USER, infoMap, STATUS_TIME);
			return Boolean.TRUE;
		}
		//如果已经包含当前Session，并且是同一个用户，跳过。
		if(infoMap.containsKey(userId) && infoMap.containsValue(sessionId)){
			//更新存储到缓存半个小时（这个时间最好和session的有效期一致或者大于session的有效期）
			RedisCacheUtil.setex(ONLINE_USER, infoMap, STATUS_TIME);
			return Boolean.TRUE;
		}
		/**
		 * 如果用户Id相同,Session不相同
		 * 1.获取到原来的session，并且标记为踢出。
		 */
		if(infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)){
			Serializable oldSessionId = infoMap.get(userId);
			Session oldSession = shiroSessionRepository.getSession(oldSessionId);
			if(null != oldSession){
				//标记session已经踢出
				oldSession.setAttribute(KICKOUT_STATUS, Boolean.TRUE);
				shiroSessionRepository.saveSession(oldSession);//更新session
				logger.debug("kickout old session success,oldId[%s]",oldSessionId);
			}else{
				shiroSessionRepository.deleteSession(oldSessionId);
				infoMap.remove(userId);
				//存储到缓存半个小时（这个时间最好和session的有效期一致或者大于session的有效期）
				RedisCacheUtil.setex(ONLINE_USER, infoMap, STATUS_TIME);
			}
			return  Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		
		//先退出
		Subject subject = getSubject(request, response);
		subject.logout();
		WebUtils.getSavedRequest(request);
		//再重定向
		WebUtils.issueRedirect(request, response,kickoutUrl);
		return false;
	}


	public static void setShiroSessionRepository(
			ShiroSessionRepository shiroSessionRepository) {
		KickoutSessionFilter.shiroSessionRepository = shiroSessionRepository;
	}


	public static String getKickoutUrl() {
		return kickoutUrl;
	}

	public static void setKickoutUrl(String kickoutUrl) {
		KickoutSessionFilter.kickoutUrl = kickoutUrl;
	}
}

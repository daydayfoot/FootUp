package com.qlqn.shiro;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.qlqn.bean.SysUserBean;
import com.qlqn.utils.StringUtil;



public class CustomSessionManager {

	/**
	 * session status 
	 */
	public static final String SESSION_STATUS ="wx:ccx_antifraud_web:status";
	@Autowired
	ShiroSessionRepository shiroSessionRepository;
	
	@Autowired
	CustomShiroSessionDAO customShiroSessionDAO;
	
	/**
	 * 根据ID查询 SimplePrincipalCollection
	 * @param userIds	用户ID
	 * @return session_loginuser_name
	 */
	@SuppressWarnings("unchecked")
	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Long...userIds){
		//把userIds 转成Set，好判断
		Set<Long> idset = (Set<Long>) StringUtil.array2Set(userIds);
		//获取所有session
		Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
		//定义返回
		List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
		for (Session session : sessions) {
			//获取SimplePrincipalCollection
			Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(null != obj && obj instanceof SimplePrincipalCollection){
				//强转
				SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
				//判断用户，匹配用户ID。
				obj = spc.getPrimaryPrincipal();
				if(null != obj && obj instanceof SysUserBean){
					SysUserBean user = (SysUserBean)obj;
					//比较用户ID，符合即加入集合
					if(null != user && idset.contains(user.getId())){
						list.add(spc);
					}
				}
			}
		}
		return list;
	}
	
	
	public void setShiroSessionRepository(
			ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}

	public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
		this.customShiroSessionDAO = customShiroSessionDAO;
	}
}

/**
 */
package com.qlqn.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;
import com.qlqn.utils.ServletsUtils;
import com.qlqn.utils.Utils;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-24
 */
public class CacheSessionDAO extends EnterpriseCacheSessionDAO implements SessionDAO {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    public CacheSessionDAO() {
        super();
    }
    
    @Override
    protected void doUpdate(Session session) {
    	if (session == null || session.getId() == null) {  
            return;
        }
    	
    	HttpServletRequest request = ServletsUtils.getRequest();
		if (request != null){
			String uri = request.getServletPath();
			// 如果是静态文件，则不更新SESSION
			if (Utils.isNullString(uri) || uri.indexOf("resources")!=-1) {
				return;
			}
			// 如果是视图文件，则不更新SESSION
			if (StringUtils.startsWith(uri, "/WEB-INF/views/") && StringUtils.endsWith(uri, ".jsp")) {
				return;
			}
		}
		super.doUpdate(session);
    	logger.debug("update {} {}", session.getId(), request != null ? request.getRequestURI() : "");
    }

    @Override
    protected void doDelete(Session session) {
    	if (session == null || session.getId() == null) {  
            return;
        }
	 	super.doDelete(session);
    	logger.debug("delete {} ", session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
		HttpServletRequest request = ServletsUtils.getRequest();
		if (request != null){
			String uri = request.getServletPath();
			// 如果是静态文件，则不创建SESSION
			if (Utils.isNullString(uri) || uri.indexOf("resources")!=-1) {
				return null;
			}
		}
		super.doCreate(session);
		logger.debug("doCreate {} {}", session, request != null ? request.getRequestURI() : "");
    	return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
		return super.doReadSession(sessionId);
    }
    
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
    	try{
    		Session s = null;
    		HttpServletRequest request = ServletsUtils.getRequest();
    		if (request != null){
    			String uri = request.getServletPath();
    			// 如果是静态文件，则不获取SESSION
    			if (Utils.isNullString(uri) || uri.indexOf("resources")!=-1) {
    				return null;
    			}
    			s = (Session)request.getAttribute("session_"+sessionId);
    		}
    		if (s != null){
    			return s;
    		}

    		Session session = super.readSession(sessionId);
    		logger.debug("readSession {} {}", sessionId, request != null ? request.getRequestURI() : "");
    		
    		if (request != null && session != null){
    			request.setAttribute("session_"+sessionId, session);
    		}
    		
    		return session;
    	}catch (UnknownSessionException e) {
			return null;
		}
    }
	
}

package com.qlqn.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qlqn.utils.ServletsUtils;


public class CustomShiroSessionDAO extends AbstractSessionDAO {
	private static final Logger logger = LoggerFactory.getLogger(CustomShiroSessionDAO.class);
	private ShiroSessionRepository shiroSessionRepository;

	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		HttpServletRequest request = ServletsUtils.getRequest();
		if (request != null) {
			String uri = request.getServletPath();
			// 如果是静态文件，则不更新SESSION
			if (ServletsUtils.isStaticFile(uri)) {
				return;
			}
			// 如果是视图文件，则不更新SESSION
			if (StringUtils.startsWith(uri, "/WEB-INF/views/") && StringUtils.endsWith(uri, ".jsp")) {
				return;
			}
		}
		shiroSessionRepository.saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if (session == null) {
			System.out.println();
			logger.info("Session 不能为null");
			return;
		}
		Serializable id = session.getId();
		if (id != null)
			shiroSessionRepository.deleteSession(id);
		logger.debug("会话sessionId----delete：" + session.getId());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return shiroSessionRepository.getAllSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {

		 HttpServletRequest request = ServletsUtils.getRequest();
		 if (request != null){
			 String uri = request.getServletPath();
			 // 如果是静态文件，则不创建SESSION
		 if (ServletsUtils.isStaticFile(uri)){
			 return null;
		 	}
		 }
		
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		shiroSessionRepository.saveSession(session);
		return sessionId;
	}

	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		Session s = doReadSession(sessionId);
		if (s == null) {
			logger.error("There is no session with id [" + sessionId + "]");
		}
		return s;
	}

	@Override
	public Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			logger.error("session id is null");
			return null;
		}
		Session session = null;
		HttpServletRequest request = ServletsUtils.getRequest();
		if (request != null) {
			String uri = request.getServletPath();
			// 如果是静态文件，则不获取SESSION
			session = (Session) request.getAttribute("session_" + sessionId);
			if (ServletsUtils.isStaticFile(uri)) {
				return null;
			}
		}
		session = shiroSessionRepository.getSession(sessionId);
		if (request != null && session != null) {
			request.setAttribute("session_" + sessionId, session);
		}
		return session;
	}
}

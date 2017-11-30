package com.qlqn.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CacheCustomSessionListener implements SessionListener {
	private static final Logger logger = LoggerFactory.getLogger(CacheCustomSessionListener.class);
	@Autowired  
	private CacheSessionDAO cacheSessionDAO;
    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        //TODO
//        System.out.println("on start");
    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
 		logger.debug("退出会话：" + session.getId());  
    }

    @Override
    public void onExpiration(Session session) {
 		cacheSessionDAO.delete(session);
        logger.debug("会话过期：" + session.getId());   
    }
}


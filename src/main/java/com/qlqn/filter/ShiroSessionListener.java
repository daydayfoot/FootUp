package com.qlqn.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroSessionListener implements SessionListener {
	private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);
	@Override  
    public void onStart(Session session) {//会话创建触发 已进入shiro的过滤连就触发这个方法  
        // TODO Auto-generated method stub  
        System.out.println("会话创建：" + session.getId());  
    }  
  
    @Override  
    public void onStop(Session session) {//退出  
    	logger.debug("退出会话：" + session.getId());  
    }  
  
    @Override  
    public void onExpiration(Session session) {//会话过期时触发  
 		logger.debug("会话过期：" + session.getId());   
    }  
    
}

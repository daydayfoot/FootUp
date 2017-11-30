package com.qlqn.shiro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSessionOverride implements ValidatingSession, Serializable{
	 private static final long serialVersionUID = -7125642695178165650L;


	    private transient static final Logger log = LoggerFactory.getLogger(SimpleSessionOverride.class);


	    /**
	    由于重写writerObject和readObject，默认首先调用defaultWriteObject和defaultReadObject，
	    为了避免序列化两次。所以以下字段都标识为了transient

	    **/
	    //sessonID，用于保持客户端浏览器和服务端Session存储容器之间的标识
	    private transient Serializable id;
	    //session创建时间
	    private transient Date startTimestamp;
	    //session停止时间，如果不为null，则代表已停止
	    private transient Date stopTimestamp;
	    //session的最后访问时间
	    private transient Date lastAccessTime;
	    //session的过期时间
	    private transient long timeout;
	    //session是否过期
	    private transient boolean expired;
	    //客户端IP或者host name
	    private transient String host;

	    private transient Map<Object, Object> attributes;
	    //当session第一次创建时候，初始化session创建时间和session最后访问时间和session过期时间
	    public SimpleSessionOverride() {
	        this.timeout = DefaultSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT; //TODO - remove concrete reference to DefaultSessionManager
	        this.startTimestamp = new Date();
	        this.lastAccessTime = this.startTimestamp;
	    }

	    public SimpleSessionOverride(String host) {
	        this();
	        this.host = host;
	    }

	    public Serializable getId() {
	        return this.id;
	    }

	    public void setId(Serializable id) {
	        this.id = id;
	    }

	    public Date getStartTimestamp() {
	        return startTimestamp;
	    }

	    public void setStartTimestamp(Date startTimestamp) {
	        this.startTimestamp = startTimestamp;
	    }


	    public Date getStopTimestamp() {
	        return stopTimestamp;
	    }

	    public void setStopTimestamp(Date stopTimestamp) {
	        this.stopTimestamp = stopTimestamp;
	    }

	    public Date getLastAccessTime() {
	        return lastAccessTime;
	    }

	    public void setLastAccessTime(Date lastAccessTime) {
	        this.lastAccessTime = lastAccessTime;
	    }

	    public boolean isExpired() {
	        return expired;
	    }

	    public void setExpired(boolean expired) {
	        this.expired = expired;
	    }

	    public long getTimeout() {
	        return timeout;
	    }

	    public void setTimeout(long timeout) {
	        this.timeout = timeout;
	    }

	    public String getHost() {
	        return host;
	    }

	    public void setHost(String host) {
	        this.host = host;
	    }

	    public Map<Object, Object> getAttributes() {
	        return attributes;
	    }

	    public void setAttributes(Map<Object, Object> attributes) {
	        this.attributes = attributes;
	    }

	    public void touch() {
	        this.lastAccessTime = new Date();
	    }

	    public void stop() {
	        if (this.stopTimestamp == null) {
	            this.stopTimestamp = new Date();
	        }
	    }

	    protected boolean isStopped() {
	        return getStopTimestamp() != null;
	    }

	    protected void expire() {
	        stop();
	        this.expired = true;
	    }

	    //判断是否停止和过期
	    public boolean isValid() {
	        return !isStopped() && !isExpired();
	    }

	    //判断是否过期具体验证
	    protected boolean isTimedOut() {
	        //如果之前已设置为过期，则true
	        if (isExpired()) {
	            return true;
	        }

	        long timeout = getTimeout();
	        /**
	    如果session有设置过期时间且大于0，则认为它的存活时间是有限制的，否则无限制时间.但是由于shiro
	    默认设置sessionID到cookie中，该cookie是没有设置存活时间的，则浏览器关闭则消失，再重新打开浏览器时，
	    即使服务端session存在且有效，还是找不到该session。会重新分配一个新的session。

	    如果需要保持浏览器和服务端session始终对应，不管浏览器关闭与否。可以设置存储sessionID的cookie为永远存活。
	    需要注意的是：
	    1.浏览器直接清空cookie，这样就找不到服务端session了，且服务端session永远停留在存储容器中


	    **/
	        if (timeout >= 0l) {

	            Date lastAccessTime = getLastAccessTime();

	            if (lastAccessTime == null) {
//	                String msg = "session.lastAccessTime for session with id [" +
//	                        getId() + "] is null.  This value must be set at " +
//	                        "least once, preferably at least upon instantiation.  Please check the " +
//	                        getClass().getName() + " implementation and ensure " +
//	                        "this value will be set (perhaps in the constructor?)";
//	                throw new IllegalStateException(msg);
	            }

	            //session过期时间规则判断。  是否过期 = 最后次访问时间 < (当前时间-存活时间限制)
	            long expireTimeMillis = System.currentTimeMillis() - timeout;
	            Date expireTime = new Date(expireTimeMillis);
	            return lastAccessTime.before(expireTime);
	        } else {
	            if (log.isTraceEnabled()) {
	                log.trace("No timeout for session with id [" + getId() +
	                        "].  Session is not considered expired.");
	            }
	        }

	        return false;
	    }

	    public void validate() throws InvalidSessionException {
	        //验证该session是否停止
	        if (isStopped()) {
	            //timestamp is set, so the session is considered stopped:
//	            String msg = "Session with id [" + getId() + "] has been " +
//	                    "explicitly stopped.  No further interaction under this session is " +
//	                    "allowed.";
//	            throw new StoppedSessionException(msg);
	        }

	        //验证是否过期
	        if (isTimedOut()) {
	            expire();
	        }
	    }

	    private Map<Object, Object> getAttributesLazy() {
	        Map<Object, Object> attributes = getAttributes();
	        if (attributes == null) {
	            attributes = new HashMap<Object, Object>();
	            setAttributes(attributes);
	        }
	        return attributes;
	    }

	    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
	        Map<Object, Object> attributes = getAttributes();
	        if (attributes == null) {
	            return Collections.emptySet();
	        }
	        return attributes.keySet();
	    }

	    public Object getAttribute(Object key) {
	        Map<Object, Object> attributes = getAttributes();
	        if (attributes == null) {
	            return null;
	        }
	        return attributes.get(key);
	    }

	    public void setAttribute(Object key, Object value) {
	        if (value == null) {
	            removeAttribute(key);
	        } else {
	            getAttributesLazy().put(key, value);
	        }
	    }

	    public Object removeAttribute(Object key) {
	        Map<Object, Object> attributes = getAttributes();
	        if (attributes == null) {
	            return null;
	        } else {
	            return attributes.remove(key);
	        }
	    }

	    //比较session。如果引用相同则true，如果session属于SimpleSession且sessionID相同则true，否则，对比各项字段是否相等
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj instanceof SimpleSession) {
	            SimpleSession other = (SimpleSession) obj;
	            Serializable thisId = getId();
	            Serializable otherId = other.getId();
	            if (thisId != null && otherId != null) {
	                return thisId.equals(otherId);
	            } else {
	                //fall back to an attribute based comparison:
	                return onEquals(other);
	            }
	        }
	        return false;
	    }


	    protected boolean onEquals(SimpleSession ss) {
	        return (getStartTimestamp() != null ? getStartTimestamp().equals(ss.getStartTimestamp()) : ss.getStartTimestamp() == null) &&
	                (getStopTimestamp() != null ? getStopTimestamp().equals(ss.getStopTimestamp()) : ss.getStopTimestamp() == null) &&
	                (getLastAccessTime() != null ? getLastAccessTime().equals(ss.getLastAccessTime()) : ss.getLastAccessTime() == null) &&
	                (getTimeout() == ss.getTimeout()) &&
	                (isExpired() == ss.isExpired()) &&
	                (getHost() != null ? getHost().equals(ss.getHost()) : ss.getHost() == null) &&
	                (getAttributes() != null ? getAttributes().equals(ss.getAttributes()) : ss.getAttributes() == null);
	    }


	    //调用该方法实现序列化

}

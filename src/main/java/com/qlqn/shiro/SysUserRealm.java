package com.qlqn.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qlqn.bean.SysUserBean;

public class SysUserRealm extends AuthorizingRealm{
	private static final Logger logger = LoggerFactory.getLogger(SysUserRealm.class);
//	 @Autowired  
//	 private UserService userService;  
//	 @Autowired
//	 PermissionService permissionService;
//	 @Autowired
//	 RoleService roleService;
//	 @Autowired
//	 SysInstitutionService sysInstitutionService;
	 
	    /** 
	     * 授权操作 
	     */  
	    @Override  
	    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
	        Long userId = TokenManager.getUserId();
			SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
//			//根据用户ID查询角色（role），放入到Authorization里。
//			Set<String> roles = roleService.findRoleByUserId(userId);
//			info.setRoles(roles);
//			//根据用户ID查询权限（permission），放入到Authorization里。
//			Set<String> permissions = permissionService.findPermissionByUserId(userId);
//			info.setStringPermissions(permissions);
////			System.out.println("++++++++++++++++++++++++");
////			System.out.println(JSONObject.fromObject(info));
////			System.out.println("++++++++++++++++++++++++");
	        return info; 
	    }  
	  
	    /** 
	     * 身份验证操作 
	     */  
	    @Override  
	    protected AuthenticationInfo doGetAuthenticationInfo(  
	            AuthenticationToken authcToken) throws AuthenticationException {  
	    	ShiroToken token = (ShiroToken) authcToken;
//	        SysUserBean user = userService.login(token.getUsername(),token.getPswd()); 
	        SysUserBean user = null;  
	        if(user==null){  
	            //木有找到用户  
	            throw new UnknownAccountException("用户账号"+token.getUsername()+"没有找到该账号");  
	        }
	        /** 
	         * 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，在此判断或自定义实现   
	         */  
	        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());  
	          
	          
	        return info;  
	    }  
	      
	    @Override  
	    public String getName() {  
	        return getClass().getName();  
	    }  
      
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }  
    /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(
//				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principalCollection);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}

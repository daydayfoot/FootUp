package com.qlqn.sys.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.qlqn.bean.InstitutionBean;
import com.qlqn.bean.SysPermissionBean;
import com.qlqn.bean.SysUserBean;
import com.qlqn.common.enums.EnumSysManage;
import com.qlqn.shiro.CacheManagerSession;
import com.qlqn.shiro.CacheSessionDAO;
import com.qlqn.shiro.ShiroToken;
import com.qlqn.sys.service.MobileCodeService;
import com.qlqn.sys.service.SysInstitutionService;
import com.qlqn.sys.service.UserService;
import com.qlqn.utils.MD5;
import com.qlqn.utils.StringUtil;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger("USER");
	@Autowired  
	private UserService userService; 
	@Autowired  
	private SysInstitutionService sysInstitutionService;  
	@Autowired  
	private MobileCodeService mobileCodeService;
	@Autowired  
	private CacheManagerSession<String, Serializable> cacheManagerSession;
	@Autowired  
	private CacheSessionDAO cacheSessionDAO;
    /** 
     * 账号登录 
     * @throws Exception 
     */ 
	@ResponseBody
    @RequestMapping(value="/loginSubmit", method=RequestMethod.POST)  
    public Map<String, String> loginSubmit(HttpServletRequest request) throws Exception{  
        String uname = request.getParameter("username").trim();  
        String pwd = request.getParameter("password").trim();
        String imgCode = request.getParameter("imgCode").trim(); 
        Map<String, String> map=new HashMap<String, String>();
    	SysUserBean  sysUserBean= userService.selectByNameOrMobile(uname);
    	if (null ==sysUserBean||  null ==sysUserBean.getAccount()) {
    		map.put("code", EnumSysManage.FAILE.getCode());
    		map.put("msg", "账号不存在");
		}
		if(null != sysUserBean && null != sysUserBean.getAccount()){
			// 万象风云  0:有效，1:冻结 2：注销   3:锁定
			String  status=String.valueOf(sysUserBean.getStatus()) ;
			//	status  2:锁定
				if ("1".equals(status)) {
					map.put("code", EnumSysManage.ACCOUNTFORZEN.getCode());
					map.put("msg", EnumSysManage.ACCOUNTFORZEN.getName()); 
				}else if("2".equals(status)){
					map.put("code",EnumSysManage.ACCOUNTCANCEL.getCode());
					map.put("msg", EnumSysManage.ACCOUNTCANCEL.getName()); 
				}else if ("3".equals(status)){
					map.put("code", EnumSysManage.ACCOUNTLOCK.getCode());
					map.put("msg", EnumSysManage.ACCOUNTLOCK.getName()); 
				}else{
					pwd=MD5.encryption(pwd);
					ShiroToken token = new ShiroToken(uname,pwd);
	//				token.setRememberMe(true);  
					token.getPrincipal();
					Subject subject = SecurityUtils.getSubject(); 
					try {
						//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
						//每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
						//所以这一步在调用login(token)方法时,它会走到sysUserRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
						logger.debug("对账号[" + uname + "]进行登录验证..验证开始");  
						subject.login(token);  
						InstitutionBean sysInstitutionBean=sysInstitutionService.selectByPrimaryKey(sysUserBean.getInstiutionId());
						if(null !=sysInstitutionBean ){
							String insStatus=sysInstitutionBean.getStatus();
							if("0".equals(insStatus)){
								// 查询账号对应权限
								List<SysPermissionBean> list = userService.findUserMenuPermission(sysUserBean.getId());
								if(null !=list && list.size()>0 ){
									
	//						 		//图片验证码验证 
//							 		String verifyCode = (String)request.getSession().getAttribute("hnair_antifraud_web_image_verify_Code");  
//							 		if (Utils.isNullString(imgCode) || !imgCode.toLowerCase().equals(verifyCode)){  
//							 			map.put("code", EnumSysManage.FAILE.getCode());
//							 			map.put("msg", "图片验证码不正确");
//							 			return map;
//							 		}
									request.getSession().setAttribute("session_loginuser_name", uname);
									request.getSession().setAttribute("user_m_permission", list);
									request.getSession().setAttribute("meun_home_permission", JSON.toJSONString(list));//
									request.getSession().setAttribute("user_id", sysUserBean.getId());
									// 清空错误统计
//									mobileCodeService.seriesErrCountVerify(uname, "pw", null, 5, false);
									
									map.put("resultURL", "home"); 
									map.put("code", EnumSysManage.SUCCESS.getCode());
									
									// 账号登录 删除同一账号最近一次登陆的上一个session  挤出
									Serializable oldSessionId=(Serializable) cacheManagerSession.get(uname);
									Serializable newSessionId=subject.getSession().getId();
									if(StringUtil.isNotEmpty(oldSessionId)){
										if(!oldSessionId.equals(newSessionId)){
											Session session=cacheSessionDAO.readSession(oldSessionId);
											cacheSessionDAO.delete(session);
											cacheManagerSession.remove(uname);
										}
									}
									cacheManagerSession.put(uname, newSessionId);
									logger.debug("对账号[" + uname + "]进行登录验证..验证通过");
									logger.info("账号[" + uname + "]登录成功.");
								}else{
									map.put("msg", "账号无访问权限，请联系中诚信征信");
									map.put("code", EnumSysManage.FAILE.getCode());
									logger.info("对账号[" + uname + "]进行登录验证..账号无访问权限，角色未分配权限");
								}
							}else{
								map.put("code", EnumSysManage.FAILE.getCode());
								map.put("msg", "账号无访问权限，请联系中诚信征信");
								logger.info("对账号[" + uname + "]进行登录验证..账号无访问权限，机构停用万象风云");
							}
						}else{
							map.put("code", EnumSysManage.SYSFAILE.getCode());
							map.put("msg", EnumSysManage.SYSFAILE.getName());
							logger.info("对账号[" + uname + "]进行登录验证..查询账号所在机构异常");
						}
					}catch(Exception e){  
						//通过处理Shiro的运行时AuthenticationException就可以控制账号登录失败或密码错误时的情景  
						logger.error("对账号[" + uname + "]-账号或密码不正确",e.getMessage()); 
						map.put("code", EnumSysManage.FAILE.getCode());
						map.put("msg", "密码不正确");
						
						String pwSeriesErrCode=mobileCodeService.seriesErrCountVerify(uname, "pw", null, 5, true);
						if(!"0000".equals(pwSeriesErrCode)){
							// 锁定之后错误次数清空
							 pwSeriesErrCode=mobileCodeService.seriesErrCountVerify(uname, "pw", null, 5, false);
							if("0000".equals(pwSeriesErrCode)){
								// 连续密码错误验证码输入错误，0:有效，1:冻结 2：注销   3:锁定
								SysUserBean  sysUserBeanPwSeriesErr=new  SysUserBean();
								sysUserBeanPwSeriesErr.setId(sysUserBean.getId());
								sysUserBeanPwSeriesErr.setStatus(3L);
								userService.updateTO(sysUserBeanPwSeriesErr);
							}else{
								logger.info("登录认证---统计密码连续错误异常,锁定账号--[" + uname + "]失败");  
							}
						}
					}
					//验证是否登录成功  
					if(subject.isAuthenticated()){  
						logger.debug("账号[" + uname + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");  
					}else{  
						token.clear();  
				}  
			}
		}
	    return map;
    } 
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
		request.getSession().removeAttribute("menuIndex");//左侧菜单定位
		request.getSession().removeAttribute("ctxUser");//用户信息
		//
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		return new ModelAndView("login");
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value = "/dropOut", method = RequestMethod.GET)
	public ModelAndView dropOut(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("redirect:login");
	}
}

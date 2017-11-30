package com.qlqn.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qlqn.bean.InstitutionBean;
import com.qlqn.bean.SysPermissionBean;
import com.qlqn.bean.SysRoleBean;
import com.qlqn.bean.SysUserBean;
import com.qlqn.bean.SysUserListBean;
import com.qlqn.common.controller.BaseController;
import com.qlqn.common.controller.ControllerUtil;
import com.qlqn.common.enums.EnumSysManage;
import com.qlqn.sys.service.RoleService;
import com.qlqn.sys.service.SysInstitutionService;
import com.qlqn.sys.service.UserRoleService;
import com.qlqn.sys.service.UserService;
import com.qlqn.utils.DateUtils;
import com.qlqn.utils.MD5;
import com.qlqn.utils.StringUtil;
import com.qlqn.utils.Utils;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/userManger")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger("USER");
	//踢出状态，true标示踢出
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private	SysInstitutionService sysInstitsutionService;
	
	@Autowired
	private UserRoleService userRoleService;
	 /** 
     * 用户登录 
     */ 
    @RequestMapping(value="/list", method = RequestMethod.GET)  
    public String list(HttpServletRequest request, HttpServletResponse response){
    	logger.debug("进入用户列表页面");
		return "system/userList_new";
    	
    }
    /**
     * 用户列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/listdata", method=RequestMethod.POST )  
    public Map<String,Object> listdata(HttpServletRequest request){
    	SysUserBean user =ControllerUtil.getSessionUser();
    	//获取请求参数
    	Map<String,Object> params = ControllerUtil.requestMap(request);
    	//分页
    	if ("0000".equals(user.getCreator())) {
    		params.put("instiutionId", "");
		}else{
			params.put("instiutionId", user.getInstiutionId());
		}
    	List<SysUserListBean> list=new ArrayList<SysUserListBean>();
    	//初始化分页对象
    	PageInfo<SysUserListBean> page = new PageInfo<>();
    	PageHelper.startPage(getPageNum(),  getPageSize());
    	list = userService.findUser(params);
		//设置对应的当前页记录
		if(!Utils.isNullString(list)){
			page=new PageInfo<SysUserListBean>(list);
		}
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("currentUserRoleType",user.getCreator());
    	map.put("currentUserId", user.getId());
    	map.put("pageInfo", page);
		return map;
    }
    /**
     * 查询所有用户 account
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findSysUserList", method=RequestMethod.POST)  
    public String findSysUserList(HttpServletRequest request){
    	SysUserBean user =ControllerUtil.getSessionUser();
    	//获取请求参数
    	Map<String,Object> params = ControllerUtil.requestMap(request);
    	if ("0000".equals(user.getCreator())) {
    		params.put("instiutionId", "");
		}else{
			params.put("instiutionId", user.getInstiutionId());
		}
    	List<SysUserListBean> list = userService.findAccountByInsId(params);
		return JSON.toJSONString(list);
    }
    
    
    /**
     * 新增用户页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value="/addUser", method=RequestMethod.GET)  
    public String savelist(HttpServletRequest request){
    	logger.info("进入用户管理页面页面");
		return "system/user_save";
    }
    /**
     * 用户编辑 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value="/selectByIdView", method=RequestMethod.GET)  
    public String selectByIdView(HttpServletRequest request){
    	Long id= Long.parseLong(request.getParameter("id"));
    	SysUserListBean SysUserListBean =userService.selectByPrimaryKey(id);
    	request.getSession().setAttribute("sysUserBean",SysUserListBean );
    	return "system/user_edit";
  }
    /**
     * 查询用户拥有权限
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/selectUserPermission", method=RequestMethod.POST)  
    public Map<String,Object> selectUserPermission(HttpServletRequest request){
    	Long id= Long.parseLong(request.getParameter("id"));
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	List<SysPermissionBean> permissionHaveList =null;
       	try {
       		permissionHaveList = userService.findUserMenuPermission(id);
	     	map.put("permissionHaveList", JSONArray.fromObject(permissionHaveList));
	     	map.put("code", EnumSysManage.SUCCESS.getCode());
   		} catch (Exception e) {
   			map.put("code", EnumSysManage.FAILE.getCode());
   			logger.error("查询用户拥有权限异常，{}",e.getMessage()); 
   		}
       	return map;
  }
    /**
     *  角色分配保存
     * @param request
     * @return
     */
    @ResponseBody
	@RequestMapping(value="addRole2User",method=RequestMethod.POST)
    public Map<String,Object> addRole2User(HttpServletRequest request){
    	String roleIds=request.getParameter("roleIds") == null ? "" : request.getParameter("roleIds").trim();
    	Long userId= Long.parseLong(request.getParameter("userId"));

    	Map<String,Object> map=new HashMap<String,Object>();
    	try {
    		int flag=userService.addRole2User(userId,roleIds);
    		if(flag>0){
    			map.put("code", EnumSysManage.SUCCESS.getCode());
    		}else{
    			map.put("code", EnumSysManage.FAILE.getCode());
    		}
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("角色分配保存异常，{}",e.getMessage()); 
		}
    	return map;
	}
    /**
     * 用户状态改变
     * @param request
     * @return 0:有效，1:停用 2：注销   
     */
    @ResponseBody
   	@RequestMapping(value="changeUserStatus",method=RequestMethod.POST)
    public Map<String,Object> changeUserStatus(HttpServletRequest request){
    	Long userId=Long.parseLong(request.getParameter("uId"));
    	String uname=request.getParameter("uname") == null ? "" : request.getParameter("uname").trim();
    	String status=request.getParameter("status") == null ? "" : request.getParameter("status").trim();
       	Map<String,Object> map=new HashMap<String,Object>();
       	if(Utils.isNullString(uname) || Utils.isNullString(status)){
       		map.put("code", EnumSysManage.FAILE.getCode());
       		return map;
       	}
       	int  flag=0;
       	SysUserBean sysUserBean=new SysUserBean();
       	sysUserBean.setId(userId);
       	//3:解锁
       	if ("3".equals(status)) {
       		// 用户有效
       		sysUserBean.setStatus(1L);
		}else{
			sysUserBean.setStatus(Long.parseLong(status));
		}
       	try {
       		// 更改数据库状态
       		 flag=userService.updateTO(sysUserBean);
   		} catch (Exception e) {
   			map.put("code", EnumSysManage.FAILE.getCode());
   			logger.error("用户状态改变异常，{}",e.getMessage()); 
   		}
       	try {
       		if(flag>0){
   				if("2".equals(status)){
       				//注销后删除 改用户 的角色
       				userRoleService.deleteByUserId(userId);
       			}
   				map.put("code", EnumSysManage.SUCCESS.getCode());
       		}
   		} catch (Exception e) {
   			logger.error("用户状态改变--状态踢出异常，{}",e.getMessage()); 
   		}
       	return map;
   	}
    /**
     * 保存用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/save", method=RequestMethod.POST)  
    public Map<String, String> save(HttpServletRequest request){
    	SysUserBean user =ControllerUtil.getSessionUser();
    	String uname=request.getParameter("uname") == null ? "" : request.getParameter("uname").trim();
    	String password=request.getParameter("password") == null ? "" : request.getParameter("password").trim();
    	String name=request.getParameter("name") == null ? "" : request.getParameter("name").trim();
    	String cid=request.getParameter("cid") == null ? "" : request.getParameter("cid").trim();
    	String mobile=request.getParameter("mobile") == null ? "" : request.getParameter("mobile").trim();
    	String email=request.getParameter("email") == null ? "" : request.getParameter("email").trim();
//    	String telephone=request.getParameter("telephone") == null ? "" : request.getParameter("telephone").trim();
    	String roleId=request.getParameter("roleId");
    	String instiutionId=request.getParameter("instiutionId");
    	
    	password = MD5.encryption(password);
    	
    	SysUserBean sysUserBean=new SysUserBean();
    	sysUserBean.setName(name);
    	sysUserBean.setCid(cid);
    	sysUserBean.setEmail(email);
    	sysUserBean.setPhone(mobile);
    	sysUserBean.setName(name);
    	sysUserBean.setPassword(password);
    	
    	if(!Utils.isNullString(instiutionId)){
    		sysUserBean.setInstiutionId(Long.parseLong(instiutionId));
    	}else{
    		sysUserBean.setInstiutionId(user.getInstiutionId());
    	}
    	sysUserBean.setAccount(uname);
    	sysUserBean.setIsDel("0");
    	sysUserBean.setStatus(0L);
    	Date date=new Date();
    	Map<String, String> map=new HashMap<String, String>();
    	try {
    		sysUserBean.setCreateTime(DateUtils.formatDate(date));
    		sysUserBean.setCreator(user.getAccount());
    		int addRoleFalg=0;
			int falg= userService.save(sysUserBean);
			//更新角色
			if(falg>0){
				if (!"".equals(roleId)) {
					addRoleFalg=userService.addRole2User(sysUserBean.getId(),roleId);
					if(addRoleFalg>0){
						map.put("code", EnumSysManage.SUCCESS.getCode());
					}else{
						// 删除 添加的sysUserBean
						userService.remove(sysUserBean.getId());
						map.put("code", EnumSysManage.FAILE.getCode());
					}
				}else{
					map.put("code", EnumSysManage.SUCCESS.getCode());
				}
			}else{
				map.put("code", EnumSysManage.FAILE.getCode());
			}
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("用户保存异常，{}",e.getMessage()); 
		}
		return map;
    }
	  /**
	   *  通过id 得到用户。详情使用
	   * @param request
	   * @return
	   */
	  @ResponseBody
	  @RequestMapping(value="/selectById", method=RequestMethod.POST)  
	  public Map<String, Object> selectById(HttpServletRequest request){
	  	long id= Long.parseLong(request.getParameter("id"));
	  	Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysUserListBean sysUserListBean =userService.selectByPrimaryKey(id);
		  	map.put("id", sysUserListBean.getId()+"");
		  	map.put("uname", sysUserListBean.getAccount()== null ? "" : sysUserListBean.getAccount());
		  	map.put("password", "Password");
			map.put("name", sysUserListBean.getName()== null ? "" : sysUserListBean.getName());
			map.put("cid", sysUserListBean.getCid()== null ? "" : sysUserListBean.getCid());
			map.put("email", sysUserListBean.getEmail()== null ? "" : sysUserListBean.getEmail());
			map.put("mobile", sysUserListBean.getPhone()== null ? "" : sysUserListBean.getPhone());
			map.put("status", sysUserListBean.getStatus()== null ? "" : sysUserListBean.getStatus()+"");
			map.put("createTime", (sysUserListBean.getCreateTime().substring(0,10)));
			
			SysUserBean user =ControllerUtil.getSessionUser();
	    	List<SysRoleBean> lm=null;
			if("0000".equals(user.getCreator())){
	    		lm=roleService.findRoleByInstitutionId(user.getInstiutionId(),"");
	    	}else{
	    		lm=roleService.findRoleByInstitutionId(sysUserListBean.getInstiutionId(),"yes");
	    	}
			List<Long> lis=userService.findRoleByUserId(id);
			if(null !=lm && lm.size()>0 && null !=lis && lis.size()>0){
					StringBuffer roleNames=new StringBuffer();
					StringBuffer roleIds=new StringBuffer();
					for (SysRoleBean sysRoleBean : lm) {
						if (lis.contains((sysRoleBean.getId()))) {
							if(null!=sysRoleBean.getId() &&
									!"".equals(sysRoleBean.getId())){
								roleIds.append(sysRoleBean.getId()).append(",");
							}
							if(null != sysRoleBean.getName() &&
									!"".equals(sysRoleBean.getName())){
								roleNames.append(sysRoleBean.getName()).append(",");
							}
						}
					}
					if(StringUtil.isNotEmpty(roleIds) && roleIds.length()>0){
						sysUserListBean.setRoleIds((roleIds.substring(0, roleIds.length()-1)));
					}
					if(StringUtil.isNotEmpty(roleNames) && roleNames.length()>0){
						sysUserListBean.setRoleNames((roleNames.substring(0, roleNames.length()-1)));
					}
			}
			map.put("roleNames", sysUserListBean.getRoleNames());
			map.put("roleIds", sysUserListBean.getRoleIds());
			
			// 得到机构
			InstitutionBean sysInstitutionBean=sysInstitsutionService.selectByPrimaryKey(sysUserListBean.getInstiutionId());
			if(StringUtil.isNotEmpty(sysInstitutionBean)){
				map.put("instiutionName",sysInstitutionBean.getName());
				map.put("instiutionId",sysInstitutionBean.getId());
				map.put("code", EnumSysManage.SUCCESS.getCode());
			}else{
				map.put("instiutionName","");
				map.put("instiutionId","");
				map.put("code", EnumSysManage.FAILE.getCode());
				logger.info("通过id得到用户-异常原因,用户所在机构不存在"); 
			}
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("通过id 得到用户-异常原因，{}",e.getMessage()); 
		}
		return map;
  }
    /**
     * 更新用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/updateTo", method=RequestMethod.POST)  
    public Map<String, String> updateTo(HttpServletRequest request){
    	long id= Long.parseLong(request.getParameter("id"));
    	String name=request.getParameter("name") == null ? "" : request.getParameter("name").trim();
    	String cid=request.getParameter("cid") == null ? "" : request.getParameter("cid").trim();
    	String mobile=request.getParameter("mobile") == null ? "" : request.getParameter("mobile").trim();
    	String email=request.getParameter("email") == null ? "" : request.getParameter("email").trim();
//    	String telephone=request.getParameter("telephone") == null ? "" : request.getParameter("telephone").trim();
    	String roleId=request.getParameter("roleId") == null ? "" : request.getParameter("roleId").trim();
    	String instiutionEdit=request.getParameter("instiutionEdit") == null ? "" : request.getParameter("instiutionEdit").trim();
    	SysUserBean sysUserBean=new SysUserBean();
    	sysUserBean.setId(id);
    	sysUserBean.setName(name);
    	sysUserBean.setCid(cid);
    	sysUserBean.setEmail(email);
    	sysUserBean.setPhone(mobile);
    	sysUserBean.setInstiutionId(Long.parseLong(instiutionEdit));
    	Map<String, String> map=new HashMap<String, String>();
    	int addRoleFalg=0;
		try {
			sysUserBean.setModifyTime(DateUtils.formatDate(new Date()));
			SysUserBean sysUserBean2=userService.selectByPrimaryKey(id);
			int falg= userService.updateTO(sysUserBean);
			//更新角色
			if(falg>0){
				if (!"".equals(roleId)) {
					addRoleFalg=userService.addRole2User(sysUserBean.getId(),roleId);
					if(addRoleFalg>0){
						map.put("code", EnumSysManage.SUCCESS.getCode());
					}else{
						// 恢复角色
						userService.updateTO(sysUserBean2);
						map.put("code", EnumSysManage.FAILE.getCode());
					}
				}else{
					map.put("code", EnumSysManage.SUCCESS.getCode());
				}
			}else{
				map.put("code", EnumSysManage.FAILE.getCode());
			}
		} catch (Exception e) {
			logger.error(" 更新用户-异常原因，{}",e.getMessage()); 
		}
		return map;
    }
    /**
     * 逻辑删除用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/remove", method=RequestMethod.POST)  
    public Map<String, String> remove(HttpServletRequest request){
    	long id= Long.parseLong(request.getParameter("id"));
    	SysUserBean sysUserBean=new SysUserBean();
    	sysUserBean.setId(id);
    	sysUserBean.setIsDel("1");
    	Map<String, String> map=new HashMap<String, String>();
    	try {
    		sysUserBean.setModifyTime(DateUtils.formatDate(new Date()));
			userService.updateTO(sysUserBean);
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error(" 逻辑删除用户-异常原因，{}",e.getMessage()); 
		}
		return map;
    }
    /**
     * 修改密码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/updatePW", method=RequestMethod.POST)  
    public Map<String, String> updatePW(HttpServletRequest request){
    	Map<String, String> map=new HashMap<String, String>();
    	String password=request.getParameter("password") == null ? "" : request.getParameter("password").trim();
    	String id=request.getParameter("id") == null ? "" : request.getParameter("id").trim();
    	if ("".equals(id)) {
    		map.put("code", EnumSysManage.FAILE.getCode());
		}else{
				SysUserBean sysUserBean=new SysUserBean();
				sysUserBean.setId(Long.parseLong(id));
				password=MD5.encryption(password);
				sysUserBean.setPassword(password);
				try {
						int flag=userService.updateTO(sysUserBean);
						if(flag>0){
									map.put("code", EnumSysManage.SUCCESS.getCode());
						}
				} catch (Exception e) {
					logger.error(" 修改密码异常:用户 id-异常原因，{}",e.getMessage()); 
					map.put("code", EnumSysManage.FAILE.getCode());
					e.printStackTrace();
				}
		}
		return map;
    }
    /**
     * 校验原密码是否正确
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/selectByIdPw", method=RequestMethod.POST)  
    public Map<String, String> selectByIdPw(HttpServletRequest request){
    	String password=request.getParameter("password") == null ? "" : request.getParameter("password").trim();
    	String id=request.getParameter("id") == null ? "" : request.getParameter("id").trim();
    	Map<String, String> map=new HashMap<String, String>();
		if (!StringUtil.isNotEmpty(password) || !StringUtil.isNotEmpty(id)) {
			map.put("code", EnumSysManage.FAILE.getCode());
		}else{
			SysUserBean sysUserBean=null;
			try {
				sysUserBean = userService.selectByPrimaryKeyForPw(Long.parseLong(id));
				password=MD5.encryption(password);
				if (password.equals(sysUserBean.getPassword())) {
					map.put("code", EnumSysManage.SUCCESS.getCode());
				}else{
					map.put("code", EnumSysManage.FAILE.getCode());
				}
			} catch (NumberFormatException e) {
				map.put("code", EnumSysManage.FAILE.getCode());
				logger.error("校验原密码是否正确异常-异常原因，{}",e.getMessage()); 
			}
		}
  		return map;
    }
    /**
     * 检测用户昵称唯一性
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findUserByName", method=RequestMethod.POST)  
    public Map<String, String> findUserByName(HttpServletRequest request){
    	String uname=request.getParameter("uname") == null ? "" : request.getParameter("uname").trim();
    	Map<String, String> map=new HashMap<String, String>();
		if ("".equals(uname)) {
			map.put("code", EnumSysManage.FAILE.getCode());
		}else{
			try {
				int sysUserBeanCount = userService.getUserByName(uname);
				if (sysUserBeanCount>0) {
					map.put("code", EnumSysManage.UNIQUEDATA.getCode());
				}else{
					map.put("code", EnumSysManage.SUCCESS.getCode());
				}
			} catch (Exception e) {
				map.put("code", EnumSysManage.FAILE.getCode());
				logger.error("检测用户昵称唯一性异常-异常原因，{}",e.getMessage()); 
			}
		}
  		return map;
    }
    /**
     * 检测手机号码唯一性
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findUserByMobile", method=RequestMethod.POST)  
    public Map<String, String> findUserByMobile(HttpServletRequest request){
    	String mobile=request.getParameter("mobile") == null ? "" : request.getParameter("mobile").trim();
    	Map<String, String> map=new HashMap<String, String>();
		if ("".equals(mobile)) {
			map.put("code", EnumSysManage.FAILE.getCode());
		}else{
			try {
				int sysUserBeanCount = userService.getUserByMobile(mobile);
				if (sysUserBeanCount>0) {
					map.put("code", EnumSysManage.UNIQUEDATA.getCode());
				}else{
					map.put("code", EnumSysManage.SUCCESS.getCode());
				}
			} catch (Exception e) {
				map.put("code", EnumSysManage.FAILE.getCode());
				logger.error("检测手机号码唯一性异常-异常原因，{}",e.getMessage()); 
			}
		}
  		return map;
    }
}

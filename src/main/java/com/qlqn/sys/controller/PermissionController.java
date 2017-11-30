package com.qlqn.sys.controller;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.qlqn.bean.SysPermissionBean;
import com.qlqn.bean.SysUserBean;
import com.qlqn.common.controller.BaseController;
import com.qlqn.common.controller.ControllerUtil;
import com.qlqn.common.enums.EnumSysManage;
import com.qlqn.sys.service.PermissionService;
import com.qlqn.sys.service.UserService;
import com.qlqn.utils.DateUtils;
import com.qlqn.utils.Utils;

@Controller
@RequestMapping(value = "/permissionManger")
public class PermissionController  extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private UserService userService;
	
	 /** 
     * 权限列表，页面跳转
     */ 
    @RequestMapping(value="/list", method=RequestMethod.GET)  
    public String list(HttpServletRequest request){
			return "system/permissionList_new";
    }
    /**
     * 权限列表数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/listdata", method=RequestMethod.POST)  
    public String listdata(HttpServletRequest request){
    	SysUserBean user =ControllerUtil.getSessionUser();
    	//获取请求参数
    	Map<String,Object> params = ControllerUtil.requestMap(request);
    	//分页
    	PageHelper.startPage(getPageNum(),  getPageSize());
    	
    	params.put("instiutionId", user.getInstiutionId());
    	
    	//查询用户列表
    	String result = permissionService.findPermission(params);
		return result;
    }
    /**
     * 根据id 得到权限
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/selectById", method=RequestMethod.POST)  
    public Map<String, String> update(HttpServletRequest request) throws ParseException{
    	long id= Long.parseLong(request.getParameter("id"));
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		SysPermissionBean uPermission =permissionService.selectByPrimaryKey(id);
			
    		map.put("id", uPermission.getId()+"");
			map.put("name", uPermission.getName()== null ? "" : uPermission.getName());
			map.put("pathUrl", uPermission.getPathUrl()== null ? "" : uPermission.getPathUrl());
			map.put("iconUrl", uPermission.getIconUrl()== null ? "" : uPermission.getIconUrl());
			map.put("parentId", uPermission.getParentId()== null ? "" : uPermission.getParentId());
			map.put("descriotion", uPermission.getDescription()== null ? "" : uPermission.getDescription());
			map.put("createTime", uPermission.getCreateTime()== null ? "" : uPermission.getCreateTime()+"");
			map.put("creator", uPermission.getCreater()== null ? "" : uPermission.getCreater());
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("根据id 得到权限异常，{}",e.getMessage()); 
		}
		return map;
    }
    /**
     * 更新权限
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/updateTo", method=RequestMethod.POST)  
    public Map<String, String> updateTo(HttpServletRequest request){
    	//获取当前登录用户信息
    	SysUserBean user = ControllerUtil.getSessionUser();

    	long id= Long.parseLong(request.getParameter("id"));
    	String name=request.getParameter("name");
    	String url=request.getParameter("url");
    	SysPermissionBean uPermission=new SysPermissionBean();
    	uPermission.setId(id);
    	uPermission.setName(name);
    	uPermission.setPathUrl(url);
    	uPermission.setModifier(user.getAccount());
    	try {
			uPermission.setModifyTime(DateUtils.formatDate(new Date()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Map<String, String> map=new HashMap<String, String>();
    	try {
			permissionService.updateTO(uPermission);
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("更新权限异常，{}",e.getMessage()); 
		}
		return map;
    }
    /**
     * 逻辑删除 权限
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/remove", method=RequestMethod.POST)  
    public Map<String,String> remove(HttpServletRequest request){
    	//获取当前登录用户信息
    	SysUserBean user = ControllerUtil.getSessionUser();
    	Map<String, String> map = new HashMap<String, String>();
    	long id= Long.parseLong(request.getParameter("id"));
    	SysPermissionBean uPermission=new SysPermissionBean();
    	uPermission.setId(id);
    	uPermission.setState(3L);
    	uPermission.setDelUser(user.getAccount());
    	try {
			uPermission.setDelTime(DateUtils.formatDate(new Date()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
    		permissionService.updateTO(uPermission);
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("逻辑删除 权限异常，{}",e.getMessage()); 
		}
		return map;
    }
    /**
     * 得到权限数据列表
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/selectpermission", method=RequestMethod.POST)  
    public List<Map<Object, Object>> selectpermission(HttpServletRequest request) throws ParseException{
    	List<Map<Object, Object>> list = permissionService.findAllUserMenuPermission();
		return list;
    }
    /**
     * 新增权限
     * @param sysPermissionBean
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/savePermission", method=RequestMethod.POST)  
    public Map<String,String> savePermission(@ModelAttribute SysPermissionBean sysPermissionBean,HttpServletRequest request,
			HttpServletResponse response){
    	//获取当前登录用户信息
    	SysUserBean user = ControllerUtil.getSessionUser();
		//添加创建人
    	sysPermissionBean.setCreater(user.getAccount());
		//添加创建时间
    	try {
    		sysPermissionBean.setCreateTime(DateUtils.formatDate(new Date()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//添加修改时间
//		sysPermissionBean.setModifyTime(new Date());
		//添加权限状态
		sysPermissionBean.setState(1L);
    	String resultMsg="";
		int result=0;
		try {
			// 赋权限给超级管理员
			SysUserBean sysUserBean=userService.getfindAdmin();
			result = permissionService.savePermission(sysPermissionBean,sysUserBean.getId());
			if(result>1){
				resultMsg=EnumSysManage.SUCCESS.getCode();
			}else if(result==1){
				resultMsg=EnumSysManage.NOPERMISSION.getCode();
			}else{
				resultMsg=EnumSysManage.FAILE.getCode();
			}
		} catch (Exception e) {
			resultMsg=EnumSysManage.FAILE.getCode();
			logger.error("新增权限异常，{}",e.getMessage()); 
		}
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("result", resultMsg);
		return resultMap;
    }
    
    /**
     * 新增权限，跳转页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/goToPermission", method = RequestMethod.GET)
	public ModelAndView goToPermission(HttpServletRequest request){
		return new ModelAndView("system/addPermission");
	}
    /**
     * 编辑权限，跳转页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/goToPermission2", method = RequestMethod.GET)
	public ModelAndView goToPermission2(HttpServletRequest request){
    	String id = request.getParameter("id");
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(!Utils.isNullString(id)){
    		map = permissionService.getPermissionById(Long.parseLong(id));
    	}
    	request.setAttribute("map", map);
		return new ModelAndView("system/editPermission");
	}
    /**
     * 权限编辑更新
     * @param sysPermissionBean
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/saveEditPermission", method=RequestMethod.POST)  
    public Map<String,String> saveEditPermission(@ModelAttribute SysPermissionBean sysPermissionBean,HttpServletRequest request,
			HttpServletResponse response){
    	//获取当前登录用户信息
    	SysUserBean user = ControllerUtil.getSessionUser();
		//添加创建人
    	sysPermissionBean.setModifier(user.getAccount());
		//添加创建时间
    	try {
    		sysPermissionBean.setModifyTime(DateUtils.formatDate(new Date()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//添加权限状态
		Long state = 1L;
		sysPermissionBean.setState(state);
    	
		String resultMsg="";
		int result=0;
		try {
			result = permissionService.saveEditPermission(sysPermissionBean);
			if(result>1){
				resultMsg=EnumSysManage.SUCCESS.getCode();
			}else if(result==1){
				resultMsg=EnumSysManage.NOPERMISSION.getCode();
			}else{
				resultMsg=EnumSysManage.FAILE.getCode();
			}
		} catch (Exception e) {
			resultMsg=EnumSysManage.FAILE.getCode();
			logger.error("权限编辑更新异常，{}",e.getMessage()); 
		}
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("result", resultMsg);
		return resultMap;
    }
    
}

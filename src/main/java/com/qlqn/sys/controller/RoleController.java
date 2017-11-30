package com.qlqn.sys.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qlqn.bean.SysPermissionBeanBo;
import com.qlqn.bean.SysRoleBean;
import com.qlqn.bean.SysRoleListBean;
import com.qlqn.bean.SysUserBean;
import com.qlqn.common.controller.BaseController;
import com.qlqn.common.controller.ControllerUtil;
import com.qlqn.common.enums.EnumSysManage;
import com.qlqn.sys.service.PermissionService;
import com.qlqn.sys.service.RoleService;
import com.qlqn.sys.service.UserRoleService;
import com.qlqn.sys.service.UserService;
import com.qlqn.utils.DateUtils;
import com.qlqn.utils.StringUtil;
import com.qlqn.utils.Utils;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/roleManger")
public class RoleController  extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	 /** 
     * 角色列表，页面跳转
     */ 
    @RequestMapping(value="/list", method=RequestMethod.GET)  
    public String list(HttpServletRequest request){
		return "system/roleList_new";
    }
    /**
     * 角色列表数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/listdata", method=RequestMethod.POST)  
    public Map<String,Object> listdata(HttpServletRequest request){
    	SysUserBean user =ControllerUtil.getSessionUser();
    	//获取请求参数
    	Map<String,Object> params = ControllerUtil.requestMap(request);
    	//分页
    	PageHelper.startPage(getPageNum(),  getPageSize());
    	if ("0000".equals(user.getCreator())) {
    		params.put("instiutionId", "");
		}else{
			params.put("instiutionId", user.getInstiutionId());
		}
    	//查询用户列表
    	PageInfo<SysRoleBean> page = roleService.findRole(params);
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("currentUserRoleType",user.getCreator());
    	// 用户id 集合
    	List<Long> lis=userService.findRoleByUserId(user.getId());
    	map.put("currentUserRoleId", lis);
    	map.put("pageInfo", page);
		return map;
    }
    /**
   	 * 根据角色ID查询权限
   	 * @param id
   	 * @return
   	 */
   	@RequestMapping(value="selectPermissionByRoleId" ,method=RequestMethod.GET)
   	public String selectPermissionByRoleId(HttpServletRequest request){
   		//获取当前登录用户信息
        SysUserBean user =ControllerUtil.getSessionUser();
        Long id= Long.parseLong(request.getParameter("id"));
    	List<SysPermissionBeanBo> permissionAllList=null;
    	SysRoleBean sysRoleBean =roleService.selectByPrimaryKey(id);
    	Set<String> sysRoleBeanBoSet=	roleService.findRoleByUserId(user.getId());
    	List<String> sysRoleBeanBoList = new ArrayList<String>(sysRoleBeanBoSet);
		// 查询当前用户的权限，用于分配给子账号
		permissionAllList = permissionService.selectPermissionByRoleId(Long.parseLong(sysRoleBeanBoList.get(0)),id);
   		request.getSession().setAttribute("permissionAllList",JSONArray.fromObject(permissionAllList) );
   		request.getSession().setAttribute("roleName", sysRoleBean.getName());
   		request.getSession().setAttribute("roleDescription", sysRoleBean.getDescription());
   		request.getSession().setAttribute("permissionAddRoleID", id);
   		return "system/permissionAssign_new";
   	}
    /**
     *  权限分配保存
     * @param request
     * @return
     */
    @ResponseBody
	@RequestMapping(value="addPermission2Role",method=RequestMethod.POST)
    public Map<String,String> addPermission2Role(HttpServletRequest request){
    	String permissionIds=request.getParameter("permissionIds") == null ? "" : request.getParameter("permissionIds").trim();
    	Long roleId= Long.parseLong(request.getParameter("roleId"));

    	Map<String,String> map=null;
    	try {
    		 map=roleService.addPermission2Role(roleId,permissionIds);
    		 map.put("result", map.toString());
    		 map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("权限分配保存异常，{}",e.getMessage()); 
		}
    	return map;
	}
    /**
     * 根据机构id得到角色
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findRoleByInstitutionId", method = RequestMethod.POST)
	public List<SysRoleBean> findRoleByInstitutionId(HttpServletRequest request){
    	String uiInstitutionId=request.getParameter("uiInstitutionId") == null ? "" : request.getParameter("uiInstitutionId").trim();
    	SysUserBean user =ControllerUtil.getSessionUser();
//    	List<SysRoleBean> lm=roleService.findRoleByInstitutionId(user.getInstitutionid());
    	List<SysRoleBean> lm=null;
    	if("".equals(uiInstitutionId)){
    		if("0000".equals(user.getCreator())){
        		lm=roleService.findRoleByInstitutionId(user.getInstiutionId(),"");
        	}else{
        		lm=roleService.findRoleByInstitutionId(user.getInstiutionId(),"yes");
        	}
    	}else{
    		lm=roleService.findRoleByInstitutionId(Long.parseLong(uiInstitutionId),"yes");
    	}
		return lm;
	}
    /**
     * 添加 角色
     * @param request
     * @return
     */
    @RequestMapping(value="/addRole", method=RequestMethod.GET)  
    public String savelist(HttpServletRequest request){
    	request.getSession().setAttribute("sysRoleBean",new SysRoleBean() );
    	request.getSession().setAttribute("sysRoleBeanId","");
    	request.getSession().setAttribute("sysRoleBeanOperation","新增");
		return "system/role_saveOrEdit";
    }
    /**
     * 编辑角色
     * @param request
     * @return
     */
    @RequestMapping(value="/editRole", method=RequestMethod.GET)  
    public String editRole(HttpServletRequest request){
    	Long id= Long.parseLong(request.getParameter("id"));
    	SysRoleBean sysRoleBean =roleService.selectByPrimaryKey(id);
    	request.getSession().setAttribute("sysRoleBean",sysRoleBean );
    	request.getSession().setAttribute("sysRoleBeanId",sysRoleBean.getId() );
    	request.getSession().setAttribute("sysRoleBeanOperation","编辑");
		return "system/role_saveOrEdit";
    }
    /**
     * 保存角色
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/save", method=RequestMethod.POST)  
    public Map<String, String> save(HttpServletRequest request){
    	//获取当前登录用户信息
    	SysUserBean user = ControllerUtil.getSessionUser();
    	String name=request.getParameter("name");
    	String instiutionId=request.getParameter("instiutionId");
    	String description=request.getParameter("description");
    	SysRoleBean uRole=new SysRoleBean();
    	uRole.setName(name);
    	if(Utils.isNullString(instiutionId)){
    		uRole.setInstiutionId(user.getInstiutionId());
    	}else{
    		uRole.setInstiutionId(Long.parseLong(instiutionId));
    	}
    	uRole.setDescription(description);
    	uRole.setCreator(user.getAccount());
    	uRole.setType("1");
    	try {
			uRole.setCreateTime(DateUtils.formatDate(new Date()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//    	uRole.setModifyTime(new Date());
    	uRole.setIsDel("0");
    	Map<String, String> map=new HashMap<String, String>();
    	try {
    		roleService.save(uRole);
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("保存角色异常，{}",e.getMessage()); 
		}
    	
		return map;
    }
    /**
     * 根据id 得到角色 ，详情使用
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/selectById", method=RequestMethod.POST)  
    public Map<String, String> update(HttpServletRequest request) throws ParseException{
    	Long id= Long.parseLong(request.getParameter("id"));
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		SysRoleListBean uRole =roleService.selectByPrimaryKey(id);
			
			map.put("id", uRole.getId()+"");
			map.put("name", uRole.getName()== null ? "" : uRole.getName());
			map.put("description", uRole.getDescription()== null ? "" : uRole.getDescription());
			map.put("psNames", uRole.getPsNames()== null ? "" : uRole.getPsNames());
			map.put("type", uRole.getType()== null ? "" : uRole.getType());
			map.put("createTime", uRole.getCreateTime()== null ? "" : uRole.getCreateTime()+"");
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("根据id 得到角色 ，详情使用异常，{}",e.getMessage()); 
		}
		return map;
    }
    /**
     * 验证角色名称唯一性
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/findRoleByName", method=RequestMethod.POST)  
    public Map<String, String> findRoleByName(HttpServletRequest request) throws ParseException{
    	//获取当前登录用户信息
    	SysUserBean user = ControllerUtil.getSessionUser();
    	String roleName=request.getParameter("roleName");
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		int  sysRoleBeanCount  =roleService.getRoleByName(roleName, user.getInstiutionId());
    		if (sysRoleBeanCount>0) {
				map.put("code", EnumSysManage.UNIQUEDATA.getCode());
			}else{
				map.put("code", EnumSysManage.SUCCESS.getCode());
			}
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("验证角色名称唯一性异常，{}",e.getMessage()); 
		}
		return map;
    }
    
    
    /**
     * 更新角色
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
       	String instiutionId=request.getParameter("instiutionId");
    	String description=request.getParameter("description");
    	SysRoleBean uRole=new SysRoleBean();
    	uRole.setId(id);
    	uRole.setName(name);
    	if(StringUtil.isNotEmpty(instiutionId)){
    		uRole.setInstiutionId(Long.parseLong(instiutionId));
    	}
    	uRole.setDescription(description);
    	try {
			uRole.setModifyTime(DateUtils.formatDate(new Date()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	uRole.setModifler(user.getAccount());
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
			roleService.updateTO(uRole);
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("更新角色异常，{}",e.getMessage()); 
		}
		return map;
    }
    /**
     * 逻辑删除角色 查询此角色下是否有正在使用的用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/removeRoleHaveUser", method=RequestMethod.POST)  
    public Map<String, String> removeRoleHaveUser(HttpServletRequest request){
    	long id= Long.parseLong(request.getParameter("id"));
    	Map<String, String> map = new HashMap<String, String>();
    	List<Long> userRoleIdList=userRoleService.findUserIdByRoleId(id);
    	List<String> UserStatusWxfyByUserIdList=new ArrayList<String>();
    	if(StringUtil.isNotEmpty(userRoleIdList)){
    		UserStatusWxfyByUserIdList=userService.findUserStatusByUserIdList(userRoleIdList);
    	}
    	if (UserStatusWxfyByUserIdList.contains("0")|| UserStatusWxfyByUserIdList.contains("1")) {
    		map.put("code", EnumSysManage.UNIQUEDATA.getCode());
		}else{
			map.put("code", EnumSysManage.SUCCESS.getCode());
		}
		return map;
    }
    /**
     * 逻辑删除角色
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/remove", method=RequestMethod.POST)  
    public Map<String, String> remove(HttpServletRequest request){
    	SysUserBean user = ControllerUtil.getSessionUser();
    	long id= Long.parseLong(request.getParameter("id"));
    	Map<String, String> map = new HashMap<String, String>();
		SysRoleBean uRole=new SysRoleBean();
		uRole.setId(id);
    	try {
			uRole.setDelTime(DateUtils.formatDate(new Date()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		uRole.setDelUser(user.getAccount());
		uRole.setIsDel("1");
		try {
			roleService.updateTO(uRole);
			map.put("code", EnumSysManage.SUCCESS.getCode());
		} catch (Exception e) {
			map.put("code", EnumSysManage.FAILE.getCode());
			logger.error("逻辑删除角色异常，{}",e.getMessage()); 
		}
		return map;
    }
    
}

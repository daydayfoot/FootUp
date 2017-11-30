package com.qlqn.sys.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qlqn.bean.InstitutionBean;
import com.qlqn.bean.SysUserBean;
import com.qlqn.common.controller.BaseController;
import com.qlqn.common.controller.ControllerUtil;
import com.qlqn.common.enums.EnumSysManage;
import com.qlqn.sys.service.SysInstitutionService;



@Controller
@RequestMapping(value = "/institutionManger")
public class InstitutionController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(InstitutionController.class);

	@Autowired
	private	SysInstitutionService sysInstitsutionService;
	
	
	/**
	 * 查询所有机构列表
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="/findSysInstitutionList", method=RequestMethod.POST)  
    public Map<String, Object> findSysInstitutionList(HttpServletRequest request){
		
		//获取当前登录用户信息
    	SysUserBean user = ControllerUtil.getSessionUser();
    	Map<String, Object> map=new HashMap<String, Object>();
    	List<InstitutionBean> list = sysInstitsutionService.findAllSysInstitutionList();
    	map.put("uiInstitutionList", list);
    	map.put("currentUserCreator",user.getCreator());
		
		return map;
    }
	
	 	@ResponseBody
	    @RequestMapping(value="/selectByInsId", method=RequestMethod.POST)  
	    public Map<String, String> selectByInsId(HttpServletRequest request) throws ParseException{
	    	long id= Long.parseLong(request.getParameter("id"));
	    	Map<String, String> map = new HashMap<String, String>();
	    	try {
	    		InstitutionBean sysInstitutionBean =sysInstitsutionService.selectByPrimaryKey(id);
				map.put("id", sysInstitutionBean.getId()+"");
				map.put("name", sysInstitutionBean.getName()== null ? "" : sysInstitutionBean.getName());
				map.put("code", EnumSysManage.SUCCESS.getCode());
			} catch (Exception e) {
				map.put("code", EnumSysManage.FAILE.getCode());
				logger.error("根据id得到机构 ，详情使用异常",e.getMessage()); 
			}
			return map;
	    }
}

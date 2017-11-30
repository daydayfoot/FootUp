package com.qlqn.sys.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.qlqn.bean.SysRoleBean;
import com.qlqn.bean.SysRoleListBean;
import com.qlqn.bean.SysRolePermissionBean;
import com.qlqn.dao.SysRoleDao;
import com.qlqn.dao.SysRolePermissionDao;
import com.qlqn.sys.service.RoleService;
import com.qlqn.utils.Utils;
@Service
public class RoleServiceImpl  implements RoleService{
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRolePermissionDao sysRolePermissionDao;
	
	@Override
	public Set<String> findRoleByUserId(Long userId) {
		Set<String> set =new HashSet<String>();
		List<String> ls=sysRoleDao.find("findRoleByUserId",userId);
		set.addAll(ls);
		return set;
	}

	@Override
	public void save(SysRoleBean uRole) {
		sysRoleDao.insert(uRole);
		
	}

	@Override
	public SysRoleListBean selectByPrimaryKey(Long id) {
		SysRoleListBean uRole=sysRoleDao.get("selectByPrimaryKey", id);
		return uRole;
	}

	@Override
	public void updateTO(SysRoleBean uRole) {
		sysRoleDao.update("updateByPrimaryKeySelective",uRole);
		
	}


	@Override
	public PageInfo<SysRoleBean> findRole(Map<String, Object> params) {
		//初始化分页对象
		PageInfo<SysRoleBean> pageInfo = null;
		try {
			//查询用户列表
			List<SysRoleBean> list = sysRoleDao.find("findRole", params);
			if(!Utils.isNullString(list)){
				pageInfo=new PageInfo<SysRoleBean>(list);
			}
		} catch (Exception e) {
			logger.error("查询角色列表异常，{}",e.getMessage()); 
			e.printStackTrace();
		}
		return pageInfo;
	}

	@Override
	public List<SysRoleBean> findRoleByInstitutionId(Long id,String flag) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("institutionId", id);
		map.put("institutionName", flag);
		List<SysRoleBean> listMap=sysRoleDao.find("findRoleByInstitutionId",map);
		return listMap;
	}
	@Transactional 
	@Override
	public Map<String, String> addPermission2Role(Long roleId, String permissionIds) {
		Map<String,String> resultMap = new HashMap<String, String>();
		//先删除原有的。
		sysRolePermissionDao.delete("deleteByRoleId",roleId);
			
			if(StringUtils.isNotBlank(permissionIds)){
				String[] idArray = null;
				
				if(StringUtils.contains(permissionIds, ",")){
					idArray = permissionIds.split(",");
				}else{
					idArray = new String[]{permissionIds};
				}
				//添加新的。
				for (String pid : idArray) {
					if(StringUtils.isNotBlank(pid)){
						SysRolePermissionBean entity = new SysRolePermissionBean(roleId,new Long(pid));
						 sysRolePermissionDao.insert("insertSelective",entity);
					}
				}
			}
			//清空拥有角色Id为：roleId 的用户权限已加载数据，让权限数据重新加载
//			List<Long> userIds = sysUserRoleDao.find("findUserIdByRoleId",roleId);
		
//		TokenManager.clearUserAuthByUserId(userIds);
		resultMap.put("status", 200+"");
		resultMap.put("message", "操作成功");
		return resultMap;
	}
	@Override
	public Integer getRoleByName(String name, Long instiutionId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("instiutionId", instiutionId);
		return sysRoleDao.get("getRoleByName",map);
	}
}

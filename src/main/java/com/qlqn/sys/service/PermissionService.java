package com.qlqn.sys.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qlqn.bean.SysPermissionBean;
import com.qlqn.bean.SysPermissionBeanBo;

public interface PermissionService {
//	根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> findPermissionByUserId(Long id);
		void save(SysPermissionBean uPermission);
		SysPermissionBean selectByPrimaryKey(Long id);
		void updateTO(SysPermissionBean uPermission);
		void remove(Long id);
		
		String findPermission(Map<String, Object> params );
//		
//
		List<SysPermissionBeanBo> selectPermissionByRoleId(Long loginUserRoleId,Long id);
		List<SysPermissionBeanBo> selectPermissionAdminByRoleId(Long id);
		
		//查询权限（permission）
		List<Map<Object, Object>> findAllUserMenuPermission();
		
		int savePermission(SysPermissionBean sysPermissionBean,Long id);
		
		Map<String, Object> getPermissionById(long id);
		
		int saveEditPermission(SysPermissionBean sysPermissionBean);
		

		}

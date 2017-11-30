package com.qlqn.sys.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.qlqn.bean.SysRoleBean;
import com.qlqn.bean.SysRoleListBean;	

public interface RoleService {
	// 根据用户ID查询角色（role），放入到Authorization里。
	Set<String> findRoleByUserId(Long id);

	void save(SysRoleBean uRole);

	SysRoleListBean selectByPrimaryKey(Long id);
	
	Integer getRoleByName(String name, Long instiutionId);

	void updateTO(SysRoleBean uRole);


	PageInfo<SysRoleBean> findRole(Map<String, Object> params);

	List<SysRoleBean> findRoleByInstitutionId(Long id,String falg);


	Map<String, String> addPermission2Role(Long roleId, String permissionIds);
	
}

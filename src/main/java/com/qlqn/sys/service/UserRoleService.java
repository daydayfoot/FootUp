package com.qlqn.sys.service;

import java.util.List;

import com.qlqn.bean.SysUserRoleBean;

public interface UserRoleService {

	void insert(SysUserRoleBean sysUserRoleBean);
	void insertSelective(SysUserRoleBean sysUserRoleBean);
	int deleteByUserId(Long  id);
	List<Long>  findUserIdByRoleId(Long  id);
	List<Long>  findRoleByUserId(Long  id);
	  
}

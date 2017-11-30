package com.qlqn.sys.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qlqn.bean.SysPermissionBean;
import com.qlqn.bean.SysUserBean;
import com.qlqn.bean.SysUserListBean;

public interface UserService {
			SysUserBean login(String account,String  password);
			
			SysUserBean getfindAdmin();
			
			void updateMobileCodeInit();
			
			//根据用户ID查询权限（permission），放入到Authorization里。
			Set<String> findPermissionByUserId(Long id);
			
			int  save(SysUserBean sysUserBean);
			
			int getUserByName(String account);
			
			int getUserByMobile(String mobile);
			
			SysUserBean selectByNameOrMobile(String accountOrMobile);
			
			SysUserBean selectByPrimaryKeyForPw(Long id);
			
			SysUserListBean selectByPrimaryKey(Long id);
			
			int updateTO(SysUserBean sysUserBean);
			
			void remove(Long id);
			
			List<SysUserListBean>  findUser(Map<String, Object> params);
			
			List<SysUserListBean>  findAccountByInsId(Map<String, Object> params);
			
			int addRole2User(Long userId, String ids);
			
			//根据用户ID查询权限（permission）
			List<SysPermissionBean> findUserMenuPermission(Long id);
			//根据用户ID查询对应角色
			List<Long> findRoleByUserId(Long id);
			
			List<String> findUserStatusByUserIdList(List<Long> list);
}

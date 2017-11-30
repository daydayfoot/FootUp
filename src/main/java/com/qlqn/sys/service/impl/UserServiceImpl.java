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

import com.qlqn.bean.SysPermissionBean;
import com.qlqn.bean.SysUserBean;
import com.qlqn.bean.SysUserListBean;
import com.qlqn.bean.SysUserRoleBean;
import com.qlqn.dao.SysUserDao;
import com.qlqn.dao.SysUserRoleDao;
import com.qlqn.sys.service.UserService;
import com.qlqn.utils.StringUtil;
@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private	SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	
	public SysUserBean login(String account,String  password) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("account", account);
		map.put("password", password);
		return sysUserDao.get("loginUser", map);
	}
	@Override
	
	public Set<String> findPermissionByUserId(Long id) {
		Set<String> set =new HashSet<String>();
		List<String> ls=sysUserDao.find("findUserFunPermission", id);
		set.addAll(ls);
		return set;
	}
	@Override
	
	public int save(SysUserBean sysUserBean) {
		
		return   sysUserDao.insert("insertSelective",sysUserBean);
	}
	@Override
	
	public SysUserListBean selectByPrimaryKey(Long id) {
		SysUserListBean sysUserBean=sysUserDao.get("selectByPrimaryKey", id);
		return sysUserBean;
	}
	@Override
	
	public int updateTO(SysUserBean sysUserBean) {
		return sysUserDao.update("updateByPrimaryKeySelective", sysUserBean);
		
	}
	@Override
	
	public void remove(Long id) {
		sysUserDao.delete("deleteByPrimaryKey",id);
		
	}
	@Override
	
	public List<SysUserListBean> findUser(Map<String, Object> params) {
		//初始化分页对象
		List<SysUserListBean> list = null;
		try {
			list = sysUserDao.find("findUser", params);
		} catch (Exception e) {
			logger.error("查询用户列表异常，{}",e.getMessage()); 
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int addRole2User(Long userId, String ids) {
		int count = 0;
			//先删除原有的。
			sysUserRoleDao.delete("deleteByUserId", userId);
			//如果ids,role 的id 有值，那么就添加。没值象征着：把这个用户（userId）所有角色取消。
			if(StringUtils.isNotBlank(ids)){
				String[] idArray = null;
				
				//这里有的人习惯，直接ids.split(",") 都可以，我习惯这么写。清楚明了。
				if(StringUtils.contains(ids, ",")){
					idArray = ids.split(",");
				}else{
					idArray = new String[]{ids};
				}
				//添加新的。
				for (String rid : idArray) {
					//这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的} 
					if(StringUtils.isNotBlank(rid)){
						SysUserRoleBean entity = new SysUserRoleBean(userId,new Long(rid));
						count += sysUserRoleDao.insert("insertSelective",entity);
					}
				}
			}
		//清空用户的权限，迫使再次获取权限的时候，得重新加载
//		TokenManager.clearUserAuthByUserId(userId);
		return count;
	}
	
	
	@Override
	
	public SysUserBean selectByPrimaryKeyForPw(Long id) {
		SysUserBean sysUserBean=sysUserDao.get("selectByPrimaryKeyForPw", id);
		return sysUserBean;
	}
	
	@Override
	
	public SysUserBean selectByNameOrMobile(String accountOrMobile) {
		List<SysUserBean> 	SysUserBeanList=sysUserDao.find("selectByNameOrMobile",accountOrMobile);
		SysUserBean ld= new SysUserBean();
		if (StringUtil.isNotEmpty(SysUserBeanList)) {
			ld=SysUserBeanList.get(0);
		}
		return ld;
	}
	@Override
	
	public int getUserByName(String account) {
		return sysUserDao.get("getUserByName",account);
	}
	@Override
	
	public int getUserByMobile(String mobile) {
		return sysUserDao.get("getUserByMobile",mobile);
	}
	//根据用户ID查询权限（permission）
	@Override
	public List<SysPermissionBean> findUserMenuPermission(Long id) {
		return sysUserDao.find("findUserMenuPermission",id);
	}
	@Override
	
	public SysUserBean getfindAdmin() {
		List<SysUserBean> ls=sysUserDao.find("getfindAdmin","") ;
		return ls.get(0) ;
	}
	@Override
	
	public void updateMobileCodeInit() {
		Map<String, Object> map= new HashMap<String,Object>();
		sysUserDao.update("updateMobileCodeInit",map);
	}
	@Override
	public List<Long> findRoleByUserId(Long id) {
		// TODO Auto-generated method stub
		return sysUserRoleDao.find("findRoleByUserId", id);
	}
	@Override
	
	public List<SysUserListBean> findAccountByInsId(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return  sysUserDao.find("findAccountByInsId", params);
	}
	@Override
	
	public List<String> findUserStatusByUserIdList(List<Long> list) {
		// TODO Auto-generated method stub
		return sysUserDao.find("findUserStatusByUserIdList", list);
	}
}

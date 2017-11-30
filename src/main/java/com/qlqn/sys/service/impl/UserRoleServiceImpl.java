package com.qlqn.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlqn.bean.SysUserRoleBean;
import com.qlqn.dao.SysUserRoleDao;
import com.qlqn.sys.service.UserRoleService;
@Service
public class UserRoleServiceImpl implements UserRoleService{
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public void insert(SysUserRoleBean sysUserRoleBean) {
		sysUserRoleDao.insert(sysUserRoleBean);
		
	}

	@Override
	public void insertSelective(SysUserRoleBean sysUserRoleBean) {
		sysUserRoleDao.insert("insertSelective",sysUserRoleBean);
	}

	@Override
	public int deleteByUserId(Long id) {
		// TODO Auto-generated method stub
		return sysUserRoleDao.delete("deleteByUserId", id);
	}

	@Override
	public List<Long> findUserIdByRoleId(Long id) {
		// TODO Auto-generated method stub
		return sysUserRoleDao.find("findUserIdByRoleId", id);
	}

	@Override
	public List<Long> findRoleByUserId(Long id) {
		// TODO Auto-generated method stub
		return sysUserRoleDao.find("findRoleByUserId", id);
	}
}

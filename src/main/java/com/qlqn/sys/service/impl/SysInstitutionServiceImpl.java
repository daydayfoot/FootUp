package com.qlqn.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlqn.bean.InstitutionBean;
import com.qlqn.dao.SysInstitutionDao;
import com.qlqn.sys.service.SysInstitutionService;
@Service
public class SysInstitutionServiceImpl implements SysInstitutionService {
	private static final Logger logger = LoggerFactory.getLogger(SysInstitutionServiceImpl.class);
	@Autowired
	private	SysInstitutionDao sysInstitutionDao;
	
	
	@Override
	public List<InstitutionBean> findAllSysInstitutionList() {
		return sysInstitutionDao.find("findAllSysInstitutionList", null);
	}

	@Override
	public InstitutionBean selectByPrimaryKey(Long id) {
		return sysInstitutionDao.get("selectByPrimaryKey",id);
	}
}

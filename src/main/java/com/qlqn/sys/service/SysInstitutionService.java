package com.qlqn.sys.service;

import java.util.List;

import com.qlqn.bean.InstitutionBean;

public interface SysInstitutionService {
	List<InstitutionBean> findAllSysInstitutionList();
	InstitutionBean selectByPrimaryKey(Long id);
	
}

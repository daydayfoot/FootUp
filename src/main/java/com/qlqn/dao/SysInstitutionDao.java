package com.qlqn.dao;

import org.springframework.stereotype.Repository;

import com.qlqn.bean.InstitutionBean;
import com.qlqn.common.dao.impl.SimpleDaoImpl;

//所用数据库表  pccredit  zx_institution

@Repository
public class SysInstitutionDao extends SimpleDaoImpl<InstitutionBean>{}
package com.qlqn.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class SysPermissionBeanBo extends SysPermissionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleId;
	private String marker;
	/**
	 * 是否勾选
	 */
	public boolean isCheck(){
		return StringUtils.equals(roleId,marker);
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
}

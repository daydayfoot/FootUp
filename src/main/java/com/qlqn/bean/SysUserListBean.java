package com.qlqn.bean;

import java.io.Serializable;

public class SysUserListBean extends SysUserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Role Name列转行，以,分割
	private String roleNames;
	//Role Id列转行，以‘,’分割
	private String roleIds;
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}

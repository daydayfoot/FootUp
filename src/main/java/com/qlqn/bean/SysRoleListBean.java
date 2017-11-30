package com.qlqn.bean;

import java.io.Serializable;

public class SysRoleListBean extends SysRoleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Permission Name列转行，以,分割
	private String psNames;
	//Permission Id列转行，以‘,’分割
	private String psIds;
	public String getPsNames() {
		return psNames;
	}
	public void setPsNames(String psNames) {
		this.psNames = psNames;
	}
	public String getPsIds() {
		return psIds;
	}
	public void setPsIds(String psIds) {
		this.psIds = psIds;
	}
}

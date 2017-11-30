package com.qlqn.bean;

import java.io.Serializable;

import net.sf.json.JSONObject;


/**
 * 权限和角色  中间表
 */
public class SysRolePermissionBean implements Serializable{
	private static final long serialVersionUID = 1L;
		private Long id;
	    private Long rid;
	    private Long pid;

	    public SysRolePermissionBean() {
		}
	    public SysRolePermissionBean(Long rid,Long pid) {
	    	this.rid = rid;
	    	this.pid = pid;
	    }
	    public Long getRid() {
	        return rid;
	    }

	    public void setRid(Long rid) {
	        this.rid = rid;
	    }

	    public Long getPid() {
	        return pid;
	    }

	    public void setPid(Long pid) {
	        this.pid = pid;
	    }
	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String toString(){
	    	return JSONObject.fromObject(this).toString();
	    }
}
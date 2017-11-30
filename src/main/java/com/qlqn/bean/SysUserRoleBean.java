package com.qlqn.bean;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 用户 和角色  中间表
 * 
 */
public class SysUserRoleBean  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
    private Long uid;
    private Long rid;

    public SysUserRoleBean(Long uid,Long rid) {
    	this.uid = uid;
    	this.rid = rid;
	}
    public SysUserRoleBean() {
    }
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
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
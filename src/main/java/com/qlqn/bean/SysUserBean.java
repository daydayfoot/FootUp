package com.qlqn.bean;

import java.io.Serializable;

public class SysUserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String account;
    private String name;
    private String privateKey;
    private String password;
    private String createTime;
    private String creator;
    private String lastLoginTime;
    private Long status;
    private Long roleId;
    private String cid;
    private String phone;
    private String email;
    private String telephone;
    private String instiutionName;
    private String instiutionCode;
    private Long instiutionId;
    private String isDel;
    private String modifyTime;
    private String modifler;
    private String delUser;
    private String delTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getInstiutionName() {
		return instiutionName;
	}
	public void setInstiutionName(String instiutionName) {
		this.instiutionName = instiutionName;
	}
	public String getInstiutionCode() {
		return instiutionCode;
	}
	public void setInstiutionCode(String instiutionCode) {
		this.instiutionCode = instiutionCode;
	}
	public Long getInstiutionId() {
		return instiutionId;
	}
	public void setInstiutionId(Long instiutionId) {
		this.instiutionId = instiutionId;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifler() {
		return modifler;
	}
	public void setModifler(String modifler) {
		this.modifler = modifler;
	}
	public String getDelUser() {
		return delUser;
	}
	public void setDelUser(String delUser) {
		this.delUser = delUser;
	}
	public String getDelTime() {
		return delTime;
	}
	public void setDelTime(String delTime) {
		this.delTime = delTime;
	}

}
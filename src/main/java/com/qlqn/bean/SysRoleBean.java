package com.qlqn.bean;

import java.io.Serializable;

public class SysRoleBean implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	    private Long id;
	    private String name;
	    private String type;
	    private String description;
	    private String createTime;
	    private String creator;
	    private String isDel;
	    private String modifyTime;
	    private String modifler;
	    private String delUser;
	    private String delTime;
	    private Long instiutionId;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
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
		public Long getInstiutionId() {
			return instiutionId;
		}
		public void setInstiutionId(Long instiutionId) {
			this.instiutionId = instiutionId;
		}
	    
}

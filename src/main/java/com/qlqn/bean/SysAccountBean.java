package com.qlqn.bean;

import java.io.Serializable;

public class SysAccountBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String institutionid;

    private String account;

    private String privatekey;

    private Long isvalid;

    private String institutionCode;
    
    private Long state;
    
    private Long accountType;

    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionid() {
        return institutionid;
    }

    public void setInstitutionid(String institutionid) {
        this.institutionid = institutionid == null ? null : institutionid.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey == null ? null : privatekey.trim();
    }

    public Long getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Long isvalid) {
        this.isvalid = isvalid;
    }



    public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode == null ? null : institutionCode.trim();
	}

	public String getCreateTime() {
		return createTime;
    }

    public void setCreateTime(String createtime) {
        this.createTime = createtime;
    }
    

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}
}
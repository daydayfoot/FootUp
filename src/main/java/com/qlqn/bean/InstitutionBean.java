package com.qlqn.bean;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author zhangyu
 *
 */
public class InstitutionBean implements Serializable, Cloneable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String shortname;
	private String account;
	private String ukeyid;
	private String office_phone_areacode;
	private String office_phone;
	private String fax;
	private String registered_capital;
	private String legal_representative;
	private String general_manager;
	private String tax_register_no;
	private String registration_no;
	private String organization_code;
	private String address;
	private String office_address;
	private String industry;
	private Date foundtime;
	private String email;
	private String post_code;
	private String web_site;
	private String contact;
	private String contact_phone_areacode;
	private String contact_phone;
	private String contact_email;
	private String phone;
	private String qq;
	private int type;
	private String type_String;
	private int typecount;      //机构类型数量
	private String introduction;
	private int state;
	private String state_String;
	private Date registertime;
	private int update_state;                                //审核修改后的资料   1.审核中，2.审核通过，3.审核失败
	private String doExcle;  //导出对账单时会用到
	private double balance;
	private String contact_department;   //所属部门
	private String contact_title;   //职务
	private double servicebalance;
	private String serviceName;
	private double accountbalance;
	private double  totalbalance;
	private String isinitialization;   //是否初始化
	private int isContract;     //是否签订合同
	private String isContract_String; 
	private double floor;
	private double sumaccount;
	private double sumservice;
	private int institutionid;
	private String principal;//负责人
	private int contracttype;
	private String contracttype_String;
	private double expense;
	private String status;//多系统状态：0：不可用，1：可用；最后一位为万象信用，倒数第二位为万象风云，倒数第三位为AXIS
	private String creator;// 创建人
	private Date modifyTime;//修改时间
	public String getType_String() {
		return type_String;
	}
	public void setType_String(String type_String) {
		this.type_String = type_String;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public int getInstitutionid() {
		return institutionid;
	}
	public void setInstitutionid(int institutionid) {
		this.institutionid = institutionid;
	}
	public double getSumaccount() {
		return sumaccount;
	}
	public void setSumaccount(double sumaccount) {
		this.sumaccount = sumaccount;
	}
	public double getSumservice() {
		return sumservice;
	}
	public void setSumservice(double sumservice) {
		this.sumservice = sumservice;
	}
	public double getFloor() {
		return floor;
	}
	public void setFloor(double floor) {
		this.floor = floor;
	}
	public double getTotalbalance() {
		return totalbalance;
	}
	public void setTotalbalance(double totalbalance) {
		this.totalbalance = totalbalance;
	}
	public int getIsContract() {
		return isContract;
	}
	public void setIsContract(int isContract) {
		this.isContract = isContract;
	}
	public int getTypecount() {
		return typecount;
	}
	public void setTypecount(int typecount) {
		this.typecount = typecount;
	}
	public String getIsinitialization() {
		return isinitialization;
	}
	public void setIsinitialization(String isinitialization) {
		this.isinitialization = isinitialization;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public double getAccountbalance() {
		return accountbalance;
	}
	public void setAccountbalance(double accountbalance) {
		this.accountbalance = accountbalance;
	}
	public double getServicebalance() {
		return servicebalance;
	}
	public void setServicebalance(double servicebalance) {
		this.servicebalance = servicebalance;
	}
	public String getContact_department() {
		return contact_department;
	}
	public void setContact_department(String contact_department) {
		this.contact_department = contact_department;
	}
	public String getContact_title() {
		return contact_title;
	}
	public void setContact_title(String contact_title) {
		this.contact_title = contact_title;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDoExcle() {
		doExcle="";
		return doExcle;
	}
	public void setDoExcle(String doExcle) {
		this.doExcle = doExcle;
	}
	public String getState_String() {
		return state_String;
	}
	public void setState_String(String state_String) {
		this.state_String = state_String;
	}
	public int getUpdate_state() {
		return update_state;
	}
	public void setUpdate_state(int update_state) {
		this.update_state = update_state;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUkeyid() {
		return ukeyid;
	}
	public void setUkeyid(String ukeyid) {
		this.ukeyid = ukeyid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getOffice_phone_areacode() {
		return office_phone_areacode;
	}
	public void setOffice_phone_areacode(String office_phone_areacode) {
		this.office_phone_areacode = office_phone_areacode;
	}
	public String getOffice_phone() {
		return office_phone;
	}
	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getRegistered_capital() {
		return registered_capital;
	}
	public void setRegistered_capital(String registered_capital) {
		this.registered_capital = registered_capital;
	}
	public String getLegal_representative() {
		return legal_representative;
	}
	public void setLegal_representative(String legal_representative) {
		this.legal_representative = legal_representative;
	}
	public String getGeneral_manager() {
		return general_manager;
	}
	public void setGeneral_manager(String general_manager) {
		this.general_manager = general_manager;
	}
	public String getTax_register_no() {
		return tax_register_no;
	}
	public void setTax_register_no(String tax_register_no) {
		this.tax_register_no = tax_register_no;
	}
	public String getRegistration_no() {
		return registration_no;
	}
	public void setRegistration_no(String registration_no) {
		this.registration_no = registration_no;
	}
	public String getOrganization_code() {
		return organization_code;
	}
	public void setOrganization_code(String organization_code) {
		this.organization_code = organization_code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOffice_address() {
		return office_address;
	}
	public void setOffice_address(String office_address) {
		this.office_address = office_address;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Date getFoundtime() {
		return foundtime;
	}
	public void setFoundtime(Date foundtime) {
		this.foundtime = foundtime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getWeb_site() {
		return web_site;
	}
	public void setWeb_site(String web_site) {
		this.web_site = web_site;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContact_phone_areacode() {
		return contact_phone_areacode;
	}
	public void setContact_phone_areacode(String contact_phone_areacode) {
		this.contact_phone_areacode = contact_phone_areacode;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getContact_email() {
		return contact_email;
	}
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getRegistertime() {
		return registertime;
	}
	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getIsContract_String() {
		return isContract_String;
	}
	public void setIsContract_String(String isContract_String) {
		this.isContract_String = isContract_String;
	}
	public int getContracttype() {
		return contracttype;
	}
	public void setContracttype(int contracttype) {
		this.contracttype = contracttype;
	}
	public String getContracttype_String() {
		return contracttype_String;
	}
	public void setContracttype_String(String contracttype_String) {
		this.contracttype_String = contracttype_String;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public double getExpense() {
		return expense;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "InstitutionBean [id=" + id + ", name=" + name + ", shortname=" + shortname + ", account=" + account
				+ ", ukeyid=" + ukeyid + ", office_phone_areacode=" + office_phone_areacode + ", office_phone="
				+ office_phone + ", fax=" + fax + ", registered_capital=" + registered_capital
				+ ", legal_representative=" + legal_representative + ", general_manager=" + general_manager
				+ ", tax_register_no=" + tax_register_no + ", registration_no=" + registration_no
				+ ", organization_code=" + organization_code + ", address=" + address + ", office_address="
				+ office_address + ", industry=" + industry + ", foundtime=" + foundtime + ", email=" + email
				+ ", post_code=" + post_code + ", web_site=" + web_site + ", contact=" + contact
				+ ", contact_phone_areacode=" + contact_phone_areacode + ", contact_phone=" + contact_phone
				+ ", contact_email=" + contact_email + ", phone=" + phone + ", qq=" + qq + ", type=" + type
				+ ", type_String=" + type_String + ", typecount=" + typecount + ", introduction=" + introduction
				+ ", state=" + state + ", state_String=" + state_String + ", registertime=" + registertime
				+ ", update_state=" + update_state + ", doExcle=" + doExcle + ", balance=" + balance
				+ ", contact_department=" + contact_department + ", contact_title=" + contact_title
				+ ", servicebalance=" + servicebalance + ", serviceName=" + serviceName + ", accountbalance="
				+ accountbalance + ", totalbalance=" + totalbalance + ", isinitialization=" + isinitialization
				+ ", isContract=" + isContract + ", isContract_String=" + isContract_String + ", floor=" + floor
				+ ", sumaccount=" + sumaccount + ", sumservice=" + sumservice + ", institutionid=" + institutionid
				+ ", principal=" + principal + ", contracttype=" + contracttype + ", contracttype_String="
				+ contracttype_String + ", expense=" + expense + ", status=" + status + ", creator=" + creator
				+ ", modifyTime=" + modifyTime + "]";
	}
	
}

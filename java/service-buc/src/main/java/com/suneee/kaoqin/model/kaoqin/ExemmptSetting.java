package com.suneee.kaoqin.model.kaoqin;

import com.suneee.core.model.BaseModel;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;


/**
 * 对象功能:免签设置 Model对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2017-05-02 10:06:05
 */
public class ExemmptSetting extends BaseModel {
	// ID
	protected Long id;
	// 免签对象ID
	protected Long targetId;
	// 免签对象类型(0-用户，1-组织，2-角色，3-岗位)
	protected Short targetType;
	// 备注说明
	protected String description;
	// 状态（0：正常，-1：禁用）
	protected Short status;
	// 创建人ID
	protected Long createby;
	// 创建时间
	protected Date createtime;
	
	protected String fullname;
	
	protected String orgName;
	
	protected String account;
	
	protected String staffNo;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	protected String roleNames;

	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 返回 ID
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	public void setTargetId(Long targetId){
		this.targetId = targetId;
	}
	/**
	 * 返回 免签对象ID
	 * @return
	 */
	public Long getTargetId() {
		return this.targetId;
	}
	public void setTargetType(Short targetType){
		this.targetType = targetType;
	}
	/**
	 * 返回 免签对象类型(0-用户，1-组织，2-角色，3-岗位)
	 * @return
	 */
	public Short getTargetType() {
		return this.targetType;
	}
	public void setDescription(String description){
		this.description = description;
	}
	/**
	 * 返回 备注说明
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	public void setStatus(Short status){
		this.status = status;
	}
	/**
	 * 返回 状态（0：正常，-1：禁用）
	 * @return
	 */
	public Short getStatus() {
		return this.status;
	}
	public void setCreateby(Long createby){
		this.createby = createby;
	}
	/**
	 * 返回 创建人ID
	 * @return
	 */
	public Long getCreateby() {
		return this.createby;
	}
	public void setCreatetime(Date createtime){
		this.createtime = createtime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public Date getCreatetime() {
		return this.createtime;
	}
	

   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ExemmptSetting)) 
		{
			return false;
		}
		ExemmptSetting rhs = (ExemmptSetting) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.targetId, rhs.targetId)
		.append(this.targetType, rhs.targetType)
		.append(this.description, rhs.description)
		.append(this.status, rhs.status)
		.append(this.createby, rhs.createby)
		.append(this.createtime, rhs.createtime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.targetId) 
		.append(this.targetType) 
		.append(this.description) 
		.append(this.status) 
		.append(this.createby) 
		.append(this.createtime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("targetId", this.targetId) 
		.append("targetType", this.targetType) 
		.append("description", this.description) 
		.append("status", this.status) 
		.append("createby", this.createby) 
		.append("createtime", this.createtime) 
		.toString();
	}
   
  

}
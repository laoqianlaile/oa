package com.suneee.platform.model.bpm;

import com.suneee.core.model.BaseModel;
import com.suneee.core.util.AppUtil;
import com.suneee.core.util.StringUtil;
import com.suneee.platform.model.form.BpmFormDef;
import com.suneee.platform.service.form.BpmFormDefService;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 对象功能:BpmNodeSet Model对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2011-12-09 14:57:19
 */
@XmlRootElement(name = "bpmNodeSet")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeSet extends BaseModel
{
	/**
	 * 没有设置表单
	 */
	public static final Short FORM_TYPE_NULL=-1;
	/**
	 * 在线表单
	 */
	public static final Short FORM_TYPE_ONLINE=0;
	/**
	 * URL表单
	 */
	public static final Short FORM_TYPE_URL=1;
	
	/**
	 * 普通任务节点
	 */
	public static final Short NODE_TYPE_NORMAL=0;
	/**
	 * 分发任务节点
	 */
	public static final Short NODE_TYPE_FORK=1;
	/**
	 * 汇集任务节点
	 */
	public static final Short NODE_TYPE_JOIN=2;
	
	/**
	 * 允许回退 =1
	 */
	public static final Short BACK_ALLOW=1;
	/**
	 * 隐藏意见表单
	 */
	public static final Short HIDE_OPTION=1;
	/**
	 * 隐藏执行路径
	 */
	public static final Short HIDE_PATH=1;
	//不隐藏路径
	public static final Short NOT_HIDE_PATH=0;
	public static final Short NOT_HIDE_OPTION=0;
	
	/**
	 * 必填
	 */
	public static final Short IS_REQUIRED=1;
	/**
	 * 非必填
	 */
	public static final Short NOT_IS_REQUIRED=0;
	/**
	 * 弹窗
	 */
	public static final Short IS_POPUP=1;
	/**
	 * 非弹窗
	 */
	public static final Short NOT_IS_POPUP=0;
	
	
	/**
	 * 正常跳转=1
	 */
	public static final Short JUMP_TYPE_NORMAL=1;
	/**
	 * 选择跳转=2
	 */
	public static final Short JUMP_TYPE_SELECT=2;
	/**
	 * 自由跳转=3
	 */
	public static final Short JUMP_TYPE_FREE=3;
	/**
	 * 跳转回本节点=4
	 */
	public static final Short JUMP_TYPE_SELF=4;
	
	/**
	 * 允许回退到流程发起人。
	 */
	public static final Short BACK_ALLOW_START=1;
	/**
	 * 不允许回退=0
	 */
	public static final Short BACK_DENY=0;
	
	/**
	 * 任务节点
	 */
	public static final Short SetType_TaskNode=0;
	
	/**
	 * 开始表单
	 */
	public static final Short SetType_StartForm=1;
	/**
	 * 全局表单
	 */
	public static final Short SetType_GloabalForm=2;
	/**
	 * 业务表单
	 */
	public static final Short SetType_BpmForm=3;
	/**
	 * 规则不符合条件时，任务按定义正常跳转
	 */
	public static final Short RULE_INVALID_NORMAL=1;
	
	/**
	 * 规则不符合条件时，任务仅是完成当前节点，不作跳转处理
	 */
	public static final Short RULE_INVALID_NO_NORMAL=0;

	
	// setId
	@XmlAttribute
	protected Long setId;
	/**
	 *  流程定义ID
	 */
	@XmlAttribute
	protected Long defId;
	/**
	 *  节点名
	 */
	@XmlAttribute
	protected String nodeName;
	
	/**
	 *  节点顺序编号
	 */
	@XmlAttribute
	protected Integer nodeOrder;
	/**
	 *  Activiti流程定义ID
	 */
	@XmlAttribute
	protected String actDefId;
	
	/**
	 *  Activiti父流程定义ID
	 */
	@XmlAttribute
	protected String parentActDefId;
	
	/**
	 *  节点ID
	 */
	@XmlAttribute
	protected String nodeId;
	/**
	 *  表单类型(0：在线表单,1:URL表单)
	 */
	@XmlAttribute
	protected Short formType=-1;
	/**
	 *  表单URL
	 */
	@XmlAttribute
	protected String formUrl;
	/**
	 *  表单定义ID
	 */
	@XmlAttribute
	protected String formKey="";
	/**
	 *  表单名称
	 */
	@XmlAttribute
	protected String formDefName;
	/**
	 * 表单ID
	 */
	@XmlAttribute
	protected Long formDefId=0L;
	/**
	 * Detail Url
	 */
	@XmlAttribute
	protected String detailUrl;
	
	/**
	 * 规则不作用时，是否正常跳转
	 */
	@XmlAttribute
	protected Short isJumpForDef=RULE_INVALID_NORMAL;
	/**
	 * 任务类型：
	 * 0=普通任务
	 * 1=分发任务
	 */
	@XmlAttribute
	protected Short nodeType;
	
	/**
	 * 当任务类型=1时，可以指定汇总任务Key
	 */
	@XmlAttribute
	protected String joinTaskKey;
	/**
	 * 当任务类型=1时，指定的汇总任务名称
	 */
	@XmlAttribute
	protected String joinTaskName;
	
	/**
	 * 前置接口
	 */
	@XmlAttribute
	protected String beforeHandler;
	/**
	 * 后置接口
	 */
	@XmlAttribute
	protected String afterHandler;
	/**
	 * 跳转类型，1=正常跳转，2=选择路径跳转，3=自由跳转，值格式如1,2
	 */
	@XmlAttribute
	protected String jumpType;
	
	/**
	 * 节点设置类型（0.任务节点1.开始表单2.默认表单)
	 */
	@XmlAttribute
	protected Short setType=0;
	
	/**
	 * 隐藏意见表单
	 */
	@XmlAttribute
	protected Short isHideOption=0;
	/**
	 * 隐藏执行路径
	 */
	@XmlAttribute
	protected Short isHidePath=0;
	
	/**
	 * 是否必填
	 */
	@XmlAttribute
	protected Short isRequired=1;

	/**
	 * 是否弹出框
	 */
	@XmlAttribute
	protected Short isPopup=1;
	
	/**
	 * 意见回填字段
	 */
	@XmlAttribute
	protected String opinionField;
	
	/**
	 * 原表单key。
	 * 
	 * 用于表单key有变化的情况。
	 */
	@XmlAttribute
	protected String oldFormKey="";
	
	/**
	 * 是否存在子表
	 */
	@XmlAttribute
	protected Short existSubTable=0;
	
	/**
	 * 主表ID
	 */
	@XmlAttribute
	protected Long mainTableId = 0l;
	protected String informType="";
	/**
	 * 每一部是否发信息给发起人 0:false 1:true
	 */
	protected Short sendMsgToCreator=0;
	
	
	//是否允许手机(0,不允许，1，允许)
	@XmlAttribute
	protected int enableMobile=0;
	
	@XmlAttribute
	protected String mobileFormKey;
	@XmlAttribute
	protected String mobileFormName;
	
	/**
	 * 沟通人员选择：
	 * 格式：{"type":"user","value":"","title":"人员"}
	 * {"type":"org","value":"","title":"部门"}
	 * {"type":"role","value":"","title":"角色"}
	 * {"type":"script","value":"","title":"脚本"}
	 */
	@XmlAttribute
	protected String communicate;
	
	
	/**
	 * 人员部门选择器时，配置人员的选定范围，和人员选择器的配置一样
	 * 格式： {"type":"system","value":"all"}全部
	 * 		 {"type":"system","value":"department"}部门
	 * 		 {"type":"system","value":"company"}分公司
	 */
	@XmlAttribute
	protected String scope;
	
	
	@XmlAttribute
	protected Short opinionHtml=0;

	
	public void setSetId(Long setId) 
	{
		this.setId = setId;
	}
	/**
	 * 返回 setId
	 * @return
	 */
	public Long getSetId() 
	{
		return setId;
	}

	public void setDefId(Long defId) 
	{
		this.defId = defId;
	}
	
	
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public Long getDefId() 
	{
		return defId;
	}
	public void setNodeName(String nodeName) 
	{
		this.nodeName = nodeName;
	}
	/**
	 * 返回 节点名
	 * @return
	 */
	public String getNodeName() 
	{
		return nodeName;
	}

	public Integer getNodeOrder() {
		return nodeOrder;
	}
	public void setNodeOrder(Integer nodeOrder) {
		this.nodeOrder = nodeOrder;
	}
	
	public String getInformType() {
		return informType;
	}
	/**
	 * sendMsgToCreator
	 * @return  the sendMsgToCreator
	 * @since   1.0.0
	 */
	
	public Short getSendMsgToCreator() {
		return sendMsgToCreator;
	}
	/**
	 * @param sendMsgToCreator the sendMsgToCreator to set
	 */
	public void setSendMsgToCreator(Short sendMsgToCreator) {
		this.sendMsgToCreator = sendMsgToCreator;
	}
	public void setInformType(String informType) {
		this.informType = informType;
	}
	public void setActDefId(String actDefId) 
	{
		this.actDefId = actDefId;
	}
	/**
	 * 返回 Activiti发布ID
	 * @return
	 */
	public String getActDefId() 
	{
		return actDefId;
	}
	
	/**
	 * 返回 从属主流程Activiti流程定义ID
	 * @return
	 */
	public String getParentActDefId() {
		return parentActDefId;
	}
	public void setParentActDefId(String parentActDefId) {
		this.parentActDefId = parentActDefId;
	}

	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}
	/**
	 * 返回 nodeId
	 * @return
	 */
	public String getNodeId() 
	{
		return nodeId;
	}

	public void setFormType(Short formType) 
	{
		this.formType = formType;
	}
	/**
	 * 返回 formType
	 * @return
	 */
	public Short getFormType() 
	{
		return formType;
	}

	public void setFormUrl(String formUrl) 
	{
		this.formUrl = formUrl;
	}
	/**
	 * 返回 formUrl
	 * @return
	 */
	public String getFormUrl() 
	{
		return formUrl;
	}

	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getFormDefName()
	{
		return formDefName;
	}
	public void setFormDefName(String formDefName)
	{
		this.formDefName = formDefName;
	}
	
	public Short getNodeType()
	{
		return nodeType;
	}
	public void setNodeType(Short nodeType)
	{
		this.nodeType = nodeType;
	}

	public String getJoinTaskKey()
	{
		return joinTaskKey;
	}
	public void setJoinTaskKey(String joinTaskKey)
	{
		this.joinTaskKey = joinTaskKey;
	}
	public String getJoinTaskName()
	{
		return joinTaskName;
	}
	public void setJoinTaskName(String joinTaskName)
	{
		this.joinTaskName = joinTaskName;
	}
	
	public String getBeforeHandler() {
		return beforeHandler;
	}
	public void setBeforeHandler(String beforeHandler) {
		this.beforeHandler = beforeHandler;
	}
	public String getAfterHandler() {
		return afterHandler;
	}
	public void setAfterHandler(String afterHandler) {
		this.afterHandler = afterHandler;
	}

	public Short getSetType() {
		return setType;
	}
	public void setSetType(Short setType) {
		this.setType = setType;
	}
	public Long getFormDefId() {
		return formDefId;
	}
	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}
	
	public String getJumpType() {
		return jumpType;
	}
	
	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	
	public Short getIsJumpForDef() {
		return isJumpForDef;
	}
	public void setIsJumpForDef(Short isJumpForDef) {
		this.isJumpForDef = isJumpForDef;
	}
	
	public String getOldFormKey() {
		return oldFormKey;
	}
	public void setOldFormKey(String oldFormKey) {
		this.oldFormKey = oldFormKey;
	}
	
	public Short getIsHideOption() {
		return isHideOption;
	}
	public void setIsHideOption(Short isHideOption) {
		this.isHideOption = isHideOption;
	}
	public Short getIsHidePath() {
		return isHidePath;
	}
	public void setIsHidePath(Short isHidePath) {
		this.isHidePath = isHidePath;
	}
	public Short getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Short isRequired) {
		this.isRequired = isRequired;
	}
	public Short getIsPopup() {
		return isPopup;
	}
	public void setIsPopup(Short isPopup) {
		this.isPopup = isPopup;
	}
	
	public String getOpinionField() {
		return opinionField;
	}
	public void setOpinionField(String opinionField) {
		this.opinionField = opinionField;
	}
	public Short getExistSubTable() {
		return existSubTable;
	}
	public void setExistSubTable(Short existSubTable) {
		this.existSubTable = existSubTable;
	}
	
	public Long getMainTableId() {
		return mainTableId;
	}
	public void setMainTableId(Long mainTableId) {
		this.mainTableId = mainTableId;
	}
	

	
	public int getEnableMobile() {
		return enableMobile;
	}
	public void setEnableMobile(int enableMobile) {
		this.enableMobile = enableMobile;
	}
	/**
	 * mobileFormKey
	 * @return  the mobileFormKey
	 * @since   1.0.0
	 */
	public String getMobileFormKey() {
		return mobileFormKey;
	}
	/**
	 * @param mobileFormKey the mobileFormKey to set
	 */
	public void setMobileFormKey(String mobileFormKey) {
		this.mobileFormKey = mobileFormKey;
	}
	
	public String getMobileFormName() {
		return mobileFormName;
	}
	public void setMobileFormName(String mobileFormName) {
		this.mobileFormName = mobileFormName;
	}
	/**
	 * 手机表单是否配置。
	 * @return
	 */
	public boolean isMobileEnabled(){
		return StringUtil.isNotEmpty(mobileFormKey);
	}
	
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
	public BpmFormDef getMobileFormDef() {
		if(StringUtil.isEmpty(mobileFormKey)) return new BpmFormDef();
		BpmFormDefService defService= AppUtil.getBean(BpmFormDefService.class);
		BpmFormDef bpmFormDef =defService.getDefaultVersionByFormKey(this.mobileFormKey);
	
		return bpmFormDef;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmNodeSet)) {
			return false;
		}
		BpmNodeSet rhs = (BpmNodeSet) object;
		return new EqualsBuilder()
		.append(this.setId, rhs.setId)
		.append(this.defId, rhs.defId)
		.append(this.nodeName, rhs.nodeName)
		.append(this.actDefId, rhs.actDefId)
		.append(this.nodeId, rhs.nodeId)
		.append(this.formType, rhs.formType)
		.append(this.formUrl, rhs.formUrl)
		.append(this.formKey, rhs.formKey)
		.append(this.nodeType,rhs.nodeType)
		.append(this.scope,rhs.scope)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.setId) 
		.append(this.defId) 
		.append(this.nodeName) 
		.append(this.actDefId) 
		.append(this.nodeId) 
		.append(this.formType) 
		.append(this.formUrl) 
		.append(this.formKey)
		.append(this.nodeType)
		.append(this.scope)
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("setId", this.setId) 
		.append("defId", this.defId) 
		.append("nodeName", this.nodeName) 
		.append("actDefId", this.actDefId) 
		.append("nodeId", this.nodeId) 
		.append("formType", this.formType) 
		.append("formUrl", this.formUrl) 
		.append("formKey", this.formKey)
		.append("nodeType",this.nodeType)
		.append("scope",this.scope)
		.append("communicate", this.communicate)
		.toString();
	}
	
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public Short getOpinionHtml() {
		return opinionHtml;
	}
	public void setOpinionHtml(Short opinionHtml) {
		this.opinionHtml = opinionHtml;
	}
	/**
	 * @return the communicate
	 */
	public String getCommunicate() {
		return communicate;
	}
	/**
	 * @param communicate the communicate to set
	 */
	public void setCommunicate(String communicate) {
		this.communicate = communicate;
	}
}
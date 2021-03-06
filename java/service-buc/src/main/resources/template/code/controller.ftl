<#assign package=table.variable.package>
<#assign class=table.variable.class>
<#assign classVar=table.variable.classVar>
<#assign package=table.variable.package>
<#assign comment=table.tableDesc>
<#assign fieldList=table.fieldList>
<#assign subTableList=table.subTableList>
<#assign hasScript=0>
<#assign hasIdentity=0>

<#list fieldList as field>
<#if field.valueFrom==1|| field.valueFrom==2>
	<#assign hasScript=1>
<#elseif field.valueFrom==3>
	<#assign hasIdentity=1>
</#if>
</#list>

package com.suneee.${system}.controller.${package};

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.suneee.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.suneee.core.util.UniqueIdUtil;
import com.suneee.core.web.util.RequestUtil;
import com.suneee.core.web.controller.BaseController;
import com.suneee.core.util.BeanUtils;
import com.suneee.core.web.query.QueryFilter;
import com.suneee.core.page.PageList;
import com.suneee.platform.model.system.SysUser;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.suneee.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.suneee.core.util.MapUtil;

import com.suneee.${system}.model.${package}.${class};
import com.suneee.${system}.service.${package}.${class}Service;
<#if subTableList?size != 0>
	<#list subTableList as subtable>
import com.suneee.${system}.model.${subtable.variable.package}.${subtable.variable.class};
	</#list>
</#if>
import com.suneee.core.web.ResultMessage;
<#if hasScript==1>
import com.suneee.core.engine.GroovyScriptEngine;
</#if>
<#if hasIdentity==1>
import com.suneee.platform.service.system.IdentityService;
</#if>
<#if flowKey?exists>
import com.suneee.core.util.StringUtil;
import com.suneee.core.api.util.ContextUtil;
import com.suneee.core.bpm.model.ProcessCmd;
import com.suneee.platform.model.bpm.ProcessRun;
import com.suneee.platform.service.bpm.ProcessRunService;
</#if>
/**
 * 对象功能:${comment} 控制器类
 */
@Controller
@RequestMapping("/${system}/${package}/${classVar}/")
public class ${class}Controller extends BaseController
{
	@Resource
	private ${class}Service ${classVar}Service;
	<#if hasScript==1>
	@Resource
	private GroovyScriptEngine engine;
	</#if>
	<#if hasIdentity==1>
	@Resource
	private IdentityService identityService;
	</#if>
	<#if flowKey?exists>
	@Resource
	private ProcessRunService processRunService;
	</#if>
	
	<#--直接绑定工作流生成-->
	<#if table.variable.flowKey?exists>
	private final String flowKey = "${model.variables.flowKey}";	//绑定流程定义
	</#if>
	/**
	 * 添加或更新${comment}。
	 * @param request
	 * @param response
	 * @param ${classVar} 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新${comment}")
	public void save(HttpServletRequest request, HttpServletResponse response,${class} ${classVar}) throws Exception
	{
		String resultMsg=null;		
		try{
			if(${classVar}.get<#if table.isExternal==0>Id<#else>${table.pkField?cap_first}</#if>()==null){
				<#if subTableList?size != 0>
				${classVar}Service.save(${classVar});			
				<#else>
				${classVar}Service.save(${classVar});
				</#if>
				resultMsg=getText("添加","${comment}");
			}else{
			    <#if subTableList?size != 0>
			    ${classVar}Service.save(${classVar});
			    <#else>
			    ${classVar}Service.save(${classVar});
			    </#if>
				resultMsg=getText("更新","${comment}");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得${comment}分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看${comment}分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<${class}> list=${classVar}Service.getAll(new QueryFilter(request,"${classVar}Item"));
		ModelAndView mv=this.getAutoView().addObject("${classVar}List",list);
		return mv;
	}
	
	/**
	 * 删除${comment}
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除${comment}")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			<#if table.isExternal==0>
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			<#else>
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			</#if>
			<#if subTableList?size != 0>
			${classVar}Service.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除${comment}及其从表成功!");
			<#else>
			</#if>
			${classVar}Service.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除${comment}成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑${comment}
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑${comment}")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		<#if table.isExternal==0>
		Long id=RequestUtil.getLong(request,"id");
		<#else>
		Long ${table.pkField}=RequestUtil.getLong(request,"id");
		</#if>
		
		<#if flowKey?exists>
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(<#if table.isExternal==0>id.toString()<#else>${table.pkField}.toString()</#if>);
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		</#if>
		String returnUrl=RequestUtil.getPrePage(request);
		<#if table.isExternal==0>
		${class} ${classVar}=${classVar}Service.getById(id);
		<#else>
		${class} ${classVar}=${classVar}Service.getById(${table.pkField});
		</#if>
		<#if hasScript==1||hasIdentity==1>
		if(BeanUtils.isEmpty(${classVar})){
			${classVar}=new ${class}();
		<#list fieldList as field>
		<#if field.valueFrom!=0>
		<#if (field.valueFrom==1||field.valueFrom==2)>
			String ${field.fieldName}_script="${field.script?trim}";
			${classVar}.set${field.fieldName?cap_first}(engine.executeObject(${field.fieldName}_script, null).toString());
		<#elseif (field.valueFrom==3)>
			String ${field.fieldName}_id=identityService.nextId("${field.identity}");
			${classVar}.set${(field.fieldName)?cap_first}(${field.fieldName}_id);
		</#if>
		</#if>
		<#if field.fieldType=="date">
			<#if field.isCurrentDateStr==1>
			${classVar}.set${field.fieldName?cap_first}(new Date());
			</#if>
		</#if>
		</#list>
		}
		</#if>
		<#if subTableList?size != 0>
		    <#list subTableList as subtable>
		    <#assign vars=subtable.variable>
		List<${subtable.variable.class}> ${subtable.variable.classVar}List=${classVar}Service.get${vars.classVar?cap_first}List(<#if table.isExternal==0>id<#else>${table.pkField}</#if>);
		    </#list>
		</#if>
		
		return getAutoView().addObject("${classVar}",${classVar})
		<#if subTableList?size != 0>
		    <#list subTableList as subtable>
							.addObject("${subtable.variable.classVar}List",${subtable.variable.classVar}List)
		    </#list>
		</#if>
		<#if flowKey?exists>
							.addObject("runId", runId)
		</#if>
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得${comment}明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看${comment}明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		<#if table.isExternal==0>
		Long id=RequestUtil.getLong(request,"id");
		${class} ${classVar}=${classVar}Service.getById(id);
		<#else>
		Long ${table.pkField}=RequestUtil.getLong(request,"id");
		${class} ${classVar}=${classVar}Service.getById(${table.pkField});
		</#if>
		<#if flowKey?exists>
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(<#if table.isExternal==0>id.toString()<#else>${table.pkField}.toString()</#if>);
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		</#if>
		<#if subTableList?size != 0>
		    <#list subTableList as subtable>
		    <#assign vars=subtable.variable>
		List<${vars.class}> ${vars.classVar}List=${classVar}Service.get${vars.classVar?cap_first}List(<#if table.isExternal==0>id<#else>${table.pkField}</#if>);
		    </#list>
		return getAutoView().addObject("${classVar}",${classVar})
			<#if flowKey?exists>
							.addObject("runId", runId)
			</#if>
		    <#list subTableList as subtable>
							.addObject("${subtable.variable.classVar}List",${subtable.variable.classVar}List)<#if !subtable_has_next>;</#if>
		    </#list>
		<#else>
		return getAutoView().addObject("${classVar}", ${classVar})<#if flowKey?exists>.addObject("runId", runId)</#if>;
		</#if>	
	}
	
<#if flowKey?exists>
	/**
	 * 流程url表单 绑定的表单明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@Action(description="表单明细")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		<#if table.isExternal==0>
		Long id=RequestUtil.getLong(request,"id");
		${class} ${classVar} = ${classVar}Service.getById(id);	
		<#else>
		Long ${table.pkField}=RequestUtil.getLong(request,"id");
		${class} ${classVar}=${classVar}Service.getById(${table.pkField});
		</#if>
		<#if subTableList?exists && subTableList?size != 0>
		    <#list subTableList as subtable>
		    <#assign vars=subtable.variable>
		List<${vars.class}> ${vars.classVar}List=${classVar}Service.get${vars.classVar?cap_first}List(<#if table.isExternal==0>id<#else>${table.pkField}</#if>);
		    </#list>
		return getAutoView().addObject("${classVar}",${classVar})
		    <#list subTableList as subtable>
							.addObject("${subtable.variable.classVar}List",${subtable.variable.classVar}List)<#if !subtable_has_next>;</#if>
		    </#list>
		<#else>
		return getAutoView().addObject("${classVar}", ${classVar});
		</#if>	
	}
	
	/**
	 * 流程url表单 绑定的表单编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("modify")
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		<#if table.isExternal==0>
		Long id=RequestUtil.getLong(request,"id");
		${class} ${classVar} = ${classVar}Service.getById(id);	
		<#else>
		Long ${table.pkField}=RequestUtil.getLong(request,"id");
		${class} ${classVar}=${classVar}Service.getById(${table.pkField});
		</#if>
		<#if subTableList?exists && subTableList?size != 0>
		    <#list subTableList as subtable>
		    <#assign vars=subtable.variable>
		List<${vars.class}> ${vars.classVar}List=${classVar}Service.get${vars.classVar?cap_first}List(<#if table.isExternal==0>id<#else>${table.pkField}</#if>);
		    </#list>
		return getAutoView().addObject("${classVar}",${classVar})
		    <#list subTableList as subtable>
							.addObject("${subtable.variable.classVar}List",${subtable.variable.classVar}List)<#if !subtable_has_next>;</#if>
		    </#list>
		<#else>
		return getAutoView().addObject("${classVar}", ${classVar});
		</#if>	
	}
	
	
	
	/**
	 * 启动流程
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 由于修改了流程启动方式，不需要这个方法，因此先注释
	@RequestMapping("run")
	@Action(description="启动流程")
	public void run(HttpServletRequest request, HttpServletResponse response,${class} ${classVar}) throws Exception
	{
		<#if table.isExternal==0>
		Long id=RequestUtil.getLong(request,"id",0L);
		<#else>
		Long ${table.pkField}=RequestUtil.getLong(request,"${table.pkField}",0L);
		</#if>
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	${class} ${classVar}Temd = ${classVar}Service.getById(${classVar}.get<#if table.isExternal==0>Id<#else>${table.pkField?cap_first}</#if>()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(${classVar}Temd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("${flowKey}");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(<#if table.isExternal==0>id<#else>${table.pkField}</#if>!=0L){
				if(isList==1){
				${classVar}=${classVar}Service.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				${classVar}Service.save(${classVar});
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				${classVar}.set<#if table.isExternal==0>Id<#else>${table.pkField?cap_first}</#if>(genId);
				processRunService.startProcess(processCmd);
				<#if subTableList?size==0>
				${classVar}Service.save(${classVar});
				<#else>
				${classVar}Service.save(${classVar});
				</#if>
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看${comment}任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<${class}> list=${classVar}Service.getMyTodoTask(userId, new QueryFilter(request,"${classVar}Item"));
		ModelAndView mv=this.getAutoView().addObject("${classVar}List",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看${comment}草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<${class}> list=${classVar}Service.getMyDraft(userId, new QueryFilter(request,"${classVar}Item"));
		ModelAndView mv=this.getAutoView().addObject("${classVar}List",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的${comment}实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<${class}> list=${classVar}Service.getMyEnd(userId, new QueryFilter(request,"${classVar}Item"));
		ModelAndView mv=this.getAutoView().addObject("${classVar}List",list);
		return mv;
	}
	</#if>
}
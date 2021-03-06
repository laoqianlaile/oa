package com.suneee.platform.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suneee.core.api.util.ContextUtil;
import com.suneee.core.log.SysAuditThreadLocalHolder;
import com.suneee.core.util.UniqueIdUtil;
import com.suneee.core.web.ResultMessage;
import com.suneee.core.web.controller.BaseFormController;
import com.suneee.core.web.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.suneee.core.api.util.ContextUtil;
import com.suneee.core.log.SysAuditThreadLocalHolder;
import com.suneee.core.util.UniqueIdUtil;
import com.suneee.core.web.ResultMessage;
import com.suneee.core.web.controller.BaseFormController;
import com.suneee.core.web.util.RequestUtil;
import com.suneee.platform.annotion.Action;
import com.suneee.platform.annotion.ActionExecOrder;
import com.suneee.platform.model.system.Seal;
import com.suneee.platform.model.system.SealRight;
import com.suneee.platform.model.system.SysAuditModelType;
import com.suneee.platform.service.system.SealRightService;
import com.suneee.platform.service.system.SealService;
import com.suneee.platform.service.system.SysFileService;
import com.suneee.platform.service.system.SysUserService;

/**
 * 对象功能:电子印章 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:raise
 * 创建时间:2012-08-29 11:26:00
 */
@Controller
@RequestMapping("/platform/system/seal/")
@Action(ownermodel=SysAuditModelType.SYSTEM_SETTING)
public class SealFormController extends BaseFormController
{
	@Resource
	private SealService sealService;
	
	@Resource
	private SealRightService sealRightService;
	
	@Resource
	private SysFileService sysFileService;
	
	@Resource
	private SysUserService sysUserService;
	
	/**
	 * 添加或更新电子印章。
	 * @param request
	 * @param response
	 * @param seal 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新电子印章",execOrder=ActionExecOrder.AFTER,
	detail="<#if isAdd>添加<#else>更新</#if>电子印章" +
			"【${SysAuditLinkService.getSealLink(Long.valueOf(sealId))}】")
	public void save(HttpServletRequest request, HttpServletResponse response, Seal seal,BindingResult bindResult) throws Exception
	{
		Long userId= ContextUtil.getCurrentUserId();
		seal.setBelongId(userId);
		seal.setSealPath(sysFileService.getById(seal.getAttachmentId()).getFilePath());
		
		String rightType= RequestUtil.getString(request,"rightType");
		String rightIds=RequestUtil.getString(request,"rightIds");
		String rightNames=RequestUtil.getString(request,"rightNames");
		
		ResultMessage resultMessage=validForm("seal", seal, bindResult, request);
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		boolean isadd=true;
		String resultMsg=null;
		if(seal.getSealId()==null){
			seal.setSealId(UniqueIdUtil.genId());
			sealService.add(seal);
			resultMsg="添加电子印章成功";
		}else{
			sealService.update(seal);
			resultMsg="更新电子印章成功";
			isadd=false;
		}
		SysAuditThreadLocalHolder.putParamerter("isAdd", isadd);
		SysAuditThreadLocalHolder.putParamerter("sealId", seal.getSealId().toString());
		//保存印章权限
		sealRightService.saveSealRight(seal.getSealId(),rightType,rightIds,rightNames,userId,SealRight.CONTROL_TYPE_SEAL);
		
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param sealid
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected Seal getFormObject(@RequestParam("sealId") Long sealId,Model model) throws Exception {
		logger.debug("enter Seal getFormObject here....");
		Seal seal=null;
		if(sealId!=null){
			seal=sealService.getById(sealId);
		}else{
			seal= new Seal();
		}
		return seal;
    }

}

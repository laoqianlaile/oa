package com.suneee.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.suneee.core.model.TaskExecutor;
import com.suneee.platform.model.bpm.BpmNodeUser;
import com.suneee.platform.model.system.SysUser;
import com.suneee.platform.service.bpm.CalcVars;
import com.suneee.platform.service.bpm.IBpmNodeUserCalculation;
import com.suneee.platform.service.system.SysUserService;
import com.suneee.core.model.TaskExecutor;
import com.suneee.platform.model.bpm.BpmNodeUser;
import com.suneee.platform.model.system.SysUser;

/**
 * 根据节点用户设置为“组织属性”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationOrgAttr implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;

	@Override
	public List<SysUser> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		List<SysUser> users = new ArrayList<SysUser>();
		try {
			users = sysUserService.getByOrgParam(bpmNodeUser.getCmpIds());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public String getTitle() {
		return "组织属性";
	}

	@Override
	public Set<TaskExecutor> getTaskExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		int extraceUser=bpmNodeUser.getExtractUser().intValue();
		List<SysUser> sysUsers = this.getExecutor(bpmNodeUser, vars);
		Set<TaskExecutor> uIdSet = BpmNodeUserUtil.getExcutorsByUsers(sysUsers, extraceUser);
		return uIdSet;
	}

	@Override
	public boolean supportMockModel() {
		return false;
	}

	@Override
	public List< PreViewModel> getMockModel(BpmNodeUser bpmNodeUser) {
		return null;
	}
	
	@Override
	public boolean supportPreView() {
		return true;
	}


}

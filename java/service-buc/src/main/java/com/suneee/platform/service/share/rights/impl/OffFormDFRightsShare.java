package com.suneee.platform.service.share.rights.impl;

import javax.annotation.Resource;

import com.suneee.platform.model.bus.BusQueryRule;
import com.suneee.platform.service.bus.BusQueryRuleService;
import org.springframework.stereotype.Service;

import com.suneee.platform.model.bus.BusQueryRule;
import com.suneee.platform.service.bus.BusQueryRuleService;
import com.suneee.platform.service.share.rights.DataTemplateVO;
import com.suneee.platform.service.share.rights.FormDFRightShare;

/**
 * @author as xianggang
 * 
 */
@Service
public class OffFormDFRightsShare extends FormDFRightShare{
	
	@Resource
    BusQueryRuleService busQueryRuleService;
	
	BusQueryRule busQueryRule;

	@Override
	public String getShareType() {
		return "offLineFormDF";
	}

	@Override
	public String getShareDesc() {
		// TODO Auto-generated method stub
		return "离线表单数据权限";
	}


	@Override
	public DataTemplateVO getDataObject(String id) {
		DataTemplateVO vo = new DataTemplateVO();
		BusQueryRule busQueryRule = busQueryRuleService.getById(Long.parseLong(id));
		vo.setDisplayField(busQueryRule.getDisplayField());
		vo.setExportField(busQueryRule.getExportField());
		vo.setFilterField(busQueryRule.getFilterField());
		vo.setPrintField(busQueryRule.getPrintField());
		return vo;
	}
	
	@Override
	public DataTemplateVO getDataTemplateVO(String ruleId) {
		busQueryRule = busQueryRuleService.getById(Long.parseLong(ruleId)); 
		DataTemplateVO vo = new DataTemplateVO();
		vo.setDisplayField(busQueryRule.getDisplayField());
		vo.setExportField(busQueryRule.getExportField());
		vo.setFilterField(busQueryRule.getFilterField());
		vo.setPrintField(busQueryRule.getPrintField());
		return vo;
	}

	@Override
	public void updateDataTemplateVO(DataTemplateVO vo) {
		busQueryRule.setDisplayField(vo.getDisplayField());
		busQueryRule.setExportField(vo.getExportField());
		busQueryRule.setFilterField(vo.getFilterField());
		busQueryRule.setPrintField(vo.getPrintField());
		busQueryRuleService.update(busQueryRule);
	}
}

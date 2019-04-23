package com.suneee.eas.oa.controller.car;import com.suneee.eas.common.component.QueryFilter;import com.suneee.eas.common.component.ResponseMessage;import com.suneee.eas.common.constant.FunctionConstant;import com.suneee.eas.common.constant.ModuleConstant;import com.suneee.eas.common.utils.ContextSupportUtil;import com.suneee.eas.common.utils.DateUtil;import com.suneee.eas.common.utils.RequestUtil;import com.suneee.eas.common.utils.StringUtil;import com.suneee.eas.flowable.service.ProcessCoreService;import com.suneee.eas.oa.model.car.CarApply;import com.suneee.eas.oa.model.car.CarAudit;import com.suneee.eas.oa.service.car.CarApplyService;import com.suneee.eas.oa.service.car.CarAuditService;import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;import org.flowable.engine.TaskService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestParam;import org.springframework.web.bind.annotation.RestController;import javax.servlet.http.HttpServletRequest;import java.util.HashMap;import java.util.List;import java.util.Map;/** * 车辆审批 * @author 子华 * @create 2018/9/4 */@RestController@RequestMapping(ModuleConstant.CAR_MODULE+ FunctionConstant.CAR_AUDIT)public class CarAuditApiController {    private static final Logger log = LogManager.getLogger(CarAuditApiController.class);    @Autowired    private CarAuditService auditService;    @Autowired    private TaskService taskService;    @Autowired    private ProcessCoreService processCoreService;    @Autowired    private CarApplyService carApplyService;    /**     * 获取车辆审批列表     * @param request     * @return     */    @RequestMapping("list")    public ResponseMessage list(HttpServletRequest request){        String startTime = RequestUtil.getString(request, "beginUseTime");        String endTime = RequestUtil.getString(request, "endUseTime");        String beginCreateTime = RequestUtil.getString(request, "beginCreateTime");        String endCreateTime = RequestUtil.getString(request, "endCreateTime");        QueryFilter filter=new QueryFilter("auditList",request);        if(StringUtil.isNotEmpty(startTime)){            filter.addFilter("beginUseTime", DateUtil.getDate(startTime));        }        if(StringUtil.isNotEmpty(endTime)){            filter.addFilter("endUseTime" , DateUtil.getDate(endTime));        }        if(StringUtil.isNotEmpty(beginCreateTime)){            filter.addFilter("beginCreateTime" , DateUtil.getDate(beginCreateTime));        }        if(StringUtil.isNotEmpty(endCreateTime)){            filter.addFilter("endCreateTime" , DateUtil.getDate(endCreateTime));        }        filter.addFilter("userId",ContextSupportUtil.getCurrentUserId());        filter.addFilter("enterpriseCode",ContextSupportUtil.getCurrentEnterpriseCode());        filter.addFilter("status",CarApply.STATUS_AUDIT_AUDITING);        return ResponseMessage.success("获取车辆审批列表成功", auditService.getPageBySqlKey(filter));    }    /**     * 获取审批历史     * @param request     * @return     */    @RequestMapping("listHis")    public ResponseMessage listHis(HttpServletRequest request){        String startTime = RequestUtil.getString(request, "beginUseTime");        String endTime = RequestUtil.getString(request, "endUseTime");        String beginCreateTime = RequestUtil.getString(request, "beginCreateTime");        String endCreateTime = RequestUtil.getString(request, "endCreateTime");        QueryFilter filter=new QueryFilter("auditHisList",request);        if(StringUtil.isNotEmpty(startTime)){            filter.addFilter("beginUseTime", DateUtil.getDate(startTime));        }        if(StringUtil.isNotEmpty(endTime)){            filter.addFilter("endUseTime" , DateUtil.getDate(endTime));        }        if(StringUtil.isNotEmpty(beginCreateTime)){            filter.addFilter("beginCreateTime" , DateUtil.getDate(beginCreateTime));        }        if(StringUtil.isNotEmpty(endCreateTime)){            filter.addFilter("endCreateTime" , DateUtil.getDate(endCreateTime));        }        filter.addFilter("userId",ContextSupportUtil.getCurrentUserId());        filter.addFilter("enterpriseCode",ContextSupportUtil.getCurrentEnterpriseCode());        return ResponseMessage.success("获取车辆审批历史列表成功",auditService.getPageBySqlKey(filter));    }    /**     * 获取车辆管理员审批列表     * @param request     * @return     */    @RequestMapping("adminAuditList")    public ResponseMessage adminAuditList(HttpServletRequest request){        QueryFilter filter=new QueryFilter("auditList",request);        filter.addFilter("userId",ContextSupportUtil.getCurrentUserId());        filter.addFilter("enterpriseCode",ContextSupportUtil.getCurrentEnterpriseCode());        filter.addFilter("status",CarApply.STATUS_CAR_ARRANGE_PENDING);        return ResponseMessage.success("获取车辆管理员审批列表成功",auditService.getPageBySqlKey(filter));    }    /**     * 根据任务ID获取详情内容     * @param taskId     * @return     */    @RequestMapping("getDetailByTaskId")    public ResponseMessage getDetailByTaskId(@RequestParam String taskId){        Map<String,Object> data=new HashMap<>();        CarApply carApply= (CarApply) taskService.getVariable(taskId,"form");        data.put("form",carApply);        QueryFilter filter=new QueryFilter("listAll");        filter.addFilter("targetId",carApply.getApplyId());        List<CarAudit> auditList = auditService.getListBySqlKey(filter);        data.put("auditList",auditList);        return ResponseMessage.success("获取审批详情",data);    }    /**     * 普通流程审批     * @return     */    @RequestMapping("doAudit")    public ResponseMessage doAudit(HttpServletRequest request){        int auditStatus = RequestUtil.getInt(request, "auditStatus");        String taskId = RequestUtil.getString(request, "taskId");        if(CarAudit.STATUS_YES == auditStatus){            //同意，跳到下个节点            auditService.doAudit(request);        }else if(CarAudit.STATUS_NO == auditStatus){            //不同意，驳回到发起人            auditService.auditDisAgree(taskId, CarApply.STATUS_AUDIT_FAIL);        }        return ResponseMessage.success("审批完毕");    }}
package com.suneee.platform.service.bpm;

import com.suneee.core.api.util.ContextUtil;
import com.suneee.core.db.IEntityDao;
import com.suneee.core.service.BaseService;
import com.suneee.core.util.UniqueIdUtil;
import com.suneee.core.web.util.CookieUitl;
import com.suneee.platform.dao.bpm.BpmRunLogDao;
import com.suneee.platform.dao.bpm.ProcessRunDao;
import com.suneee.platform.model.bpm.BpmRunLog;
import com.suneee.platform.model.bpm.ProcessRun;
import com.suneee.platform.model.system.SysUser;
import com.suneee.platform.model.system.SystemConst;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 对象功能:流程运行日志 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:heyifan
 * 创建时间:2012-08-06 13:56:42
 */
@Service
public class BpmRunLogService extends BaseService<BpmRunLog>
{
	@Resource
	private BpmRunLogDao dao;
	@Resource
	private ProcessRunDao processRunDao;
	
	public BpmRunLogService()
	{
	}
	
	@Override
	protected IEntityDao<BpmRunLog, Long> getEntityDao()
	{
		return dao;
	}	

	/**
	 * 通过用户ID获取用户操作的流程日志
	 * @param userId 用户ID
	 * @return
	 */
	public List<BpmRunLog> getByUserId(Long userId){
		List list=dao.getByUserId(userId);
		return list;
	}
	
	/**
	 * 通过流程运行ID获取流程的操作日志
	 * @param runId 流程运行ID
	 * @return
	 */
	public List<BpmRunLog> getByRunId(Long runId){		
		List list=dao.getByRunId(runId);
		return list;
	}
	
	/**
	 * 根据流程运行ID删除流程的操作日志
	 * @param runId
	 */
	public void delByRunId(Long runId){
		dao.delByRunId(runId);
	}
	
	/**
	 * 添加流程运行日志
	 * @param opType 操作类型
	 * @param memo 备注
	 */
	public void addRunLog(Long runId,Integer operatortype,String memo){
		ProcessRun processRun= processRunDao.getById(runId);
		this.addRunLog(processRun, operatortype, memo);
	}

	/**
	 * 添加流程运行日志。
	 * @param user
	 * @param runId
	 * @param operatortype
	 * @param memo
	 */
	public void addRunLog(SysUser user, Long runId, Integer operatortype, String memo){
		ProcessRun processRun= processRunDao.getById(runId);
		this.addRunLog(user,processRun, operatortype, memo);
	}
	
	/**
	 * 添加流程运行日志
	 * @param opType 操作类型
	 * @param memo 备注
	 */
	public void addRunLog(ProcessRun processRun,Integer operatortype,String memo){
		SysUser user=(SysUser) ContextUtil.getCurrentUser();
		Long userId = SystemConst.SYSTEMUSERID;
		String userName = SystemConst.SYSTEMUSERNAME;
		if(user!=null){
			userId = user.getUserId();
			userName = user.getFullname();
		}
		Date now= Calendar.getInstance().getTime();
		BpmRunLog runLog=new BpmRunLog();
		runLog.setId(UniqueIdUtil.genId());
		runLog.setUserid(userId);
		runLog.setUsername(userName);
		if(processRun != null){
			runLog.setRunid(processRun.getRunId());
			runLog.setProcessSubject(processRun.getSubject());
			runLog.setGlobalFlowNo(processRun.getGlobalFlowNo());
		}
		runLog.setCreatetime(now);
		runLog.setOperatortype(operatortype);
		runLog.setMemo(memo);
		runLog.setOrgCode(CookieUitl.getCurrentEnterpriseCode());
		dao.add(runLog);
	}
	
	/**
	 * 添加流程运行日志。
	 * @param user
	 * @param runId
	 * @param operatortype
	 * @param memo
	 */
	public void addRunLog(SysUser user,ProcessRun processRun,Integer operatortype,String memo){
		BpmRunLog runLog=new BpmRunLog();
		runLog.setId(UniqueIdUtil.genId());
		runLog.setUserid(user.getUserId());
		runLog.setUsername(user.getFullname());
		runLog.setRunid(processRun.getRunId());
		runLog.setProcessSubject(processRun.getSubject());
		runLog.setCreatetime(new Date());
		runLog.setOperatortype(operatortype);
		runLog.setMemo(memo);
		
		dao.add(runLog);
	}
}

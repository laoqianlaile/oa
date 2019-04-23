package com.suneee.platform.dao.system;

import com.suneee.core.db.BaseDao;
import com.suneee.core.web.util.CookieUitl;
import com.suneee.platform.model.system.SysOrgRole;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 对象功能:组织角色授权信息 Dao类
 * 开发公司:宏天
 * 开发人员:hotent
 * 创建时间:2012-10-30 09:55:49
 */
@Repository
public class SysOrgRoleDao extends BaseDao<SysOrgRole>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysOrgRole.class;
	}
	
	public boolean getCountByOrgidRoleid(Long orgId,Long roleId){		
		Map map=new HashMap();
		map.put("orgId", orgId);
		map.put("roleId", roleId);
		List<SysOrgRole> roles = this.getBySqlKey("getCountByOrgidRoleid", map);
		return roles.size() > 0;
	}

	public List<SysOrgRole> getRolesByOrgId(Long orgId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("enterpriseCode", CookieUitl.getCurrentEnterpriseCode());
		return this.getBySqlKey("getRolesByOrgId",params);
	}
	
	/**
	 * 根据角色ID删除组织角色授权信息
	 * @param roleId
	 */
	public void delByRoleId(Long roleId){
		this.delBySqlKey("delByRoleId", roleId);
	}
	
	/**
	 * 根据组织ID删除组织角色授权信息
	 * @param orgId
	 */
	public void delByOrgId(Long orgId){
		this.delBySqlKey("delByOrgId", orgId);
	}

	/**
	 * 根据组织路径删除组织与角色的绑定关系
	 */
	public void delByOrgPath(String path) {
		this.delBySqlKey("delByOrgPath", path);
	}

	public void delByOrgIdAndRoleId(Long orgId, Long roleId) {
		Map<String, Object> param =new HashMap<String, Object>();
		param.put("orgId", orgId);
		param.put("roleId", roleId);
		this.delBySqlKey("delByOrgIdAndRoleId", param);
	}
}
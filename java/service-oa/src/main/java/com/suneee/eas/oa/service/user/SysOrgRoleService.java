package com.suneee.eas.oa.service.user;import com.suneee.platform.model.system.SysOrgRole;import java.util.List;/** * @program: eas-parent * @description: 组织角色 * @author: liuhai * @create: 2018-08-15 10:08 **/public interface SysOrgRoleService {    List<SysOrgRole> getRolesByOrgId(Long orgId);    List<SysOrgRole> getRolesByOrgId(Long orgId,String enterpriseCode);}
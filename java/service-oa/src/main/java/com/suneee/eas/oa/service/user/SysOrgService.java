package com.suneee.eas.oa.service.user;import com.suneee.platform.model.system.SysOrg;import java.util.List;/** * @program: eas-parent * @description: 组织相关类 * @author: liuhai * @create: 2018-08-15 11:01 **/public interface SysOrgService {    List<SysOrg> getSysOrgList(Long userId, String enterpriseCode);}
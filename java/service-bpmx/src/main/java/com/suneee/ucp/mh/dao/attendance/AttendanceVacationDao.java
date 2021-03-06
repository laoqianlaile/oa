package com.suneee.ucp.mh.dao.attendance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.suneee.ucp.base.dao.UcpBaseDao;
import com.suneee.ucp.mh.model.attendance.AttendanceVacation;
/**
 *<pre>
 * 对象功能:假期类型 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2017-05-09 10:43:17
 *</pre>
 */
@Repository("ucpAttendanceVacationDao")
public class AttendanceVacationDao extends UcpBaseDao<AttendanceVacation>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AttendanceVacation.class;
	}

	public List<AttendanceVacation> checkRepeat(Long id, String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (id != null && id != 0) {
			params.put("id", id);
		}
		params.put("name", name);
		return this.getBySqlKey("checkRepeat", params);
	}
}
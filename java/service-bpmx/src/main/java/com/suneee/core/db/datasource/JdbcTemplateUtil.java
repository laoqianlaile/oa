/**
 * 描述：TODO
 * 包名：com.suneee.core.db.datasource
 * 文件名：JdbcTemplateUtil.java
 * 作者：User-mailto:chensx@jee-soft.cn
 * 日期2014-8-25-下午2:38:10
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.suneee.core.db.datasource;

import com.suneee.core.bpm.util.BpmConst;
import com.suneee.core.db.IRollBack;
import com.suneee.core.db.RollbackJdbcTemplate;
import com.suneee.core.mybatis.Dialect;
import com.suneee.core.page.PageBean;
import com.suneee.core.page.PageList;
import com.suneee.core.util.AppUtil;
import com.suneee.core.util.DialectUtil;
import com.suneee.core.util.StringUtil;
import com.suneee.datasource.DataSourceProperties;
import com.suneee.eas.common.datasource.DynamicDataSourceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * jdbc关于pagebean的查询！！！
 * 注意：里面的alias参数都是数据源的别名！！
 * 构建组：bpm_3.3
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-8-25-下午2:38:10
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Component
public class JdbcTemplateUtil {
	private static DataSourceProperties dataSourceProperties;

	@Autowired
	public void setDataSourceProperties(DataSourceProperties dataSourceProperties) {
		JdbcTemplateUtil.dataSourceProperties = dataSourceProperties;
	}

	/**
	 * 使用JdbcTemplate 执行SQL语句。
	 * 
	 * @param sql
	 */
	public static void execute(String sql) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute(sql);
	}

	/**
	 * 使用JdbcTemplate执行外部SQL语句。
	 * 
	 * @param dsName
	 *            数据源名称
	 * @param sql
	 *            SQL语句
	 * @throws Exception
	 */
	public static void execute(String dsName, String sql) throws Exception {
		DataSource dataSource = DataSourceUtil.getDataSourceByAlias(dsName);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute(sql);
	}

	/**
	 * 获取名称JdbcTemplate。
	 * 
	 * <pre>
	 * SQL语句使用下面模版的形式。
	 * 执行类似的语句。
	 * select * from test where name=:name;
	 * Map<String, Object> paramMap = new HashMap<String, Object>();  
	 * paramMap.put("name", "name5");
	 * </pre>
	 * 
	 * @param jdbcTemplate
	 * @return
	 */
	public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
	}

	/**
	 * 根据数据源别名获取NamedParameterJdbcTemplate对象。
	 * 
	 * @param alias
	 *            数据源别名。
	 * @return
	 */
	public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String alias) {
		try {
			String targetDataSourceName=DynamicDataSourceHolder.getDataSource();
			if (targetDataSourceName==null){
				targetDataSourceName=dataSourceProperties.getDefaultDatasource();
			}
			DataSource dataSource=dataSourceProperties.getDatasourceMap().get(targetDataSourceName);
			return new NamedParameterJdbcTemplate(dataSource);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取分页数据，List 为PageList。
	 * 
	 * @param alias
	 *            数据源别名
	 * @param pageBean
	 *            分页对象
	 * @param sql
	 *            分页sql语句
	 * @param rowMap
	 *            对象映射接口。
	 * @return
	 */
	public static PageList getPage(String alias, PageBean pageBean, String sql, RowMapper rowMap) {

		int pageSize = pageBean.getPageSize();
		int offset = pageBean.getFirst();

		Map map = new HashMap();

		Dialect dialect = null;
		try {
			dialect = DialectUtil.getDialectByDataSourceAlias(alias);
		} catch (Exception e) {
			return null;
		}
		String pageSql = dialect.getLimitString(sql, offset, pageSize);
		String totalSql = dialect.getCountSql(sql);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
		namedParameterJdbcTemplate = getNamedParameterJdbcTemplate(alias);
		List list = namedParameterJdbcTemplate.query(pageSql, map, rowMap);
		int total = namedParameterJdbcTemplate.queryForObject(totalSql, map,Integer.class);

		pageBean.setTotalCount(total);
		PageList pageList = new PageList();
		pageList.setPageBean(pageBean);
		pageList.addAll(list);

		return pageList;
	}

	public static <T> T getPage(String alias, String sql, ResultSetExtractor<T> rse, PageBean pageBean, Map<String, Object> params) {

		T result = null;
		NamedParameterJdbcTemplate template = null;
		template = getNamedParameterJdbcTemplate(alias);
		if (pageBean != null) {
			int pageSize = pageBean.getPageSize();
			int offset = pageBean.getFirst();
			Dialect dialect = null;
			try {
				dialect = DialectUtil.getDialectByDataSourceAlias(alias);
			} catch (Exception e) {
				return null;
			}

			String pageSql = dialect.getLimitString(sql, offset, pageSize);
			String totalSql = dialect.getCountSql(sql);
			result = template.query(pageSql, params, rse);
			int total = template.queryForObject(totalSql, params,Integer.class);
			pageBean.setTotalCount(total);
		} else {
			result = template.query(sql, params, rse);
		}
		return result;
	}

	public static PageList getPage(String alias, int currentPage, int pageSize, String sql, Map paraMap) {

		int offset = (currentPage - 1) * pageSize;

		Dialect dialect = null;
		try {
			dialect = DialectUtil.getDialectByDataSourceAlias(alias);
		} catch (Exception e) {
			return null;
		}

		String pageSql = dialect.getLimitString(sql, offset, pageSize);
		String totalSql = dialect.getCountSql(sql);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
		namedParameterJdbcTemplate = getNamedParameterJdbcTemplate(alias);
		List list = namedParameterJdbcTemplate.queryForList(pageSql, paraMap);
		int total = namedParameterJdbcTemplate.queryForObject(totalSql, paraMap,Integer.class);

		PageBean pageBean = new PageBean(currentPage, pageSize);

		pageBean.setTotalCount(total);

		PageList pageList = new PageList();

		pageList.addAll(list);

		pageList.setPageBean(pageBean);

		return pageList;
	}

	public static List getPage(String alias, String sql, Map<?, ?> paraMap, PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		return getPage(alias, currentPage, pageSize, sql, paraMap);
	}

	/**
	 * 因为service层不能切换当前的数据源，我们就手动new一个切换了数据源的JdbcTemplate
	 * 
	 * @param alias
	 *            :数据源别名
	 * @return JdbcTemplate
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @exception
	 * @since 1.0.0
	 */
	public static JdbcTemplate getNewJdbcTemplate(String alias) throws Exception {
		if(StringUtil.isEmpty(alias) || alias.equals(DataSourceUtil.DEFAULT_DATASOURCE) || alias.equals(BpmConst.LOCAL_DATASOURCE)){
			return (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		}
		Map<Object, Object> map = DataSourceUtil.getDataSources();
		DataSource ds = (DataSource) map.get(alias);
		return new JdbcTemplate(ds);
	}
	
	
	/**
	 * 执行SQL,并指定是否可以回滚。
	 * @param sql	：sql
	 * @param rollback	:是否启动事务回滚
	 * @return 
	 * Boolean
	 */
	public static Boolean executeSql(String sql, boolean rollback) {
		final JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		RollbackJdbcTemplate rollbackJdbcTemplate = (RollbackJdbcTemplate) AppUtil.getBean("rollbackJdbcTemplate");
		if (!rollback) {
			jdbcTemplate.execute(sql);
			return true;
		}

		Map<String, Object> param = new HashMap<String, Object>();
		Boolean b = (Boolean) rollbackJdbcTemplate.executeRollBack(new IRollBack() {

			@Override
			public Object execute(String script, Map<String, Object> map) {
				try {
					jdbcTemplate.execute(script);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

				return true;
			}
		}, sql, param);
		
		return b;
	}
}

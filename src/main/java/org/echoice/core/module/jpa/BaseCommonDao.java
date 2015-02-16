package org.echoice.core.module.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.web.paper.PageBean;
import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class BaseCommonDao {
	protected Logger logger=LoggerFactory.getLogger(getClass());
	@PersistenceContext  
	protected EntityManager entityManager;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	protected String dialect;
	
	public PageBean findPageHQL(String hql, int pageNo, int pageSize, Object... values) {
		Assert.hasText(hql);
		//Count查询
		String countHql = "select count(1) " + removeSelect(removeOrders(hql));
		Number totalCountN = findUnique(countHql, values);
		int totalCount=0;
		totalCount=totalCountN.intValue();
		PageBean  page=new PageBean(pageNo,pageSize,totalCount);
		if (totalCount < 1) return page;
		//实际查询返回分页对象
		int startIndex = page.getStartIndex()-1;
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
		page.setDataList(list);
		
		return page;
	}

	/**
	 * 基于jdbcTemplate SQL查询
	 * @param sql
	 * @param rowMapper
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public PageBean findPageSQL(String sql,RowMapper rowMapper,int pageNo, int pageSize, Object... values){
		String countQueryString = "select count(1) " + removeSelect(removeOrders(sql));
		int totalCount=getJdbcTemplate().queryForInt(countQueryString, values);
		PageBean  page=new PageBean(pageNo,pageSize,totalCount);
		if (totalCount < 1) return page;
		int startIndex = page.getStartIndex()-1;
		String limitSql=getDialectObj().getLimitString(sql, startIndex, pageSize);
		List listValue=new ArrayList();
		for (Object object2 : values) {
			listValue.add(object2);
		}
		
		if(checkMySql()){
			if(startIndex>0){
				listValue.add(startIndex);
			}
			listValue.add(page.getPageSize());
		}else{
			listValue.add(page.getEndIndex());
			if(startIndex>0){
				listValue.add(startIndex);
			}
		}
		logger.debug(limitSql);
		List list=getJdbcTemplate().query(limitSql, listValue.toArray(), rowMapper);
		page.setDataList(list);
		return page;
	}
	/**
	 * 基于jdbcTemplate SQL查询，需要传入总记录数Sql,取记录sql
	 * @param countSql
	 * @param listSql
	 * @param rowMapper
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public PageBean findPageSQL(String countSql,String listSql,RowMapper rowMapper,int pageNo, int pageSize, Object... values){
		String countQueryString = countSql;
		int totalCount=getJdbcTemplate().queryForInt(countQueryString, values);
		PageBean  page=new PageBean(pageNo,pageSize,totalCount);
		if (totalCount < 1) return page;
		int startIndex = page.getStartIndex()-1;
		String limitSql=getDialectObj().getLimitString(listSql, startIndex, pageSize);
		List listValue=new ArrayList();
		for (Object object2 : values) {
			listValue.add(object2);
		}
		
		if(checkMySql()){
			if(startIndex>0){
				listValue.add(startIndex);
			}
			listValue.add(page.getPageSize());
		}else{
			listValue.add(page.getEndIndex());
			if(startIndex>0){
				listValue.add(startIndex);
			}
		}
		
		logger.debug(limitSql);
		List list=getJdbcTemplate().query(limitSql, listValue.toArray(), rowMapper);
		page.setDataList(list);
		return page;
	}

	
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getEntityManager().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter((i+1), values[i]);
			}
		}
		return query;
	}
	
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).getSingleResult();
	}
	
	/**
	 * 去除hql/sql的select 子句，未考虑union的情况,，用于pagedQuery.
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql/sql的orderby 子句，用于pagedQuery.
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	protected Dialect getDialectObj(){
		try {
			Dialect dialectObj=(Dialect)Class.forName(getDialect()).newInstance();
			return dialectObj;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected boolean checkMySql(){
		if(StringUtils.indexOfIgnoreCase(getDialect(), "MySQL")!=-1){
			return true;
		}
		return false;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
}

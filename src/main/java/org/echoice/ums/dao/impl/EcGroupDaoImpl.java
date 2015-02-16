package org.echoice.ums.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcGroup;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
@Transactional
public class EcGroupDaoImpl extends BaseCommonDao {

	public List<EcGroup> findGroupTreeChild(Long parentId) {
		// TODO Auto-generated method stub
		String hql="select t from EcGroup t where t.parentId=? order by t.taxis asc,t.groupId asc";
		List<EcGroup> list=createQuery(hql,parentId).getResultList();
		return list;
	}

	public List<EcGroup> findGroupTreeChild(String parentIds) {
		// TODO Auto-generated method stub
		String hql="select t from EcGroup t where t.parentId in("+parentIds+")";
		List<EcGroup> list=createQuery(hql).getResultList();
		return list;
	}
	
	public List<EcGroup> findGroupListByIds(String ids) {
		// TODO Auto-generated method stub
		String hql="select t from EcGroup t where t.groupId in("+ids+")";
		List<EcGroup> list=createQuery(hql).getResultList();
		return list;
	}
	
	public void deleteGroupByIds(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from ec_group where group_id in("+ids+")";
		getJdbcTemplate().update(sql);
	}
	
	public List findGroupTreeParent() {
		// TODO Auto-generated method stub
		String hql="select distinct t.parentId from EcGroup t group by t.parentId order by t.parentId asc";
		List list=createQuery(hql).getResultList();
		return list;
	}
	
	
	public PageBean findPageCondition(EcGroup ecGroup, int pageNo,int pageSize) {
		// TODO Auto-generated method stub
		String hql="select t from EcGroup t where 1=1";
		List<Object> paramValues=Lists.newArrayList();
		if(StringUtils.isNotBlank(ecGroup.getName())){
			hql+=" and t.name like ?";
			paramValues.add("%"+ecGroup.getName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(ecGroup.getAlias())){
			hql+=" and t.alias like ?";
			paramValues.add("%"+ecGroup.getAlias().trim()+"%");
		}
		
		if(ecGroup.getParentId()!=null){
			hql+=" and t.parentId = ?";
			paramValues.add(ecGroup.getParentId());
		}
		hql+=" order by t.taxis asc,t.groupId desc";
		return findPageHQL(hql, pageNo, pageSize, paramValues.toArray());
	}
	@Transactional
	public int updateDrag(Long dragId,Long targetId){
		String hql="update EcGroup t set t.parentId=? where t.groupId=?";
		javax.persistence.Query query = createQuery(hql, new Object[]{targetId,dragId});
		return query.executeUpdate();
	}
	@Transactional
	public int countGroupUser(String ids){
		String sql="select count(*) from ec_user_group t where t.group_id in("+ids+")";;
		int count=getJdbcTemplate().queryForInt(sql);
		return count;
	}
	@Transactional
	public int updateGroupFullNameByProc(final int groupId) {
		Integer result = getJdbcTemplate().execute(new ConnectionCallback<Integer>() {
			public Integer doInConnection(Connection con) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				CallableStatement proc = null;
				proc = con.prepareCall("{ call P_SYNC_UMS_GROUP(?,?) }");
				proc.setInt(1, groupId);
				proc.registerOutParameter(2, Types.NUMERIC);
				proc.execute();
				int result = proc.getInt(2);
				return result;
			}
		});
		return result;
	}
}

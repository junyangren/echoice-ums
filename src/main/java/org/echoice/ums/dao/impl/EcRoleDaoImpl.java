package org.echoice.ums.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcRole;
import org.echoice.ums.web.view.RoleView;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public class EcRoleDaoImpl extends BaseCommonDao {
	public List<EcRole> findRoleTreeChild(Long parentId) {
		// TODO Auto-generated method stub
		String hql="select t from EcRole t where t.parentId=? order by t.taxis asc,t.roleId asc";
		List<EcRole> list=createQuery(hql,parentId).getResultList();
		return list;
	}
	
	public List<EcRole> findRoleByIDs(String roleIds) {
		// TODO Auto-generated method stub
		String hql="select t from EcRole t where t.roleId in("+roleIds+") order by t.taxis asc,t.roleId asc";
		List<EcRole> list=createQuery(hql).getResultList();
		return list;
	}
	
	public List findRoleTreeParent() {
		// TODO Auto-generated method stub
		String hql="select distinct t.parentId from EcRole t group by t.parentId order by t.parentId asc";
		List list=createQuery(hql).getResultList();
		return list;
	}
	
	public PageBean searchPageCondition(EcRole ecRole, int pageNo,int pageSize) {
		// TODO Auto-generated method stub
		
		List listParam=new ArrayList();
		String sql="select t1.role_id,t1.alias,t1.name,t1.status,t1.op_time,"+ 
			"t2.parentId,t2.parentName,t1.taxis"+
			" from ec_role t1 "+ 
			"left join "+ 
			"(select ta.role_id parentId,ta.name parentName from ec_role ta) t2 on t1.parent_id=t2.parentId"+
			" where 1=1";
		if(StringUtils.isNotBlank(ecRole.getName())){
			String strLike="%"+ecRole.getName()+"%";
			sql+=" and t1.name like ?";
			listParam.add(strLike);
		}
		
		if(StringUtils.isNotBlank(ecRole.getAlias())){
			String strLike="%"+ecRole.getAlias()+"%";
			sql+=" and t1.alias like ? ";
			listParam.add(strLike);
		}
		
		if(ecRole.getParentId()!=null){
			sql+=" and t1.parent_id =? ";
			listParam.add(ecRole.getParentId());
		}
		sql+=" order by t1.taxis asc,t1.role_id asc";
		
		PageBean pageBean=super.findPageSQL(sql,new RowMapper<RoleView>() {

			public RoleView mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				RoleView obj=new RoleView();
				obj.setRoleId(rs.getLong("role_id"));
				obj.setAlias(rs.getString("alias"));
				obj.setName(rs.getString("name"));
				obj.setStatus(rs.getString("status"));
				obj.setOpTime(rs.getTimestamp("op_time"));
				obj.setTaxis(rs.getLong("taxis"));
				obj.setParentId(rs.getLong("parentId"));
				obj.setParentName(rs.getString("parentName"));
				return obj;
			}
		}, pageNo, pageSize,listParam.toArray());
		return pageBean;
	}
	@Transactional
	public int updateDrag(Long dragId,Long targetId){
		String hql="update EcRole t set t.parentId=? where t.roleId=?";
		Query query = createQuery(hql, new Object[]{targetId,dragId});
		return query.executeUpdate();
	}
}

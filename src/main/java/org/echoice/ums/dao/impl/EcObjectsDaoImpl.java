package org.echoice.ums.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.web.view.ObjectsView;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
@Transactional
public class EcObjectsDaoImpl extends BaseCommonDao{
	/**
	public List<EcObjects> findByAlias(String alias){
		String hql="select t from EcObjects t where t.alias=?";
		return createQuery(hql, alias).getResultList();
	}
	
	public EcObjects getObjectsByAlias(String alias){
		List<EcObjects> list=findByAlias(alias);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<EcObjects> findChildObjects(Long parentId){
		String hql="select t from EcObjects t where t.parentId=? order by t.taxis asc,t.objId asc";
		return createQuery(hql, parentId).getResultList();
	}
	**/
	
	public PageBean searchPageCondition(EcObjects objects, int pageNo,int pageSize) {
		// TODO Auto-generated method stub
		List<Object> listParam=Lists.newArrayList();
		String sql="select t1.obj_id,t1.alias,t1.name,t1.icon,t1.type,t1.status,t1.op_time,"+ 
			"t2.parentId,t2.parentName,t1.taxis"+
			" from ec_objects t1 "+ 
			"left join "+ 
			"(select ta.obj_id parentId,ta.name parentName from ec_objects ta) t2 on t1.parent_id=t2.parentId"+
			" where 1=1";
		if(StringUtils.isNotBlank(objects.getName())){
			String strLike="%"+objects.getName().trim()+"%";
			sql+=" and t1.name like ?";
			listParam.add(strLike);
		}
		
		if(StringUtils.isNotBlank(objects.getAlias())){
			String strLike="%"+objects.getAlias().trim()+"%";
			sql+=" and t1.alias like ? ";
			listParam.add(strLike);
		}
		
		if(objects.getParentId()!=null){
			sql+=" and t1.parent_id =? ";
			listParam.add(objects.getParentId());
		}
		sql+=" order by t1.taxis asc,t1.obj_id desc";
		
		PageBean pageBean=super.findPageSQL(sql,new RowMapper<ObjectsView>() {

			public ObjectsView mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				ObjectsView obj=new ObjectsView(); 
				obj.setObjId(rs.getLong("obj_id"));
				obj.setAlias(rs.getString("alias"));
				obj.setName(rs.getString("name"));
				obj.setIcon(rs.getString("icon"));
				obj.setType(rs.getString("type"));
				obj.setStatus(rs.getString("status"));
				obj.setOpTime(rs.getTimestamp("op_time"));
				obj.setParentId(rs.getLong("parentId"));
				obj.setParentName(rs.getString("parentName"));
				obj.setTaxis(rs.getLong("taxis"));
				return obj;
			}
			
		},pageNo, pageSize,listParam.toArray());
		return pageBean;
	}
	public int updateDrag(Long dragId,Long targetId){
		String hql="update EcObjects t set t.parentId=? where t.objId=?";
		Query query = createQuery(hql, new Object[]{targetId,dragId});
		return query.executeUpdate();
	}
	
}

package org.echoice.ums.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;
import org.echoice.ums.domain.EcUserGroup;
import org.echoice.ums.web.view.EcUserInfoView;
import org.echoice.ums.web.view.EcUserView;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

public class EcUserDaoImpl extends BaseCommonDao {
	@Transactional
	public void saveUserGroup(EcUserGroup userGroup ){
		getEntityManager().persist(userGroup);
	}
	@Transactional
	public void save(EcUser ecUser,EcUserExtend ecUserExtend){
		if(ecUser.getUserId()==null){
			getEntityManager().persist(ecUser);
			ecUserExtend.setUserId(ecUser.getUserId());
			getEntityManager().merge(ecUserExtend);
		}else{
			getEntityManager().merge(ecUser);
			ecUserExtend.setUserId(ecUser.getUserId());
			getEntityManager().merge(ecUserExtend);
		}
	}
	
	public List<EcUser> findUserListByIds(String ids){
		String hql="select t from EcUser t where t.userId in("+ids+")";
		return createQuery(hql).getResultList();
	}
	
	public EcUserInfoView getByAlias(String alias){
		//List list=findBy("alias", alias);
		String hql="select t from EcUser t where t.alias=?";
		List list=createQuery(hql,alias).getResultList();
		if(list!=null&&list.size()>0){
			EcUser ecUser=(EcUser)list.get(0);
			String groupIds="";
			String groupNames="";
			EcUserExtend ecUserExtend=(EcUserExtend)getEntityManager().find(EcUserExtend.class, ecUser.getUserId());
			EcUserInfoView ecUserInfoView=new EcUserInfoView();
			List groupList=findGroupByUserId(ecUser.getUserId());
			for (Object object : groupList) {
				EcGroup ecGroup=(EcGroup)object;
				ecUserInfoView.setGroupId(ecGroup.getGroupId());
				ecUserInfoView.setGroupName(ecGroup.getName());
				groupIds+=ecGroup.getGroupId()+",";
				groupNames+=ecGroup.getName()+",";
				ecUserInfoView.setGroupIds(groupIds);
				ecUserInfoView.setGroupNames(groupNames);
			}
			try {
				BeanUtils.copyProperties(ecUserInfoView, ecUserExtend);
				BeanUtils.copyProperties(ecUserInfoView, ecUser);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ecUserInfoView;
		}
		return null;
	}
	
	public List findGroupByUserId(Long userId){
		String hql="select t3 from EcUserGroup t inner join t.ecUser t2 inner join t.ecGroup t3";
		hql+=" where t2.userId=?";
		return createQuery(hql,userId).getResultList();
	}
	
	public List findGroupByUserAlias(String userAlias){
		String hql="select t3 from EcUserGroup t inner join t.ecUser t2 inner join t.ecGroup t3";
		hql+=" where t2.alias=?";
		return createQuery(hql,userAlias).getResultList();
		//return find(hql, userAlias);
	}
	
	public List<EcGroup> findGroupByUserAndParenRoleAlias(String userAlias,String parentRoleAlias){
		String sql="select ecg.group_id,ecg.alias,ecg.name,ecg.note,ecg.taxis,ecg.op_time from ec_group ecg" +
				" where ecg.alias in" +
				"(select t2.alias from ec_users_assignmen t1, ec_role t2, ec_user t3" +
				" where t1.role_id = t2.role_id and t1.user_id = t3.user_id and t3.alias=?" +
				" and t2.parent_id in(select tbrole.role_id from ec_role tbrole where tbrole.alias =?))";
		//logger.info(sql);
		List<EcGroup> list=getJdbcTemplate().query(sql, new Object[]{userAlias,parentRoleAlias}, new RowMapper<EcGroup>(){

			public EcGroup mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				EcGroup ecGroup=new EcGroup();
				ecGroup.setGroupId(rs.getLong(1));
				ecGroup.setAlias(rs.getString(2));
				ecGroup.setName(rs.getString(3));
				ecGroup.setNote(rs.getString(4));
				ecGroup.setTaxis(rs.getLong(5));
				ecGroup.setOpTime(rs.getDate(6));
				return ecGroup;
			}});
		return list;
	}
	public EcUserInfoView getById(Long userId){
		EcUser ecUser=getEntityManager().find(EcUser.class, userId);
		EcUserExtend ecUserExtend=(EcUserExtend)getEntityManager().find(EcUserExtend.class, userId);
		EcUserInfoView ecUserInfoView=new EcUserInfoView();
		List groupList=findGroupByUserId(ecUser.getUserId());
		String groupIds="";
		String groupNames="";
		for (Object object : groupList) {
			EcGroup ecGroup=(EcGroup)object;
			ecUserInfoView.setGroupId(ecGroup.getGroupId());
			ecUserInfoView.setGroupName(ecGroup.getName());
			groupIds+=ecGroup.getGroupId()+",";
			//groupNames+=ecGroup.getName()+";";
			if(StringUtils.isNotBlank(ecGroup.getFullName())){
				groupNames+=ecGroup.getFullName()+";\n";
			}else{
				groupNames+=ecGroup.getName()+";";
			}
			
		}
		ecUserInfoView.setGroupIds(groupIds);
		ecUserInfoView.setGroupNames(groupNames);
		try {
			BeanUtils.copyProperties(ecUserInfoView, ecUserExtend);
			BeanUtils.copyProperties(ecUserInfoView, ecUser);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ecUserInfoView;
	}
	
	
	public PageBean findPageCondition(EcUserView ecUser, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		String hql2="select t2 from EcUserGroup t inner join t.ecUser t2 inner join t.ecGroup t3";
		hql2+=" where t3.groupId=?";
		hql2+=" order by t2.userId desc";
		PageBean pageBean=super.findPageHQL(hql2, pageNo, pageSize,ecUser.getGroupId());
		return pageBean;
	}

	public PageBean searchPageCondition(EcUserView ecUser, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		List list=new ArrayList();
		String hql2="select t from EcUser t where 1=1";
		if(StringUtils.isNotBlank(ecUser.getName())){
			String strLike="%"+ecUser.getName().trim()+"%";
			hql2+=" and t.name like ?";
			list.add(strLike);
		}
		
		if(StringUtils.isNotBlank(ecUser.getAlias())){
			String strLike="%"+ecUser.getAlias().trim()+"%";
			hql2+=" and t.alias like ? ";
			list.add(strLike);
		}
		hql2+=" order by t.userId desc";
		PageBean pageBean=super.findPageHQL(hql2, pageNo, pageSize,list.toArray());
		return pageBean;
	}
	
	public PageBean searchPageConditionSQL(EcUserView ecUser, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		List list=new ArrayList();
		StringBuilder sql=new StringBuilder();
		sql.append("select tf.user_id,tf.name,tf.alias,tf.password,tf.email,tf.status,tf.note,tf.taxis,tf.job_number from (");
		sql.append("select distinct t2.user_id,t2.name,t2.alias,t2.password,t2.email,t2.status,t2.note,t2.taxis,t2.job_number");
		sql.append(" from ec_user_group t1,ec_user t2,");
		if(ecUser.getGroupId()!=null){
			sql.append("(select * from ec_group ta1 start with ta1.group_id=? connect by prior ta1.group_id=ta1.parent_id) t3");
			list.add(ecUser.getGroupId());
		}else{
			sql.append("ec_group t3");
		}
		sql.append(" where t1.user_id=t2.user_id and t1.group_id=t3.group_id) tf where 1=1");
		if(StringUtils.isNotBlank(ecUser.getName())){
			String strLike="%"+ecUser.getName().trim()+"%";
			sql.append(" and tf.name like ?");
			list.add(strLike);
		}
		
		if(StringUtils.isNotBlank(ecUser.getAlias())){
			String strLike="%"+ecUser.getAlias().trim()+"%";
			sql.append(" and tf.alias like ?");
			list.add(strLike);
		}
		
		sql.append(" order by tf.user_id desc");
		PageBean pageBean=super.findPageSQL(sql.toString(), new RowMapper<EcUser>(){
			public EcUser mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				EcUser ecUser=new EcUser();
				ecUser.setUserId(rs.getLong("user_id"));
				ecUser.setAlias(rs.getString("alias"));
				ecUser.setName(rs.getString("name"));
				ecUser.setJobNumber(rs.getString("job_number"));
				ecUser.setTaxis(rs.getLong("taxis"));
				ecUser.setStatus(rs.getString("status"));
				return ecUser;
			}}, pageNo, pageSize, list.toArray());
		return pageBean;
	}
	
	public EcUserGroup getUserGroup(Long groupId, Long userId) {
		// TODO Auto-generated method stub
		String hql="select t from EcUserGroup t inner join t.ecUser t2 inner join t.ecGroup t3";
		hql+=" where t3.groupId=?";
		hql+=" and t2.userId=?";
		List<EcUserGroup> list=createQuery(hql, new Object[]{groupId,userId}).getResultList();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Transactional
	public void removeUserGroup(Long groupId){
		String sql="delete from ec_user_group t where t.ug_id=?";
		getJdbcTemplate().update(sql, new Object[]{groupId});
	}
	

}

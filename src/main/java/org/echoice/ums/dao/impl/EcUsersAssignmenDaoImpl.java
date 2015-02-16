package org.echoice.ums.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcRole;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserGroup;
import org.echoice.ums.domain.EcUsersAssignmen;
import org.echoice.ums.web.view.UserGroupTotalView;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public class EcUsersAssignmenDaoImpl extends BaseCommonDao {

	public boolean checkIsAssign(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		String hql="select count(1) from EcUsersAssignmen t where t.ecUser.userId=? and t.ecRole.roleId=?";
		Query query=createQuery(hql, new Object[]{userId,roleId});
		List<Number> list=query.getResultList();
		Number tmp=list.get(0);
		if(tmp.intValue()>0){
			return true;
		}else{
			return false;
		}
	}

	public boolean checkIsAssignByAlias(String userAlias, String roleAlias) {
		// TODO Auto-generated method stub
		String sql="select count(*) from ec_users_assignmen t1,ec_user t2,ec_role t3" +
				" where t1.user_id=t2.user_id and t1.role_id=t3.role_id" +
				" and t2.alias=? and t3.alias=?";
		int count=getJdbcTemplate().queryForInt(sql,new Object[]{userAlias,roleAlias});
		if(count>0){
			return true;
		}
		return false;
	}
	
	
	public boolean checkIsAssignUserGroup(Long userId,Long groupId) {
		// TODO Auto-generated method stub
		String hql="select count(*) from EcUserGroup t inner join t.ecUser t2 inner join t.ecGroup t3";
		hql+=" where t3.groupId=?";
		hql+=" and t2.userId=?";
		Query query=createQuery(hql, new Object[]{groupId,userId});
		List<Number> list=query.getResultList();
		Number tmp=list.get(0);
		if(tmp.intValue()>0){
			return true;
		}else{
			return false;
		}
	}

	public void remove(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		String hql="delete from EcUsersAssignmen t where t.ecUser.userId=? and t.ecRole.roleId=?";
		Query query=createQuery(hql, new Object[]{userId,roleId});
		query.executeUpdate();
	}

	public void removeBatch(Long[] userIds, Long[] roleIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < userIds.length; i++) {
			Long userId=userIds[i];
			for (int j = 0; j < roleIds.length; j++) {
				Long roleId=roleIds[j];
				remove(userId,roleId);
			}
		}
	}
	
	public void removeAssingGroup(Long[] userIds, Long[] groupIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < userIds.length; i++) {
			Long userId=userIds[i];
			for (int j = 0; j < groupIds.length; j++) {
				Long roleId=groupIds[j];
				removeGroup(userId,roleId);
			}
		}
	}
	
	public List<UserGroupTotalView> findUserGroupCount(Long[] userIds){
		String userIdsStr=StringUtils.join(userIds, ",");
		String sql="select count(*) groupCount,t.user_id from ec_user_group t where t.user_id in("+userIdsStr+") group by t.user_id";
		List<UserGroupTotalView> list=getJdbcTemplate().query(sql, new RowMapper<UserGroupTotalView>(){

			public UserGroupTotalView mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				UserGroupTotalView totalView=new UserGroupTotalView();
				totalView.setGroupCount(rs.getInt("groupCount"));
				totalView.setUserId(rs.getLong("user_id"));
				return totalView;
			}});
		return list;
	}
	
	public List<UserGroupTotalView> findUserGroupCount(Long[] userIds,Long[] groupIds){
		String userIdsStr=StringUtils.join(userIds, ",");
		String groupIdsStr=StringUtils.join(groupIds, ",");
		String sql="select count(*) groupCount,t.user_id from ec_user_group t where t.user_id in("+userIdsStr+") and t.group_id in("+groupIdsStr+") group by t.user_id";
		List<UserGroupTotalView> list=getJdbcTemplate().query(sql, new RowMapper<UserGroupTotalView>(){

			public UserGroupTotalView mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				UserGroupTotalView totalView=new UserGroupTotalView();
				totalView.setGroupCount(rs.getInt("groupCount"));
				totalView.setUserId(rs.getLong("user_id"));
				return totalView;
			}});
		return list;
	}
	
	public void removeGroup(Long userId, Long groupId) {
		// TODO Auto-generated method stub
		String hql="delete from EcUserGroup t where t.ecUser.userId=? and t.ecGroup.groupId=?";
		Query query=createQuery(hql, new Object[]{userId,groupId});
		query.executeUpdate();
	}
	
	public List findAssignRoleList(Long userId){
		String hql="select t3 from EcUsersAssignmen t inner join t.ecUser t2 inner join t.ecRole t3" +
				" where t2.userId=?";
		List list=createQuery(hql,new Object[]{userId}).getResultList();
		return list;
	}

	public List findAssignRoleList(String userAlias){
		String hql="select t3 from EcUsersAssignmen t inner join t.ecUser t2 inner join t.ecRole t3" +
				" where t2.alias=?";
		List list=createQuery(hql,new Object[]{userAlias}).getResultList();
		return list;
	}
	
	public List<EcRole> findAssignRoleList(Long userId,Long parnetRoleId){
		String hql="select t3 from EcUsersAssignmen t inner join t.ecUser t2 inner join t.ecRole t3" +
				" where t2.userId=? and t3.parentId=?";
		List<EcRole> list=createQuery(hql,new Object[]{userId,parnetRoleId}).getResultList();
		return list;
	}
	
	public void saveBatch(Long[] userIds, Long[] roleIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < userIds.length; i++) {
			Long userId=userIds[i];
			EcUser ecUser=new EcUser();
			ecUser.setUserId(userId);
			for (int j = 0; j < roleIds.length; j++) {
				Long roleId=roleIds[j];
				//根节点不能分配
				if(roleId==-1){
					continue;
				}
				boolean isAssign=checkIsAssign(userId,roleId);
				if(!isAssign){
					EcUsersAssignmen usersAssignmen=new EcUsersAssignmen();
					usersAssignmen.setEcUser(ecUser);
					EcRole ecRole=new EcRole();
					ecRole.setRoleId(roleId);
					usersAssignmen.setEcRole(ecRole);
					usersAssignmen.setOpTime(new Date());
					getEntityManager().persist(usersAssignmen);
				}
			}
		}
	}
	
	public void saveAssignGroups(Long[] userIds, Long[] groupIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < userIds.length; i++) {
			Long userId=userIds[i];
			EcUser ecUser=new EcUser();
			ecUser.setUserId(userId);
			for (int j = 0; j < groupIds.length; j++) {
				Long groupId=groupIds[j];
				//根节点不能分配
				if(groupId==-1){
					continue;
				}
				boolean isAssign=checkIsAssignUserGroup(userId,groupId);
				if(!isAssign){
					EcUserGroup ecUserGroup=new EcUserGroup();
					EcGroup ecGroup=new EcGroup();
					ecGroup.setGroupId(groupId);
					ecUserGroup.setEcGroup(ecGroup);
					ecUserGroup.setEcUser(ecUser);
					getEntityManager().merge(ecUserGroup);
				}
			}
		}
	}
}

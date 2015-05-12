package org.echoice.ums.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.dao.EcOperatorDao;
import org.echoice.ums.dao.mapper.RowMapperForObjects;
import org.echoice.ums.dao.mapper.RowMapperForOperatorView;
import org.echoice.ums.dao.mapper.RowMapperForRolePermissionView;
import org.echoice.ums.dao.mapper.RowMapperForUserPermissionView;
import org.echoice.ums.domain.EcAccssMode;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.domain.EcOperator;
import org.echoice.ums.domain.EcPermission;
import org.echoice.ums.domain.EcRole;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.web.view.EcUserView;
import org.echoice.ums.web.view.GroupPermissionView;
import org.echoice.ums.web.view.PermissionView;
import org.echoice.ums.web.view.UserPermissionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

public class EcPermissionDaoImpl extends BaseCommonDao {
	@Autowired
	private EcObjectsDao ecObjectsDao;
	@Autowired
	private EcOperatorDao ecOperatorDao;
	
	@Transactional
	public void savePermission(Long roleIds[],Long objId,Long accessIds[]){
		for (int i = 0; i < roleIds.length; i++) {
			EcRole ecRole=new EcRole();
			ecRole.setRoleId(roleIds[i]);
			for (int j = 0; j < accessIds.length; j++) {
				EcOperator ecOperator=ecOperatorDao.getEcOperator(accessIds[j], objId);
				if(ecOperator!=null){
					//判断该权限是否已经分配
					boolean isAssign=checkIsAssign(roleIds[i],ecOperator.getOperId());
					if(!isAssign){
						EcPermission ecPermission=new EcPermission();
						ecPermission.setEcOperator(ecOperator);
						ecPermission.setEcRole(ecRole);
						ecPermission.setOpTime(new Date());
						getEntityManager().merge(ecPermission);
					}
				}
			}
		}
	}
	@Transactional
	public void removePermission(Long roleIds[],Long objId,Long accessIds[]){
		for (int i = 0; i < roleIds.length; i++) {
			EcRole ecRole=new EcRole();
			ecRole.setRoleId(roleIds[i]);
			for (int j = 0; j < accessIds.length; j++) {
				EcOperator ecOperator=ecOperatorDao.getEcOperator(accessIds[j], objId);
				if(ecOperator!=null){
					remove(roleIds[i],ecOperator.getOperId());
				}
			}
		}
	}
	@Transactional
	public void removeRoleAssignUserByUaIdsArr(Long uaIds[]){
		for (int i = 0; i < uaIds.length; i++) {
			removeRoleAssignUserByUaId(uaIds[i]);
		}
	}
	@Transactional
	public void removeRoleAssignUserByUaId(Long uaId){
		String hql="delete from EcUsersAssignmen t where t.uaId=?";
		Query query=createQuery(hql, new Object[]{uaId});
		query.executeUpdate();
	}
	@Transactional
	public void removePermissionByPaIdsArr(Long paIds[]){
		for (int i = 0; i < paIds.length; i++) {
			removePermissionByPaId(paIds[i]);
		}
	}
	@Transactional
	public void removePermissionByPaId(Long paId){
		String hql="delete from EcPermission t where t.paId=?";
		Query query=createQuery(hql, new Object[]{paId});
		query.executeUpdate();
	}
	@Transactional
	public void remove(Long roleId,Long operId) {
		// TODO Auto-generated method stub
		String hql="delete from EcPermission t where t.ecRole.roleId=? and t.ecOperator.operId=?";
		Query query=createQuery(hql, new Object[]{roleId,operId});
		query.executeUpdate();
	}
	
	public boolean checkIsAssign(Long roleId,Long operId){
		String hql="select count(*) from EcPermission t where t.ecRole.roleId=? and t.ecOperator.operId=?";
		Query query=createQuery(hql, new Object[]{roleId,operId});
		List<Number> list=query.getResultList();
		Number tmp=list.get(0);
		if(tmp.intValue()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkIsAssign(String roleAlias, String objAlias,String accessModeAlias) {
		// TODO Auto-generated method stub
		String sql="select count(*) from "+
		"ec_users_assignmen t1,"+
		"ec_role t2,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7"+
		" where  t1.role_id=t2.role_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t2.alias=? and t6.alias=? and t7.alias=? and t2.status=? and t6.status=? and t7.status=?";
		logger.debug(sql);
		int count=getJdbcTemplate().queryForInt(sql, new Object[]{roleAlias,objAlias,accessModeAlias,"y","y","y"});
		if(count>0){
			return true;
		}
		return false;
	}
	
	public PageBean findRolePermissionPage(PermissionView permissionView,int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select t1.pa_id,t1.role_id,t1.oper_id,t3.obj_id,t3.alias,t3.name,t4.accss_id,t4.accessAlias,t4.accessName" +
				" from ec_permission t1,ec_operator t2,ec_objects t3," +
				"(select tb.accss_id as accss_id,tb.alias as accessAlias,tb.name as accessName from ec_accss_mode tb) t4" +//不同表名同名列重命名
				" where t1.oper_id=t2.oper_id" +
				" and t2.obj_id=t3.obj_id" +
				" and t2.accss_id=t4.accss_id";
				
		List paramList=new ArrayList();		
		if(permissionView.getRoleId()!=null){
			sql+=" and t1.role_id=?";
			paramList.add(permissionView.getRoleId());
		}
		
		
		if(StringUtils.isNotBlank(permissionView.getAccessName())){
			sql+=" and t4.accessName like ?";
			paramList.add("%"+permissionView.getAccessName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(permissionView.getObjName())){
			sql+=" and t3.name like ?";
			paramList.add("%"+permissionView.getObjName().trim()+"%");
		}
		
		sql+=" order by t3.obj_id asc,t4.accss_id asc";
		PageBean pageBean=super.findPageSQL(sql,new RowMapperForRolePermissionView(), pageNo, pageSize, paramList.toArray());
		return pageBean;
	}
	
	public PageBean findRoleAssingUserPage(Long roldId, int pageNo, int pageSize){
		String sql="select t1.ua_id, t2.user_id, t2.name, t2.alias, t2.email"+
		" from ec_users_assignmen t1, ec_user t2"+
		" where t1.user_id = t2.user_id"+
		" and t1.role_id=?";
		PageBean pageBean=findPageSQL(sql,new RowMapper(){

			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				EcUserView ecUser=new EcUserView();
				ecUser.setUaId(rs.getLong(1));
				ecUser.setUserId(rs.getLong(2));
				ecUser.setName(rs.getString(3));
				ecUser.setAlias(rs.getString(4));
				return ecUser;
			}}, pageNo, pageSize, new Object[]{roldId});
		
		return pageBean;
	}
	
	public PageBean findRoleAssingUserPage(Long roldId,String userAlias,String userName, int pageNo, int pageSize){
		String sql="select t1.ua_id, t2.user_id, t2.name, t2.alias, t2.email"+
		" from ec_users_assignmen t1, ec_user t2"+
		" where t1.user_id = t2.user_id";
		//" and t1.role_id=?";
		
		
		List param=new ArrayList();
		if(roldId!=null){
			sql+=" and t1.role_id=?";
			param.add(roldId);
		}
		
		if(StringUtils.isNotBlank(userAlias)){
			sql+=" and t2.alias=?";
			param.add(userAlias);
		}
		
		if(StringUtils.isNotBlank(userName)){
			sql+=" and t2.name like ?";
			param.add("%"+userName+"%");
		}
	
		PageBean pageBean=findPageSQL(sql,new RowMapper<EcUserView>(){

			public EcUserView mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				EcUserView ecUser=new EcUserView();
				ecUser.setUaId(rs.getLong(1));
				ecUser.setUserId(rs.getLong(2));
				ecUser.setName(rs.getString(3));
				ecUser.setAlias(rs.getString(4));
				return ecUser;
			}}, pageNo, pageSize, param.toArray());
		
		return pageBean;
	}
	
	public PageBean findUserPermissionPage(UserPermissionView permissionView, int pageNo, int pageSize){
		//用户+组角色权限
		String sql2="select t2.role_id,t2.alias,t2.name,"+
		"t5.obj_id,t5.objAlias,t5.objName,"+
		"t6.accss_id,t6.accessAlias,t6.accessName"+
		" from  "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union all "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_permission t3,"+
		"ec_operator t4,"+
		"(select ta.obj_id,ta.alias as objAlias,ta.name as objName from ec_objects ta) t5,"+
		"(select tb.accss_id as accss_id,tb.alias as accessAlias,tb.name as accessName from ec_accss_mode tb) t6"+
		" where t1.assign_role_id=t2.role_id"+
		"  and t2.role_id=t3.role_id"+
		"  and t3.oper_id=t4.oper_id"+
		"  and t4.obj_id=t5.obj_id"+
		"  and t4.accss_id=t6.accss_id";
		
		
		List paramList=new ArrayList();
		if(permissionView.getUserId()!=null){
			sql2+="  and t1.assign_user_id=?";
			paramList.add(permissionView.getUserId());
		}
		
		if(StringUtils.isNotBlank(permissionView.getRoleName())){
			sql2+="  and t2.name like ?";
			paramList.add("%"+permissionView.getRoleName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(permissionView.getObjName())){
			sql2+="  and t5.objName like ?";
			paramList.add("%"+permissionView.getObjName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(permissionView.getAccessName())){
			sql2+="  and t6.accessName like ?";
			paramList.add("%"+permissionView.getAccessName().trim()+"%");
		}
		
		
		sql2+=" order by t2.role_id asc,t5.obj_id asc,t6.accss_id asc";
		
		PageBean pageBean=super.findPageSQL(sql2,new RowMapperForUserPermissionView(), pageNo, pageSize, paramList.toArray());
		return pageBean;
	}

	public PageBean findGroupPermissionPage(GroupPermissionView permissionView, int pageNo, int pageSize){
		//String PERMISSION_FIELD_USER_ARR[]=new String[]{"roleId","roleAlias","roleName","objId","objAlias","objName","accessId","accessAlias","accessName"};
		String sql="select t2.role_id,t2.alias,t2.name,"+
					"t5.obj_id,t5.objAlias,t5.objName,"+
					"t6.accss_id,t6.accessAlias,t6.accessName"+
					" from  "+
					"ec_group_assignment t1,"+
					"ec_role t2,"+
					"ec_permission t3,"+
					"ec_operator t4,"+
					"(select ta.obj_id,ta.alias as objAlias,ta.name as objName from ec_objects ta) t5,"+
					"(select tb.accss_id as accss_id,tb.alias as accessAlias,tb.name as accessName from ec_accss_mode tb) t6"+
					" where  t1.role_id=t2.role_id"+
					"  and t2.role_id=t3.role_id"+
					"  and t3.oper_id=t4.oper_id"+
					"  and t4.obj_id=t5.obj_id"+
					"  and t4.accss_id=t6.accss_id";
		List paramList=new ArrayList();
		if(permissionView.getGroupId()!=null){
			sql+="  and t1.group_id=?";
			paramList.add(permissionView.getGroupId());
		}
		
		if(StringUtils.isNotBlank(permissionView.getRoleName())){
			sql+="  and t2.name like ?";
			paramList.add("%"+permissionView.getRoleName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(permissionView.getObjName())){
			sql+="  and t5.objName like ?";
			paramList.add("%"+permissionView.getObjName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(permissionView.getAccessName())){
			sql+="  and t6.accessName like ?";
			paramList.add("%"+permissionView.getAccessName().trim()+"%");
		}
		
		sql+=" order by t2.role_id asc,t5.obj_id asc,t6.accss_id asc";
		PageBean pageBean=super.findPageSQL(sql,new RowMapperForUserPermissionView(), pageNo, pageSize, paramList.toArray());
		
		return pageBean;
	}	
	
	public List showPermissionRoleList(Long objId,Long accessId){
		//String PERMISSION_FIELD_USER_ARR[]=new String[]{"roleId","roleAlias","roleName"};
		String sql="select t2.role_id,t2.alias,t2.name from "+
				"ec_permission t1,"+
				"ec_role t2,"+
				" ec_operator t3"+
				" where t1.role_id=t2.role_id "+
				" and t1.oper_id=t3.oper_id"+
				" and t3.obj_id=?"+
				" and t3.accss_id=?";
		List<UserPermissionView> rpList=getJdbcTemplate().query(sql, new Object[]{objId,accessId},new RowMapper<UserPermissionView>(){

			public UserPermissionView mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				UserPermissionView obj=new UserPermissionView();
				obj.setRoleId(rs.getLong("role_id"));
				obj.setRoleAlias(rs.getString("alias"));
				obj.setRoleName(rs.getString("name"));
				return obj;
			}} );
		
		return rpList;
	}
	
	public List showPermissionRoleList(Long objId,Long accessIds[]){
		String PERMISSION_FIELD_USER_ARR[]=new String[]{"roleId","roleAlias","roleName"};
		String sql="select distinct t2.role_id,t2.alias,t2.name from "+
				"ec_permission t1,"+
				"ec_role t2,"+
				" ec_operator t3"+
				" where t1.role_id=t2.role_id "+
				" and t1.oper_id=t3.oper_id"+
				" and t3.obj_id=?"+
				" and t3.accss_id in";
		Object tmpArr[]=new Object[accessIds.length+1];
		tmpArr[0]=objId;
		String inStr="(";
		for (int i = 0; i < accessIds.length; i++) {
			inStr+="?,";
			tmpArr[i+1]=accessIds[i];
		}
		inStr=inStr.substring(0,inStr.length()-1);
		inStr+=")";
		sql+=inStr;
		
		List<UserPermissionView> rpList=getJdbcTemplate().query(sql, tmpArr,new RowMapper<UserPermissionView>(){

			public UserPermissionView mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				UserPermissionView obj=new UserPermissionView();
				obj.setRoleId(rs.getLong("role_id"));
				obj.setRoleAlias(rs.getString("alias"));
				obj.setRoleName(rs.getString("name"));
				return obj;
			}} );
		return rpList;
	}

	public List findAssignPermissionAccessModeList(String userAlias,String objAlias) {
		// TODO Auto-generated method stub
		//用户+组角色权限
		String sql="select distinct t7.accss_id as accss_id,t7.alias as accessAlias,t7.name accessName,t7.status as accessStatus,t7.note as accessNote from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t3.alias=? and t6.alias=? order by t7.accss_id asc";
		logger.info(sql);
		List<EcAccssMode> list=getJdbcTemplate().query(sql, new Object[]{userAlias,objAlias}, new RowMapper<EcAccssMode>(){

			public EcAccssMode mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				EcAccssMode tmp=new EcAccssMode();
				tmp.setAccssId(rs.getLong(1));
				tmp.setAlias(rs.getString(2));
				tmp.setName(rs.getString(3));
				tmp.setStatus(rs.getString(4));
				tmp.setNote(rs.getString(5));
				return tmp;
			}});
		
		return list;
	}

	public List findAssignPermissionList(String userAlias) {
		// TODO Auto-generated method stub
		//用户+组角色权限
		String sql="select distinct t6.obj_id,t6.alias,t6.name,t7.accss_id,t7.alias,t7.name from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t3.alias=? and t2.status=? and t6.status=? and t7.status=? order by t6.obj_id asc";

		//logger.debug(sql);
		List list=getJdbcTemplate().query(sql, new Object[]{userAlias,"y","y","y"},new RowMapperForOperatorView());
		return list;
	}

	public List findAssignPermissionObjectList(String userAlias,String accessAlias) {
		// TODO Auto-generated method stub
		//用户+组角色权限
		String sql="select distinct t6.obj_id,t6.alias,t6.name,t6.type,t6.icon,t6.status,t6.parent_id,t6.taxis,t6.note,t6.op_time,t6.note2,t6.note3 from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t3.alias=? and t7.alias=? and t2.status=? and t6.status=? and t7.status=? order by t6.obj_id asc";		
		

		//logger.info(sql);
		List list=getJdbcTemplate().query(sql, new Object[]{userAlias,accessAlias,"y","y","y"},new RowMapperForObjects());
		return list;
	}

	public List findAssignPermissionObjectList(String userAlias,
			String accessAlias, String parentAlias) {
		// TODO Auto-generated method stub		
		//查找对象id
		EcObjects ecObjects=ecObjectsDao.getObjectsByAlias(parentAlias);
		if(ecObjects!=null){
			Long parentId=ecObjects.getObjId();
			//用户+组角色权限
			String sql="select distinct t6.obj_id,t6.alias,t6.name,t6.type,t6.icon,t6.status,t6.parent_id,t6.taxis,t6.note,t6.op_time,t6.note2,t6.note3 from "+
			"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
			" union "+
			"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
			"ec_role t2,"+
			"ec_user t3,"+
			"ec_permission t4,"+
			"ec_operator t5,"+
			"ec_objects t6,"+
			"ec_accss_mode t7"+
			" where  t1.assign_role_id=t2.role_id"+
			"  and t1.assign_user_id=t3.user_id"+
			"  and t2.role_id=t4.role_id"+
			"  and t4.oper_id=t5.oper_id"+
			"  and t5.obj_id=t6.obj_id"+
			"  and t5.accss_id=t7.accss_id"+
			"  and t3.alias=? and t7.alias=? and t6.parent_id=? and t2.status=? and t6.status=? and t7.status=? order by t6.obj_id asc";			
			

			//logger.info(sql);
			List list=getJdbcTemplate().query(sql, new Object[]{userAlias,accessAlias,parentId,"y","y","y"},new RowMapperForObjects());
			return list;			
		}
		return null;
	}

	public List findAssignPermissionObjectList(String userAlias,
			String accessAlias, Long parentIdArr[]) {
		// TODO Auto-generated method stub		
		//查找对象id
		//用户+组角色权限
		String parentIds=StringUtils.join(parentIdArr,",");
		
		String sql="select distinct t6.obj_id,t6.alias,t6.name,t6.type,t6.icon,t6.status,t6.parent_id,t6.taxis,t6.note,t6.op_time,t6.note2,t6.note3 from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t3.alias=? and t7.alias=? and t2.status=? and t6.status=? and t7.status=?" +
		"  and t6.parent_id in("+parentIds+")" +
		"  order by t6.obj_id asc";
		//logger.info(sql);
		List list=getJdbcTemplate().query(sql, new Object[]{userAlias,accessAlias,"y","y","y"},new RowMapperForObjects());
		return list;
	}
	
	public boolean isAssignPermission(String userAlias, String objAlias,
			String accessAlias) {
		// TODO Auto-generated method stub	
		//用户+组角色权限
		String sql="select count(*) from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t3.alias=? and t6.alias=? and t7.alias=? and t2.status=? and t6.status=? and t7.status=?";
		//logger.info(sql);
		int count=getJdbcTemplate().queryForInt(sql, new Object[]{userAlias,objAlias,accessAlias,"y","y","y"});
		if(count>0){
			return true;
		}
		return false;
	}
	
	public List findPermissionUser(String objectAlias,String accessAlias){
		String sql="select distinct t3.user_id,t3.alias,t3.name,t3.email,t3.status,t3.note from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t6.alias=? and t7.alias=? and t2.status=? and t6.status=? and t7.status=?";
		//logger.info(sql);		
		
		List list=getJdbcTemplate().query(sql, new Object[]{objectAlias,accessAlias,"y","y","y"},new RowMapper<EcUser>(){

			public EcUser mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				EcUser user=new EcUser();
				user.setUserId(rs.getLong(1));
				user.setAlias(rs.getString(2));
				user.setName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setStatus(rs.getString(5));
				user.setNote(rs.getString(6));
				return user;
			}});
		return list;
	}
	
	public List findAccessModeList(Long roleId,Long objId){
		String sql="select distinct t4.accss_id,t4.alias,t4.name,t4.status,t4.note" +
		" from ec_permission t1,ec_operator t2,ec_objects t3,ec_accss_mode t4" +" where t1.oper_id=t2.oper_id" +
		" and t2.obj_id=t3.obj_id" +
		" and t2.accss_id=t4.accss_id" +
		" and t1.role_id=?" +
		" and t3.obj_id=?" +
		" order by t4.accss_id asc";
		List<EcAccssMode> list=getJdbcTemplate().query(sql, new Object[]{roleId,objId}, new RowMapper<EcAccssMode>(){

			public EcAccssMode mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				EcAccssMode tmp=new EcAccssMode();
				tmp.setAccssId(rs.getLong(1));
				tmp.setAlias(rs.getString(2));
				tmp.setName(rs.getString(3));
				tmp.setStatus(rs.getString(4));
				tmp.setNote(rs.getString(5));
				return tmp;
			}});
		
		return list;
	}
	public EcOperatorDao getEcOperatorDao() {
		return ecOperatorDao;
	}
	
	public void setEcOperatorDao(EcOperatorDao ecOperatorDao) {
		this.ecOperatorDao = ecOperatorDao;
	}
	public EcObjectsDao getEcObjectsDao() {
		return ecObjectsDao;
	}

	public void setEcObjectsDao(EcObjectsDao ecObjectsDao) {
		this.ecObjectsDao = ecObjectsDao;
	}

	public static void main(String[] args) {
		Long a[]=new Long[]{new Long(123),new Long(321)};
		System.out.println(StringUtils.join(a,","));
	}
	
}

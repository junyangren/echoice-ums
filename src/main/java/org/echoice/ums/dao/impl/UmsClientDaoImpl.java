package org.echoice.ums.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.echoice.modules.encrypt.MD5;
import org.echoice.ums.dao.UmsClientDao;
import org.echoice.ums.dao.mapper.RowMapperForGroup;
import org.echoice.ums.dao.mapper.RowMapperForObjects;
import org.echoice.ums.dao.mapper.RowMapperForUser;
import org.echoice.ums.dao.mapper.RowMapperForUserExtend;
import org.echoice.ums.domain.EcAccssMode;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UmsClientDaoImpl implements UmsClientDao{
	private JdbcTemplate jdbcTemplate;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public List<EcObjects> findChildObjects(Long parentId) {
		// TODO Auto-generated method stub
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3 from ec_objects t " +
				"where t.status=? and t.parent_id=? order by t.taxis asc";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y",parentId},new RowMapperForObjects());
		return list;
	}

	public List<EcObjects> findChildObjects(Long parentId, String type) {
		// TODO Auto-generated method stub
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3 from ec_objects t " +
				"where t.status=? and t.parent_id=? and t.type=? order by t.taxis asc";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y",parentId,type},new RowMapperForObjects());
		return list;
	}

	public List<EcObjects> findChildObjects(Long parentId, String typeArr[]) {
		// TODO Auto-generated method stub
		String joinType="'"+StringUtils.join(typeArr, "','")+"'";
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3 from ec_objects t " +
				"where t.status=? and t.parent_id=? and t.type in("+joinType+")";
		sql+=" order by t.taxis asc";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y",parentId},new RowMapperForObjects());
		return list;
	}
	
	public List<Long> findParenObjects() {
		// TODO Auto-generated method stub
		String sql="select t.parent_id from ec_objects t";
		List<Long> list=getJdbcTemplate().query(sql,new RowMapper<Long>(){

			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getLong(1);
			}});
		return list;
	}

	public List<Long> findParenObjects(String typeArr[]) {
		// TODO Auto-generated method stub
		String joinType="'"+StringUtils.join(typeArr, "','")+"'";
		String sql="select t.parent_id from ec_objects t where t.type in("+joinType+")";
		List<Long> list=getJdbcTemplate().query(sql,new RowMapper<Long>(){

			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getLong(1);
			}});
		return list;
	}
	
	
	public List<Long> findParenObjects(String type) {
		// TODO Auto-generated method stub
		String sql="select t.parent_id from ec_objects t where t.type=?";
		List<Long> list=getJdbcTemplate().query(sql,new Object[]{type},new RowMapper<Long>(){

			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getLong(1);
			}});
		return list;
	}
	
	public List<EcObjects> findObjectsByParentAlias(String alias){
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3 from ec_objects t " +
				"where t.status=? and t.parent_id in(select t2.obj_id from ec_objects t2 where t2.alias=?) order by t.taxis asc";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y",alias},new RowMapperForObjects());
		return list;
	}

	public List<EcObjects> findObjectsByParentAlias(String alias,String type){
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3 from ec_objects t " +
				"where t.status=? and t.parent_id in(select t2.obj_id from ec_objects t2 where t2.alias=?) and t.type=? order by t.taxis asc";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y",alias,type},new RowMapperForObjects());
		return list;
	}
	
	public List<EcObjects> findObjectsByAccessModeAlias(String accessModealias){
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3" +
				" from ec_operator ta1,ec_objects t,ec_accss_mode ta3" +
				" where ta1.obj_id=t.obj_id and ta1.accss_id=ta3.accss_id and t.status=? and ta3.status=? and ta3.alias=? order by t.taxis asc";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y","y",accessModealias},new RowMapperForObjects());
		return list;
	}
	
	public List<EcObjects> findObjectsByType(String type){
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3 from ec_objects t " +
				"where t.status=? and t.type=? order by t.taxis asc";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y",type},new RowMapperForObjects());
		return list;
	}
	
	public EcObjects getObjectsByAlias(String alias) {
		// TODO Auto-generated method stub
		String sql="select t.obj_id,t.alias,t.name,t.type,t.icon,t.status,t.parent_id,t.taxis,t.note,t.op_time,t.note2,t.note3 from ec_objects t " +
		"where t.status=? and t.alias=?";
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{"y",alias},new RowMapperForObjects());
		if(list!=null&&list.size()>0){
			return (EcObjects)list.get(0);
		}
		return null;
	}
	
	public void updateUser(EcUser ecUser){
		//密码加密
		String password=ecUser.getAlias()+ecUser.getPassword();
		MD5 md5=new MD5();
		String md5Password=md5.getMD5ofStr(password);
	}
	
	public boolean updateUserPassword(String alias,String oldPassword,String newPassword){
		//
		MD5 md5=new MD5();
		String oldPasswordStr=alias+oldPassword;
		oldPasswordStr=md5.getMD5ofStr(oldPasswordStr);
		
		String sql="select count(*) from ec_user t2 where t2.alias=? and t2.password=?";
		int count=getJdbcTemplate().queryForInt(sql, new Object[]{alias,oldPasswordStr});
		if(count>0){
			String newPasswordStr=alias+newPassword;
			newPasswordStr=md5.getMD5ofStr(newPasswordStr);
			String sql2="update ec_user set password=? where alias=?";
			getJdbcTemplate().update(sql2, new Object[]{newPasswordStr,alias});
			return true;
		}
		return false;
	}
	
	public EcUser getUser(String userAlias){
		String sql="select t2.user_id,t2.alias,t2.name,t2.email from ec_user t2" +
				" where t2.alias=?";
		List<EcUser> list=getJdbcTemplate().query(sql, new Object[]{userAlias}, new RowMapperForUser());
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public EcUserExtend getEcUserExtend(Long userId){
		String sql="select user_id,mobile,tel,address,qq,msn,duty,leader_id from ec_user_extend t where t.user_id=?";
		List<EcUserExtend> list=getJdbcTemplate().query(sql, new Object[]{userId}, new RowMapperForUserExtend());
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public EcUser getUser(String userAlias,boolean isGroup){
		String sql="select t2.user_id,t2.alias,t2.name,t2.email from ec_user t2" +
				" where t2.alias=?";
		List<EcUser> list=getJdbcTemplate().query(sql, new Object[]{userAlias}, new RowMapperForUser());
		if(list!=null&&list.size()>0){
			EcUser ecUser=list.get(0);
			if(isGroup){
				List<EcGroup> groupList=findGroupsByUserAlias(userAlias);
				ecUser.getGroupList().addAll(groupList);
			}
			return ecUser;
		}
		return null;
	}
	
	public List<EcUser> findUserByGroupId(Long groupId){
		String sql="select t2.user_id,t2.alias,t2.name,t2.email from ec_user_group t1,ec_user t2 where t1.user_id=t2.user_id and t1.group_id=? and t2.status=?";
		List<EcUser> list=getJdbcTemplate().query(sql, new Object[]{groupId,"y"}, new RowMapperForUser());
		return list;
	}
	
	public List<EcGroup> findChildGroup(String parentGroupAlias){
		String sql="select t.group_id,t.name,t.alias,t.parent_id,t.note,t.op_time,t.full_name,t.group_path,t.note2,t.note3" +
				" from ec_group t where t.parent_id in(select t2.group_id from ec_group t2 where t2.alias=?) order by t.taxis asc,t.group_id asc";
		List<EcGroup> list=getJdbcTemplate().query(sql, new Object[]{parentGroupAlias},new RowMapperForGroup());
		return list;
	}
	
	public List<EcGroup> findChildGroup(Long parentId){
		String sql="select t.group_id,t.name,t.alias,t.parent_id,t.note,t.op_time,t.full_name,t.group_path,t.note2,t.note3" +
			" from ec_group t where t.parent_id=? order by t.taxis asc,t.group_id asc";
		List<EcGroup> list=getJdbcTemplate().query(sql, new Object[]{parentId},new RowMapperForGroup());
		return list;
	}
	
	public List<Long> findParenGroup(){
		String sql="select t.parent_id from ec_group t";
		List<Long> list=getJdbcTemplate().query(sql,new RowMapper<Long>(){

			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getLong(1);
			}});
		return list;
	}
	
	public List<EcObjects> findAssignPermissionObjectList(String userAlias,String accessAlias,Long parentId) {
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
		"  and t3.alias=? and t7.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=? and t6.parent_id=? order by t6.taxis asc,t6.obj_id asc";
		logger.debug(sql);
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{userAlias,accessAlias,"y","y","y","y",parentId},new RowMapperForObjects());
		return list;
	}
	
	public List<EcObjects> findAssignPermissionObjectList(String userAlias,String accessAlias) {
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
		"  and t3.alias=? and t7.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=? order by t6.taxis asc,t6.obj_id asc";
		logger.debug(sql);
		List<EcObjects> list=getJdbcTemplate().query(sql, new Object[]{userAlias,accessAlias,"y","y","y","y"},new RowMapperForObjects());
		return list;
	}
	
	public List<Long> findAssignPermissionObjectParent(String userAlias,String accessAlias) {
		//用户+组角色权限
		String sql="select distinct t6.parent_id from "+
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
		"  and t3.alias=? and t7.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=?";
		logger.debug(sql);
		List<Long> list=getJdbcTemplate().query(sql, new Object[]{userAlias,accessAlias,"y","y","y","y"},new RowMapper<Long>(){

			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getLong(1);
			}
			
		});
		return list;
	}
	
	public List<EcUser> findPermissionUser(String objectAlias,String accessAlias){
		String sql="select distinct t3.user_id,t3.alias,t3.name,t3.email from "+
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
		"  and t6.alias=? and t7.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=?";
		logger.debug(sql);		
		
		List<EcUser> list=getJdbcTemplate().query(sql, new Object[]{objectAlias,accessAlias,"y","y","y","y"},new RowMapperForUser());
		return list;
	}
	
	public List<EcUser> findPermissionUser(String groupAlias,String objectAlias,String accessAlias){
		String sql="select distinct t3.user_id,t3.alias,t3.name,t3.email from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7,"+
		"ec_user_group t8,ec_group t9"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t6.alias=? and t7.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=?"+
		"  and t8.user_id=t3.user_id and t8.group_id=t9.group_id and t9.alias=?";
		logger.debug(sql);
		
		List<EcUser> list=getJdbcTemplate().query(sql, new Object[]{objectAlias,accessAlias,"y","y","y","y",groupAlias},new RowMapperForUser());
		return list;
	}
	
	public List<EcUser> findPermissionUser(String groupAlias,String objectAlias,String accessAlias,boolean isChild){
		String sql="select distinct t3.user_id,t3.alias,t3.name,t3.email from "+
		"(select ta1.user_id assign_user_id,ta2.role_id assign_role_id from ec_user_group ta1,ec_group_assignment ta2 where ta1.group_id=ta2.group_id"+
		" union "+
		"select ta3.user_id assign_user_id,ta3.role_id assign_role_id from ec_users_assignmen ta3) t1,"+
		"ec_role t2,"+
		"ec_user t3,"+
		"ec_permission t4,"+
		"ec_operator t5,"+
		"ec_objects t6,"+
		"ec_accss_mode t7,"+
		"ec_user_group t8,ec_group t9"+
		" where  t1.assign_role_id=t2.role_id"+
		"  and t1.assign_user_id=t3.user_id"+
		"  and t2.role_id=t4.role_id"+
		"  and t4.oper_id=t5.oper_id"+
		"  and t5.obj_id=t6.obj_id"+
		"  and t5.accss_id=t7.accss_id"+
		"  and t6.alias=? and t7.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=?"+
		"  and t8.user_id=t3.user_id and t8.group_id=t9.group_id";
		if(isChild){
			sql+=" and t9.alias like ?";
			groupAlias+="%";
		}else{
			sql+=" and t9.alias =?";
		}
		logger.debug(sql);
		
		List<EcUser> list=getJdbcTemplate().query(sql, new Object[]{objectAlias,accessAlias,"y","y","y","y",groupAlias},new RowMapperForUser());
		return list;
	}
	
	public List<EcUser> findUserByRole(String roleAlias){
		String sql="select t2.user_id,t2.alias,t2.name,t2.email from ec_users_assignmen t1,ec_user t2,ec_role t3" +
				" where t1.user_id=t2.user_id and t1.role_id=t3.role_id and t3.alias=?";
		List<EcUser> list=getJdbcTemplate().query(sql, new Object[]{roleAlias}, new RowMapperForUser());
		return list;
	}
	
	public List<EcAccssMode> findAssignPermissionAccessModeList(String userAlias,String objAlias) {
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
		"  and t3.alias=? and t6.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=? order by t7.accss_id asc";
		logger.debug(sql);
		List<EcAccssMode> list=getJdbcTemplate().query(sql, new Object[]{userAlias,objAlias,"y","y","y","y"}, new RowMapper<EcAccssMode>(){

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
	
	public boolean checkHasPermission(String userAlias,String objAlias,String accessAlias) {
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
		"  and t3.alias=? and t6.alias=? and t7.alias=? and t2.status=? and t3.status=? and t6.status=? and t7.status=? order by t7.accss_id asc";
		//logger.debug(sql);
		int count=getJdbcTemplate().queryForInt(sql, new Object[]{userAlias,objAlias,accessAlias,"y","y","y","y"});
		if(count>0){
			return true;
		}
		return false;
	}
	
	public List<EcGroup> findGroupsByUserAlias(String userAlias) {
		// TODO Auto-generated method stub
		String sql="select t3.group_id,t3.name,t3.alias,t3.parent_id,t3.note,t3.op_time,t3.full_name,t3.group_path,t3.note2,t3.note3" +
		" from ec_user_group t1,ec_user t2,ec_group t3"+
		" where t1.user_id=t2.user_id and t1.group_id=t3.group_id and t2.alias=?";
		List<EcGroup> list=getJdbcTemplate().query(sql, new Object[]{userAlias},new RowMapperForGroup());
		return list;
	}
	
	public EcGroup getGroupByAlias(String alias){
		String sql="select t.group_id,t.name,t.alias,t.parent_id,t.note,t.op_time,t.full_name,t.group_path,t.note2,t.note3" +
			" from ec_group t where t.alias=?";
		List<EcGroup> list=getJdbcTemplate().query(sql, new Object[]{alias},new RowMapperForGroup());
		if(list!=null&&list.size()>0){
			return (EcGroup)list.get(0);
		}
		return null;
	}
	
	public EcGroup getGroupById(long id ){
		String sql="select t.group_id,t.name,t.alias,t.parent_id,t.note,t.op_time,t.full_name,t.group_path,t.note2,t.note3" +
			" from ec_group t where t.group_id=?";
		List<EcGroup> list=getJdbcTemplate().query(sql, new Object[]{id},new RowMapperForGroup());
		if(list!=null&&list.size()>0){
			return (EcGroup)list.get(0);
		}
		return null;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}

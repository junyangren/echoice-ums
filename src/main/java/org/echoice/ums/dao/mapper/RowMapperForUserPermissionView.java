package org.echoice.ums.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.echoice.ums.web.view.UserPermissionView;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperForUserPermissionView implements RowMapper<UserPermissionView>{

	public UserPermissionView mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		UserPermissionView obj=new UserPermissionView(); 
		obj.setRoleId(rs.getLong("role_id"));
		obj.setRoleAlias(rs.getString("alias"));
		obj.setRoleName(rs.getString("name"));
		obj.setObjId(rs.getLong("obj_id"));
		obj.setObjAlias(rs.getString("objAlias"));
		obj.setObjName(rs.getString("objName"));
		obj.setAccessId(rs.getLong("accss_id"));
		obj.setAccessAlias(rs.getString("accessAlias"));
		obj.setAccessName("accessName");			
		return obj;
	}

}

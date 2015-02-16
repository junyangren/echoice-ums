package org.echoice.ums.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.echoice.ums.web.view.RolePermissionView;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperForRolePermissionView implements RowMapper<RolePermissionView>{
	public RolePermissionView mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		RolePermissionView obj=new RolePermissionView();
		obj.setPaId(rs.getLong("pa_id"));
		obj.setRoleId(rs.getLong("role_id"));
		obj.setOperId(rs.getLong("oper_id"));
		obj.setObjId(rs.getLong("obj_id"));
		obj.setObjAlias(rs.getString("alias"));
		obj.setObjName(rs.getString("name"));
		obj.setAccessId(rs.getLong("accss_id"));
		obj.setAccessAlias(rs.getString("accessAlias"));
		obj.setAccessName(rs.getString("accessName"));
		return obj;
	}
}

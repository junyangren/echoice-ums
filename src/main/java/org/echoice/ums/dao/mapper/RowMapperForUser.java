package org.echoice.ums.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.echoice.ums.domain.EcUser;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperForUser implements RowMapper<EcUser>{
	public EcUser mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		EcUser user=new EcUser();
		user.setUserId(rs.getLong(1));
		user.setAlias(rs.getString(2));
		user.setName(rs.getString(3));
		user.setEmail(rs.getString(4));
		return user;
	}
}

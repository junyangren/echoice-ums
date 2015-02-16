package org.echoice.ums.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.echoice.ums.domain.EcGroup;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperForGroup implements RowMapper<EcGroup>{
	public EcGroup mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		EcGroup ecGroup=new EcGroup();
		ecGroup.setGroupId(rs.getLong(1));
		ecGroup.setName(rs.getString(2));
		ecGroup.setAlias(rs.getString(3));
		ecGroup.setParentId(rs.getLong(4));
		ecGroup.setNote(rs.getString(5));
		ecGroup.setOpTime(rs.getTimestamp(6));
		ecGroup.setFullName(rs.getString(7));
		ecGroup.setNote2(rs.getString(8));
		ecGroup.setNote3(rs.getString(9));
		return ecGroup;
	}
}

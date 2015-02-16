package org.echoice.ums.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.echoice.ums.domain.EcUserExtend;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperForUserExtend implements RowMapper<EcUserExtend>{
	public EcUserExtend mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		EcUserExtend tmp=new EcUserExtend();
		tmp.setUserId(rs.getLong("user_id"));
		tmp.setAddress(rs.getString("address"));
		tmp.setDuty(rs.getString("duty"));
		tmp.setLeaderId(rs.getLong("leader_id"));
		tmp.setMobile(rs.getString("mobile"));
		tmp.setMsn(rs.getString("msn"));
		tmp.setQq(rs.getString("qq"));
		tmp.setTel(rs.getString("tel"));
		return tmp;
	}
}

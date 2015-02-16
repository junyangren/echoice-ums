package org.echoice.ums.dao.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;
import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.domain.EcObjects;
import org.springframework.jdbc.core.RowMapper;



public class RowMapperForObjects implements RowMapper<EcObjects> {

	public EcObjects mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		EcObjects tmp=new EcObjects();
		for (int i = 0; i < ConfigConstants.JDBC_DOMAIN_ECOBJECTS_FIELDS.length; i++) {
			try {
				BeanUtils.copyProperty(tmp, ConfigConstants.JDBC_DOMAIN_ECOBJECTS_FIELDS[i], rs.getObject(i+1));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tmp;
	}
}

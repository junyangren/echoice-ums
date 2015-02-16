package org.echoice.ums.dao.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;
import org.echoice.ums.web.view.OperatorView;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperForOperatorView implements RowMapper<OperatorView>{

	public OperatorView mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		OperatorView tmp=new OperatorView();
		for (int i = 0; i < OperatorView.JDBC_FIELDS.length; i++) {
			try {
				BeanUtils.copyProperty(tmp, OperatorView.JDBC_FIELDS[i], rs.getObject(i+1));
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

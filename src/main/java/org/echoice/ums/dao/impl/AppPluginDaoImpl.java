package org.echoice.ums.dao.impl;

import java.util.List;

import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.ums.dao.AppPluginDao;
import org.echoice.ums.dao.mapper.RowMapperForGroup;
import org.echoice.ums.domain.EcGroup;

public class AppPluginDaoImpl extends BaseCommonDao implements AppPluginDao{
	public boolean checkStorageForGroup(String groupIds){
		String sql="select count(*) from ccm_storage_realtime t1,ec_group t2";
		sql+=" where t1.alias_dept=t2.alias and t2.group_id in("+groupIds+")";
		
		int count=getJdbcTemplate().queryForInt(sql);
		if(count>0){
			return false;
		}
		
		return true;
	}

	public int findOAUserList(String jobsNumbers){
		String sql="select count(distinct t.job_number) from oa_user t where t.job_number in("+jobsNumbers+")";
		int count=getJdbcTemplate().queryForInt(sql);
		return count;
	}
	
	public boolean checkUserWorkFlowTask(String userIds){
		String sql="select count(*) from (";
		sql+="select ta1.assignee_ taskUser from act_ru_task ta1 where ta1.assignee_ is not null";
		sql+=" union all ";
		sql+="select ta2.user_id_ taskUser from act_ru_identitylink ta2";
		sql+=") tb1 where exists(select * from ec_user tb2 where tb2.alias=tb1.taskUser and tb2.user_id in("+userIds+"))";
		int count=getJdbcTemplate().queryForInt(sql);
		if(count>0){
			return false;
		}
		return true;
	}
	
	public List<EcGroup> findUserGroupNotOA(String userIds){
		String sql="select distinct t2.group_id,t2.name,t2.alias,t2.parent_id,t2.note,t2.op_time,t2.full_name,t2.group_path,t2.note2,t2.note3";
		sql+=" from ec_user_group t1,ec_group t2,ec_user t3";
		sql+=" where t1.group_id=t2.group_id and t1.user_id=t3.user_id and t3.user_id in ("+userIds+")";
		sql+=" and not exists (select * from oa_user t4 where t4.job_number=t3.alias)";
		List<EcGroup> list=getJdbcTemplate().query(sql, new RowMapperForGroup());
		return list;
	}
}

package org.echoice.ums.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcGroupAssignment;
import org.echoice.ums.domain.EcRole;
import org.springframework.transaction.annotation.Transactional;


public class EcGroupAssignmentDaoImpl extends BaseCommonDao{
	
	public List findAssignRoleList(Long groupId){
		String hql="select t3 from EcGroupAssignment t inner join t.ecGroup t2 inner join t.ecRole t3" +
				" where t2.groupId=?";
		List list=createQuery(hql,new Object[]{groupId}).getResultList();
		return list;
	}
	
	public boolean checkIsAssign(Long groupId, Long roleId) {
		// TODO Auto-generated method stub
		String hql="select count(*) from EcGroupAssignment t where t.ecGroup.groupId=? and t.ecRole.roleId=?";
		Query query=createQuery(hql, new Object[]{groupId,roleId});
		List<Number> list=query.getResultList();
		Number tmp=list.get(0);
		if(tmp.intValue()>0){
			return true;
		}else{
			return false;
		}
	}
	@Transactional
	public void saveBatch(Long[] groupIds, Long[] roleIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < groupIds.length; i++) {
			Long groupId=groupIds[i];
			EcGroup ecGroup=new EcGroup();
			ecGroup.setGroupId(groupId);
			for (int j = 0; j < roleIds.length; j++) {
				Long roleId=roleIds[j];
				//根节点不能分配
				if(roleId==-1){
					continue;
				}
				boolean isAssign=checkIsAssign(groupId,roleId);
				if(!isAssign){
					EcGroupAssignment groupAssignment=new EcGroupAssignment();
					groupAssignment.setEcGroup(ecGroup);
					EcRole ecRole=new EcRole();
					ecRole.setRoleId(roleId);
					groupAssignment.setEcRole(ecRole);
					groupAssignment.setOpTime(new Date());
					getEntityManager().merge(groupAssignment);
				}
			}
		}
	}
	@Transactional
	public void remove(Long groupId, Long roleId) {
		// TODO Auto-generated method stub
		String hql="delete from EcGroupAssignment t where t.ecGroup.groupId=? and t.ecRole.roleId=?";
		Query query=createQuery(hql, new Object[]{groupId,roleId});
		query.executeUpdate();
	}
	@Transactional
	public void removeBatch(Long[] groupIds, Long[] roleIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < groupIds.length; i++) {
			Long groupId=groupIds[i];
			for (int j = 0; j < roleIds.length; j++) {
				Long roleId=roleIds[j];
				remove(groupId,roleId);
			}
		}
	}
}

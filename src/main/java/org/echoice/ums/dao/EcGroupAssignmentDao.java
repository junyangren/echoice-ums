package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcGroupAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcGroupAssignmentDao extends JpaRepository<EcGroupAssignment,Long>{
	public boolean checkIsAssign(Long groupId, Long roleId);
	public void saveBatch(Long groupIds[],Long roleIds[]);
	public void remove(Long groupId, Long roleId);
	public void removeBatch(Long[] groupIds, Long[] roleIds);
	public List findAssignRoleList(Long groupId);
}

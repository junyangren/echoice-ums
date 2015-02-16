package org.echoice.ums.dao;

import java.util.List;

import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcRoleDao extends JpaRepository<EcRole,Long> {
	public List<EcRole> findByAlias(String alias);
	public List findRoleTreeParent();
	public List findRoleTreeChild(Long parentId);
	public PageBean searchPageCondition(EcRole ecRole, int pageNo,int pageSize);
	public int updateDrag(Long dragId,Long targetId);
	/**
	 * 根据id号取角色列表数据
	 * @param roleIds
	 * @return
	 */
	public List<EcRole> findRoleByIDs(String roleIds);
	
}

package org.echoice.ums.dao;

import java.util.List;

import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcGroupDao extends JpaRepository<EcGroup,Long> {
	public List<EcGroup> findByAlias(String alias);
	public List findGroupTreeParent();
	public List findGroupTreeChild(Long parentId);
	public PageBean findPageCondition(EcGroup ecGroup, int pageNo,int pageSize);
	public int updateDrag(Long dragId,Long targetId);
	public List<EcGroup> findGroupListByIds(String ids);
	public List<EcGroup> findGroupTreeChild(String parentIds);
	/**
	 * ����ɾ���û�
	 * @param ids
	 */
	public void deleteGroupByIds(String ids);
	/**
	 * ��ѯָ���û������û�������
	 * @param ids
	 * @return
	 */
	public int countGroupUser(String ids);
	/**
	 * ͨ���洢���̸����û���ȫ��
	 * @param groupId
	 * @return
	 */
	public int updateGroupFullNameByProc(final int groupId);
}

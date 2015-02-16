package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcOperator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcOperatorDao extends JpaRepository<EcOperator,Long> {
	/**
	 * �������Ѷ���+����
	 * @param accssMode
	 * @param objects
	 */
	public void saveBatch(Long[] accssModeIds, Long[] objectsIds);
	/**
	 * ����ɾ��������Ӧ�Ķ���
	 * @param accssModeIds
	 * @param objectsIds
	 */
	public void removeBatch(Long[] accssModeIds, Long[] objectsIds);
	
	/**
	 * ɾ��������Ӧ�Ķ���
	 * @param accessId
	 * @param objId
	 */
	public void remove(Long accessId,Long objId);
	/**
	 * �ж϶����Ƿ��������Ӧ�Ĳ���
	 * @param accessId
	 * @param objId
	 * @return
	 */
	public boolean checkIsAssign(Long accessId,Long objId);
	/**
	 * �õ������ѷ���Ķ���
	 * @param accessId
	 * @return
	 */
	public List findObjectListByAccessId(Long accessId);
	/**
	 * �õ������ѷ������
	 * @param objId
	 * @return
	 */
	public List findAccessListByObjId(Long objId);
	/**
	 * ����+����
	 * @param accessId����
	 * @param objId����
	 * @return
	 */
	public EcOperator getEcOperator(Long accessId,Long objId);
}

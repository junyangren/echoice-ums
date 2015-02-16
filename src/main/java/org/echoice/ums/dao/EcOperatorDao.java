package org.echoice.ums.dao;

import java.util.List;

import org.echoice.ums.domain.EcOperator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcOperatorDao extends JpaRepository<EcOperator,Long> {
	/**
	 * 批量分醒对象+操作
	 * @param accssMode
	 * @param objects
	 */
	public void saveBatch(Long[] accssModeIds, Long[] objectsIds);
	/**
	 * 批量删除操作对应的对象
	 * @param accssModeIds
	 * @param objectsIds
	 */
	public void removeBatch(Long[] accssModeIds, Long[] objectsIds);
	
	/**
	 * 删除操作对应的对象
	 * @param accessId
	 * @param objId
	 */
	public void remove(Long accessId,Long objId);
	/**
	 * 判断对象是否分配了相应的操作
	 * @param accessId
	 * @param objId
	 * @return
	 */
	public boolean checkIsAssign(Long accessId,Long objId);
	/**
	 * 得到操作已分配的对象
	 * @param accessId
	 * @return
	 */
	public List findObjectListByAccessId(Long accessId);
	/**
	 * 得到对象已分配操作
	 * @param objId
	 * @return
	 */
	public List findAccessListByObjId(Long objId);
	/**
	 * 对象+操作
	 * @param accessId操作
	 * @param objId对象
	 * @return
	 */
	public EcOperator getEcOperator(Long accessId,Long objId);
}

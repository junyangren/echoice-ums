package org.echoice.ums.dao;

import java.util.List;

import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcObjects;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EcObjectsDao extends PagingAndSortingRepository<EcObjects,Long>,JpaSpecificationExecutor<EcObjects> {
	@Query("select t from EcObjects t where t.alias=?1")
	public List<EcObjects> findByAlias(String alias);
	/**
	 * 通过别名查找对象
	 * @param alias
	 * @return
	 */
	@Query("select t from EcObjects t where t.alias=?1")
	public EcObjects getObjectsByAlias(String alias);
	/**
	 * 通过父节点id,查找子节点对象
	 * @param parentId
	 * @return
	 */
	@Query("select t from EcObjects t where t.parentId=?1 order by t.taxis asc,t.objId asc")
	public List<EcObjects> findChildObjects(Long parentId);
	/**
	 * 根据条件分页查找资源对象
	 * @param objects
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */	
	public PageBean searchPageCondition(EcObjects objects, int pageNo,int pageSize);
	/**
	 * 查询所有的非叶子节点
	 * @return
	 */
	@Query("select distinct t.parentId from EcObjects t order by t.parentId asc")
	public List findAllParent();
	/**
	 * 对象节点移动转移
	 * @param dragId
	 * @param targetId
	 * @return
	 */
	public int updateDrag(Long dragId,Long targetId);
}

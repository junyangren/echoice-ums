package org.echoice.ums.dao;

import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcAccssMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EcAccssModeDao extends JpaRepository<EcAccssMode,Long>,JpaSpecificationExecutor<EcAccssMode> {
	/**
	 * ������������ҳ��ѯ��¼
	 * @param ecAccssMode
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean findPageCondition(EcAccssMode ecAccssMode, int pageNo,int pageSize);
}

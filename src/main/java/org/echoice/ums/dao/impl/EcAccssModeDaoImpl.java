package org.echoice.ums.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.echoice.core.module.jpa.BaseCommonDao;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.EcAccssMode;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
@Transactional
public class EcAccssModeDaoImpl extends BaseCommonDao{
	public PageBean findPageCondition(EcAccssMode ecAccssMode, int pageNo,int pageSize) {
		// TODO Auto-generated method stub
		String hql="select t from EcAccssMode t where 1=1";
		List<Object> paramValues=Lists.newArrayList();
		if(StringUtils.isNotBlank(ecAccssMode.getName())){
			hql+=" and t.name like ?";
			paramValues.add("%"+ecAccssMode.getName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(ecAccssMode.getAlias())){
			hql+=" and t.alias like ?";
			paramValues.add("%"+ecAccssMode.getAlias().trim()+"%");
		}
		return super.findPageHQL(hql, pageNo, pageSize, paramValues.toArray());
	}

}

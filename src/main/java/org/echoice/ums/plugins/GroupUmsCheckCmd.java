package org.echoice.ums.plugins;

import java.util.List;


import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.plugins.bean.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupUmsCheckCmd implements Command<ResultMsg, String>{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	private EcGroupDao ecGroupDao;
	public ResultMsg execute(String ids) {
		// TODO Auto-generated method stub
		logger.debug("用户组删除时，子节点及用户存在判断");
		
		ResultMsg resultMsg=new ResultMsg();
		resultMsg.setResult(true);
		List list=ecGroupDao.findGroupTreeChild(ids);
		if(list!=null&&list.size()>0){
			resultMsg.setResult(false);
			resultMsg.addMsg("{msg:\"存在子节点不能删除！！\",failure:true}");
			return resultMsg;
		}
		
		//判断是否存在用户
		int count=ecGroupDao.countGroupUser(ids);
		if(count>0){
			resultMsg.addMsg("{msg:\"用户组下存在用户不能删除！！\",failure:true}");
			return resultMsg;
		}
		
		return resultMsg;
	}
	public EcGroupDao getEcGroupDao() {
		return ecGroupDao;
	}
	public void setEcGroupDao(EcGroupDao ecGroupDao) {
		this.ecGroupDao = ecGroupDao;
	}

}

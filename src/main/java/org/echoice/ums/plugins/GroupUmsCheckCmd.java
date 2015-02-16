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
		logger.debug("�û���ɾ��ʱ���ӽڵ㼰�û������ж�");
		
		ResultMsg resultMsg=new ResultMsg();
		resultMsg.setResult(true);
		List list=ecGroupDao.findGroupTreeChild(ids);
		if(list!=null&&list.size()>0){
			resultMsg.setResult(false);
			resultMsg.addMsg("{msg:\"�����ӽڵ㲻��ɾ������\",failure:true}");
			return resultMsg;
		}
		
		//�ж��Ƿ�����û�
		int count=ecGroupDao.countGroupUser(ids);
		if(count>0){
			resultMsg.addMsg("{msg:\"�û����´����û�����ɾ������\",failure:true}");
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

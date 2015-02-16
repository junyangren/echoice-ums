package org.echoice.ums.plugins;

import org.echoice.ums.dao.AppPluginDao;
import org.echoice.ums.plugins.bean.ResultMsg;
/**
 * �û�ɾ��ʱ���ڴ����������
 * @author junyang
 *
 */
public class DelUserForTaskCheckCmd implements Command<ResultMsg, String>{
	private AppPluginDao appPluginDao;
	public ResultMsg execute(String userIds) {
		// TODO Auto-generated method stub
		ResultMsg resultMsg=new ResultMsg();
		boolean result=appPluginDao.checkUserWorkFlowTask(userIds);
		if(!result){
			resultMsg.addMsg("{msg:\"Ҫɾ�����û����д��쵥δ����ɾ��ʧ�ܣ���\",failure:true}");
		}
		resultMsg.setResult(result);
		return resultMsg;
	}
	public AppPluginDao getAppPluginDao() {
		return appPluginDao;
	}
	public void setAppPluginDao(AppPluginDao appPluginDao) {
		this.appPluginDao = appPluginDao;
	}

}

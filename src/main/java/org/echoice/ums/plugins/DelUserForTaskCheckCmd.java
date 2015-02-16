package org.echoice.ums.plugins;

import org.echoice.ums.dao.AppPluginDao;
import org.echoice.ums.plugins.bean.ResultMsg;
/**
 * 用户删除时存在待办任务过滤
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
			resultMsg.addMsg("{msg:\"要删除的用户还有待办单未处理，删除失败！！\",failure:true}");
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

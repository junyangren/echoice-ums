package org.echoice.ums.web.view;

import java.util.HashMap;
import java.util.Map;

public class ExtJsActionView {
	private boolean success=true;
	private Map<String, String> errorsMap=new HashMap();
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, String> getErrorsMap() {
		return errorsMap;
	}
	public void setErrorsMap(Map<String, String> errorsMap) {
		this.errorsMap = errorsMap;
	}
	
	public void addErrorCodeMsg(String code,String msg){
		errorsMap.put(code, msg);
	};
	
}

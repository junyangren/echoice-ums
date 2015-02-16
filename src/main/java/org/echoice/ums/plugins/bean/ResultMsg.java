package org.echoice.ums.plugins.bean;

public class ResultMsg {
	private boolean result=false;
	private StringBuilder msg=new StringBuilder();
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public void addMsg(String msgStr){
		msg.append(msgStr);
	}
	
	public String getMsg() {
		return msg.toString();
	}
	
}

package org.echoice.ums.service.bean;

public class AuthResp {
	private String respId;
	private String respTime;
	private boolean authPass;
	public String getRespId() {
		return respId;
	}
	public void setRespId(String respId) {
		this.respId = respId;
	}
	public boolean isAuthPass() {
		return authPass;
	}
	public void setAuthPass(boolean authPass) {
		this.authPass = authPass;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	
}

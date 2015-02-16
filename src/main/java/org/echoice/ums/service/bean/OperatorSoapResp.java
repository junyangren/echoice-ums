package org.echoice.ums.service.bean;

public class OperatorSoapResp {
	private int result;
	private OperatorSoapBean[] operatorSoapBeanArr;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public OperatorSoapBean[] getOperatorSoapBeanArr() {
		return operatorSoapBeanArr;
	}
	public void setOperatorSoapBeanArr(OperatorSoapBean[] operatorSoapBeanArr) {
		this.operatorSoapBeanArr = operatorSoapBeanArr;
	}
	
}

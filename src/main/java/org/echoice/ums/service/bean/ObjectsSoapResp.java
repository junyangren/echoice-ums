package org.echoice.ums.service.bean;

public class ObjectsSoapResp {
	private int result;
	private ObjectsSoapBean[] objectsSoapBeanArr;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public ObjectsSoapBean[] getObjectsSoapBeanArr() {
		return objectsSoapBeanArr;
	}
	public void setObjectsSoapBeanArr(ObjectsSoapBean[] objectsSoapBeanArr) {
		this.objectsSoapBeanArr = objectsSoapBeanArr;
	}
	
}

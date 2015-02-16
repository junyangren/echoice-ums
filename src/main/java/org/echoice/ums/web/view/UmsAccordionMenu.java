package org.echoice.ums.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.echoice.ums.domain.EcObjects;

public class UmsAccordionMenu implements Serializable{
	private EcObjects parentMenuObj;
	private List<EcObjects> subMenuObjList=new ArrayList<EcObjects>();
	public EcObjects getParentMenuObj() {
		return parentMenuObj;
	}
	public void setParentMenuObj(EcObjects parentMenuObj) {
		this.parentMenuObj = parentMenuObj;
	}
	public List<EcObjects> getSubMenuObjList() {
		return subMenuObjList;
	}
	public void setSubMenuObjList(List<EcObjects> subMenuObjList) {
		this.subMenuObjList = subMenuObjList;
	}
	
	public void addSumbMenuObj(EcObjects obj){
		this.subMenuObjList.add(obj);
	}
}

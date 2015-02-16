/**
 * 
 */
package org.echoice.ums.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * 
 * @Copyright: Copyright (c) 2009 FFCS All Rights Reserved
 *
 * @Company: 北京福富软件技术股份有限公司福州分公司
 *
 * @author Patrick Lau 
 * @time Nov 30, 2009 2:30:40 PM
 * @version 1.00.000
 * @history
 * 
 */
public class EcUmsAccordion {
	private String accordionName;
	private String menu_icon;
	private String menu_clickevent;
	private List<EcObjects> subMenuList=new ArrayList<EcObjects>();
	public String getAccordionName() {
		return accordionName;
	}
	public void setAccordionName(String accordionName) {
		this.accordionName = accordionName;
	}
	public String getMenu_icon() {
		return menu_icon;
	}
	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}
	public String getMenu_clickevent() {
		return menu_clickevent;
	}
	public void setMenu_clickevent(String menu_clickevent) {
		this.menu_clickevent = menu_clickevent;
	}
	public List<EcObjects> getSubMenuList() {
		return subMenuList;
	}
	public void setSubMenuList(List<EcObjects> subMenuList) {
		this.subMenuList = subMenuList;
	}
}

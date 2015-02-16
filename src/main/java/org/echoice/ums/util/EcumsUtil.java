package org.echoice.ums.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.echoice.ums.domain.EcObjects;

public class EcumsUtil {
	public static boolean isShow(HttpServletRequest request,String objAlias){
		
		List<EcObjects> list=(List<EcObjects>)request.getSession().getAttribute("permissionObjectList");
		for (EcObjects ecObjects : list) {
			if(objAlias.equalsIgnoreCase(ecObjects.getAlias())){
				return true;
			}
		}
		return false;
	}
}

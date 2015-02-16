package org.echoice.ums.util;

import javax.servlet.http.HttpServletRequest;

import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.domain.EcGroup;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.validation.Assertion;

public class CasUmsUtil {
    public static String getUser(HttpServletRequest request){
    	Object obj=request.getSession().getAttribute(AuthenticationFilter.CONST_CAS_ASSERTION);
    	if (obj instanceof Assertion) {
			Assertion assertion = (Assertion) obj;
			return assertion.getPrincipal().getName();
		}else{
			return (String)obj;
		}
    }
    
    public static boolean isAdmin(HttpServletRequest request){
    	return request.getSession().getAttribute(ConfigConstants.IS_SUPER_ADMIN)==null?false:true;
    }
    
    public static EcGroup getUserGroup(HttpServletRequest request){
    	return (EcGroup)request.getSession().getAttribute(ConfigConstants.UMS_GROUP_SESSION);
    }
    
    public static void setUserGroup(HttpServletRequest request,EcGroup group){
    	request.getSession().setAttribute(ConfigConstants.UMS_GROUP_SESSION, group);
    }
    
}

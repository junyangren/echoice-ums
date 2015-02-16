package org.echoice.ums.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.service.UmsServiceFactory;

public class GroupSelFilter implements Filter {
	private String allowedFiles;
	private String fileArr[];
	public final String GROUP_SEL_CHECK="GROUP_SEL_CHECK";
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.allowedFiles=filterConfig.getInitParameter("AllowedFiles");
		fileArr=StringUtils.splitByWholeSeparator(allowedFiles,"|");
	}
	
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest)req;
		EcGroup tmp=CasUmsUtil.getUserGroup(request);
		if(tmp==null){
			String uri=request.getRequestURI();
			int count=0;
			for (int i = 0; i < fileArr.length; i++) {
				if(StringUtils.indexOf(uri, fileArr[i])==-1){
					count++;
				}
			}
			
			if(count==fileArr.length){
				List<EcGroup> groupList= UmsServiceFactory.getEcUserDao().findGroupByUserAlias(CasUmsUtil.getUser(request));
				if(groupList!=null&&groupList.size()==1){					
					CasUmsUtil.setUserGroup(request, groupList.get(0));
				}else{
					request.setAttribute("groupList", groupList);
					request.getRequestDispatcher("/group_sel.jsp").forward(request, response);
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		allowedFiles=null;
		fileArr=null;
	}

}

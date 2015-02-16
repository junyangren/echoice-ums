package org.echoice.ums.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.dao.EcAccssModeDao;
import org.echoice.ums.domain.EcAccssMode;
import org.springframework.web.servlet.ModelAndView;

public class AccssModeController extends UmsBaseController {
	private EcAccssModeDao ecAccssModeDao;
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String optype=request.getParameter("optype");
		EcAccssMode ecAccssMode=new EcAccssMode();
		bindObject(request, ecAccssMode);
		int extjsPage[]=getExtJsPage(request);
		if(StringUtils.isNotBlank(optype)){
			extjsPage[1]=500;
		}
		//Page<EcAccssMode> page= getEcAccssModeDao().findAll(new PageRequest(0, 2));
		//logger.info(page.getNumber()+":"+page.getSize()+":"+page.getTotalElements()+":"+page.getTotalPages());
		PageBean pageBean=getEcAccssModeDao().findPageCondition(ecAccssMode, extjsPage[0], extjsPage[1]);
		//renderExtjsList(response, page.getContent(), page.getTotalElements(), new String[]{"ecOperators"});
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(), new String[]{"ecOperators"});
		return null;
	}
	/**
	 * 保存操作信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String accessMode=request.getParameter("accesModes");
		JSONArray jsonArray=JSONArray.fromObject(accessMode);
		List<EcAccssMode> collection= JSONArray.toList(jsonArray,EcAccssMode.class);
		for (EcAccssMode ecAccssMode : collection) {
			ecAccssMode.setAlias(ecAccssMode.getAlias().trim());
			ecAccssMode.setName(ecAccssMode.getName().trim());
			getEcAccssModeDao().save(ecAccssMode);
		}
		
		return null;
	}
	
	/**
	 * 删除操作信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String ids=request.getParameter("ids");
		JSONArray jsonArray=JSONArray.fromObject(ids);
		Object idsArr[]=jsonArray.toArray();
		for (int i = 0; i < idsArr.length; i++) {
			Long id=new Long((Integer)idsArr[i]);
			getEcAccssModeDao().delete(id);
		}
		
		return null;
	}

	
	public EcAccssModeDao getEcAccssModeDao() {
		return ecAccssModeDao;
	}
	public void setEcAccssModeDao(EcAccssModeDao ecAccssModeDao) {
		this.ecAccssModeDao = ecAccssModeDao;
	}

	
}

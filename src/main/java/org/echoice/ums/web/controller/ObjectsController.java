package org.echoice.ums.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.echoice.modules.utils.DateUtil;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.modules.web.json.bean.JSONTreeNode;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.web.view.ObjectsView;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class ObjectsController extends UmsBaseController{
	private EcObjectsDao ecObjectsDao;
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcObjects ecObjects=new EcObjects();
		bindObject(request, ecObjects);
		int extjsPage[]=getExtJsPage(request);
		PageBean pageBean=ecObjectsDao.searchPageCondition(ecObjects, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(), new String[]{"ecOperators"});
		return null;
	}

	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcObjects ecObjects=new EcObjects();
		bindObject(request, ecObjects);		
		ecObjects=getEcObjectsDao().findOne(ecObjects.getObjId());
		ObjectsView objectsView=new ObjectsView();
		BeanUtils.copyProperties(objectsView, ecObjects);
		if(ecObjects.getParentId()!=null){
			EcObjects ecObjectsParent=getEcObjectsDao().findOne(ecObjects.getParentId());
			if(ecObjectsParent!=null){
				objectsView.setParentName(ecObjectsParent.getName());
			}else{
				objectsView.setParentName("对象");
			}
		}
		renderExtjsObject(response, ecObjects, new String[]{"ecOperators"});
		return null;
	}
	
	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String ids=request.getParameter("ids");
		JSONArray jsonArray=JSONArray.fromObject(ids);
		Object idsArr[]=jsonArray.toArray();
		for (int i = 0; i < idsArr.length; i++) {
			Long id=new Long((Integer)idsArr[i]);
			List list=getEcObjectsDao().findChildObjects(id);
			if(list==null||list.size()==0){
				getEcObjectsDao().delete(id);
			}
		}
		return null;
	}
	/**
	 * 节点拖动
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView drag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String dragId=request.getParameter("dragId");
		String targetId=request.getParameter("targetId");
		getEcObjectsDao().updateDrag(Long.valueOf(dragId), Long.valueOf(targetId));
		return null;
	}
	/**
	 * 保存对象数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ExtJsActionView actionView=new ExtJsActionView(); 
		actionView.setSuccess(true);
		EcObjects ecObjects=new EcObjects();
		bindObject(request, ecObjects);
		ecObjects.setAlias(ecObjects.getAlias().trim());
		ecObjects.setName(ecObjects.getName().trim());
		if(ecObjects.getObjId()==null){
			List list=ecObjectsDao.findByAlias(ecObjects.getAlias());
			if(list!=null&&list.size()>0){
				actionView.addErrorCodeMsg("msg", "对不起，对象标识"+ecObjects.getAlias()+"已经存在，请换一个");
				renderExtjsActionView(response, actionView,true);
				return null;
			}else{
				actionView.addDataCodeMsg("leaf", "true");
			}
			
		}
		String serverPath=getServletContext().getRealPath("/");
		serverPath+=File.separator+"uploadfiles/images/icons"+File.separator;
		File file2=new File(serverPath);
		if(!file2.exists()){
			file2.mkdirs();
		}
		String fildPath="uploadfiles/images/icons"+File.separator;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator temp=multipartRequest.getFileNames();
		while(temp.hasNext()){
			String element = (String) temp.next();
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(element);
			if(StringUtils.isNotBlank(file.getOriginalFilename())){
				String dateStr=DateUtil.format(new Date(),"yyyyMMddHHmmss");
				String fileName=dateStr+"-"+file.getOriginalFilename();
				FileCopyUtils.copy(file.getBytes(), new File(serverPath+fileName));
				ecObjects.setIcon(fildPath+fileName);
			}
		}
		ecObjects.setOpTime(new Date());
		getEcObjectsDao().save(ecObjects);
		actionView.addDataCodeMsg("id", ConfigConstants.OBJECT_TREE+ecObjects.getObjId());
		actionView.addDataCodeMsg("text", ecObjects.getName());
		renderExtjsActionView(response, actionView,true);
		//renderExtjsActionView(response, actionView);
		return null;
	}
	/**
	 * 得到树型对象树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView tree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String parentId=request.getParameter("parentId");
		List list=ecObjectsDao.findAllParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		
		String strParentTree=bf.toString();
		
		List<EcObjects> childList=ecObjectsDao.findChildObjects(Long.valueOf(parentId));
		List<JSONTreeNode> listTree=new ArrayList<JSONTreeNode>();
		for (EcObjects ecObject : childList) {
			JSONTreeNode treeNode=new JSONTreeNode();
			treeNode.setId(ConfigConstants.OBJECT_TREE+ecObject.getObjId());
			treeNode.setText(ecObject.getName());
			if(strParentTree.indexOf("|"+ecObject.getObjId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			listTree.add(treeNode);
		}
		JSONArray jsonarr=JSONArray.fromObject(listTree);
		rendTextExtjs(response, jsonarr.toString());
		return null;
	}
	
	public ModelAndView assginAccessMode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	public EcObjectsDao getEcObjectsDao() {
		return ecObjectsDao;
	}
	
	public void setEcObjectsDao(EcObjectsDao ecObjectsDao) {
		this.ecObjectsDao = ecObjectsDao;
	}

	
}

package org.echoice.ums.plugins;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.domain.EcRole;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.service.UmsServiceFactory;

public class OAUserAassingRoleFilterCmd implements Command<Boolean,String>{
	private final String FILTER_ROLE_OBJECT_ALIAS="FILTER_ROLE_OBJECT_ALIAS";//必须验证OA工号的角色列表ids
	private final String FILTER_ROLE_OBJECT_ALIAS2="FILTER_ROLE_OBJECT_ALIAS2";//当层级大于X，且为末梢节点时，可以分配给该角色ids
	private final String FILTER_GTROUP_LEVEL_ALIAS="FILTER_GTROUP_LEVEL_ALIAS";//用户组层级大于X
	private Long userIdArr[];
	private Long roleIdsArr[];
	private String msg="非OA工号不可分配领导角色";
	public OAUserAassingRoleFilterCmd(Long[] userIdArr, Long[] roleIdsArr) {
		this.userIdArr = userIdArr;
		this.roleIdsArr = roleIdsArr;
	}
	
	public Boolean execute(String obj) {
		// TODO Auto-generated method stub
		EcObjects objects=UmsServiceFactory.getEcObjectsDao().getObjectsByAlias(FILTER_ROLE_OBJECT_ALIAS);
		String ids=StringUtils.join(userIdArr, ",");
		if(objects!=null){
			if(StringUtils.isNotBlank(objects.getNote())){
				String roleNameStr=findRoleNameStr(objects.getNote());
				if(StringUtils.isNotBlank(roleNameStr)){
					String configRoleArr[]=StringUtils.splitByWholeSeparator(objects.getNote(), ",");
					//查看要分配的角色中是否存在OA工号检查的
					boolean isCheckRole=false;
					String tmpId=null;
					for (int i = 0; i < roleIdsArr.length; i++) {
						tmpId=String.valueOf(roleIdsArr[i]);
						for (int k = 0; k < configRoleArr.length; k++) {
							if(tmpId.equals(configRoleArr[k])){
								isCheckRole=true;
								break;
							}
						}
					}
					
					if(isCheckRole){
						//根据用户Id，取用户数据
						List<EcUser> userList=UmsServiceFactory.getEcUserDao().findUserListByIds(ids);
						String jobNumberArr[]=new String[userList.size()];
						int j=0;
						for (EcUser ecUser : userList) {
							jobNumberArr[j]=ecUser.getAlias();
							j++;
						}
						int count=UmsServiceFactory.getAppPluginDao().findOAUserList("'"+StringUtils.join(jobNumberArr,"','")+"'");
						if(count!=jobNumberArr.length){
							msg="<div style='word-wrap: break-word;width:300px'>" +
									"非OA工号，不可分配（"+roleNameStr+"）角色" +
								"</div>";
							return false;
						}
					}
				}
			}
		}
		EcObjects objects2=UmsServiceFactory.getEcObjectsDao().getObjectsByAlias(FILTER_ROLE_OBJECT_ALIAS2);
		if(objects2!=null){
			if(StringUtils.isNotBlank(objects2.getNote())){
				//根据IDARRS取角色名称
				String roleNameStr=findRoleNameStr(objects2.getNote());
				if(StringUtils.isNotBlank(roleNameStr)){
					//查看选择的角色是否在限制的角色中
					String configRoleArr[]=StringUtils.splitByWholeSeparator(objects2.getNote(), ",");
					//查看要分配的角色中是否存在配置中要过滤的
					boolean isCheckRole=false;
					String tmpId=null;
					for (int i = 0; i < roleIdsArr.length; i++) {
						tmpId=String.valueOf(roleIdsArr[i]);
						for (int k = 0; k < configRoleArr.length; k++) {
							if(tmpId.equals(configRoleArr[k])){
								isCheckRole=true;
								break;
							}
						}
					}
					
					if(isCheckRole){
						//取用户的组层级，哪些角色可以跳过OA工号分配
						//如层级大于3，且为末梢节点
						List<EcGroup> groupList=UmsServiceFactory.getAppPluginDao().findUserGroupNotOA(ids);
						if(groupList!=null&&groupList.size()>0){
							EcGroupDao ecGroupDao=UmsServiceFactory.getEcGroupDao(); 
							List<Long> parentIdList=ecGroupDao.findGroupTreeParent();
							StringBuffer bf=new StringBuffer();
							bf.append("|");
							for (Long temp : parentIdList) {
								bf.append(temp);
								bf.append("|");
							}
							String strParentTree=bf.toString();
							//判断是否为末梢节点
							for (EcGroup ecGroup : groupList) {
								if(strParentTree.indexOf("|"+ecGroup.getGroupId()+"|")!=-1){
									//msg="非OA工号及末级库点下用户，不可分配领导审核角色";
									msg="<div style='word-wrap: break-word;width:300px'>" +
											"OA工号用户或末级库点下非OA工号用户，才可分配（"+roleNameStr+"）角色" +
										"</div>";
									return false;
								}
							}
							
							//取层级判断
							EcObjects objectsLevel=UmsServiceFactory.getEcObjectsDao().getObjectsByAlias(FILTER_GTROUP_LEVEL_ALIAS);
							if(objectsLevel!=null&&StringUtils.isNotBlank(objectsLevel.getNote())){
								int level=Integer.parseInt(objectsLevel.getNote());
								int groupLevel=0;
								for (EcGroup ecGroup : groupList) {
									groupLevel=StringUtils.splitByWholeSeparator(ecGroup.getAlias(), "-").length;
									if(groupLevel<level){
										//msg="非OA工号及库点层级大于"+level+"的末级库点下用户，不可分配领导审核角色";
										msg="<div style='word-wrap: break-word;width:300px'>" +
												"OA工号用户或层级大于"+level+"的末级库点下非OA工号用户，<br />才可分配["+roleNameStr+"]角色" +
											 "<div>";
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	private String findRoleNameStr(String roleIds){
		List<EcRole> roleList=UmsServiceFactory.getEcRoleDao().findRoleByIDs(roleIds);
		if(roleList!=null&&roleList.size()>0){
			String roleNameArr[]=new String[roleList.size()];
			int j=0;
			for (EcRole ecRole : roleList) {
				roleNameArr[j]=ecRole.getName();
				j++;
			}
			String roleNameStr=StringUtils.join(roleNameArr, ",");
			return roleNameStr;
		}
		return null;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}

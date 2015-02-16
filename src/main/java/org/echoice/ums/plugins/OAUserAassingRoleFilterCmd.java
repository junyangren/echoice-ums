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
	private final String FILTER_ROLE_OBJECT_ALIAS="FILTER_ROLE_OBJECT_ALIAS";//������֤OA���ŵĽ�ɫ�б�ids
	private final String FILTER_ROLE_OBJECT_ALIAS2="FILTER_ROLE_OBJECT_ALIAS2";//���㼶����X����Ϊĩ�ҽڵ�ʱ�����Է�����ý�ɫids
	private final String FILTER_GTROUP_LEVEL_ALIAS="FILTER_GTROUP_LEVEL_ALIAS";//�û���㼶����X
	private Long userIdArr[];
	private Long roleIdsArr[];
	private String msg="��OA���Ų��ɷ����쵼��ɫ";
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
					//�鿴Ҫ����Ľ�ɫ���Ƿ����OA���ż���
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
						//�����û�Id��ȡ�û�����
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
									"��OA���ţ����ɷ��䣨"+roleNameStr+"����ɫ" +
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
				//����IDARRSȡ��ɫ����
				String roleNameStr=findRoleNameStr(objects2.getNote());
				if(StringUtils.isNotBlank(roleNameStr)){
					//�鿴ѡ��Ľ�ɫ�Ƿ������ƵĽ�ɫ��
					String configRoleArr[]=StringUtils.splitByWholeSeparator(objects2.getNote(), ",");
					//�鿴Ҫ����Ľ�ɫ���Ƿ����������Ҫ���˵�
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
						//ȡ�û�����㼶����Щ��ɫ��������OA���ŷ���
						//��㼶����3����Ϊĩ�ҽڵ�
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
							//�ж��Ƿ�Ϊĩ�ҽڵ�
							for (EcGroup ecGroup : groupList) {
								if(strParentTree.indexOf("|"+ecGroup.getGroupId()+"|")!=-1){
									//msg="��OA���ż�ĩ��������û������ɷ����쵼��˽�ɫ";
									msg="<div style='word-wrap: break-word;width:300px'>" +
											"OA�����û���ĩ������·�OA�����û����ſɷ��䣨"+roleNameStr+"����ɫ" +
										"</div>";
									return false;
								}
							}
							
							//ȡ�㼶�ж�
							EcObjects objectsLevel=UmsServiceFactory.getEcObjectsDao().getObjectsByAlias(FILTER_GTROUP_LEVEL_ALIAS);
							if(objectsLevel!=null&&StringUtils.isNotBlank(objectsLevel.getNote())){
								int level=Integer.parseInt(objectsLevel.getNote());
								int groupLevel=0;
								for (EcGroup ecGroup : groupList) {
									groupLevel=StringUtils.splitByWholeSeparator(ecGroup.getAlias(), "-").length;
									if(groupLevel<level){
										//msg="��OA���ż����㼶����"+level+"��ĩ��������û������ɷ����쵼��˽�ɫ";
										msg="<div style='word-wrap: break-word;width:300px'>" +
												"OA�����û���㼶����"+level+"��ĩ������·�OA�����û���<br />�ſɷ���["+roleNameStr+"]��ɫ" +
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

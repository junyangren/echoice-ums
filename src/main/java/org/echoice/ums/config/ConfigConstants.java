package org.echoice.ums.config;

public class ConfigConstants {
	public final static String OBJECT_TREE="ec.object.tree.";
	public final static String OBJECT_ASSIGN_TREE="ec.object.assign.tree.";
	public final static String GROUP_TREE="ec.group.tree.";
	public final static String ROLE_TREE="ec.role.tree.";
	public final static String ROLE_ASSIGN_TREE="ec.role.assing.tree.";
	
	public final static String[] JDBC_DOMAIN_ECOBJECTS_FIELDS=new String[]{"objId","alias","name","type","icon","status","parentId","taxis","note","opTime","note2","note3"};
	public final static Long ADMIN_ROLE=new Long(1);
	
	public final static String IS_SUPER_ADMIN="ISADMIN";
	
	public final static String UMS_GROUP_SESSION="umsGroupSession";
	public final static String UMS_SPLIT_CHART="|";
	
	public final static String QUARTZ_JOBGRUP_KEY="QUARTZ_JOBGRUP_KEY_SYNCGROUP";
	public final static String QUARTZ_TRIGGER_KEY="QUARTZ_TRIGGER_KEY_SYNCGROUP";
	
	public final static String UMS_GROUP_ROOT_ID="umsGroupRootId";
	
}

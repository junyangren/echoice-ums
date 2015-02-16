package org.echoice.ums.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * EcGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_group")
@TableGenerator(name="ums_ec_group_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_group",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcGroup implements java.io.Serializable {

	// Fields

	private Long groupId;
	private String name;
	private String alias;
	private Long parentId;
	private String note;
	private String note2;
	private String note3;
	private Long taxis;
	private Date opTime;
	private String type;
	private Set<EcUserGroup> ecUserGroups = new HashSet<EcUserGroup>(0);
	private Set<EcGroupAssignment> ecGroupAssignments = new HashSet<EcGroupAssignment>(
			0);
	private String fullName;//全名
	private String groupPath;//组织树路径
	// Constructors

	/** default constructor */
	public EcGroup() {
	}

	/** full constructor */
	public EcGroup(String name, String alias, Long parentId, String note,
			Long taxis, Date opTime, Set<EcUserGroup> ecUserGroups,
			Set<EcGroupAssignment> ecGroupAssignments) {
		this.name = name;
		this.alias = alias;
		this.parentId = parentId;
		this.note = note;
		this.taxis = taxis;
		this.opTime = opTime;
		this.ecUserGroups = ecUserGroups;
		this.ecGroupAssignments = ecGroupAssignments;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_group_gen")
	@Column(name = "group_id", unique = true, nullable = false)
	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "alias", length = 50)
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "note2", length = 200)
	public String getNote2() {
		return this.note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}
	
	@Column(name = "note3", length = 200)
	public String getNote3() {
		return this.note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}
	
	@Column(name = "taxis")
	public Long getTaxis() {
		return this.taxis;
	}

	public void setTaxis(Long taxis) {
		this.taxis = taxis;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "op_time", length = 0)
	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecGroup")
	public Set<EcUserGroup> getEcUserGroups() {
		return this.ecUserGroups;
	}

	public void setEcUserGroups(Set<EcUserGroup> ecUserGroups) {
		this.ecUserGroups = ecUserGroups;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecGroup")
	public Set<EcGroupAssignment> getEcGroupAssignments() {
		return this.ecGroupAssignments;
	}

	public void setEcGroupAssignments(Set<EcGroupAssignment> ecGroupAssignments) {
		this.ecGroupAssignments = ecGroupAssignments;
	}
	@Column(name = "FULL_NAME", length = 512)
	//@Transient
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Column(name = "type", length = 50)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "GROUP_PATH", length = 512)
	//@Transient
	public String getGroupPath() {
		return groupPath;
	}

	public void setGroupPath(String groupPath) {
		this.groupPath = groupPath;
	}
	
}
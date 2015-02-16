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

/**
 * EcRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_role")
@TableGenerator(name="ums_ec_role_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_role",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcRole implements java.io.Serializable {

	// Fields

	private Long roleId;
	private String name;
	private String alias;
	private String status;
	private Long parentId;
	private String note;
	private Long taxis;
	private Date opTime;
	private Set<EcPermission> ecPermissions = new HashSet<EcPermission>(0);
	private Set<EcGroupAssignment> ecGroupAssignments = new HashSet<EcGroupAssignment>(
			0);
	private Set<EcUsersAssignmen> ecUsersAssignmens = new HashSet<EcUsersAssignmen>(
			0);

	// Constructors

	/** default constructor */
	public EcRole() {
	}

	/** full constructor */
	public EcRole(String name, String alias, String status, Long parentId,
			String note, Long taxis, Date opTime,
			Set<EcPermission> ecPermissions,
			Set<EcGroupAssignment> ecGroupAssignments,
			Set<EcUsersAssignmen> ecUsersAssignmens) {
		this.name = name;
		this.alias = alias;
		this.status = status;
		this.parentId = parentId;
		this.note = note;
		this.taxis = taxis;
		this.opTime = opTime;
		this.ecPermissions = ecPermissions;
		this.ecGroupAssignments = ecGroupAssignments;
		this.ecUsersAssignmens = ecUsersAssignmens;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_role_gen")
	@Column(name = "role_id", unique = true, nullable = false)
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	@Column(name = "status", length = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecRole")
	public Set<EcPermission> getEcPermissions() {
		return this.ecPermissions;
	}

	public void setEcPermissions(Set<EcPermission> ecPermissions) {
		this.ecPermissions = ecPermissions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecRole")
	public Set<EcGroupAssignment> getEcGroupAssignments() {
		return this.ecGroupAssignments;
	}

	public void setEcGroupAssignments(Set<EcGroupAssignment> ecGroupAssignments) {
		this.ecGroupAssignments = ecGroupAssignments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecRole")
	public Set<EcUsersAssignmen> getEcUsersAssignmens() {
		return this.ecUsersAssignmens;
	}

	public void setEcUsersAssignmens(Set<EcUsersAssignmen> ecUsersAssignmens) {
		this.ecUsersAssignmens = ecUsersAssignmens;
	}

}
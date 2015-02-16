package org.echoice.ums.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EcGroupAssignment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_group_assignment")
@TableGenerator(name="ums_ec_group_assignment_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_group_assignment",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcGroupAssignment implements java.io.Serializable {

	// Fields

	private Long gaId;
	private EcRole ecRole;
	private EcGroup ecGroup;
	private Date opTime;

	// Constructors

	/** default constructor */
	public EcGroupAssignment() {
	}

	/** full constructor */
	public EcGroupAssignment(EcRole ecRole, EcGroup ecGroup, Date opTime) {
		this.ecRole = ecRole;
		this.ecGroup = ecGroup;
		this.opTime = opTime;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_group_assignment_gen")
	@Column(name = "ga_id", unique = true, nullable = false)
	public Long getGaId() {
		return this.gaId;
	}

	public void setGaId(Long gaId) {
		this.gaId = gaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public EcRole getEcRole() {
		return this.ecRole;
	}

	public void setEcRole(EcRole ecRole) {
		this.ecRole = ecRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	public EcGroup getEcGroup() {
		return this.ecGroup;
	}

	public void setEcGroup(EcGroup ecGroup) {
		this.ecGroup = ecGroup;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "op_time", length = 0)
	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

}
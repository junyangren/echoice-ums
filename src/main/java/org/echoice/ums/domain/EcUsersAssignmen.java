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
 * EcUsersAssignmen entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_users_assignmen")
@TableGenerator(name="ums_ec_users_assignmen_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_users_assignmen",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcUsersAssignmen implements java.io.Serializable {

	// Fields

	private Long uaId;
	private EcRole ecRole;
	private EcUser ecUser;
	private Date opTime;

	// Constructors

	/** default constructor */
	public EcUsersAssignmen() {
	}

	/** full constructor */
	public EcUsersAssignmen(EcRole ecRole, EcUser ecUser, Date opTime) {
		this.ecRole = ecRole;
		this.ecUser = ecUser;
		this.opTime = opTime;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_users_assignmen_gen")
	@Column(name = "ua_id", unique = true, nullable = false)
	public Long getUaId() {
		return this.uaId;
	}

	public void setUaId(Long uaId) {
		this.uaId = uaId;
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
	@JoinColumn(name = "user_id")
	public EcUser getEcUser() {
		return this.ecUser;
	}

	public void setEcUser(EcUser ecUser) {
		this.ecUser = ecUser;
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
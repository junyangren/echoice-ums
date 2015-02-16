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
 * EcPermission entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_permission")
@TableGenerator(name="ums_ec_permission_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_permission",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcPermission implements java.io.Serializable {

	// Fields

	private Long paId;
	private EcRole ecRole;
	private EcOperator ecOperator;
	private Date opTime;

	// Constructors

	/** default constructor */
	public EcPermission() {
	}

	/** full constructor */
	public EcPermission(EcRole ecRole, EcOperator ecOperator, Date opTime) {
		this.ecRole = ecRole;
		this.ecOperator = ecOperator;
		this.opTime = opTime;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_permission_gen")
	@Column(name = "pa_id", unique = true, nullable = false)
	public Long getPaId() {
		return this.paId;
	}

	public void setPaId(Long paId) {
		this.paId = paId;
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
	@JoinColumn(name = "oper_id")
	public EcOperator getEcOperator() {
		return this.ecOperator;
	}

	public void setEcOperator(EcOperator ecOperator) {
		this.ecOperator = ecOperator;
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
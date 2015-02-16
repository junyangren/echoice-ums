package org.echoice.ums.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * EcOperator entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_operator")
@TableGenerator(name="ums_ec_operator_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_operator",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcOperator implements java.io.Serializable {

	// Fields

	private Long operId;
	private EcAccssMode ecAccssMode;
	private EcObjects ecObjects;
	private Set<EcPermission> ecPermissions = new HashSet<EcPermission>(0);

	// Constructors

	/** default constructor */
	public EcOperator() {
	}

	/** full constructor */
	public EcOperator(EcAccssMode ecAccssMode, EcObjects ecObjects,
			Set<EcPermission> ecPermissions) {
		this.ecAccssMode = ecAccssMode;
		this.ecObjects = ecObjects;
		this.ecPermissions = ecPermissions;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_operator_gen")
	@Column(name = "oper_id", unique = true, nullable = false)
	public Long getOperId() {
		return this.operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accss_id")
	public EcAccssMode getEcAccssMode() {
		return this.ecAccssMode;
	}

	public void setEcAccssMode(EcAccssMode ecAccssMode) {
		this.ecAccssMode = ecAccssMode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "obj_id")
	public EcObjects getEcObjects() {
		return this.ecObjects;
	}

	public void setEcObjects(EcObjects ecObjects) {
		this.ecObjects = ecObjects;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecOperator")
	public Set<EcPermission> getEcPermissions() {
		return this.ecPermissions;
	}

	public void setEcPermissions(Set<EcPermission> ecPermissions) {
		this.ecPermissions = ecPermissions;
	}

}
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * EcAccssMode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_accss_mode")
@TableGenerator(name="ums_ec_accss_mode_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_accss_mode",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcAccssMode implements java.io.Serializable {

	// Fields

	private Long accssId;
	private String name;
	private String alias;
	private String status;
	private String note;
	private Long taxis;
	private Set<EcOperator> ecOperators = new HashSet<EcOperator>(0);

	// Constructors

	/** default constructor */
	public EcAccssMode() {
	}

	/** full constructor */
	public EcAccssMode(String name, String alias, String status, String note,
			Long taxis, Set<EcOperator> ecOperators) {
		this.name = name;
		this.alias = alias;
		this.status = status;
		this.note = note;
		this.taxis = taxis;
		this.ecOperators = ecOperators;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_accss_mode_gen")
	@Column(name = "accss_id", unique = true, nullable = false)
	public Long getAccssId() {
		return this.accssId;
	}

	public void setAccssId(Long accssId) {
		this.accssId = accssId;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecAccssMode")
	public Set<EcOperator> getEcOperators() {
		return this.ecOperators;
	}

	public void setEcOperators(Set<EcOperator> ecOperators) {
		this.ecOperators = ecOperators;
	}

}
package org.echoice.ums.domain;

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

/**
 * EcUserGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_user_group")
@TableGenerator(name="ums_ec_user_group_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_user_group",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcUserGroup implements java.io.Serializable {

	// Fields

	private Long ugId;
	private EcUser ecUser;
	private EcGroup ecGroup;

	// Constructors

	/** default constructor */
	public EcUserGroup() {
	}

	/** full constructor */
	public EcUserGroup(EcUser ecUser, EcGroup ecGroup) {
		this.ecUser = ecUser;
		this.ecGroup = ecGroup;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_user_group_gen")
	@Column(name = "ug_id", unique = true, nullable = false)
	public Long getUgId() {
		return this.ugId;
	}

	public void setUgId(Long ugId) {
		this.ugId = ugId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public EcUser getEcUser() {
		return this.ecUser;
	}

	public void setEcUser(EcUser ecUser) {
		this.ecUser = ecUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	public EcGroup getEcGroup() {
		return this.ecGroup;
	}

	public void setEcGroup(EcGroup ecGroup) {
		this.ecGroup = ecGroup;
	}

}
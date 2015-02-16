package org.echoice.ums.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EcResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_resource")
@TableGenerator(name="ums_ec_resource_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_resource",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcResource implements java.io.Serializable {

	// Fields

	private Long resourceId;
	private String name;
	private String alias;
	private String type;
	private String status;
	private Long parentId;
	private Long taxis;
	private Date opTime;

	// Constructors

	/** default constructor */
	public EcResource() {
	}

	/** full constructor */
	public EcResource(String name, String alias, String type, String status,
			Long parentId, Long taxis, Date opTime) {
		this.name = name;
		this.alias = alias;
		this.type = type;
		this.status = status;
		this.parentId = parentId;
		this.taxis = taxis;
		this.opTime = opTime;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_resource_gen")
	@Column(name = "resource_id", unique = true, nullable = false)
	public Long getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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

	@Column(name = "type", length = 30)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

}
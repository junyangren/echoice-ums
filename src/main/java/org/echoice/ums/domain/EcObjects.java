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
 * EcObjects entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_objects")
@TableGenerator(name="ums_ec_objects_gen", 
		table="ec_common_seq",
		pkColumnName="pk_key",
		pkColumnValue="ums_ec_objects",
		valueColumnName="pk_value",
		allocationSize=1,initialValue=1)
public class EcObjects implements java.io.Serializable {

	// Fields

	private Long objId;
	private String name;
	private String alias;
	private String type;
	private String icon;
	private String status;
	private Long parentId;
	private String note;
	private Long taxis;
	private Date opTime;
	private String note2;
	private String note3;
	private Set<EcOperator> ecOperators = new HashSet<EcOperator>(0);

	// Constructors

	/** default constructor */
	public EcObjects() {
	}

	/** full constructor */
	public EcObjects(String name, String alias, String type, String icon,
			String status, Long parentId, String note, Long taxis, Date opTime,
			Set<EcOperator> ecOperators) {
		this.name = name;
		this.alias = alias;
		this.type = type;
		this.icon = icon;
		this.status = status;
		this.parentId = parentId;
		this.note = note;
		this.taxis = taxis;
		this.opTime = opTime;
		this.ecOperators = ecOperators;
	}

	// Property accessors
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ums_ec_objects_gen")
	@Column(name = "obj_id", unique = true, nullable = false)
	public Long getObjId() {
		return this.objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
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

	@Column(name = "icon", length = 50)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ecObjects")
	public Set<EcOperator> getEcOperators() {
		return this.ecOperators;
	}

	public void setEcOperators(Set<EcOperator> ecOperators) {
		this.ecOperators = ecOperators;
	}

}
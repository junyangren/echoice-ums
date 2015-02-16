package org.echoice.ums.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * EcUserExtend entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ec_user_extend")
public class EcUserExtend implements java.io.Serializable {

	// Fields

	private Long userId;
	private String mobile;
	private String tel;
	private String address;
	private String qq;
	private String msn;
	private String duty;
	private Long leaderId;

	// Constructors

	/** default constructor */
	public EcUserExtend() {
	}

	/** full constructor */
	public EcUserExtend(String mobile, String tel, String address, String qq,
			String msn, String duty, Long leaderId) {
		this.mobile = mobile;
		this.tel = tel;
		this.address = address;
		this.qq = qq;
		this.msn = msn;
		this.duty = duty;
		this.leaderId = leaderId;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator="userExtend.id")
	@GenericGenerator(name="userExtend.id",strategy="assigned")
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "mobile", length = 30)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "tel", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "qq", length = 20)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "msn", length = 50)
	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "duty", length = 50)
	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "leader_id")
	public Long getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

}
package org.echoice.ums.web.view;

import org.echoice.ums.domain.EcUser;

public class EcUserView extends EcUser {
	private Long groupId;
	private Long uaId;
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getUaId() {
		return uaId;
	}

	public void setUaId(Long uaId) {
		this.uaId = uaId;
	}
	
}

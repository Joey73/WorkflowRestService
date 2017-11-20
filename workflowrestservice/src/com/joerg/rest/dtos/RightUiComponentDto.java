package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RightUiComponentDto {
	private String rightId;
	private String uicomponentId;
	private boolean visible;
	private boolean enabled;
	private boolean required;
	
	public RightUiComponentDto(){
		super();
	}
	
	public RightUiComponentDto(String rightId, String uicomponentId, boolean visible, boolean enabled, boolean required){
		this.rightId = rightId;
		this.uicomponentId = uicomponentId;
		this.visible = visible;
		this.enabled = enabled;
		this.required = required;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getUicomponentId() {
		return uicomponentId;
	}

	public void setUicomponentId(String uicomponentId) {
		this.uicomponentId = uicomponentId;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
}

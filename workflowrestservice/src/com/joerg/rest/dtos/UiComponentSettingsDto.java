package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UiComponentSettingsDto {
	private String componentId;
	private boolean visible;
	private boolean enabled;
	private boolean required;
	
	public UiComponentSettingsDto(){
		super();
	}
	
	public UiComponentSettingsDto(String componentId, boolean visible, boolean enabled, boolean required){
		this.componentId = componentId;
		this.visible = visible;
		this.enabled = enabled;
		this.required = required;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
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

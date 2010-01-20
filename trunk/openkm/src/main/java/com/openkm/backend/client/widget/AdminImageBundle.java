package com.openkm.backend.client.widget;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface AdminImageBundle extends ImageBundle {

	@Resource("com/openkm/backend/public/img/icon/toolbar/home.png")
	public AbstractImagePrototype homeIcon();

	@Resource("com/openkm/backend/public/img/icon/toolbar/search.png")
	public AbstractImagePrototype searchIcon();

	@Resource("com/openkm/backend/public/img/icon/toolbar/users.png")
	public AbstractImagePrototype usersIcon();

	@Resource("com/openkm/backend/public/img/icon/toolbar/utils.png")
	public AbstractImagePrototype utilsIcon();

	@Resource("com/openkm/backend/public/img/icon/toolbar/properties.png")
	public AbstractImagePrototype propertiesIcon();
	
	@Resource("com/openkm/backend/public/img/icon/toolbar/workflow.png")
	public AbstractImagePrototype workflowIcon();
	
	@Resource("com/openkm/backend/public/img/icon/toolbar/config.png")
	public AbstractImagePrototype configIcon();
}

package com.openkm.backend.client.widget;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface AdminImageBundle extends ImageBundle {

	@Resource("es/git/openkm/backend/public/img/icon/toolbar/home.png")
	public AbstractImagePrototype homeIcon();

	@Resource("es/git/openkm/backend/public/img/icon/toolbar/search.png")
	public AbstractImagePrototype searchIcon();

	@Resource("es/git/openkm/backend/public/img/icon/toolbar/users.png")
	public AbstractImagePrototype usersIcon();

	@Resource("es/git/openkm/backend/public/img/icon/toolbar/utils.png")
	public AbstractImagePrototype utilsIcon();

	@Resource("es/git/openkm/backend/public/img/icon/toolbar/properties.png")
	public AbstractImagePrototype propertiesIcon();
	
	@Resource("es/git/openkm/backend/public/img/icon/toolbar/workflow.png")
	public AbstractImagePrototype workflowIcon();
	
	@Resource("es/git/openkm/backend/public/img/icon/toolbar/config.png")
	public AbstractImagePrototype configIcon();
}

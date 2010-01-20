package com.openkm.frontend.client.widget.startup;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface StartUpImageBundle extends ImageBundle {

	@Resource("com/openkm/frontend/public/img/icon/loaded.gif")
	public AbstractImagePrototype loadedIcon();

	@Resource("com/openkm/frontend/public/img/icon/loaded_disabled.gif")
	public AbstractImagePrototype loadedDisabledIcon();
	
	@Resource("com/openkm/frontend/public/img/icon/loaded_error.gif")
	public AbstractImagePrototype loadedErrorIcon();
}

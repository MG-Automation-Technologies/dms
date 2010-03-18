package es.git.openkm.frontend.client.widget.startup;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface StartUpImageBundle extends ImageBundle {

	@Resource("es/git/openkm/frontend/public/img/icon/loaded.gif")
	public AbstractImagePrototype loadedIcon();

	@Resource("es/git/openkm/frontend/public/img/icon/loaded_disabled.gif")
	public AbstractImagePrototype loadedDisabledIcon();
	
	@Resource("es/git/openkm/frontend/public/img/icon/loaded_error.gif")
	public AbstractImagePrototype loadedErrorIcon();
}

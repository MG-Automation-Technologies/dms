package com.openkm.frontend.client.widget.dashboard;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface DashboardImageBundle extends ImageBundle {

	@Resource("com/openkm/frontend/public/img/icon/toolbar/user.png")
	public AbstractImagePrototype userIcon();

	@Resource("com/openkm/frontend/public/img/icon/toolbar/mail.png")
	public AbstractImagePrototype mailIcon();

	@Resource("com/openkm/frontend/public/img/icon/toolbar/news.png")
	public AbstractImagePrototype newsIcon();

	@Resource("com/openkm/frontend/public/img/icon/toolbar/general.png")
	public AbstractImagePrototype generalIcon();

	@Resource("com/openkm/frontend/public/img/icon/toolbar/workflow.png")
	public AbstractImagePrototype workflowIcon();
	
	@Resource("com/openkm/frontend/public/img/icon/toolbar/keyword_map.png")
	public AbstractImagePrototype keywordMapIcon();
}

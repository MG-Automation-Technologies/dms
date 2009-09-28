package es.git.openkm.frontend.client.widget.dashboard;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface DashboardImageBundle extends ImageBundle {

	@Resource("es/git/openkm/frontend/public/img/icon/toolbar/user.png")
	public AbstractImagePrototype userIcon();

	@Resource("es/git/openkm/frontend/public/img/icon/toolbar/mail.png")
	public AbstractImagePrototype mailIcon();

	@Resource("es/git/openkm/frontend/public/img/icon/toolbar/news.png")
	public AbstractImagePrototype newsIcon();

	@Resource("es/git/openkm/frontend/public/img/icon/toolbar/general.png")
	public AbstractImagePrototype generalIcon();

	@Resource("es/git/openkm/frontend/public/img/icon/toolbar/workflow.png")
	public AbstractImagePrototype workflowIcon();
	
	@Resource("es/git/openkm/frontend/public/img/icon/toolbar/keyword_map.png")
	public AbstractImagePrototype keywordMapIcon();
}

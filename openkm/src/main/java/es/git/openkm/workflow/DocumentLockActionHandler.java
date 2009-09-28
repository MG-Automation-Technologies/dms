/**
 * 
 */
package es.git.openkm.workflow;

import java.util.Iterator;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMDocument;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.direct.DirectAuthModule;
import es.git.openkm.module.direct.DirectRepositoryModule;

/**
 * @author pavila
 * 
 */
public class DocumentLockActionHandler implements ActionHandler {
	private static Logger log = LoggerFactory.getLogger(DocumentLockActionHandler.class);
	private static final long serialVersionUID = -4813518815259981308L;

	/**
	 * 
	 */
	public DocumentLockActionHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbpm.graph.def.ActionHandler#execute(org.jbpm.graph.exe.ExecutionContext)
	 */
	public void execute(ExecutionContext ctx) throws Exception {
		String path = (String)ctx.getContextInstance().getVariable("path");
		log.info("Path: "+path);
		String token = SessionManager.getInstance().getSystemToken();
		log.info("Token: "+token);
		
		try {
			OKMDocument.getInstance().lock(token, path);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage());
		}
	}
}

/**
 * 
 */
package es.git.openkm.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMDocument;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.SessionManager;

/**
 * @author pavila
 * 
 */
public class DocumentUnlockActionHandler implements ActionHandler {
	private static Logger log = LoggerFactory.getLogger(DocumentUnlockActionHandler.class);
	private static final long serialVersionUID = -4813518815259981308L;

	/**
	 * 
	 */
	public DocumentUnlockActionHandler() {
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
			OKMDocument.getInstance().unlock(token, path);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage());
		}
	}
}

/**
 * 
 */
package com.openkm.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDocument;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.SessionManager;

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

package com.openkm.module.direct;

import javax.jcr.Node;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;
import com.openkm.bean.Repository;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.UserConfigDAO;
import com.openkm.dao.bean.UserConfig;
import com.openkm.module.UserConfigModule;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectUserConfigModule implements UserConfigModule {
	private static Logger log = LoggerFactory.getLogger(DirectUserConfigModule.class);
	
	@Override
	public void setHome(String nodePath) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("setHome({})", nodePath);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			session = JCRUtils.getSession();
			Node rootNode = session.getRootNode();
			Node node = rootNode.getNode(nodePath.substring(1));
			UserConfig uc = new UserConfig();
			uc.setHomePath(nodePath);
			uc.setHomeUuid(node.getUUID());
			uc.setHomeType(JCRUtils.getNodeType(node));
			UserConfigDAO.setHome(uc);
			
			// Activity log
			UserActivity.log(session.getUserID(), "USER_CONFIG_SET_HOME", null, nodePath);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			JCRUtils.logout(session);
		}

		log.debug("setHome: void");
	}
	
	@Override
	public UserConfig getConfig() throws RepositoryException, DatabaseException {
		log.debug("getConfig()");
		UserConfig ret = new UserConfig();
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			ret = UserConfigDAO.findByPk(session.getUserID());
			
			if (ret == null) {
				Node okmRoot = session.getRootNode().getNode(Repository.ROOT);
				ret = new UserConfig();
				ret.setHomePath(okmRoot.getPath());
				ret.setHomeUuid(okmRoot.getUUID());
				ret.setHomeType(Folder.TYPE);
				ret.setUser(session.getUserID());
				UserConfigDAO.create(ret);
			} else {
				Node node = session.getNodeByUUID(ret.getHomeUuid());
				
				if (!node.getPath().equals(ret.getHomePath())) {
					ret.setHomePath(node.getPath());
					ret.setHomeType(JCRUtils.getNodeType(node));
					UserConfigDAO.update(ret);
				}
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "USER_CONFIG_GET_CONFIG", null, null);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			JCRUtils.logout(session);
		}

		log.debug("getConfig: {}", ret);
		return ret;
	}
}

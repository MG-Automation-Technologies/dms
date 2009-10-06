/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.git.openkm.module.direct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;

import org.apache.commons.io.FileUtils;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.WorkspaceConfig;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.apache.jackrabbit.core.nodetype.compact.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Permission;
import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.Repository;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.OKMSystemSession;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.RepositoryModule;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.UUIDGenerator;
import es.git.openkm.util.UserActivity;

public class DirectRepositoryModule implements RepositoryModule {
	private static Logger log = LoggerFactory.getLogger(DirectRepositoryModule.class);
	private static javax.jcr.Repository repository = null;
	private static Session systemSession = null;

	/**
	 * Cache the repository information
	 * 
	 * @return The actual repository.
	 * @throws NamingException
	 * @throws javax.jcr.RepositoryException
	 */
	public synchronized static javax.jcr.Repository getRepository() throws RepositoryException {
		log.debug("getRepository()");
		String repConfig = Config.JBOSS_HOME+"/"+Config.REPOSITORY_CONFIG;
		String repHome = Config.JBOSS_HOME+"/"+Config.REPOSITORY_HOME; 
		WorkspaceConfig wc = null;
		
		if (repository == null) {
			// Repository config
			try {
				RepositoryConfig config = RepositoryConfig.create(repConfig, repHome);
				wc = config.getWorkspaceConfig(config.getDefaultWorkspaceName());
				repository = RepositoryImpl.create(config);
			} catch (ConfigurationException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			} catch (javax.jcr.RepositoryException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			}
		}
		
		// Creation of a top access level SYSTEM. Needed by the AccessManager.
		if (systemSession == null) {
			// System User Session
			try {
				systemSession = OKMSystemSession.create((RepositoryImpl)repository, wc);
			} catch (LoginException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			} catch (NoSuchWorkspaceException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			} catch (javax.jcr.RepositoryException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			}
		}

		log.debug("getRepository: " + repository);
		return repository;
	}
	
	/**
	 * Close repository and free the lock 
	 */
	public synchronized static void shutdown() {
		log.debug("shutdownRepository()");
		
		// Preserve system user config
        String token = SessionManager.getInstance().getSystemToken();
    	log.info("*** Logout (system): "+token+" ***");
    	
    	try {
    		new DirectAuthModule().logout(token);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
				
		systemSession = null;
        ((RepositoryImpl)repository).shutdown();
        repository = null;
        log.debug("shutdownRepository: void");
    }
	
	/**
	 * Get the System User Session to perform unsecured operations.
	 * 
	 * @return The System User Session.
	 */
	public static Session getSystemSession() {
		log.debug("getSystemSession()");
				
		if (systemSession != null) {
			log.debug("systemSession.isLive() = "+systemSession.isLive());
			log.debug("systemSession.getUserID() = "+systemSession.getUserID());
			
			try {
				log.debug("systemSession.hasPendingChanges() = "+systemSession.hasPendingChanges());
			} catch (javax.jcr.RepositoryException e) {
				log.error("# MKK-1 # MKK-1 # MKK-1 # MKK-1 # MKK-1 # MKK-1 # MKK-1 #");
				log.error(e.getMessage(), e);
				log.error("# MKK-1 # MKK-1 # MKK-1 # MKK-1 # MKK-1 # MKK-1 # MKK-1 #");
			}
		} else {
			log.error("# MKK-2 # MKK-2 # MKK-2 # MKK-2 # MKK-2 # MKK-2 # MKK-2 #");
			log.error("systemSession is NULL");
			log.error("# MKK-2 # MKK-2 # MKK-2 # MKK-2 # MKK-2 # MKK-2 # MKK-2 #");
		}
		
		log.debug("getSystemSession: "+systemSession);
		return systemSession;
	}

	/**
	 * Initialize the repository.
	 * 
	 * @return The root path of the initialized repository.
	 * @throws AccessDeniedException If there is any security problem: you can't access the parent document folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public synchronized static String initialize() throws RepositoryException {
		log.debug("initialize()");
				
		// Initializes Repository and SystemSession
		getRepository();
		Session systemSession = getSystemSession();
		String okmRootPath = create(systemSession);
		
		// Store system session token 
		try {
			DirectAuthModule.loadUserData(systemSession);
			SessionManager.getInstance().putSystem(systemSession);
			log.debug("*** System user created "+systemSession.getUserID());				
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e);
		}
		
		log.debug("initialize: "+okmRootPath);
		return okmRootPath;
	}
	
	/**
	 * @param session
	 * @return
	 * @throws RepositoryException
	 */
	public synchronized static String create(Session session) throws RepositoryException {
		String okmRootPath = null;
		Node rootNode = null;
		
		try {
			rootNode = session.getRootNode().getNode(Repository.ROOT);
		} catch (javax.jcr.PathNotFoundException e) {
			log.info("No okm:root node found");
		} catch (javax.jcr.RepositoryException e) {
			log.info("No okm:root node found");
		}
			
		try {
			if (rootNode == null) {
				log.info("Repository creation");

				// Register namespaces
				log.info("Register namespace");
				Workspace ws = session.getWorkspace();
				NamespaceRegistry nsr = ws.getNamespaceRegistry(); 
				nsr.registerNamespace(Repository.OKM, Repository.OKM_URI);
				nsr.registerNamespace(PropertyGroup.GROUP, PropertyGroup.GROUP_URI);
				nsr.registerNamespace(PropertyGroup.GROUP_PROPERTY, PropertyGroup.GROUP_PROPERTY_URI);

				// Register custom node types from resources
				log.info("Register custom node types");
				InputStream is = DirectRepositoryModule.class.getResourceAsStream(Config.NODE_DEFINITIONS);
				
				if (is != null) {
					registerCustomNodeTypes(session, is);
				} else {
					String msg = "Configuration error: "+Config.NODE_DEFINITIONS+" not found";
					log.debug(msg);
					throw new RepositoryException(msg);
				}
								
				// Create root base node
				log.info("Create root base node");
				Node root = session.getRootNode();
				Node okmRoot = root.addNode(Repository.ROOT, Folder.TYPE);
				
				// Add basic properties
				okmRoot.setProperty(Folder.AUTHOR, session.getUserID());
				okmRoot.setProperty(Folder.NAME, Repository.ROOT);
				
				// Auth info
				okmRoot.setProperty(Permission.USERS_READ, new String[] { session.getUserID() });
				okmRoot.setProperty(Permission.USERS_WRITE, new String[] { session.getUserID() });
				okmRoot.setProperty(Permission.ROLES_READ, new String[] { Config.DEFAULT_USER_ROLE });
				okmRoot.setProperty(Permission.ROLES_WRITE, new String[] { Config.DEFAULT_USER_ROLE });

				okmRootPath = okmRoot.getPath();
				
				// Create root base node
				log.info("Create home node");
				Node okmHome = root.addNode(Repository.HOME, Folder.TYPE);

				// Add basic properties
				okmHome.setProperty(Folder.AUTHOR, session.getUserID());
				okmHome.setProperty(Folder.NAME, Repository.HOME);

				// Auth info
				okmHome.setProperty(Permission.USERS_READ, new String[] { session.getUserID() });
				okmHome.setProperty(Permission.USERS_WRITE, new String[] { session.getUserID() });
				okmHome.setProperty(Permission.ROLES_READ, new String[] { Config.DEFAULT_USER_ROLE });
				okmHome.setProperty(Permission.ROLES_WRITE, new String[] { Config.DEFAULT_USER_ROLE });
				
				// Create template base node
				log.info("Create template node");
				Node okmTemplate = root.addNode(Repository.TEMPLATES, Folder.TYPE);

				// Add basic properties
				okmTemplate.setProperty(Folder.AUTHOR, session.getUserID());
				okmTemplate.setProperty(Folder.NAME, Repository.TEMPLATES);

				// Auth info
				okmTemplate.setProperty(Permission.USERS_READ, new String[] { session.getUserID() });
				okmTemplate.setProperty(Permission.USERS_WRITE, new String[] { session.getUserID() });
				okmTemplate.setProperty(Permission.ROLES_READ, new String[] { Config.DEFAULT_USER_ROLE });
				okmTemplate.setProperty(Permission.ROLES_WRITE, new String[] {});
				
				// Create config base node
				log.info("Create config node");
				Node okmConfig = root.addNode(Repository.SYS_CONFIG, Repository.SYS_CONFIG_TYPE);

				// Generate installation UUID
				String uuid = UUIDGenerator.generate(okmConfig);
				okmConfig.setProperty(Repository.SYS_CONFIG_UUID, uuid);
				Repository.setUuid(uuid);
				
				// Set repository version
				okmConfig.setProperty(Repository.SYS_CONFIG_VERSION, Repository.VERSION);
				
				root.save();
			} else {
				log.info("Repository already created");
				Node root = session.getRootNode();
				Node okmConfig = root.getNode(Repository.SYS_CONFIG);
				
				// Get installation UUID
				String uuid = okmConfig.getProperty(Repository.SYS_CONFIG_UUID).getString();
				Repository.setUuid(uuid);
				
				// Test repository version
				String repoVer = okmConfig.getProperty(Repository.SYS_CONFIG_VERSION).getString();
				
				if (!Repository.VERSION.equals(repoVer)) {
					log.warn("### Repository version ("+repoVer+") differs from application version ("+Repository.VERSION+") ###");
					log.warn("### You should upgrade the repository ###");
				}
			}
		} catch (NamespaceException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (InvalidNodeTypeDefException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		return okmRootPath;
	}

	/**
	 * Remove a repository from disk.
	 * 
	 * @throws AccessDeniedException If there is any security problem: you can't access the parent document folder because of lack of permissions. 
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public synchronized void remove() throws RepositoryException {
		log.debug("remove()");
		try {
			FileUtils.deleteDirectory(new File(Config.JBOSS_HOME+"/"+Config.REPOSITORY_HOME));
		} catch (IOException e) {
			System.err.println("No previous repository found");
		}
		log.debug("create: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getRootFolder(java.lang.String)
	 */
	public Folder getRootFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getRootFolder(" + token + ")");
		Folder rootFolder = new Folder();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			rootFolder = new DirectFolderModule().getProperties(session, "/"+Repository.ROOT);
			
			// Activity log
			UserActivity.log(session, "GET_ROOT_FOLDER", null, rootFolder.getPath());
		} catch (javax.jcr.PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getRootFolder: "+rootFolder);
		return rootFolder;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getTrashFolder(java.lang.String)
	 */
	public Folder getTrashFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getTrash(" + token + ")");
		Folder trashFolder = new Folder();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			trashFolder = new DirectFolderModule().getProperties(session, "/"+Repository.HOME+"/"+session.getUserID()+"/"+Repository.TRASH);
			
			// Activity log
			UserActivity.log(session, "GET_TRASH_FOLDER", null, trashFolder.getPath());
		} catch (javax.jcr.PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getTrashFolder: "+trashFolder);
		return trashFolder;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getTemplate(java.lang.String)
	 */
	public Folder getTemplatesFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getTemplatesFolder(" + token + ")");
		Folder templatesFolder = new Folder();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			templatesFolder = new DirectFolderModule().getProperties(session, "/"+Repository.TEMPLATES);
			
			// Activity log
			UserActivity.log(session, "GET_TEMPLATES_FOLDER", null, templatesFolder.getPath());
		} catch (javax.jcr.PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getTemplatesFolder: "+templatesFolder);
		return templatesFolder;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getPersonalFolder(java.lang.String)
	 */
	public Folder getPersonalFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getPersonalFolder(" + token + ")");
		Folder personalFolder = new Folder();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			personalFolder = new DirectFolderModule().getProperties(session, "/"+Repository.HOME+"/"+session.getUserID()+"/"+Repository.PERSONAL);
			
			// Activity log
			UserActivity.log(session, "GET_PERSONAL_FOLDER", null, personalFolder.getPath());
		} catch (javax.jcr.PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getPersonalFolder: "+personalFolder);
		return personalFolder;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getMailFolder(java.lang.String)
	 */
	public Folder getMailFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getMailFolder(" + token + ")");
		Folder mailFolder = new Folder();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			mailFolder = new DirectFolderModule().getProperties(session, "/"+Repository.HOME+"/"+session.getUserID()+"/"+Repository.MAIL);
			
			// Activity log
			UserActivity.log(session, "GET_MAIL_FOLDER", null, mailFolder.getPath());
		} catch (javax.jcr.PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getMailFolder: "+mailFolder);
		return mailFolder;
	}

	/**
	 * Register custom node definition from file.
	 * 
	 * @param token
	 * @param cndFileName
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws RepositoryException
	 * @throws InvalidNodeTypeDefException
	 */
	@SuppressWarnings("unchecked")
	public synchronized static void registerCustomNodeTypes(Session session, InputStream cndFile)
			throws FileNotFoundException, ParseException,
			javax.jcr.RepositoryException, InvalidNodeTypeDefException {
		log.debug("registerCustomNodeTypes(" + session + ", " + cndFile
						+ ")");

		// Read in the CND file
		InputStreamReader fileReader = new InputStreamReader(cndFile);
		
		// Create a CompactNodeTypeDefReader
		CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(
				fileReader, Config.NODE_DEFINITIONS);

		// Get the List of NodeTypeDef objects
		List<NodeTypeDef> ntdList = cndReader.getNodeTypeDefs();

		// Get the NodeTypeManager from the Workspace.
		// Note that it must be cast from the generic JCR NodeTypeManager to the
		// Jackrabbit-specific implementation.
		Workspace ws = session.getWorkspace();
		NodeTypeManagerImpl ntmgr = (NodeTypeManagerImpl) ws.getNodeTypeManager();

		// Acquire the NodeTypeRegistry
		NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();

		// Loop through the prepared NodeTypeDefs
		for (Iterator<NodeTypeDef> i = ntdList.iterator(); i.hasNext();) {
			// Get the NodeTypeDef...
			NodeTypeDef ntd = i.next();

			// ...and register or reregister it
			if (!ntreg.isRegistered(ntd.getName())) {
				log.info("Register type " + ntd.getName().toString());
				ntreg.registerNodeType(ntd);
			} else {
				log.info("Reregister type " + ntd.getName().toString());
				ntreg.reregisterNodeType(ntd);
			}
		}

		log.debug("registerCustomNodeTypes: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#purgeTrash(java.lang.String)
	 */
	public void purgeTrash(String token) throws AccessDeniedException, RepositoryException {
		log.debug("purgeTrash("+token+")");
		Node userTrash = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			userTrash = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.TRASH);
			
			for (NodeIterator it = userTrash.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				child.remove();
			}
			
			userTrash.save();
			
			// Activity log
			UserActivity.log(session, "PURGE_TRASH", null, null);
		} catch (javax.jcr.AccessDeniedException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(userTrash);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(userTrash);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("purgeTrash: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getUpdateMessage(java.lang.String)
	 */
	public String getUpdateMessage(String token) throws RepositoryException {
		return Repository.getUpdateMsg();
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getUUID(java.lang.String)
	 */
	public String getUuid(String token) throws RepositoryException {
		return Repository.getUuid();
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#hasNode(java.lang.String, java.lang.String)
	 */
	public boolean hasNode(String token, String path) throws RepositoryException {
		log.debug("hasNode(" + token + ", " + path + ")");
		boolean ret = false;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			ret = session.getRootNode().hasNode(path.substring(1));
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("hasNode: "+ret);
		return ret;
	}
}

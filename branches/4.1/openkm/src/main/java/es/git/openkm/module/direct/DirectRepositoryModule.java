/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.jcr.LoginException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.naming.NamingException;

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

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Permission;
import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.Repository;
import es.git.openkm.bean.cache.UserItems;
import es.git.openkm.cache.UserItemsManager;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.OKMSystemSession;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.RepositoryModule;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.MailUtils;
import es.git.openkm.util.UUIDGenerator;
import es.git.openkm.util.UserActivity;
import es.git.openkm.util.WarUtils;

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
	public synchronized static javax.jcr.Repository getRepository() throws javax.jcr.RepositoryException {
		log.debug("getRepository()");
		String repConfig = Config.HOME_DIR + File.separator + Config.REPOSITORY_CONFIG;
		String repHome = null;
		WorkspaceConfig wc = null;
				
		if (repository == null) {
			// Allow absolute repository path
			if ((new File(Config.REPOSITORY_HOME)).isAbsolute()) {
				repHome = Config.REPOSITORY_HOME;
			} else {
				repHome = Config.HOME_DIR + File.separator + Config.REPOSITORY_HOME;
			}
			
			// Repository configuration
			try {
				RepositoryConfig config = RepositoryConfig.create(repConfig, repHome);
				wc = config.getWorkspaceConfig(config.getDefaultWorkspaceName());
				repository = RepositoryImpl.create(config);
			} catch (ConfigurationException e) {
				log.error(e.getMessage(), e);
				throw e;
			} catch (javax.jcr.RepositoryException e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		}
		
		// Creation of a top access level SYSTEM. Needed by the AccessManager.
		if (systemSession == null) {
			// System User Session
			try {
				systemSession = OKMSystemSession.create((RepositoryImpl)repository, wc);
			} catch (LoginException e) {
				log.error(e.getMessage(), e);
				throw e;
			} catch (NoSuchWorkspaceException e) {
				log.error(e.getMessage(), e);
				throw e;
			} catch (javax.jcr.RepositoryException e) {
				log.error(e.getMessage(), e);
				throw e;
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
	public synchronized static String initialize() throws javax.jcr.RepositoryException, FileNotFoundException, InvalidNodeTypeDefException, ParseException {
		log.debug("initialize()");
				
		// Initializes Repository and SystemSession
		getRepository();
		Session systemSession = getSystemSession();
		String okmRootPath = create(systemSession);
		
		// Store system session token 
		DirectAuthModule.loadUserData(systemSession);
		SessionManager.getInstance().putSystem(systemSession);
		log.debug("*** System user created "+systemSession.getUserID());				
				
		log.debug("initialize: "+okmRootPath);
		return okmRootPath;
	}
	
	/**
	 * @param session
	 * @return
	 * @throws RepositoryException
	 * @throws FileNotFoundException 
	 * @throws InvalidNodeTypeDefException 
	 * @throws ParseException 
	 */
	public synchronized static String create(Session session) throws javax.jcr.RepositoryException,
			FileNotFoundException, InvalidNodeTypeDefException, ParseException {
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
					throw new javax.jcr.RepositoryException(msg);
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
				
				// Create user home base node
				log.info("Create user home base node");
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
				log.info("Create template base node");
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
				okmConfig.setProperty(Repository.SYS_CONFIG_VERSION, WarUtils.getAppVersion().getMajor());
				
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
				
				if (!WarUtils.getAppVersion().getMajor().equals(repoVer)) {
					log.warn("### Actual repository version ("+repoVer+") differs from application repository version ("+WarUtils.getAppVersion().getMajor()+") ###");
					log.warn("### You should upgrade the repository ###");
				}
			}
		} catch (NamespaceException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (InvalidNodeTypeDefException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw e;
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
		String repHome = null;
		
		// Allow absolute repository path
		if ((new File(Config.REPOSITORY_HOME)).isAbsolute()) {
			repHome = Config.REPOSITORY_HOME;
		} else {
			repHome = Config.HOME_DIR+File.separator+Config.REPOSITORY_HOME;
		}
		
		try {
			FileUtils.deleteDirectory(new File(repHome));
		} catch (IOException e) {
			System.err.println("No previous repository found");
		}
		log.debug("create: void");
	}
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
	public Folder getMailFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getMailFolder(" + token + ")");
		Folder mailFolder = new Folder();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			String mailPath = MailUtils.getUserMailPath(session.getUserID());
			mailFolder = new DirectFolderModule().getProperties(session, mailPath);
			
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
	 * TODO For Jackrabbit 2.0 should be done as:
	 *   InputStream is = getClass().getClassLoader().getResourceAsStream("test.cnd");
	 *   Reader cnd = new InputStreamReader(is);
	 *   NodeType[] nodeTypes = CndImporter.registerNodeTypes(cnd, session);
	 * 
	 * The key method is:
	 *   CndImporter.registerNodeTypes("cndfile", session);
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

	@Override
	public void purgeTrash(String token) throws AccessDeniedException, RepositoryException {
		log.debug("purgeTrash("+token+")");
		Node userTrash = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			userTrash = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.TRASH);
			HashMap<String, UserItems> userItemsHash = new HashMap<String, UserItems>(); 
			
			for (NodeIterator it = userTrash.getNodes(); it.hasNext(); ) {
				HashMap<String, UserItems> userItemsHashRet = new HashMap<String, UserItems>();
				Node child = it.nextNode();
				
				if (child.isNodeType(Document.TYPE)) {
					userItemsHashRet = new DirectDocumentModule().purgeHelper(session, child.getParent(), child);
				} else if (child.isNodeType(Folder.TYPE)) {
					userItemsHashRet = new DirectFolderModule().purgeHelper(session, child);
				}
				
				// Join hash maps
				for (Iterator<Entry<String, UserItems>> entIt = userItemsHashRet.entrySet().iterator(); entIt.hasNext(); ) {
					Entry<String, UserItems> entry = entIt.next();
					String uid = entry.getKey();
					UserItems userItem = entry.getValue();
					UserItems userItemTmp = userItemsHash.get(uid);
					if (userItemTmp == null) userItemTmp = new UserItems();
					userItemTmp.setSize(userItemTmp.getSize() + userItem.getSize());
					userItemTmp.setDocuments(userItemTmp.getDocuments() + userItem.getDocuments());
					userItemsHash.put(uid, userItemTmp);
				}
			}
			
			userTrash.save();
			
			// Update user items
			for (Iterator<Entry<String, UserItems>> it = userItemsHash.entrySet().iterator(); it.hasNext(); ) {
				Entry<String, UserItems> entry = it.next();
				String uid = entry.getKey();
				UserItems userItems = entry.getValue();
				UserItemsManager.decSize(uid, userItems.getSize());
				UserItemsManager.decDocuments(uid, userItems.getDocuments());
				UserItemsManager.decFolders(uid, userItems.getDocuments());
			}
			
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
	
	@Override
	public String getUpdateMessage(String token) throws RepositoryException {
		return Repository.getUpdateMsg();
	}

	@Override
	public String getUuid(String token) throws RepositoryException {
		return Repository.getUuid();
	}
	
	@Override
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

	@Override
	public String getPath(String token, String uuid) throws PathNotFoundException, RepositoryException {
		log.debug("getPath(" + token + ", " + uuid + ")");
		String ret;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			ret = session.getNodeByUUID(uuid).getPath();
		} catch (javax.jcr.ItemNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getPath: "+ret);
		return ret;
	}
}

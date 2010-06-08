package com.openkm.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.TimerTask;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.WorkspaceConfig;
import org.apache.jackrabbit.core.data.DataIdentifier;
import org.apache.jackrabbit.core.data.GarbageCollector;
import org.apache.jackrabbit.core.data.ScanEventListener;
import org.apache.jackrabbit.core.state.ItemStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.module.direct.DirectRepositoryModule;

public class DataStoreGarbageCollector extends TimerTask implements ScanEventListener {
	private static Logger log = LoggerFactory.getLogger(DataStoreGarbageCollector.class);

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("*** Begin datastore garbage collector ***");
		GarbageCollector gc = null;
		Session tmp = null;
		
		try {
			// See http://wiki.apache.org/jackrabbit/DataStore
			Repository rep = DirectRepositoryModule.getRepository();
			RepositoryConfig rc = ((RepositoryImpl)rep).getConfig();
			String name = rc.getDefaultWorkspaceName();
			WorkspaceConfig wc = rc.getWorkspaceConfig(name);
			tmp = OKMSystemSession.create((RepositoryImpl)rep, wc);
			
			// Force system garbage collection a few times before running the data store garbage collection
			for (int i=0; i<5; i++) {
				System.gc();
			}
			
			gc = ((SessionImpl)tmp).createDataStoreGarbageCollector();
			
			// optional (if you want to implement a progress bar / output)
			gc.setScanEventListener(this);
			gc.scan();
			
			Iterator<DataIdentifier> it = gc.getDataStore().getAllIdentifiers();
			while (it.hasNext()) {
				log.info("DataIdent: "+it.next());
			}
			
			gc.stopScan();
						
			// delete old data
			int deleted = gc.deleteUnused();
			log.info("Deleted garbage documents: "+deleted);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalStateException e) {
			log.error(e.getMessage(), e);
		} catch (ItemStateException e) {
			log.error(e.getMessage(), e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		} finally {
			log.info("Clean temporal session and close DSGC");
			if (gc != null ) gc.close();
			if (tmp != null) tmp.logout();
		}
		
		log.info("*** End datastore garbage collector ***");
	}

	@Override
	public void afterScanning(Node node) throws RepositoryException {
		//log.info("ScanEventListener - afterScanning: "+node);
	}

	@Override
	public void beforeScanning(Node node) throws RepositoryException {
		//log.info("ScanEventListener - beforeScanning: "+node);
	}

	@Override
	public void done() {
		log.info("ScanEventListener: done");
	}
}

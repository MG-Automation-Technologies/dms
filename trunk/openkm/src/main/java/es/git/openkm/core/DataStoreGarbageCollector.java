package es.git.openkm.core;

import java.io.IOException;
import java.util.TimerTask;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.data.GarbageCollector;
import org.apache.jackrabbit.core.data.ScanEventListener;
import org.apache.jackrabbit.core.state.ItemStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStoreGarbageCollector extends TimerTask implements ScanEventListener {
	private static Logger log = LoggerFactory.getLogger(DataStoreGarbageCollector.class);

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		log.info("*** Begin datastore garbage collector ***");
		
		try {
			SessionManager sm = SessionManager.getInstance();
			String sysToken = sm.getSystemToken();
			SessionImpl si = (SessionImpl)sm.get(sysToken);
			GarbageCollector gc = si.createDataStoreGarbageCollector();
			
			// optional (if you want to implement a progress bar / output)
			gc.setScanEventListener(this);
			gc.scan();
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
		}
		
		log.info("*** End datastore garbage collector ***");
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.core.data.ScanEventListener#afterScanning(javax.jcr.Node)
	 */
	public void afterScanning(Node node) throws RepositoryException {
		//log.info("ScanEventListener - afterScanning: "+node);
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.core.data.ScanEventListener#beforeScanning(javax.jcr.Node)
	 */
	public void beforeScanning(Node node) throws RepositoryException {
		//log.info("ScanEventListener - beforeScanning: "+node);
	}

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.core.data.ScanEventListener#done()
	 */
	public void done() {
		log.info("ScanEventListener: done");
	}
}

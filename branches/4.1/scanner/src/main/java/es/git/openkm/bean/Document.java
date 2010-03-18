package es.git.openkm.bean;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class Document implements KvmSerializable {
	protected static final String NAMESPACE = "http://endpoint.ws.openkm.git.es/";
	public String path;
	
	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
	 */
	public Object getProperty(int index) {
		if (index == 0) {
			return path;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getPropertyCount()
	 */
	public int getPropertyCount() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
		info.type = PropertyInfo.STRING_CLASS;
		
		if (index == 0) {
			info.name = "path";
		}
	}

	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#setProperty(int, java.lang.Object)
	 */
	public void setProperty(int index, Object value) {
		 throw new RuntimeException("Request.setProperty is not implemented yet");
	}

	/**
	 * @param envelope
	 */
	public void register(SoapSerializationEnvelope envelope) {
		envelope.addMapping(NAMESPACE, "document", this.getClass());
	}
}

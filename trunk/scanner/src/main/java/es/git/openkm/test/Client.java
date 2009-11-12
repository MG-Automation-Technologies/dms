package es.git.openkm.test;

import java.io.IOException;
import java.util.logging.Logger;

import org.ksoap2.SoapFault;
import org.xmlpull.v1.XmlPullParserException;

import es.git.openkm.util.SoapClient;

public class Client {
	private static Logger log = Logger.getLogger(Client.class.getName());
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SoapClient client = new SoapClient("localhost:8888");
			String token = client.login("okmAdmin", "admin");
			client.create(token, "/okm:root/prueba.txt", "Esto es un mensaje".getBytes());
			client.logout(token);
		} catch (SoapFault e) {
			log.severe("FaultActor: "+e.faultactor);
			log.severe("FaultCode: "+e.faultcode);
			log.severe("FaultString: "+e.faultstring);
			log.severe("FaultDetail: "+SoapClient.getDetail(e.detail));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}

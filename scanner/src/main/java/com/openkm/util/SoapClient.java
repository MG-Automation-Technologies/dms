package com.openkm.util;

import java.io.IOException;
import java.util.logging.Logger;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import com.openkm.bean.Document;

public class SoapClient {
	private static Logger log = Logger.getLogger(SoapClient.class.getName());
	private static final String NAMESPACE = "http://endpoint.ws.openkm.git.es/";
	private String url;
	
	public SoapClient(String url) {
		this.url = url;
	}
	
	/**
	 * @param user
	 * @param pass
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public String login(String user, String pass) throws IOException, XmlPullParserException {
		SoapObject request = new SoapObject(NAMESPACE, "login");
		request.addProperty("arg0", user);
		request.addProperty("arg1", pass);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		log.info("EndPoint: "+url+"/OKMAuth");
		HttpTransportSE http = new HttpTransportSE(url+"/OKMAuth");
		http.call("login", envelope);
		
		log.finest("BodyOut: "+envelope.bodyOut.toString());
		log.finest("BodyIn: "+envelope.bodyIn.toString());
		log.finest("Response: "+envelope.getResponse());
		return envelope.getResponse().toString();
	}
	
	/**
	 * @param token
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public void logout(String token) throws IOException, XmlPullParserException {
		SoapObject request = new SoapObject(NAMESPACE, "logout");
		request.addProperty("arg0", token);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		log.info("EndPoint: "+url+"/OKMAuth");
		HttpTransportSE http = new HttpTransportSE(url+"/OKMAuth");
		http.call("logout", envelope);
		
		log.finest("BodyOut: "+envelope.bodyOut.toString());
		log.finest("BodyIn: "+envelope.bodyIn.toString());
		log.finest("Response: "+envelope.getResponse());
	}
	
	/**
	 * @param token
	 * @param path
	 * @param data
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public void create(String token, String path, byte[] data) throws IOException, XmlPullParserException {
		String NAMESPACE = "http://endpoint.ws.openkm.git.es/";
		SoapObject request = new SoapObject(NAMESPACE, "create");
		Document doc = new Document();
		doc.path = path;
		request.addProperty("arg0", token);
		request.addProperty("arg1", doc);
		request.addProperty("arg2", data);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		new MarshalBase64().register(envelope);
		doc.register(envelope);
		envelope.setOutputSoapObject(request);
		
		log.info("EndPoint: "+url+"/OKMDocument");
		HttpTransportSE http = new HttpTransportSE(url+"/OKMDocument");
		http.call("create", envelope);
		
		log.finest("BodyOut: "+envelope.bodyOut.toString());
		log.finest("BodyIn: "+envelope.bodyIn.toString());
		log.finest("Response: "+envelope.getResponse());
	}

	/**
	 * @param node
	 * @return
	 */
	public static String getDetail(Node node) {
		String detail = "";
		
		if (node.getChildCount() > 0) {
			Element elta = (Element) node.getChild(0);
			
			if (elta.getChildCount() > 0) {
				Element eltb = (Element) elta.getChild(0);
				detail = eltb.getName();
			}
		}

		return detail;
	}
}

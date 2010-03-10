#/bin/bash

wsimport -p com.openkm.ws.client http://localhost:8080/OpenKM/OKMAuth?wsdl
wsimport -p com.openkm.ws.client http://localhost:8080/OpenKM/OKMDocument?wsdl
wsimport -p com.openkm.ws.client http://localhost:8080/OpenKM/OKMFolder?wsdl
wsimport -p com.openkm.ws.client http://localhost:8080/OpenKM/OKMSearch?wsdl
wsimport -p com.openkm.ws.client http://localhost:8080/OpenKM/OKMNotification?wsdl
wsimport -p com.openkm.ws.client http://localhost:8080/OpenKM/OKMRepository?wsdl

jar cvf okm-ws-client-2.1.jar com

rm -rf com

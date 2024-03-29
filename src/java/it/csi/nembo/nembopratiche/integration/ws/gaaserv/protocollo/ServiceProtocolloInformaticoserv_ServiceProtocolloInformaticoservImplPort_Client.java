
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.9
 * 2019-01-03T10:45:10.224+01:00
 * Generated source version: 2.4.9
 * 
 */
public final class ServiceProtocolloInformaticoserv_ServiceProtocolloInformaticoservImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "ServiceProtocolloInformaticoservImplService");

    private ServiceProtocolloInformaticoserv_ServiceProtocolloInformaticoservImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ServiceProtocolloInformaticoservImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                //e.printStackTrace();
            }
        }
      
        ServiceProtocolloInformaticoservImplService ss = new ServiceProtocolloInformaticoservImplService(wsdlURL, SERVICE_NAME);
        ServiceProtocolloInformaticoserv port = ss.getServiceProtocolloInformaticoservImplPort();  
        
        {
        Logger.debug("Invoking isAlive...");
        try {
            boolean _isAlive__return = port.isAlive();
            Logger.debug("isAlive.result=" + _isAlive__return);

        } catch (Exception_Exception e) { 
            Logger.debug("Expected exception: Exception has occurred.");
            Logger.debug(e.toString());
        }
            }
        {
        Logger.debug("Invoking getDocument...");
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetDocumentRequestVO _getDocument_arg0 = null;
        try {
            it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetDocumentResponseVO _getDocument__return = port.getDocument(_getDocument_arg0);
            Logger.debug("getDocument.result=" + _getDocument__return);

        } catch (Exception_Exception e) { 
            Logger.debug("Expected exception: Exception has occurred.");
            Logger.debug(e.toString());
        }
            }
        {
        Logger.debug("Invoking getByNumeroProtocollo...");
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.NumeroProtocolloRequestVO _getByNumeroProtocollo_arg0 = null;
        try {
            it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ProtocolloResponseVO _getByNumeroProtocollo__return = port.getByNumeroProtocollo(_getByNumeroProtocollo_arg0);
            Logger.debug("getByNumeroProtocollo.result=" + _getByNumeroProtocollo__return);

        } catch (Exception_Exception e) { 
            Logger.debug("Expected exception: Exception has occurred.");
            Logger.debug(e.toString());
        }
            }
        {
        Logger.debug("Invoking testResources...");
        try {
            boolean _testResources__return = port.testResources();
            Logger.debug("testResources.result=" + _testResources__return);

        } catch (Exception_Exception e) { 
            Logger.debug("Expected exception: Exception has occurred.");
            Logger.debug(e.toString());
        }
            }
        {
        Logger.debug("Invoking insertProtocollo...");
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.InsertProtocolloRequestVO _insertProtocollo_arg0 = null;
        try {
            it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ProtocolloResponseVO _insertProtocollo__return = port.insertProtocollo(_insertProtocollo_arg0);
            Logger.debug("insertProtocollo.result=" + _insertProtocollo__return);

        } catch (ProtocolloInformaticoException_Exception e) { 
            Logger.debug("Expected exception: Exception has occurred.");
            Logger.debug(e.toString());
        }
            }
        {
        Logger.debug("Invoking addAllegati...");
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.AddAllegatiRequestVO _addAllegati_arg0 = null;
        try {
            int _addAllegati__return = port.addAllegati(_addAllegati_arg0);
            Logger.debug("addAllegati.result=" + _addAllegati__return);

        } catch (Exception_Exception e) { 
            Logger.debug("Expected exception: Exception has occurred.");
            Logger.debug(e.toString());
        }
            }
        {
        Logger.debug("Invoking notificaByPec...");
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.NotificaByPecRequestVO _notificaByPec_arg0 = null;
        try {
            boolean _notificaByPec__return = port.notificaByPec(_notificaByPec_arg0);
            Logger.debug("notificaByPec.result=" + _notificaByPec__return);

        } catch (Exception_Exception e) { 
            Logger.debug("Expected exception: Exception has occurred.");
            Logger.debug(e.toString());
        }
            }

        System.exit(0);
    }

}

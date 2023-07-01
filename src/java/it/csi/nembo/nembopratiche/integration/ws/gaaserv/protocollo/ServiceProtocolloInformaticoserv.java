package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.9
 * 2019-01-28T13:31:37.957+01:00
 * Generated source version: 2.4.9
 * 
 */
@WebService(targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "ServiceProtocolloInformaticoserv")
@XmlSeeAlso({ObjectFactory.class})
public interface ServiceProtocolloInformaticoserv {

    @WebMethod
    @RequestWrapper(localName = "isAlive", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.IsAlive")
    @ResponseWrapper(localName = "isAliveResponse", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.IsAliveResponse")
    @WebResult(name = "return", targetNamespace = "")
    public boolean isAlive() throws Exception_Exception;

    @WebMethod
    @RequestWrapper(localName = "getDocument", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetDocument")
    @ResponseWrapper(localName = "getDocumentResponse", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetDocumentResponse")
    @WebResult(name = "return", targetNamespace = "")
    public it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetDocumentResponseVO getDocument(
        @WebParam(name = "arg0", targetNamespace = "")
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetDocumentRequestVO arg0
    ) throws Exception_Exception;

    @WebMethod
    @RequestWrapper(localName = "getByNumeroProtocollo", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetByNumeroProtocollo")
    @ResponseWrapper(localName = "getByNumeroProtocolloResponse", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.GetByNumeroProtocolloResponse")
    @WebResult(name = "return", targetNamespace = "")
    public it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ProtocolloResponseVO getByNumeroProtocollo(
        @WebParam(name = "arg0", targetNamespace = "")
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.NumeroProtocolloRequestVO arg0
    ) throws Exception_Exception;

    @WebMethod
    @RequestWrapper(localName = "testResources", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.TestResources")
    @ResponseWrapper(localName = "testResourcesResponse", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.TestResourcesResponse")
    @WebResult(name = "return", targetNamespace = "")
    public boolean testResources() throws Exception_Exception;

    @WebMethod
    @RequestWrapper(localName = "insertProtocollo", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.InsertProtocollo")
    @ResponseWrapper(localName = "insertProtocolloResponse", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.InsertProtocolloResponse")
    @WebResult(name = "return", targetNamespace = "")
    public it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ProtocolloResponseVO insertProtocollo(
        @WebParam(name = "arg0", targetNamespace = "")
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.InsertProtocolloRequestVO arg0
    ) throws ProtocolloInformaticoException_Exception;

    @WebMethod
    @RequestWrapper(localName = "addAllegati", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.AddAllegati")
    @ResponseWrapper(localName = "addAllegatiResponse", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.AddAllegatiResponse")
    @WebResult(name = "return", targetNamespace = "")
    public int addAllegati(
        @WebParam(name = "arg0", targetNamespace = "")
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.AddAllegatiRequestVO arg0
    ) throws Exception_Exception;

    @WebMethod
    @RequestWrapper(localName = "notificaByPec", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.NotificaByPec")
    @ResponseWrapper(localName = "notificaByPecResponse", targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/", className = "it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.NotificaByPecResponse")
    @WebResult(name = "return", targetNamespace = "")
    public boolean notificaByPec(
        @WebParam(name = "arg0", targetNamespace = "")
        it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.NotificaByPecRequestVO arg0
    ) throws Exception_Exception;
}
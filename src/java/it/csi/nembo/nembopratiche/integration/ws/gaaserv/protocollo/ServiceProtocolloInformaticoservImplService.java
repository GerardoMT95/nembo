package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.9
 * 2019-01-23T14:50:40.907+01:00
 * Generated source version: 2.4.9
 * 
 */
@WebServiceClient(name = "ServiceProtocolloInformaticoservImplService", 
                  wsdlLocation = "http://portalesiarb.regione.basilicata.it/gaaserv/ServiceProtocolloInformaticoservImpl?wsdl",
                  targetNamespace = "http://service.gaaserv.agricoltura.aizoon.it/") 
public class ServiceProtocolloInformaticoservImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "ServiceProtocolloInformaticoservImplService");
    public final static QName ServiceProtocolloInformaticoservImplPort = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "ServiceProtocolloInformaticoservImplPort");
    
	
	 
    static {
    	URL url = ServiceProtocolloInformaticoservImplService.class.getResource("/protocollo_ws.wsdl");
        WSDL_LOCATION = url;
    }
   
    

    public ServiceProtocolloInformaticoservImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ServiceProtocolloInformaticoservImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServiceProtocolloInformaticoservImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ServiceProtocolloInformaticoservImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ServiceProtocolloInformaticoservImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ServiceProtocolloInformaticoservImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns ServiceProtocolloInformaticoserv
     */
    @WebEndpoint(name = "ServiceProtocolloInformaticoservImplPort")
    public ServiceProtocolloInformaticoserv getServiceProtocolloInformaticoservImplPort() {
        return super.getPort(ServiceProtocolloInformaticoservImplPort, ServiceProtocolloInformaticoserv.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServiceProtocolloInformaticoserv
     */
    @WebEndpoint(name = "ServiceProtocolloInformaticoservImplPort")
    public ServiceProtocolloInformaticoserv getServiceProtocolloInformaticoservImplPort(WebServiceFeature... features) {
        return super.getPort(ServiceProtocolloInformaticoservImplPort, ServiceProtocolloInformaticoserv.class, features);
    }

}

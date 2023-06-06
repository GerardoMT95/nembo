
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TestResourcesResponse_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "testResourcesResponse");
    private final static QName _GetDocument_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "getDocument");
    private final static QName _TestResources_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "testResources");
    private final static QName _InsertProtocollo_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "insertProtocollo");
    private final static QName _ProtocolloInformaticoException_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "ProtocolloInformaticoException");
    private final static QName _IsAliveResponse_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "isAliveResponse");
    private final static QName _InsertProtocolloResponse_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "insertProtocolloResponse");
    private final static QName _GetByNumeroProtocolloResponse_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "getByNumeroProtocolloResponse");
    private final static QName _Exception_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "Exception");
    private final static QName _GetDocumentResponse_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "getDocumentResponse");
    private final static QName _NotificaByPecResponse_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "notificaByPecResponse");
    private final static QName _IsAlive_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "isAlive");
    private final static QName _GetByNumeroProtocollo_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "getByNumeroProtocollo");
    private final static QName _AddAllegatiResponse_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "addAllegatiResponse");
    private final static QName _AddAllegati_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "addAllegati");
    private final static QName _NotificaByPec_QNAME = new QName("http://service.gaaserv.agricoltura.aizoon.it/", "notificaByPec");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestResourcesResponse }
     * 
     */
    public TestResourcesResponse createTestResourcesResponse() {
        return new TestResourcesResponse();
    }

    /**
     * Create an instance of {@link TestResources }
     * 
     */
    public TestResources createTestResources() {
        return new TestResources();
    }

    /**
     * Create an instance of {@link GetDocument }
     * 
     */
    public GetDocument createGetDocument() {
        return new GetDocument();
    }

    /**
     * Create an instance of {@link InsertProtocolloResponse }
     * 
     */
    public InsertProtocolloResponse createInsertProtocolloResponse() {
        return new InsertProtocolloResponse();
    }

    /**
     * Create an instance of {@link GetByNumeroProtocolloResponse }
     * 
     */
    public GetByNumeroProtocolloResponse createGetByNumeroProtocolloResponse() {
        return new GetByNumeroProtocolloResponse();
    }

    /**
     * Create an instance of {@link InsertProtocollo }
     * 
     */
    public InsertProtocollo createInsertProtocollo() {
        return new InsertProtocollo();
    }

    /**
     * Create an instance of {@link ProtocolloInformaticoException }
     * 
     */
    public ProtocolloInformaticoException createProtocolloInformaticoException() {
        return new ProtocolloInformaticoException();
    }

    /**
     * Create an instance of {@link IsAliveResponse }
     * 
     */
    public IsAliveResponse createIsAliveResponse() {
        return new IsAliveResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link GetDocumentResponse }
     * 
     */
    public GetDocumentResponse createGetDocumentResponse() {
        return new GetDocumentResponse();
    }

    /**
     * Create an instance of {@link NotificaByPecResponse }
     * 
     */
    public NotificaByPecResponse createNotificaByPecResponse() {
        return new NotificaByPecResponse();
    }

    /**
     * Create an instance of {@link IsAlive }
     * 
     */
    public IsAlive createIsAlive() {
        return new IsAlive();
    }

    /**
     * Create an instance of {@link GetByNumeroProtocollo }
     * 
     */
    public GetByNumeroProtocollo createGetByNumeroProtocollo() {
        return new GetByNumeroProtocollo();
    }

    /**
     * Create an instance of {@link AddAllegatiResponse }
     * 
     */
    public AddAllegatiResponse createAddAllegatiResponse() {
        return new AddAllegatiResponse();
    }

    /**
     * Create an instance of {@link NotificaByPec }
     * 
     */
    public NotificaByPec createNotificaByPec() {
        return new NotificaByPec();
    }

    /**
     * Create an instance of {@link AddAllegati }
     * 
     */
    public AddAllegati createAddAllegati() {
        return new AddAllegati();
    }

    /**
     * Create an instance of {@link TipoDocumentoVO }
     * 
     */
    public TipoDocumentoVO createTipoDocumentoVO() {
        return new TipoDocumentoVO();
    }

    /**
     * Create an instance of {@link ComuneVO }
     * 
     */
    public ComuneVO createComuneVO() {
        return new ComuneVO();
    }

    /**
     * Create an instance of {@link ClassificazioneVO }
     * 
     */
    public ClassificazioneVO createClassificazioneVO() {
        return new ClassificazioneVO();
    }

    /**
     * Create an instance of {@link InsertProtocolloRequestVO }
     * 
     */
    public InsertProtocolloRequestVO createInsertProtocolloRequestVO() {
        return new InsertProtocolloRequestVO();
    }

    /**
     * Create an instance of {@link NumeroProtocolloVO }
     * 
     */
    public NumeroProtocolloVO createNumeroProtocolloVO() {
        return new NumeroProtocolloVO();
    }

    /**
     * Create an instance of {@link ListaDocumentiVO }
     * 
     */
    public ListaDocumentiVO createListaDocumentiVO() {
        return new ListaDocumentiVO();
    }

    /**
     * Create an instance of {@link DataVO }
     * 
     */
    public DataVO createDataVO() {
        return new DataVO();
    }

    /**
     * Create an instance of {@link NumeroProtocolloRequestVO }
     * 
     */
    public NumeroProtocolloRequestVO createNumeroProtocolloRequestVO() {
        return new NumeroProtocolloRequestVO();
    }

    /**
     * Create an instance of {@link ProtocolloResponseVO }
     * 
     */
    public ProtocolloResponseVO createProtocolloResponseVO() {
        return new ProtocolloResponseVO();
    }

    /**
     * Create an instance of {@link FascicoloVO }
     * 
     */
    public FascicoloVO createFascicoloVO() {
        return new FascicoloVO();
    }

    /**
     * Create an instance of {@link ListaDestinatariPecCCVO }
     * 
     */
    public ListaDestinatariPecCCVO createListaDestinatariPecCCVO() {
        return new ListaDestinatariPecCCVO();
    }

    /**
     * Create an instance of {@link DocumentoRefVO }
     * 
     */
    public DocumentoRefVO createDocumentoRefVO() {
        return new DocumentoRefVO();
    }

    /**
     * Create an instance of {@link RecordRubricaVO }
     * 
     */
    public RecordRubricaVO createRecordRubricaVO() {
        return new RecordRubricaVO();
    }

    /**
     * Create an instance of {@link AddAllegatiRequestVO }
     * 
     */
    public AddAllegatiRequestVO createAddAllegatiRequestVO() {
        return new AddAllegatiRequestVO();
    }

    /**
     * Create an instance of {@link DocumentoVO }
     * 
     */
    public DocumentoVO createDocumentoVO() {
        return new DocumentoVO();
    }

    /**
     * Create an instance of {@link UfficioProtocollazioneVO }
     * 
     */
    public UfficioProtocollazioneVO createUfficioProtocollazioneVO() {
        return new UfficioProtocollazioneVO();
    }

    /**
     * Create an instance of {@link MittenteVO }
     * 
     */
    public MittenteVO createMittenteVO() {
        return new MittenteVO();
    }

    /**
     * Create an instance of {@link AreaOrganizzativaOmogeneaVO }
     * 
     */
    public AreaOrganizzativaOmogeneaVO createAreaOrganizzativaOmogeneaVO() {
        return new AreaOrganizzativaOmogeneaVO();
    }

    /**
     * Create an instance of {@link LivelloClassificazioneVO }
     * 
     */
    public LivelloClassificazioneVO createLivelloClassificazioneVO() {
        return new LivelloClassificazioneVO();
    }

    /**
     * Create an instance of {@link DestinatarioVO }
     * 
     */
    public DestinatarioVO createDestinatarioVO() {
        return new DestinatarioVO();
    }

    /**
     * Create an instance of {@link UsernameTokenVO }
     * 
     */
    public UsernameTokenVO createUsernameTokenVO() {
        return new UsernameTokenVO();
    }

    /**
     * Create an instance of {@link NotificaByPecRequestVO }
     * 
     */
    public NotificaByPecRequestVO createNotificaByPecRequestVO() {
        return new NotificaByPecRequestVO();
    }

    /**
     * Create an instance of {@link GetDocumentResponseVO }
     * 
     */
    public GetDocumentResponseVO createGetDocumentResponseVO() {
        return new GetDocumentResponseVO();
    }

    /**
     * Create an instance of {@link IndirizzoVO }
     * 
     */
    public IndirizzoVO createIndirizzoVO() {
        return new IndirizzoVO();
    }

    /**
     * Create an instance of {@link GetDocumentRequestVO }
     * 
     */
    public GetDocumentRequestVO createGetDocumentRequestVO() {
        return new GetDocumentRequestVO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "testResourcesResponse")
    public JAXBElement<TestResourcesResponse> createTestResourcesResponse(TestResourcesResponse value) {
        return new JAXBElement<TestResourcesResponse>(_TestResourcesResponse_QNAME, TestResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "getDocument")
    public JAXBElement<GetDocument> createGetDocument(GetDocument value) {
        return new JAXBElement<GetDocument>(_GetDocument_QNAME, GetDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestResources }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "testResources")
    public JAXBElement<TestResources> createTestResources(TestResources value) {
        return new JAXBElement<TestResources>(_TestResources_QNAME, TestResources.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertProtocollo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "insertProtocollo")
    public JAXBElement<InsertProtocollo> createInsertProtocollo(InsertProtocollo value) {
        return new JAXBElement<InsertProtocollo>(_InsertProtocollo_QNAME, InsertProtocollo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolloInformaticoException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "ProtocolloInformaticoException")
    public JAXBElement<ProtocolloInformaticoException> createProtocolloInformaticoException(ProtocolloInformaticoException value) {
        return new JAXBElement<ProtocolloInformaticoException>(_ProtocolloInformaticoException_QNAME, ProtocolloInformaticoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAliveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "isAliveResponse")
    public JAXBElement<IsAliveResponse> createIsAliveResponse(IsAliveResponse value) {
        return new JAXBElement<IsAliveResponse>(_IsAliveResponse_QNAME, IsAliveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertProtocolloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "insertProtocolloResponse")
    public JAXBElement<InsertProtocolloResponse> createInsertProtocolloResponse(InsertProtocolloResponse value) {
        return new JAXBElement<InsertProtocolloResponse>(_InsertProtocolloResponse_QNAME, InsertProtocolloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByNumeroProtocolloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "getByNumeroProtocolloResponse")
    public JAXBElement<GetByNumeroProtocolloResponse> createGetByNumeroProtocolloResponse(GetByNumeroProtocolloResponse value) {
        return new JAXBElement<GetByNumeroProtocolloResponse>(_GetByNumeroProtocolloResponse_QNAME, GetByNumeroProtocolloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "getDocumentResponse")
    public JAXBElement<GetDocumentResponse> createGetDocumentResponse(GetDocumentResponse value) {
        return new JAXBElement<GetDocumentResponse>(_GetDocumentResponse_QNAME, GetDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotificaByPecResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "notificaByPecResponse")
    public JAXBElement<NotificaByPecResponse> createNotificaByPecResponse(NotificaByPecResponse value) {
        return new JAXBElement<NotificaByPecResponse>(_NotificaByPecResponse_QNAME, NotificaByPecResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAlive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "isAlive")
    public JAXBElement<IsAlive> createIsAlive(IsAlive value) {
        return new JAXBElement<IsAlive>(_IsAlive_QNAME, IsAlive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByNumeroProtocollo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "getByNumeroProtocollo")
    public JAXBElement<GetByNumeroProtocollo> createGetByNumeroProtocollo(GetByNumeroProtocollo value) {
        return new JAXBElement<GetByNumeroProtocollo>(_GetByNumeroProtocollo_QNAME, GetByNumeroProtocollo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAllegatiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "addAllegatiResponse")
    public JAXBElement<AddAllegatiResponse> createAddAllegatiResponse(AddAllegatiResponse value) {
        return new JAXBElement<AddAllegatiResponse>(_AddAllegatiResponse_QNAME, AddAllegatiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAllegati }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "addAllegati")
    public JAXBElement<AddAllegati> createAddAllegati(AddAllegati value) {
        return new JAXBElement<AddAllegati>(_AddAllegati_QNAME, AddAllegati.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotificaByPec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.gaaserv.agricoltura.aizoon.it/", name = "notificaByPec")
    public JAXBElement<NotificaByPec> createNotificaByPec(NotificaByPec value) {
        return new JAXBElement<NotificaByPec>(_NotificaByPec_QNAME, NotificaByPec.class, null, value);
    }

}

package it.csi.nembo.nembopratiche.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;

import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ServiceProtocolloInformaticoserv;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ServiceProtocolloInformaticoservImplService;
import it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica.IMessaggisticaWS;
import it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica.Messaggistica;
import it.csi.nembo.nembopratiche.integration.ws.smrcomms.smrcommsrvservice.SmrcommSrvService;
import it.csi.papua.papuaserv.dto.gestioneutenti.Intermediario;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsDatiAutenticazioneVO;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsDatiInvioMailPecVO;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsDatiProtocolloVO;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsDocumentoInputVO;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsDocumentoVO;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsMetadatoVO;
import it.csi.smrcomms.siapcommws.interfacews.smrcomm.SmrcommSrv;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellDocumentoVO;

public class WsUtils
{
  public static String      PAPUASERV_MESSAGGISTICA_WSDL = null;
  public static String      SIAPCOMM_WSDL                = null;
  public static final int   SHORT_CONNECT_TIMEOUT        = 1000;
  public static final int   SHORT_REQUEST_TIMEOUT        = 5000;

  public static String       SERVICE_PROTOCOLLO_ENDPOINT_URL       = null;
  public static String       USER_ACCOUNT_PROT       = null;
  public static String       PWD_ACCOUNT_PROT       = null;
  
  /* 10 secondi */
  public static final int   MAX_CONNECT_TIMEOUT          = 10 * 1000;
  /* 280 secondi */
  public static final int   MAX_REQUEST_TIMEOUT          = 280 * 1000;
  public static final int[] SHORT_TIMEOUT                = new int[]
  { SHORT_CONNECT_TIMEOUT, SHORT_REQUEST_TIMEOUT };
  public static final int[] MAX_TIMEOUT                  = new int[]
  { MAX_CONNECT_TIMEOUT, MAX_REQUEST_TIMEOUT };
  static
  {
    ResourceBundle res = ResourceBundle.getBundle("config");
    PAPUASERV_MESSAGGISTICA_WSDL = res
        .getString("papuaserv.messaggistica.wsdl.server");
    SIAPCOMM_WSDL = res.getString("siapcomm.wsdl");

    SERVICE_PROTOCOLLO_ENDPOINT_URL = res.getString("SERVICE_PROTOCOLLO_ENDPOINT_URL");
    USER_ACCOUNT_PROT = res.getString("USER_ACCOUNT_PROT");
    PWD_ACCOUNT_PROT = res.getString("PWD_ACCOUNT_PROT");
  }

  public void setTimeout(BindingProvider provider, int[] timeouts)
      throws MalformedURLException
  {
    Map<String, Object> requestContext = provider.getRequestContext();
    requestContext.put("javax.xml.ws.client.connectionTimeout", timeouts[0]);
    requestContext.put("javax.xml.ws.client.receiveTimeout", timeouts[1]);
  }

  public IMessaggisticaWS getMessaggistica() throws MalformedURLException
  {
    return getMessaggisticaWithTimeout(MAX_TIMEOUT);
  }

  public IMessaggisticaWS getMessaggisticaWithTimeout(int timeouts[])
      throws MalformedURLException
  {
    final IMessaggisticaWS messaggisticaPort = new Messaggistica(
        new URL(PAPUASERV_MESSAGGISTICA_WSDL),
        new QName(
            "http://papuaserv.webservice.business.papuaserv.papua.csi.it/",
            "messaggistica")).getMessaggisticaPort();
    if (timeouts != null)
    {
      setTimeout((BindingProvider) messaggisticaPort, timeouts);
    }
    return messaggisticaPort;
  }

  public SmrcommSrv getSiapComm() throws MalformedURLException
  {
    final SmrcommSrv smrcomm = new SmrcommSrvService(new URL(SIAPCOMM_WSDL))
        .getSmrcommSrvPort();
    Client client = ClientProxy.getClient(smrcomm);
    client.getRequestContext().put(Message.ENDPOINT_ADDRESS, SIAPCOMM_WSDL);
    return smrcomm;
  }
  
  public ServiceProtocolloInformaticoserv getServiceProtocolloInformaticoserv() throws MalformedURLException
  {
	  ServiceProtocolloInformaticoservImplService ss = new ServiceProtocolloInformaticoservImplService(
			  this.getClass().getResource("/protocollo_ws.wsdl"), ServiceProtocolloInformaticoservImplService.SERVICE);
      ServiceProtocolloInformaticoserv port = ss.getServiceProtocolloInformaticoservImplPort(); 
      BindingProvider bp = (BindingProvider)port;
      bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SERVICE_PROTOCOLLO_ENDPOINT_URL);

      /* Optional  credentials */
      //bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
      //bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");
    return port;
  }

  public SiapCommWsDocumentoInputVO convertAgriWellVOToSiapComm(
      String user,
      String password,
      AgriWellDocumentoVO agriWellDocumentoVO,
      SiapCommWsDatiProtocolloVO datiProtocollo,
      SiapCommWsDatiInvioMailPecVO datiPEC,
      boolean flagInvioPEC,
      boolean flagProtocolla,
      boolean flagTimbroProtocollo,
      boolean flagEreditaProtocollo,
      boolean isUpdate,
      boolean isDocumentoPrincipale,
      Long idDocumentoIndexPrincipale,
      long idUtenteAggiornamento,
      long idProcedimento)
  {
    SiapCommWsDatiAutenticazioneVO datiAutenticazione = new SiapCommWsDatiAutenticazioneVO();
    SiapCommWsDocumentoInputVO inputVO = new SiapCommWsDocumentoInputVO();
    SiapCommWsDocumentoVO documentoVO = new SiapCommWsDocumentoVO();

    datiAutenticazione.setUsername(user);
    datiAutenticazione.setPassword(password);

    documentoVO.setAnno(agriWellDocumentoVO.getAnno());
    documentoVO.setDaFirmare(agriWellDocumentoVO.getDaFirmare());
    documentoVO.setDataInserimento(NemboUtils.DATE
        .toXMLGregorianCalendar(agriWellDocumentoVO.getDataInserimento()));
    /* DA CAPIRE COME VALORIZZARE */
    documentoVO.setDataRepertorio(null);
    documentoVO.setNumeroRepertorio(null);
    /* ---------------------------- */
    documentoVO.setEstensione(agriWellDocumentoVO.getEstensione());
    documentoVO
        .setFlagCartellaDaCreare(agriWellDocumentoVO.getFlagCartellaDaCreare());
    documentoVO.setIdAzienda(agriWellDocumentoVO.getExtIdAzienda());
    documentoVO.setIdentificativo(agriWellDocumentoVO.getIdentificativo());
    documentoVO.setIdIntermediario(agriWellDocumentoVO.getExtIdIntermediario());
    documentoVO.setIdLivello(agriWellDocumentoVO.getIdLivello());
    documentoVO.setIdTipoDocumento(agriWellDocumentoVO.getIdTipoDocumento());
    documentoVO.setNomeCartella(agriWellDocumentoVO.getNomeCartella());
    documentoVO.setNomeFile(agriWellDocumentoVO.getNomeFile());
    documentoVO.setNoteDocumento(agriWellDocumentoVO.getNoteDocumento());
    documentoVO.setNumeroDomanda(agriWellDocumentoVO.getNumeroDomanda());
    documentoVO.setCodiceVisibilitaDoc("T");

    inputVO.setDatiAutenticazione(datiAutenticazione);
    inputVO.setIdProcedimento(Integer
        .parseInt(String.valueOf(agriWellDocumentoVO.getIdProcedimento())));
    inputVO.setDatiInvioPec(datiPEC);
    inputVO.setDatiProtocollo(datiProtocollo);
    inputVO.setFlagEreditaProtocollo((flagEreditaProtocollo)
        ? NemboConstants.FLAGS.SI : NemboConstants.FLAGS.NO);
    inputVO.setFlagInvioPec((flagInvioPEC) ? NemboConstants.FLAGS.SI
        : NemboConstants.FLAGS.NO);
    inputVO.setFlagProtocolla((flagProtocolla) ? NemboConstants.FLAGS.SI
        : NemboConstants.FLAGS.NO);
    inputVO.setFlagTimbroProtocollo((flagTimbroProtocollo)
        ? NemboConstants.FLAGS.SI : NemboConstants.FLAGS.NO);
    inputVO.setIdDocumentoIndex(agriWellDocumentoVO.getIdDocumentoIndex());
    inputVO.setIdDocumentoIndexPadre(idDocumentoIndexPrincipale);
    inputVO.setIdUtenteAggiornamento(
        agriWellDocumentoVO.getIdUtenteAggiornamento());
    inputVO.setNuovoDocumento(documentoVO);
    inputVO.setTipoArchiviazione((isUpdate) ? "U" : "N");
    inputVO.setTipoDocumento((isDocumentoPrincipale) ? "P" : "A");
    inputVO.setIdUtenteAggiornamento(idUtenteAggiornamento);
    inputVO.setIdentificativoPraticaOrigine(String.valueOf(idProcedimento));
    inputVO.setFlagProtocolloEsterno("N");

    return inputVO;
  }

  public SiapCommWsDocumentoInputVO getSiapCommDocumentoVOPerArchiviazioneListeLiquidazione(
      RigaJSONElencoListaLiquidazioneDTO lista,
      Map<String, String> mapParametri,
      String fileName,
      String emailAmmCompetenza,
      long idUtenteAggiornamento,
      int idProcedimentoAgricolo)
  {
    SiapCommWsDatiAutenticazioneVO datiAutenticazione = new SiapCommWsDatiAutenticazioneVO();
    SiapCommWsDocumentoInputVO inputVO = new SiapCommWsDocumentoInputVO();
    SiapCommWsDocumentoVO documentoVO = new SiapCommWsDocumentoVO();
    String paramUsr = mapParametri
        .get(NemboConstants.PARAMETRO.DOQUIAGRI_USR_PSW_SC);
    String user = paramUsr.split("#")[0];
    String password = paramUsr.split("#")[1];

    datiAutenticazione.setUsername(user);
    datiAutenticazione.setPassword(password);

    documentoVO.setAnno(lista.getAnnoDataCreazione());
    documentoVO.setDaFirmare(null);
    documentoVO.setDataInserimento(
        NemboUtils.DATE.toXMLGregorianCalendar(new Date()));
    /* DA CAPIRE COME VALORIZZARE */
    documentoVO.setDataRepertorio(null);
    documentoVO.setNumeroRepertorio(null);
    /* ---------------------------- */
    final String fileExtension = NemboUtils.FILE.getFileExtension(fileName,
        true);
    documentoVO.setEstensione(fileExtension);
    documentoVO.setFlagCartellaDaCreare(NemboConstants.FLAGS.NO);
    documentoVO.setIdAzienda(null);
    documentoVO.setIdentificativo(null);
    documentoVO.setIdIntermediario(null);
    documentoVO.setIdLivello(null);

    long idTipoDocumento = NemboUtils.NUMBERS.getNumericValue(
        mapParametri
            .get(
                NemboConstants.PARAMETRO.DOQUIAGRI_ID_TIPO_DOCUMENTO_LISTA_LIQUIDAZIONE)
            .trim());
    documentoVO.setIdTipoDocumento(idTipoDocumento);
    documentoVO.setNomeCartella(
        mapParametri.get(NemboConstants.PARAMETRO.DOQUIAGRI_CARTELLA));
    documentoVO.setNomeFile(fileName);
    documentoVO.setNoteDocumento(null);
    documentoVO.setNumeroDomanda(null);
    documentoVO.setCodiceVisibilitaDoc(
        NemboConstants.AGRIWELL.ARCHIVIAZIONE.VISIBILITA.PA);
    addMetadatiListaLiquidazione(documentoVO, mapParametri, lista);

    inputVO.setDatiAutenticazione(datiAutenticazione);
    inputVO.setIdProcedimento(idProcedimentoAgricolo);
    inputVO.setFlagEreditaProtocollo(NemboConstants.FLAGS.NO);
    inputVO.setFlagInvioPec(NemboConstants.FLAGS.NO);
    inputVO.setFlagProtocolla(NemboConstants.FLAGS.SI);
    inputVO.setFlagTimbroProtocollo(
        ("pdf".equalsIgnoreCase(fileExtension)) ? NemboConstants.FLAGS.SI
            : NemboConstants.FLAGS.NO);
    inputVO.setIdDocumentoIndex(null);
    inputVO.setIdDocumentoIndexPadre(null);
    inputVO.setNuovoDocumento(documentoVO);
    inputVO.setTipoArchiviazione("N");
    inputVO.setTipoDocumento("P");
    inputVO.setIdUtenteAggiornamento(idUtenteAggiornamento);
    inputVO.setFlagProtocolloEsterno("N");
    SiapCommWsDatiProtocolloVO datiProtocollo = new SiapCommWsDatiProtocolloVO();
    datiProtocollo.setTipoProtocollo(
        NemboConstants.SIAPCOMMWS.DATI_PROTOCOLLO.TIPO_PROTOCOLLO.USCITA);
    datiProtocollo.setEmailMittenteDestinatario(emailAmmCompetenza);
    datiProtocollo
        .setDenominazioneMittenteDestinatario(lista.getOrganismoDelegato());
    inputVO.setDatiProtocollo(datiProtocollo);

    return inputVO;
  }

  public void addMetadatiListaLiquidazione(SiapCommWsDocumentoVO documentoVO,
      Map<String, String> mapParametri,
      RigaJSONElencoListaLiquidazioneDTO lista)
  {
    // Metadati
    List<SiapCommWsMetadatoVO> metadati = new ArrayList<SiapCommWsMetadatoVO>();
    // METADATI DA PASSARE SEMPRE - INIZIO
    SiapCommWsMetadatoVO metadato = new SiapCommWsMetadatoVO();

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("classificazione_regione");
    metadato.setValoreElemento(mapParametri.get(
        NemboConstants.PARAMETRO.DOQUIAGRI_CLASS_REG_LISTE_LIQUIDAZIONE));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("fascicolazione");
    metadato.setValoreElemento(mapParametri.get(
        NemboConstants.PARAMETRO.DOQUIAGRI_FASCICOLA_LISTE_LIQUIDAZIONE));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("data_lista");
    metadato.setValoreElemento(lista.getDataCreazione());
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("numero_lista");
    metadato.setValoreElemento(String.valueOf(lista.getNumeroLista()));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("id_bando");
    metadato.setValoreElemento(String.valueOf(lista.getIdBando()));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("id_amministrazione_competenza");
    metadato.setValoreElemento(String.valueOf(lista.getExtIdAmmCompetenza()));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("id_tipo_importo");
    metadato.setValoreElemento(String.valueOf(lista.getIdIdTipoImporto()));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("id_tecnico_liquidatore");
    metadato
        .setValoreElemento(String.valueOf(lista.getExtIdTecnicoLiquidatore()));
    metadati.add(metadato);

    if (documentoVO.getMetadati() != null)
    {
      documentoVO.getMetadati().addAll(metadati);
    }
    else
    {
      documentoVO.setMetadati(metadati);
    }
  }

  public void addMetadatiCoordinateProtocolloListaLiquidazione(
      SiapCommWsDocumentoVO documentoVO, Map<String, String> mapParametri)
  {
    // Metadati
    List<SiapCommWsMetadatoVO> metadati = new ArrayList<SiapCommWsMetadatoVO>();
    SiapCommWsMetadatoVO metadato = new SiapCommWsMetadatoVO();

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("numeroPaginaProtocollo");
    metadato.setValoreElemento("1");
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("coordinataXsinistraProtocollo");
    metadato.setValoreElemento(mapParametri.get(
        NemboConstants.PARAMETRO.DOQUIAGRI_LISTE_LIQUIDAZIONE_X_SINISTRA));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("coordinataXdestraProtocollo");
    metadato.setValoreElemento(mapParametri.get(
        NemboConstants.PARAMETRO.DOQUIAGRI_LISTE_LIQUIDAZIONE_X_DESTRA));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("coordinataYbassoProtocollo");
    metadato.setValoreElemento(mapParametri
        .get(NemboConstants.PARAMETRO.DOQUIAGRI_LISTE_LIQUIDAZIONE_Y_BASSO));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("coordinataYaltoProtocollo");
    metadato.setValoreElemento(mapParametri
        .get(NemboConstants.PARAMETRO.DOQUIAGRI_LISTE_LIQUIDAZIONE_Y_ALTO));
    metadati.add(metadato);

    if (documentoVO.getMetadati() != null)
    {
      documentoVO.getMetadati().addAll(metadati);
    }
    else
    {
      documentoVO.setMetadati(metadati);
    }
  }

  public void addMetadati(SiapCommWsDocumentoVO documentoVO,
      List<SiapCommWsMetadatoVO> metadatiAggiuntivi,
      Date dataLimiteFirma, String originalFileName, String cuaa,
      UtenteAbilitazioni utenteAbilitazioni,
      Map<String, String> mapParametri, String descrizioneBando, long numeroBandoDoc, String identificativo)
  {
    String fileName = NemboUtils.FILE.getFileName(originalFileName);
    // Metadati
    List<SiapCommWsMetadatoVO> metadati = new ArrayList<SiapCommWsMetadatoVO>();
    // METADATI DA PASSARE SEMPRE - INIZIO
    SiapCommWsMetadatoVO metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("cuaa");
    metadato.setValoreElemento(cuaa);
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("data_inizio");
    metadato.setValoreElemento(NemboUtils.DATE.formatDateTime(new Date()));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("descrizioneBando");
    metadato.setValoreElemento(descrizioneBando);
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("numeroBandoDoc");
    metadato.setValoreElemento(String.valueOf(numeroBandoDoc));
    metadati.add(metadato);
    
    if(identificativo != null)
    {
    	metadato = new SiapCommWsMetadatoVO();
    	metadato.setNomeEtichetta("GruppoIdentificativo");
    	metadato.setValoreElemento(identificativo);
    	metadati.add(metadato);
    }

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("id_tipo_visibilita");
    metadato.setValoreElemento("1");
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("codice_ente_utente");
    metadato.setValoreElemento(
        NemboUtils.PAPUASERV.extractInfoEnteBaseFromEnteLogin(
            utenteAbilitazioni.getEnteAppartenenza()).getCodiceEnte());
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("titolo_documento");
    metadato.setValoreElemento(fileName.replaceAll("\\s", "_"));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("classificazione_regione");
    metadato.setValoreElemento(
        mapParametri.get(NemboConstants.PARAMETRO.DOQUIAGRI_CLASS_REG));
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("fascicolazione");
    metadato.setValoreElemento(
        mapParametri.get(NemboConstants.PARAMETRO.DOQUIAGRI_FASCICOLA));
    metadati.add(metadato);

    if (dataLimiteFirma != null)
    {
      metadato = new SiapCommWsMetadatoVO();
      metadato.setNomeEtichetta("data_limite_firma");
      metadato.setValoreElemento(
          NemboUtils.DATE.formatDateTime(dataLimiteFirma));
      metadati.add(metadato);
    }

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("documento_firmato");
    metadato.setValoreElemento("FALSE");
    metadati.add(metadato);

    metadato = new SiapCommWsMetadatoVO();
    metadato.setNomeEtichetta("documento_verificato");
    metadato.setValoreElemento("FALSE");
    metadati.add(metadato);
    if (metadatiAggiuntivi != null && !metadatiAggiuntivi.isEmpty())
    {
      metadati.addAll(metadatiAggiuntivi);
    }
    if (documentoVO.getMetadati() != null)
    {
      documentoVO.getMetadati().addAll(metadati);
    }
    else
    {
      documentoVO.setMetadati(metadati);
    }
  }

  public SiapCommWsDocumentoInputVO prepareInputSiapComm(
      String user,
      String password,
      SiapCommWsDatiProtocolloVO datiProtocollo,
      SiapCommWsDatiInvioMailPecVO datiPEC,
      boolean flagInvioPEC,
      boolean flagProtocolla,
      boolean flagTimbroProtocollo,
      boolean flagEreditaProtocollo,
      boolean isUpdate,
      Long idDocumentoIndex,
      boolean isDocumentoPrincipale,
      Long idDocumentoIndexPrincipale,
      long idUtenteAggiornamento,
      long idProcedimento, int idProcedimentoAgricoltura)
  {
    SiapCommWsDatiAutenticazioneVO datiAutenticazione = new SiapCommWsDatiAutenticazioneVO();
    SiapCommWsDocumentoInputVO inputVO = new SiapCommWsDocumentoInputVO();
    SiapCommWsDocumentoVO documentoVO = new SiapCommWsDocumentoVO();

    datiAutenticazione.setUsername(user);
    datiAutenticazione.setPassword(password);

    /* DA CAPIRE COME VALORIZZARE */
    documentoVO.setDataRepertorio(null);
    documentoVO.setNumeroRepertorio(null);
    /* ---------------------------- */

    inputVO.setDatiAutenticazione(datiAutenticazione);
    inputVO.setIdProcedimento(
        Integer
            .parseInt(String.valueOf((long) idProcedimentoAgricoltura)));
    inputVO.setDatiInvioPec(datiPEC);
    inputVO.setDatiProtocollo(datiProtocollo);
    inputVO.setFlagEreditaProtocollo((flagEreditaProtocollo)
        ? NemboConstants.FLAGS.SI : NemboConstants.FLAGS.NO);
    inputVO.setFlagInvioPec((flagInvioPEC) ? NemboConstants.FLAGS.SI
        : NemboConstants.FLAGS.NO);
    inputVO.setFlagProtocolla((flagProtocolla) ? NemboConstants.FLAGS.SI
        : NemboConstants.FLAGS.NO);
    inputVO.setFlagTimbroProtocollo((flagTimbroProtocollo)
        ? NemboConstants.FLAGS.SI : NemboConstants.FLAGS.NO);
    inputVO.setIdDocumentoIndexPadre(idDocumentoIndexPrincipale);
    inputVO.setIdDocumentoIndex(idDocumentoIndex);
    inputVO.setNuovoDocumento(documentoVO);
    inputVO.setTipoArchiviazione((isUpdate) ? "U" : "N");
    inputVO.setTipoDocumento((isDocumentoPrincipale) ? "P" : "A");
    inputVO.setIdUtenteAggiornamento(idUtenteAggiornamento);
    inputVO.setIdentificativoPraticaOrigine(String.valueOf(idProcedimento));
    inputVO.setFlagProtocolloEsterno("N");
    return inputVO;
  }

  public SiapCommWsDocumentoVO getSiapCommWsDocumentoVO(Long idAzienda,
      String identificativo, long idTipoDocumento,
      Map<String, String> mapParametri, Integer annoCampagna,
      Date dataInizioBando, String originalFileName,
      UtenteAbilitazioni utenteAbilitazioni, String flagFirmaGrafometrica)
  {
    SiapCommWsDocumentoVO documentoInputVO = new SiapCommWsDocumentoVO();
    Integer anno = annoCampagna;
    if (anno == null)
    {
      anno = NemboUtils.DATE.getYearFromDate(dataInizioBando);
    }
    String fileName = NemboUtils.FILE.getFileName(originalFileName);
    documentoInputVO.setAnno(anno);
    documentoInputVO.setDataInserimento(
        NemboUtils.DATE.toXMLGregorianCalendar(new Date()));
    documentoInputVO
        .setEstensione(NemboUtils.FILE.getOnlyFileExtension(fileName));
    documentoInputVO.setDaFirmare(flagFirmaGrafometrica);
    if (utenteAbilitazioni.getRuolo().isUtenteIntermediario())
    {
      Intermediario intermediario = utenteAbilitazioni.getEnteAppartenenza()
          .getIntermediario();
      documentoInputVO.setIdIntermediario(intermediario.getIdIntermediario());
    }
    documentoInputVO.setIdAzienda(idAzienda);
    documentoInputVO.setIdTipoDocumento(idTipoDocumento);
    documentoInputVO.setNomeCartella(
        mapParametri.get(NemboConstants.PARAMETRO.DOQUIAGRI_CARTELLA));
    documentoInputVO.setNomeFile(fileName);
    documentoInputVO.setIdentificativo(identificativo);
    documentoInputVO.setFlagCartellaDaCreare(NemboConstants.FLAGS.SI);
    documentoInputVO.setCodiceVisibilitaDoc("T");
    return documentoInputVO;
  }

}

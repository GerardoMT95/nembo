package it.csi.nembo.nembopratiche.business.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.apache.commons.io.IOUtils;
import org.jboss.ejb3.annotation.TransactionTimeout;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import it.csi.nembo.nembopratiche.business.IAsyncEJB;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativi;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.QuadroNewDAO;
import it.csi.nembo.nembopratiche.util.DumpUtils;
import it.csi.nembo.nembopratiche.util.InputStreamDataSource;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.pdf.PDFCoordinateExtractor;
import it.csi.nembo.nembopratiche.util.stampa.Stampa;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;
import it.csi.smrcomms.siapcommws.dto.smrcomm.EsitoDocumentoVO;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsDocumentoInputVO;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsMetadatoVO;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellDocumentoVO;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellEsitoVO;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellMetadatoVO;

@Stateless()
@EJB(name = "java:app/Async", beanInterface = IAsyncEJB.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Asynchronous()
@TransactionTimeout(value = 300, unit = TimeUnit.SECONDS)
public class AsyncEJB extends NemboAbstractEJB<QuadroNewDAO>
    implements IAsyncEJB
{
  private static final String THIS_CLASS = AsyncEJB.class.getSimpleName();

  @Override
  public void generaStampePerProcedimento(long idProcedimentoOggetto,
      long idProcedimento)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "[" + THIS_CLASS
        + "::generaStampePerProcedimento]";
    final long idUtenteLogin = dao
        .findIdUtenteAggiornamentoProcedimentoOggetto(idProcedimentoOggetto);
    try
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " BEGIN.");
      }

      List<StampaOggettoDTO> elenco = dao
          .getElencoStampeOggetto(idProcedimentoOggetto, null);
      if (elenco != null)
      {
        logger.info(THIS_METHOD + " Generazione di " + elenco.size()
            + " stampa/e in corso per idProcedimentoOggetto = "
            + idProcedimentoOggetto
            + ", idProcedimento = " + idProcedimento + " e idUtente "
            + idUtenteLogin);
        for (StampaOggettoDTO stampa : elenco)
        {
          long idOggettoIcona = stampa.getIdOggettoIcona();
          logger.info(
              THIS_METHOD + " Generazione della stampa con idOggettoIcona = "
                  + idOggettoIcona + " per idProcedimentoOggetto = "
                  + idProcedimentoOggetto
                  + ", idProcedimento = " + idProcedimento + " e idUtente "
                  + idUtenteLogin);
          if (stampa.getIdProcedimOggettoStampa() != null)
          {
            privateGeneraStampa(idProcedimentoOggetto, idProcedimento, stampa);
          }
        }
      }
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
    catch (Exception e)
    {
      logger.error(THIS_METHOD
          + " Errore nella generazione delle stampa per il procedimento oggetto #"
          + idProcedimentoOggetto + " richiesto dall'utente #"
          + idUtenteLogin + "\nEccezione rilevata:\n"
          + DumpUtils.getExceptionStackTrace(e));
    }
  }

  @Override
  public void generaStampa(long idProcedimentoOggetto, long idProcedimento,
      long idOggettoIcona)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "[" + THIS_CLASS + "::generaStampa]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      List<StampaOggettoDTO> elenco = dao
          .getElencoStampeOggetto(idProcedimentoOggetto, idOggettoIcona);
      if (elenco != null && !elenco.isEmpty())
      {
        StampaOggettoDTO stampaOggetto = elenco.get(0);
        privateGeneraStampa(idProcedimentoOggetto, idProcedimento,
            stampaOggetto);
      }
    }
    catch (Exception e)
    {
      logger.error(THIS_METHOD
          + " Errore nella generazione delle stampa per il procedimento oggetto #"
          + idProcedimentoOggetto + "\nEccezione rilevata:\n"
          + DumpUtils.getExceptionStackTrace(e));
    }
  }

  public void privateGeneraStampa(long idProcedimentoOggetto, long idProcedimento, StampaOggettoDTO stampaOggetto)
	      throws InternalUnexpectedException
	  {
	    final String THIS_METHOD = "[" + THIS_CLASS + "::privateGeneraStampa]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    UtenteAbilitazioni utenteAbilitazioni = null;
	    try
	    {
	      dao.lockOggettoIcona(stampaOggetto.getIdOggettoIcona());
	      final long idUtenteLogin = dao.findIdUtenteAggiornamentoProcedimentoOggetto(idProcedimentoOggetto);
	      utenteAbilitazioni = PapuaservProfilazioneServiceFactory.getRestServiceClient().getUtenteAbilitazioniByIdUtenteLogin(idUtenteLogin);
	     
	      if (stampaOggetto != null)
	      {
	        final long idOggettoIcona = stampaOggetto.getIdOggettoIcona();
	        final String codiceCdu = stampaOggetto.getCodiceCdu();
	        Stampa stampa = NemboUtils.STAMPA.getStampaFromCdU(codiceCdu);
	        if (stampa == null)
	        {
	          logger.error(THIS_METHOD + " Errore nella generazione della stampa con idOggettoIcona #" + idOggettoIcona + " per il procedimento oggetto #"
	              + idProcedimentoOggetto + " richiesto dall'utente #" + idUtenteLogin
	              + "\nERRORE GRAVE:\nTipologia di stampa non registrata, non è stato trovata nessuna classe deputata a generare la stampa!\n\n");
	          dao.updateProcedimOggettoStampaByIdOggetoIcona(
	              idProcedimentoOggetto,
	              idOggettoIcona,
	              null,
	              NemboConstants.STATO.STAMPA.ID.STAMPA_FALLITA,
	              "Errore nella generazione della stampa:\nERRORE INTERNO GRAVE: Tipologia di stampa non registrata, non è stato trovata nessuna classe deputata a generare la stampa!",
	              idUtenteLogin);
	          return;
	        }
	        byte[] pdf = null;
	        try
	        {
	          pdf = stampa.findStampaFinale(idProcedimentoOggetto, codiceCdu).genera(idProcedimentoOggetto, codiceCdu);
	        }
	        catch (Exception e)
	        {
	          // Registrare l'eccezione...
	          dao.updateProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, idOggettoIcona, null,
	        		  NemboConstants.STATO.STAMPA.ID.STAMPA_FALLITA, "Errore nella generazione della stampa:\n" + DumpUtils.getExceptionStackTrace(e), idUtenteLogin);
	          return;
	        }
	        
	        dao.updateProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, idOggettoIcona, pdf,
	            getIdStatoStampa(utenteAbilitazioni, stampaOggetto.getFlagFirmaGrafometrica()), null, idUtenteLogin);
	      }
	    }
	    catch (Exception e)
	    {
	      logger.error(THIS_METHOD + " Errore nella generazione della stampa con idOggettoIcona #"
	          + (stampaOggetto == null ? null : stampaOggetto.getIdOggettoIcona()) + " per il procedimento oggetto #"
	          + idProcedimentoOggetto + " richiesto dall'utente #"
	          + (utenteAbilitazioni == null ? "utenteAbilitazioni==null" : utenteAbilitazioni.getIdUtenteLogin())
	          + "\nEccezione rilevata:\n" + DumpUtils.getExceptionStackTrace(e));
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	  }

  private int getIdStatoStampa(UtenteAbilitazioni utenteAbilitazioni,
      String flagFirmaGrafometrica)
  {
    if (utenteAbilitazioni.getRuolo().isUtenteIntermediario())
    {
      if (NemboConstants.FLAGS.SI.equals(flagFirmaGrafometrica))
      {
        return NemboConstants.STATO.STAMPA.ID.IN_ATTESA_FIRMA_GRAFOMETRICA;
      }
      else
        if (NemboConstants.FLAGS.NO.equals(flagFirmaGrafometrica))
        {
          return NemboConstants.STATO.STAMPA.ID.IN_ATTESA_FIRMA_SU_CARTA;
        }
    }

    if ((!"X".equals(flagFirmaGrafometrica))
        && utenteAbilitazioni.getRuolo().isAziendaAgricola())
    {
      return NemboConstants.STATO.STAMPA.ID.IN_ATTESA_FIRMA_ELETTRONICA_LEGGERA;
    }

    return NemboConstants.STATO.STAMPA.ID.STAMPATA;
  }

  private AgriWellDocumentoVO getAgriWellDocumentoVO(long idProcedimentoOggetto,
      long idProcedimento, StampaOggettoDTO stampaOggetto, byte[] pdf,
      String fileName, UtenteAbilitazioni utenteAbilitazioni,
      List<AgriWellMetadatoVO> metadatiAggiuntivi, String flagFirmaGrafometrica)
      throws InternalUnexpectedException, IOException
  {
    Map<String, String> mapParametri = getParametri(new String[]
    { NemboConstants.PARAMETRO.DOQUIAGRI_CARTELLA,
        NemboConstants.PARAMETRO.DOQUIAGRI_CLASS_REG,
        NemboConstants.PARAMETRO.DOQUIAGRI_FASCICOLA });
    TestataProcedimento testataProcedimento = dao
        .getTestataProcedimento(idProcedimento);
    String identificativo = dao.getIdentificativo(idProcedimentoOggetto);
    // ID Quadro in questo caso non è significativo quindi posso usare un valore
    // qualsiati (tanto mi servono solo id_azienda e cuaa che non sono
    // influenzati da
    // questo id)
    DatiIdentificativi datiIdentificativi = dao
        .getDatiIdentificativiProcedimentoOggetto(idProcedimentoOggetto, -1,
            null, dao.getIdProcedimentoAgricoloByIdProcedimentoOggetto(idProcedimentoOggetto));
    return NemboUtils.AGRIWELL.getAgriWellDocumentoVO(
        stampaOggetto.getExtIdTipoDocumento(), pdf, fileName, mapParametri,
        utenteAbilitazioni,
        flagFirmaGrafometrica, testataProcedimento, datiIdentificativi,
        identificativo, metadatiAggiuntivi);
  }

  private String getFlagFirmaGrafometrica(StampaOggettoDTO stampaOggetto,
      UtenteAbilitazioni utenteAbilitazioni)
  {
    String flagFirmaGrafometrica = NemboConstants.FLAGS.FIRMA_GRAFOMETRICA.DA_NON_FIRMARE;
    if (NemboConstants.FLAGS.SI
        .equals(stampaOggetto.getFlagFirmaGrafometrica()))
    {
      flagFirmaGrafometrica = NemboConstants.FLAGS.FIRMA_GRAFOMETRICA.DA_FIRMARE_GRAFOMETRICAMENTE;
    }
    return flagFirmaGrafometrica;
  }

  @Override
  public void generaStampaListaLiquidazione(long idListaLiquidazione)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "[" + THIS_CLASS
        + "::generaStampaListaLiquidazione]";

    // dao.lockListaLiquidazione(idListaLiquidazione);

    try
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " BEGIN.");
      }

      logger.info(THIS_METHOD
          + " Generazione di stampa in corso per idListaLiquidazione = "
          + idListaLiquidazione);

      Stampa stampa = NemboUtils.STAMPA.getStampaFromCdU(
          NemboConstants.USECASE.LISTE_LIQUIDAZIONE.STAMPA);
      if (stampa == null)
      {
        logger.error(THIS_METHOD
            + " Errore nella generazione della stampa. \nERRORE GRAVE:\nTipologia di stampa non registrata, non è stato trovata nessuna classe deputata a generare la stampa!\n\n");
        dao.updateFileListaLiquidazione(
            idListaLiquidazione,
            NemboConstants.STATO.STAMPA.ID.STAMPA_FALLITA,
            "Errore nella generazione della stampa:\nERRORE INTERNO GRAVE: Tipologia di stampa non registrata, non è stato trovata nessuna classe deputata a generare la stampa!");
        return;
      }
      byte[] pdf = null;
      try
      {
        pdf = stampa.genera(idListaLiquidazione,
            NemboConstants.USECASE.LISTE_LIQUIDAZIONE.STAMPA);
        dao.caricaFileListaLiquidazione(idListaLiquidazione, pdf,
            NemboConstants.STATO.STAMPA.ID.STAMPATA);
      }
      catch (Exception e)
      {
        // Registrare l'eccezione...
        dao.updateFileListaLiquidazione(idListaLiquidazione,
            NemboConstants.STATO.STAMPA.ID.STAMPA_FALLITA,
            "Errore nella generazione della stampa.");
        logger.error(THIS_METHOD
            + " Errore nella generazione delle stampa per la lista con idListaLiqudazione #"
            + idListaLiquidazione + "Exception: "
            + DumpUtils.getExceptionStackTrace(e));
        return;
      }

      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }

    }
    catch (Exception e)
    {
      logger.error(THIS_METHOD
          + " Errore nella generazione delle stampa per la lista con idListaLiqudazione #"
          + idListaLiquidazione);
    }
  }

}

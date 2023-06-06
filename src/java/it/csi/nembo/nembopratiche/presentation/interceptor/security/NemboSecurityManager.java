package it.csi.nembo.nembopratiche.presentation.interceptor.security;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.ElencoCduDTO;
import it.csi.nembo.nembopratiche.dto.permission.UpdatePermissionProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.AzioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException.ExceptionType;
import it.csi.nembo.nembopratiche.presentation.interceptor.BaseManager;
import it.csi.nembo.nembopratiche.util.NemboApplication;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity.TipoMapping;
import it.csi.papua.papuaserv.dto.gestioneutenti.AziendaAgricola;
import it.csi.papua.papuaserv.dto.gestioneutenti.EnteLogin;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

public class NemboSecurityManager extends BaseManager
{
  @Autowired
  IQuadroEJB                  quadroEJB                               = null;
  public static final String  ERRORE_UTENTE_SENZA_DELEGA              = "L'utente corrente non ha delega sull'azienda a cui appartiene il procedimento";
  public static final String  ERRORE_AZIENDA_NON_TROVATA              = "Il procedimento non è collegato ad alcuna azienda o il procedimento è stato cancellato";
  public static final String  ERRORE_UTENTE_NON_COLLEGATO_AZIENDA     = "L'utente corrente non ha diritto di accedere ai dati dell'azienda a cui appartiene il procedimento";
  public static final String  ERRORE_UTENTE_CON_RUOLO_NON_AUTORIZZATO = "L'utente corrente ha un ruolo non abilitato ad accedere ai dati dei procedimenti";
  public static final String  BASE_PATH_URL_NEMBO                     = "cunembo";
  public static final Pattern REGEXP_QUADRO_DINAMICO                  = Pattern
      .compile("/cunembo(116|117|118)([a-z])?_([a-z0-9_]+)/");

  public NemboSecurityManager(IQuadroEJB quadroEJB)
  {
    this.quadroEJB = quadroEJB;
  }

  @Override
  public Return validate(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception
  {
    NemboSecurity annotation = (NemboSecurity) findHandlerAnnotation(
        NemboSecurity.class, handler);
    try
    {
      if (annotation != null)
      {
        request.setAttribute("useCaseController", annotation.value());
        if (annotation.controllo() == NemboSecurity.Controllo.NESSUNO)
        {
          return Return.CONTINUE_TO_NEXT_MANAGER;
        }
        String codiceCU = null;
        if (annotation.tipoMapping() == TipoMapping.USECASE)
        {
          codiceCU = annotation.value();
        }
        else
        {
          if (annotation.tipoMapping() == TipoMapping.QUADRO_DINAMICO)
          {
            codiceCU = extractCUQuadroDinamicoFromUrl(request);
          }
          else
          {
            // Se si aggiungesse un altro tipo di mapping SI DEVE
            // NECESSARIAMENTE inserire la gestione in questo punto.
            // Volutamente non metto un valore di
            // default per evitare che ci si dimentichi, in quel caso il
            // codiceCU sarà null e schianterà il controllo
            // successivo!
          }
        }
        validaAccesso(request, codiceCU, annotation.controllo());
      }
      else
      {
        NemboSercurityRedirectException exception = new NemboSercurityRedirectException(
            "Autorizzazione di accesso alla pagina non specificata. Si prega di segnalare il problema all'assistenza tecnica");
        logger.error(
            "[NemboSecurityManager::validate] Si è verificato un errore: Autorizzazione di accesso alla pagina non specificata",
            exception);
        throw exception;
      }
    }
    catch (NemboSercurityRedirectException sr)
    {
      sendErrorPage(request, response, sr.getMessage(), annotation);
      return Return.SKIP_ALL_MANAGER_AND_CONTROLLER;
    }
    return Return.CONTINUE_TO_NEXT_MANAGER;
  }

  private String extractCUQuadroDinamicoFromUrl(HttpServletRequest request)
  {
    String uri = request.getRequestURI();
    Matcher matcher = REGEXP_QUADRO_DINAMICO.matcher(uri);
    if (matcher.find())
    {
      int groupCount = matcher.groupCount();
      if (groupCount == 3)
      {
        StringBuilder sb = new StringBuilder();
        sb.append("CU-NEMBO-").append(matcher.group(1));
        String cuCode = matcher.group(2);
        if (!GenericValidator.isBlankOrNull(cuCode))
        {
          sb.append("-").append(matcher.group(2).toUpperCase());
        }
        sb.append("_").append(matcher.group(3).toUpperCase());
        return sb.toString();
      }
    }
    return null;
  }

  public boolean isAccessoAutorizzatoForCU(HttpServletRequest request,
      String nomeCdu, NemboSecurity.Controllo controllo)
  {
    try
    {
      validaAccesso(request, nomeCdu, controllo);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  public void validaAccesso(HttpServletRequest request, String nomeCdu,
      NemboSecurity.Controllo controllo)
      throws NemboSercurityRedirectException,
      ServletException, IOException,
      InternalUnexpectedException
  {
    ElencoCduDTO cdu = NemboUtils.APPLICATION.getCdu(nomeCdu);
    if (cdu == null)
    {
      throw new NemboSercurityRedirectException(
          "Errore di accesso ai dati: il caso d'uso non è riconosciuto");
    }
    String tipoAzione = cdu.getTipoAzione();
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) request
        .getSession()
        .getAttribute("utenteAbilitazioni");
    if (ElencoCduDTO.TIPO_AZIONE_READ_WRITE.equals(tipoAzione))
    {
      if (NemboUtils.PAPUASERV.isUtenteReadOnly(utenteAbilitazioni))
      {
        throw new NemboSercurityRedirectException(
            "Utente non abilitato alle funzionalità di modifica dei dati");
      }
    }
    switch (controllo)
    {
      case PROCEDIMENTO:
        verificaSeUtenteAbilitatoCDU(utenteAbilitazioni, cdu, null);
        validaProcedimento(request, cdu, utenteAbilitazioni);
        break;
      case PROCEDIMENTO_OGGETTO:
        validaProcedimentoOggetto(request, utenteAbilitazioni, cdu);
        break;
      case PROCEDIMENTO_OGGETTO_CHIUSO:
        validaProcedimentoOggettoChiuso(request, utenteAbilitazioni, cdu);
        break;
      default:
        verificaSeUtenteAbilitatoCDU(utenteAbilitazioni, cdu,
            NemboFactory.getProcedimentoOggetto(request));
        break;
    }
  }

  private void verificaSeUtenteAbilitatoCDU(
      UtenteAbilitazioni utenteAbilitazioni, ElencoCduDTO cdu,
      ProcedimentoOggetto po)
      throws NemboSercurityRedirectException
  {
    String extCodMacroCdu = null;
    if (po != null)
    {
      // Se sono all'interno di un procedimento oggetto allora ricerco se il cu
      // in questione è associato ad un'azione
      AzioneDTO azioneDTO = po.findAzioneByCU(cdu.getCodiceCdu());
      if (azioneDTO != null)
      {
        // E' associato!
        // In questo caso il macrocdu iride2 associato all'azione VINCE su
        // quello assegnato di default nella tabella di
        // elenco dei CDU (E' sicuramente <> null
        // in quanto il campo è NOT NULL su db).
        extCodMacroCdu = azioneDTO.getExtCodMacroCdu();
      }
    }
    if (extCodMacroCdu == null)
    {
      // Se extCodMacroCdu è ancora null significa che questo cu non è associato
      // a nessuna azione allora prendo il
      // macrocdu di defaul attributo nella tabella
      // NEMBO_d_elenco_cdu
      extCodMacroCdu = cdu.getExtCodMacroCdu();
    }
    if (extCodMacroCdu != null)
    {
      // Verifico se l'utente è abilitato
      if (!NemboUtils.PAPUASERV.isMacroCUAbilitato(utenteAbilitazioni,
          extCodMacroCdu))
      {
        throw new NemboSercurityRedirectException(
            "Utente con ruolo non abilitato alla funzionalità");
      }
      else
      {
        /* Se l’attore è un BENEFICIARIO o INTERMEDIARIO_CAA: */
        /*
         * per poter visualizzare un PROCEDIMENTO_OGGETTO (nella pagina di
         * dettaglio di un procedimento) riferito ad un oggetto non istanza
         * (FLAG_ISTANZA = 'N' su NEMBO_D_OGGETTO) e per poter accedere a qls
         * quadro all’interno del procedimento oggetto, devono essere verificate
         * le seguenti condizioni: o il PROCEDIMENTO_OGGETTO deve essere stat
         * chiuso (DATA_FINE not NULL) e l’esito deve essere approvato (ID_ESITO
         * deve corrispondere sulla tabella NEMBO_D_ESITO ad un CODICE like
         * 'APP%' o 'NOLIQ%'
         */
        if (NemboUtils.PAPUASERV.isAttoreAbilitato(utenteAbilitazioni,
            NemboConstants.PAPUA.ATTORE.BENEFICIARIO) ||
            NemboUtils.PAPUASERV.isAttoreAbilitato(utenteAbilitazioni,
                NemboConstants.PAPUA.ATTORE.INTERMEDIARIO_CAA))
        {
          if (po != null)
          {
            if (NemboConstants.FLAGS.NO.equals(po.getFlagIstanza()))
            {
              /* Non è un istanza ==> quindi è un'istruttoria. */

              if (po.getDataFine() == null)
              {
                throw new NemboSercurityRedirectException(
                    "L'utente può accedere a questa funzionalità solo ad oggetto chiuso");
              }
              else
              {
                String codiceEsito = NemboUtils.STRING
                    .nvl(po.getCodiceEsito());
                if (!codiceEsito.startsWith("APP")
                    && !codiceEsito.startsWith("NOLIQ"))
                {
                  throw new NemboSercurityRedirectException(
                      "L'esito della pratica non permette l'accesso a questa funzionalità per l'utente corrente");
                }
              }
            }
          }
        }
      }
    }
    else
    {
      // Non è stato associato nessun macro caso d'uso iride a questo cu
      // (possibile in quanto ext_cod_macro_cdu su
      // NEMBO_d_elenco_cdu è colonna di tipo NULL)
      // allora non permetto la prosecuzione in quanto è un errore di
      // configurazione CHE DEVE ESSERE CORRETTO su DB.
      throw new NemboSercurityRedirectException(
          "Si è verificato un errore grave nell'accesso alla funzionalità. Contattare l'assistenza tecnica comunicando il seguente messaggio: La funzionalità richiesta non è stata associata a un MacroCDU");
    }
  }

  protected void validaUtente(HttpServletRequest request,
      UtenteAbilitazioni utenteAbilitazioni,
      ElencoCduDTO cdu, Procedimento procedimento)
      throws InternalUnexpectedException, NemboSercurityRedirectException
  {
    
	if(utenteAbilitazioni.getIdProcedimento() != procedimento.getIdProcedimentoAgricolo())
    {
    	throw new NemboSercurityRedirectException("Il procedimento selezionato si può gestire esclusivamente con l'applicativo " + NemboApplication.getNomeApplicativoByIdProcedimentoAgricolo(procedimento.getIdProcedimentoAgricolo()));
    }
    else
    {
    	if (utenteAbilitazioni.getRuolo().isUtentePA())
    	{
    		validaAccessoPA(request, utenteAbilitazioni, procedimento, cdu);
    	}
    	else
    	{
    		if (utenteAbilitazioni.getRuolo().isUtenteIntermediario())
    		{
    			validaAccessoIntermediario(request, utenteAbilitazioni, procedimento,
    					cdu);
    		}
    		else
    		{
    			if (utenteAbilitazioni.getEnteAppartenenza()
    					.getAziendaAgricola() != null)
    			{
    				validaAccessoAziendaAgricola(request, utenteAbilitazioni,
    						procedimento, cdu);
    			}
    		}
    	}
    }
	  
  }

  protected void validaAccessoAziendaAgricola(HttpServletRequest request,
      UtenteAbilitazioni utenteAbilitazioni,
      Procedimento procedimento, ElencoCduDTO cdu)
      throws InternalUnexpectedException, NemboSercurityRedirectException
  {
    if (ElencoCduDTO.TIPO_AZIONE_READ_WRITE.equals(cdu.getTipoAzione()))
    {
      String errore = validaAccessoAziendaAgricola(utenteAbilitazioni,
          procedimento.getIdProcedimento(), quadroEJB);
      if (errore != null)
      {
        throw new NemboSercurityRedirectException(errore);
      }
    }
  }

  public static String validaAccessoAziendaAgricola(
      UtenteAbilitazioni utenteAbilitazioni, long idProcedimento,
      IQuadroEJB quadroEJB)
      throws InternalUnexpectedException
  {
    EnteLogin[] listaEnti = utenteAbilitazioni.getEntiAbilitati();
    if (listaEnti != null)
    {
      Long extIdAziendaAgricola = quadroEJB
          .getAziendaAgricolaProcedimento(idProcedimento, null);
      if (extIdAziendaAgricola == null)
      {
        return ERRORE_AZIENDA_NON_TROVATA;
      }
      long lIdAzienda = extIdAziendaAgricola;
      for (EnteLogin ente : listaEnti)
      {
        final AziendaAgricola aziendaAgricola = ente.getAziendaAgricola();
        if (aziendaAgricola != null)
        {
          if (aziendaAgricola.getIdAzienda() == lIdAzienda)
          {
            return null;
          }
        }
      }
      return ERRORE_UTENTE_NON_COLLEGATO_AZIENDA;
    }
    else
    {
      return ERRORE_UTENTE_NON_COLLEGATO_AZIENDA;
    }
  }

  protected void validaAccessoIntermediario(HttpServletRequest request,
      UtenteAbilitazioni utenteAbilitazioni,
      Procedimento procedimento, ElencoCduDTO cdu)
      throws InternalUnexpectedException, NemboSercurityRedirectException
  {
    if (ElencoCduDTO.TIPO_AZIONE_READ_WRITE.equals(cdu.getTipoAzione()))
    {
      String errore = validaAccessoIntermediario(utenteAbilitazioni,
          procedimento.getIdProcedimento(), quadroEJB);
      if (errore != null)
      {
        throw new NemboSercurityRedirectException(errore);
      }
    }
  }

  public static String validaAccessoIntermediario(
      UtenteAbilitazioni utenteAbilitazioni,
      long idProcedimento, IQuadroEJB quadroEJB)
      throws InternalUnexpectedException
  {
    if (!quadroEJB.hasDelegaForProcedimento(
        utenteAbilitazioni.getEnteAppartenenza().getIntermediario()
            .getIdIntermediario(),
        idProcedimento, null))
    {
      return "Non si dispone della delega per l'azienda selezionata";
    }
    return null;
  }

  protected void validaAccessoPA(HttpServletRequest request,
      UtenteAbilitazioni utenteAbilitazioni,
      Procedimento procedimento, ElencoCduDTO cdu)
      throws InternalUnexpectedException, NemboSercurityRedirectException
  {
    if (ElencoCduDTO.TIPO_AZIONE_READ_WRITE.equals(cdu.getTipoAzione()))
    {
      Long idAmmCompetenza = quadroEJB
          .getIdAmmCompetenzaProcedimento(procedimento.getIdProcedimento());
      if (idAmmCompetenza == null
          || !NemboUtils.PAPUASERV.hasAmministrazioneCompetenza(
              utenteAbilitazioni, idAmmCompetenza))
      {
        throw new NemboSercurityRedirectException(
            "Il procedimento è di competenza di un'altra amministrazione");
      }
    }
  }

  public boolean isModificaAutorizzataPerProcedimento(
      HttpServletRequest request, UtenteAbilitazioni utenteAbilitazioni,
      String nomeCdu)
      throws InternalUnexpectedException
  {
    try
    {
      ElencoCduDTO cdu = NemboUtils.APPLICATION.getCdu(nomeCdu);
      validaProcedimentoOggetto(request, utenteAbilitazioni, cdu);
      return true;
    }
    catch (NemboSercurityRedirectException e)
    {
      return false;
    }
    catch (Exception e)
    {
      throw new InternalUnexpectedException(e);
    }
  }

  protected void validaProcedimentoOggetto(HttpServletRequest request,
      UtenteAbilitazioni utenteAbilitazioni,
      ElencoCduDTO cdu)
      throws ServletException,
      IOException, InternalUnexpectedException,
      NemboSercurityRedirectException
  {
    validaProcedimento(request, cdu, utenteAbilitazioni);
    ProcedimentoOggetto po = NemboFactory.getProcedimentoOggetto(request);
    if (po == null)
    {
      throw new NemboSercurityRedirectException(
          "Errore di accesso ai dati: nessun oggetto selezionato");
    }
    verificaSeUtenteAbilitatoCDU(utenteAbilitazioni, cdu, po);
    if (ElencoCduDTO.TIPO_AZIONE_READ_WRITE.equals(cdu.getTipoAzione()))
    {
      try
      {
        long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
        UpdatePermissionProcedimentoOggetto updatePermission = quadroEJB
            .canUpdateProcedimentoOggetto(idProcedimentoOggetto, true);
        request.setAttribute("canUpdatePO", Boolean.TRUE);
        String codAttore = updatePermission.getExtCodAttore();
        if (codAttore != null)
        {
          if (!NemboUtils.PAPUASERV.isAttoreAbilitato(utenteAbilitazioni,
              codAttore))
          {
            throw new NemboSercurityRedirectException(
                "L'oggetto non è modificabile per il tipo di utente connesso");
          }
        }
      }
      catch (NemboPermissionException e)
      {
        request.setAttribute("canUpdatePO", Boolean.FALSE); // In linea teorica
                                                            // nessuna action
                                                            // dovrebbe poterlo
                                                            // usare...
                                                            // ma per coerenza
                                                            // lo inserisco lo
                                                            // stesso
        if (e
            .getType() == NemboPermissionException.ExceptionType.NOTIFICHE_BLOCCANTI)
        {
          // Notifiche bloccanti
          String errorMessage = ExceptionType.NOTIFICHE_BLOCCANTI
              .getErrorMessage(); // TODO reperire il messaggio.
          throw new NemboSercurityRedirectException(errorMessage);
        }
        else
          if (e
              .getType() == NemboPermissionException.ExceptionType.NOTIFICHE_GRAVI)
          {
            // Notifiche bloccanti
            String errorMessage = ExceptionType.NOTIFICHE_GRAVI
                .getErrorMessage(); // TODO reperire il messaggio.
            throw new NemboSercurityRedirectException(errorMessage);
          }
          else
          {
            throw new NemboSercurityRedirectException(e.getMessage());
          }
      }
    }
  }

  protected void validaProcedimentoOggettoChiuso(HttpServletRequest request,
      UtenteAbilitazioni utenteAbilitazioni,
      ElencoCduDTO cdu)
      throws ServletException,
      IOException, InternalUnexpectedException,
      NemboSercurityRedirectException
  {
    validaProcedimento(request, cdu, utenteAbilitazioni);
    ProcedimentoOggetto po = NemboFactory.getProcedimentoOggetto(request);
    if (po == null)
    {
      throw new NemboSercurityRedirectException(
          "Errore di accesso ai dati: nessun oggetto selezionato");
    }
    verificaSeUtenteAbilitatoCDU(utenteAbilitazioni, cdu, po);
    if (ElencoCduDTO.TIPO_AZIONE_READ_WRITE.equals(cdu.getTipoAzione()))
    {
      try
      {
        long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
        UpdatePermissionProcedimentoOggetto updatePermission = quadroEJB
            .canUpdateProcedimentoOggetto(idProcedimentoOggetto, false);
        if (updatePermission != null)
        {
          if (updatePermission.getDataFineBandoOggetto() != null)
          {
            throw new NemboPermissionException(
                ExceptionType.PROCEDIMENTO_CHIUSO);
          }
          if (updatePermission.getDataFineProcedimentoOggetto() == null)
          {
            throw new NemboPermissionException(
                ExceptionType.PROCEDIMENTO_OGGETTO_APERTO);
          }
          if (!NemboConstants.FLAGS.SI
              .equals(updatePermission.getFlagBandoOggettoAttivo()))
          {
            throw new NemboPermissionException(
                ExceptionType.OGGETTO_BANDO_NON_ATTIVO);
          }
          if (updatePermission.getCountNotificheBloccanti() > 0)
          {
            throw new NemboPermissionException(
                ExceptionType.NOTIFICHE_BLOCCANTI);
          }
        }
        else
        {
          throw new NemboPermissionException(
              ExceptionType.PROCEDIMENTO_OGGETTO_NON_TROVATO);
        }

        request.setAttribute("canUpdatePO", Boolean.TRUE);
        String codAttore = updatePermission.getExtCodAttore();
        if (codAttore != null)
        {
          if (!NemboUtils.PAPUASERV.isAttoreAbilitato(utenteAbilitazioni,
              codAttore))
          {
            throw new NemboSercurityRedirectException(
                "L'oggetto non è modificabile per il tipo di utente connesso");
          }
        }
      }
      catch (NemboPermissionException e)
      {
        request.setAttribute("canUpdatePO", Boolean.FALSE); // In linea teorica
                                                            // nessuna action
                                                            // dovrebbe poterlo
                                                            // usare...
                                                            // ma per coerenza
                                                            // lo inserisco lo
                                                            // stesso
        if (e
            .getType() == NemboPermissionException.ExceptionType.NOTIFICHE_BLOCCANTI)
        {
          // Notifiche bloccanti
          String errorMessage = "Notifiche gravi o bloccanti"; // TODO reperire
                                                               // il messaggio.
          throw new NemboSercurityRedirectException(errorMessage);
        }
        else
        {
          throw new NemboSercurityRedirectException(e.getMessage());
        }
      }
    }
  }

  protected Procedimento validaProcedimento(HttpServletRequest request,
      ElencoCduDTO cdu,
      UtenteAbilitazioni utenteAbilitazioni)
      throws ServletException, IOException, NemboSercurityRedirectException,
      InternalUnexpectedException
  {
    Procedimento procedimento = NemboFactory.getProcedimento(request);
    if (procedimento == null)
    {
      throw new NemboSercurityRedirectException(
          "Errore di accesso ai dati: nessun procedimento selezionato");
    }
    validaUtente(request, utenteAbilitazioni, cdu, procedimento);
    return procedimento;
  }

  protected void sendErrorPage(HttpServletRequest request,
      HttpServletResponse response, String errore,
      NemboSecurity annotation)
      throws ServletException, IOException
  {
    request.setAttribute("errore", errore);
    String errorPage = annotation == null ? null : annotation.errorPage();
    if (GenericValidator.isBlankOrNull(errorPage))
    {
      if (Boolean.TRUE.equals(request.getAttribute("isPopup")))
      {
        errorPage = "/WEB-INF/jsp/dialog/soloErrore.jsp";
      }
      else
      {
        errorPage = "/WEB-INF/jsp/errore/utenteNonAutorizzato.jsp";
      }
    }
    request.getRequestDispatcher(errorPage).forward(request, response);
  }

  protected class NemboSercurityRedirectException extends Exception
  {
    /** serialVersionUID */
    private static final long serialVersionUID = -4384286202614572376L;

    public NemboSercurityRedirectException(String message)
    {
      super(message);
    }
  }
}

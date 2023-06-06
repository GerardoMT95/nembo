package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.util.StringTokenizer;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.stampa.placeholder.PlaceHolderManager;

public class FirmaVerbaleAmmissioneFinanziamento extends Fragment
{
  public static final String TAG_NAME_FIRMA = "RiquadroFirma";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB,
      String cuName) throws Exception
  {
    String parametroFunzSupEsitoVerbale = quadroEJB.getValoreParametroFunzSupEsitoVerbale();
    Long idBando = quadroEJB.getProcedimento(procedimentoOggetto.getIdProcedimento()).getIdBando();
    QuadroOggettoDTO quadroControlliTecAmm = procedimentoOggetto.findQuadroByCU("CU-NEMBO-165-D");
    Long idOggetto = quadroControlliTecAmm.getIdOggetto();

    /*
     * controllo se nel VALORE di PSR_D_PARAMETRO con codice 'FUNZ_SUP_ESITO_VERB' è contenuta la coppia
     * idBando/idOggetto (separatore #) relativo al bando interessato e al quadro con CU-NEMBO-165-D (Controlli tecnico
     * Amministrativi). Nel caso sia contenuta, come funzionario di grado superiore stampo il funzionario contenuto
     * nell'esito ottenuto passando l'idQuadroOggetto del quadero "Controlli tecnico Amministrativi"
     */
    boolean esiste = false;
    if (parametroFunzSupEsitoVerbale != null && idBando != null && idOggetto != null)
    {
      StringTokenizer st = new StringTokenizer(parametroFunzSupEsitoVerbale, "#");
      while (st.hasMoreElements())
      {
        String s = (String) st.nextElement();
        if (s.compareTo(idBando.toString() + "/" + idOggetto.toString()) == 0)
          esiste = true;
      }
    }

    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU("CU-NEMBO-166-V");

    EsitoFinaleDTO esitoFinaleDTO = quadroEJB.getEsitoFinale(procedimentoOggetto.getIdProcedimentoOggetto(),
        quadro.getIdQuadroOggetto());
    writer.writeStartElement(TAG_NAME_FIRMA);
    writeTag(writer, "DataFirma", NemboUtils.DATE.formatDate(procedimentoOggetto.getDataFine()));
    if (esitoFinaleDTO != null)
    {
      writeTag(writer, "FunzionarioIstruttore", esitoFinaleDTO.getDescrTecnico());
      if (!esiste)
        writeTag(writer, "FunzionarioGradoSuperiore", esitoFinaleDTO.getDescrGradoSup());
      else
      {
        esitoFinaleDTO = quadroEJB.getEsitoFinale(procedimentoOggetto.getIdProcedimentoOggetto(),
            quadroControlliTecAmm.getIdQuadroOggetto());
        if (esitoFinaleDTO != null)
        {
          writeTag(writer, "FunzionarioGradoSuperiore", esitoFinaleDTO.getDescrGradoSup());
        }
      }
    }
    else
    {
      writeTag(writer, "FunzionarioIstruttore", PlaceHolderManager.PLACEHOLDER_NON_VALORIZZATO);
      writeTag(writer, "FunzionarioGradoSuperiore", PlaceHolderManager.PLACEHOLDER_NON_VALORIZZATO);
    }
    writer.writeEndElement();
  }

}

package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.stampa.placeholder.PlaceHolderManager;

public class FirmaLetteraAmmissioneFinanziamento extends Fragment
{
  public static final String TAG_NAME_FIRMA = "RiquadroFirma";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU("CU-NEMBO-166-V");
    EsitoFinaleDTO esitoFinaleDTO = quadroEJB.getEsitoFinale(procedimentoOggetto.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto());
    writer.writeStartElement(TAG_NAME_FIRMA);
    writeTag(writer, "DataFirma", NemboUtils.DATE.formatDate(procedimentoOggetto.getDataFine()));
    if (esitoFinaleDTO!=null)
    {
      writeTag(writer, "FunzionarioIstruttore", esitoFinaleDTO.getDescrTecnico());
      writeTag(writer, "FunzionarioGradoSuperiore", esitoFinaleDTO.getDescrGradoSup());
    }
    else
    {
      writeTag(writer, "FunzionarioIstruttore", PlaceHolderManager.PLACEHOLDER_NON_VALORIZZATO);
      writeTag(writer, "FunzionarioGradoSuperiore", PlaceHolderManager.PLACEHOLDER_NON_VALORIZZATO);
    }
    writer.writeEndElement();
  }
}

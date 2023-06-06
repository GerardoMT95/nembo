package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.stampa.placeholder.PlaceHolderManager;

public class FirmaLetteraAmmissionePremio extends Fragment
{
  public static final String TAG_NAME_FIRMA = "RiquadroFirma";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    writer.writeStartElement(TAG_NAME_FIRMA);
    writeTag(writer, "DataFirma", NemboUtils.DATE.formatDate(procedimentoOggetto.getDataFine()));
    writeTag(writer, "FunzionarioIstruttore", PlaceHolderManager.PLACEHOLDER_NON_VALORIZZATO);

    String funzionario = quadroEJB.getResponsabileProcedimento(procedimentoOggetto.getIdProcedimento());
    if(funzionario != null)
    {
      writeTag(writer, "FunzionarioGradoSuperiore", funzionario);
    }
    else
    {
      writeTag(writer, "FunzionarioGradoSuperiore", PlaceHolderManager.PLACEHOLDER_NON_VALORIZZATO);
    }
    writer.writeEndElement();
  }
}

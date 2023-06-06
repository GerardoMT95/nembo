package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.IntegrazioneAlPremioDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroIntegrazione extends Fragment
{
  public static final String TAG_NAME_FRAGMENT_INTEGRAZIONE = "QuadroIntegrazione";
  public static final String TAG_ELENCO_INTEGRAZIONE        = "ElencoIntegrazione";
  public static final String TAG_RIGA_INTEGRAZIONE          = "RigaIntegrazione";
  public static final String TAG_TOTALE_INTEGRAZIONE        = "TotaleIntegrazione";

  @Override
  public void writeFragment(XMLStreamWriter writer,
      ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB,
      String cuName) throws Exception
  {
    List<IntegrazioneAlPremioDTO> elenco = quadroEJB.getIntegrazioneAlPremio(
        procedimentoOggetto.getIdProcedimento(),
        procedimentoOggetto.getIdProcedimentoOggetto(), null);
    writer.writeStartElement(TAG_NAME_FRAGMENT_INTEGRAZIONE);
    if (elenco != null && !elenco.isEmpty())
    {
      writeTag(writer, "VisibilityIntegrazione", "true");
      writer.writeStartElement(TAG_ELENCO_INTEGRAZIONE);
      BigDecimal totaleContributoConcesso = BigDecimal.ZERO;
      BigDecimal totaleLiquidato = BigDecimal.ZERO;
      BigDecimal totaleEconomie = BigDecimal.ZERO;
      BigDecimal totaleContributoIntegrazione = BigDecimal.ZERO;
      BigDecimal totaleSanzioni = BigDecimal.ZERO;
      for (IntegrazioneAlPremioDTO integrazione : elenco)
      {
        writer.writeStartElement(TAG_RIGA_INTEGRAZIONE);
        writeTag(writer, "Livello", integrazione.getOperazione());
        final BigDecimal contributoConcesso = integrazione
            .getContributoConcesso();
        totaleContributoConcesso = NemboUtils.NUMBERS
            .add(totaleContributoConcesso, contributoConcesso);
        writeTag(writer, "ContributoConcesso",
            NemboUtils.FORMAT.formatCurrency(contributoConcesso));
        final BigDecimal liquidato = integrazione.getTotaleLiquidato();
        totaleLiquidato = NemboUtils.NUMBERS.add(totaleLiquidato, liquidato);
        writeTag(writer, "Liquidato",
            NemboUtils.FORMAT.formatCurrency(liquidato));
        final BigDecimal economie = integrazione.getEconomie();
        totaleEconomie = NemboUtils.NUMBERS.add(totaleEconomie, economie);
        writeTag(writer, "Economie",
            NemboUtils.FORMAT.formatCurrency(economie));
        final BigDecimal contributoIntegrazione = integrazione
            .getContributoIntegrazione();
        totaleContributoIntegrazione = NemboUtils.NUMBERS
            .add(totaleContributoIntegrazione, contributoIntegrazione);
        writeTag(writer, "ContributoIntegrazione",
            NemboUtils.FORMAT.formatCurrency(contributoIntegrazione));
        final BigDecimal sanzioni = integrazione
            .getContributoRiduzioniSanzioni();
        totaleSanzioni = NemboUtils.NUMBERS.add(totaleSanzioni, sanzioni);
        writeTag(writer, "Sanzioni",
            NemboUtils.FORMAT.formatCurrency(sanzioni));
        writer.writeEndElement(); // TAG_RIGA_INTEGRAZIONE
      }
      writer.writeEndElement(); // TAG_ELENCO_INTEGRAZIONE

      writer.writeStartElement(TAG_TOTALE_INTEGRAZIONE);
      writeTag(writer, "TotaleConcesso",
          NemboUtils.FORMAT.formatCurrency(totaleContributoConcesso));
      writeTag(writer, "TotaleLiquidato",
          NemboUtils.FORMAT.formatCurrency(totaleLiquidato));
      writeTag(writer, "TotaleEconomie",
          NemboUtils.FORMAT.formatCurrency(totaleEconomie));
      writeTag(writer, "TotaleContrInteg",
          NemboUtils.FORMAT.formatCurrency(totaleContributoIntegrazione));
      writeTag(writer, "TotaleSanzioni",
          NemboUtils.FORMAT.formatCurrency(totaleSanzioni));
      writer.writeEndElement(); // TAG_TOTALE_INTEGRAZIONE
    }
    else
    {
      writeTag(writer, "VisibilityIntegrazione", "false");
    }
    writer.writeEndElement(); // TAG_NAME_FRAGMENT_INTEGRAZIONE
  }

}

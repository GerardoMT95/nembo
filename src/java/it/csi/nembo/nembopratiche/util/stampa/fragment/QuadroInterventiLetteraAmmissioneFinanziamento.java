package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.validator.GenericValidator;

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DatiLocalizzazioneParticellarePerStampa;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.RigaJSONInterventoQuadroEconomicoDTO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroInterventiLetteraAmmissioneFinanziamento extends Fragment
{
  public static final String TAG_NAME_FRAGMENT_INTERVENTI = "QuadroInterventi";
  public static final String TAG_NAME_ELENCO_INTERVENTI = "Interventi";
  public static final String TAG_NAME_LOCALIZZAZIONE = "Localizzazione";
  public static final String TAG_NAME_ELENCO_PARTICELLE = "ElencoParticelleInt";
  public static final String TAG_NAME_DATI_PARTICELLA = "DatiParticellaInt";
  public static final String TAG_NAME_RIBASSO = "Ribasso";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    final IInterventiEJB ejbInterventi = NemboUtils.APPLICATION.getEjbInterventi();
    final long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
    List<RigaJSONInterventoQuadroEconomicoDTO> elenco = null; //ejbInterventi.getElencoInterventiQuadroEconomico(idProcedimentoOggetto);
    if (elenco != null)
    {

      writer.writeStartElement(TAG_NAME_FRAGMENT_INTERVENTI);
      writeVisibility(writer, true);
      
      writer.writeStartElement(TAG_NAME_ELENCO_INTERVENTI);
      BigDecimal totaleInvestimento = BigDecimal.ZERO;
      BigDecimal totaleAmmesso = BigDecimal.ZERO;
      BigDecimal totaleContributo = BigDecimal.ZERO;

      for (RigaJSONInterventoQuadroEconomicoDTO item : elenco)
      {
        if (NemboConstants.INTERVENTI.TIPO_OPERAZIONE_ELIMINAZIONE.equals(item.getFlagTipoOperazione()))
        {
          // Ignoro gli interventi eliminati
          continue;
        }
        writer.writeStartElement("Intervento");
        writeTag(writer, "Numero", String.valueOf((item.getProgressivo() != null) ? item.getProgressivo() : " "));
      //  writeTag(writer, "TipoIntervento", item.getDescTipoClassificazione());

        final String ulterioriInformazioni = item.getUlterioriInformazioni();
        if (!GenericValidator.isBlankOrNull(ulterioriInformazioni))
        {
          writeTag(writer, "DescrizioneIntervento", item.getDescIntervento() + " - " + ulterioriInformazioni);
        }
        else
        {
          writeTag(writer, "DescrizioneIntervento", item.getDescIntervento());
        }

        final BigDecimal importoInvestimento = item.getImportoInvestimento().setScale(2, RoundingMode.HALF_UP);
        totaleInvestimento = NemboUtils.NUMBERS.add(totaleInvestimento, importoInvestimento);
        writeTag(writer, "SpesaPreventivata", NemboUtils.FORMAT.formatDecimal2(importoInvestimento));
        final BigDecimal importoAmmesso= NemboUtils.NUMBERS.nvl(item.getImportoAmmesso()).setScale(2, RoundingMode.HALF_UP);
        totaleAmmesso = NemboUtils.NUMBERS.add(totaleAmmesso, importoAmmesso);
        writeTag(writer, "SpesaAmmessa", NemboUtils.FORMAT.formatDecimal2(importoAmmesso));
        
        writeTag(writer, "PercentualeContributo", NemboUtils.FORMAT.formatDecimal2(item.getPercentualeContributo()));

        final BigDecimal importoContributo= NemboUtils.NUMBERS.nvl(item.getImportoContributo()).setScale(2, RoundingMode.HALF_UP);
        totaleContributo = NemboUtils.NUMBERS.add(totaleContributo, importoContributo);
        writeTag(writer, "ImportoContributo", NemboUtils.FORMAT.formatDecimal2(importoContributo));

        writer.writeEndElement(); // Intervento
      }
      writer.writeEndElement(); // Interventi
      writer.writeStartElement("TotaliInterventi");
      writeTag(writer, "TotaleSpesaPreventivata", NemboUtils.FORMAT.formatDecimal2(totaleInvestimento));
      writeTag(writer, "TotaleSpesaAmmessa", NemboUtils.FORMAT.formatDecimal2(totaleAmmesso));
      writeTag(writer, "TotaleImportoContributo", NemboUtils.FORMAT.formatDecimal2(totaleContributo));
      writer.writeEndElement(); // "TotaliInterventi"
      List<DatiLocalizzazioneParticellarePerStampa> listParticellare = ejbInterventi.getLocalizzazioneParticellePerStampa(idProcedimentoOggetto, NemboConstants.FLAGS.NO);
      /*
      if (bandoConPercentualeRiduzione)
      {
        InfoRiduzione infoRiduzione = ejbInterventi.getInfoRiduzione(idProcedimentoOggetto);
        writer.writeStartElement(TAG_NAME_RIBASSO);
        writeVisibility(writer, true);
        writeTag(writer, "PercentualeRiduzione", NemboUtils.FORMAT.formatDecimal2(infoRiduzione.getPercentuale()));
        writeTag(writer, "TotaleImportoRichiesto", NemboUtils.FORMAT.formatDecimal2(infoRiduzione.getTotaleRichiesto()));
        writer.writeEndElement(); // TAG_NAME_RIBASSO
      }*/
      writer.writeStartElement(TAG_NAME_LOCALIZZAZIONE);
      if (listParticellare!=null && !listParticellare.isEmpty())
      {
        writeVisibility(writer, true);
        writer.writeStartElement(TAG_NAME_ELENCO_PARTICELLE);
        boolean tipoLocalizzazioneSup=false;
        for(DatiLocalizzazioneParticellarePerStampa particella:listParticellare)
        {
          writer.writeStartElement(TAG_NAME_DATI_PARTICELLA);
          writeTag(writer, "Numero", NemboUtils.STRING.nvl(particella.getProgressivo()));
          writeTag(writer, "DescrizioneIntervento", particella.getDescIntervento());
          writeTag(writer, "ComuneInt", particella.getDescComune());
          writeTag(writer, "SezioneInt", particella.getSezione());
          writeTag(writer, "FoglioInt", NemboUtils.STRING.nvl(particella.getFoglio()));
          writeTag(writer, "ParticellaInt", NemboUtils.STRING.nvl(particella.getParticella()));
          writeTag(writer, "SubalternoInt", NemboUtils.STRING.nvl(particella.getSubalterno()));
          writeTag(writer, "SupCatastaleInt", particella.getSupCatastale());
          writeTag(writer, "DescDestinazioneProduttivaInt", particella.getDescTipoUtilizzo());
          writeTag(writer, "SupUtilizzataInt", particella.getSuperficieUtilizzata());
          final String superficieImpegno = particella.getSuperficieImpegno();
          if (!tipoLocalizzazioneSup && superficieImpegno!=null)
          {
            tipoLocalizzazioneSup=true;
          }
          writeTag(writer, "SupImpegnoInt", NemboUtils.FORMAT.formatDecimal4(NemboUtils.NUMBERS.getBigDecimal(superficieImpegno)));
          writer.writeEndElement(); // TAG_NAME_DATI_PARTICELLA
        }
        writer.writeEndElement(); // TAG_NAME_ELENCO_PARTICELLE
        writeTag(writer, "TipoLocalizzazione", tipoLocalizzazioneSup?"SUP":"NOSUP");        
      }
      else
      {
        writeVisibility(writer, false);
      }
      writer.writeEndElement(); // TAG_NAME_LOCALIZZAZIONE
      writer.writeEndElement(); // TAG_NAME_FRAGMENT_INTERVENTI
    }
  }

}

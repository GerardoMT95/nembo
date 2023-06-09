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
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.InfoMisurazioneIntervento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.InfoRiduzione;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaElencoInterventi;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroInterventi extends Fragment
{
  public static final String TAG_NAME_FRAGMENT_INTERVENTI = "QuadroInterventi";
  public static final String TAG_NAME_LOCALIZZAZIONE = "Localizzazione";
  public static final String TAG_NAME_ELENCO_PARTICELLE = "ElencoParticelleInt";
  public static final String TAG_NAME_DATI_PARTICELLA = "DatiParticellaInt";
  public static final String TAG_NAME_RIBASSO = "Ribasso";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    String dato = "";
    String valore = "";
    String um = "";

    final IInterventiEJB ejbInterventi = NemboUtils.APPLICATION.getEjbInterventi();
    final long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
    //List<RigaElencoInterventi> elenco = ejbInterventi.getElencoInterventiProcedimentoOggetto(idProcedimentoOggetto, NemboConstants.FLAGS.NO);
    List<RigaElencoInterventi> elenco = null;
    final boolean bandoConPercentualeRiduzione = ejbInterventi.isBandoConPercentualeRiduzione(idProcedimentoOggetto);
    if (elenco != null)
    {

      writer.writeStartElement(TAG_NAME_FRAGMENT_INTERVENTI);
      writeVisibility(writer, true);
      
      String tipoLocalizzazione = "NOSUP";
      long idLocalizzRif = 8;
      
      for(RigaElencoInterventi item: elenco)
      {
    	  if(item.getIdTipoLocalizzazione() == idLocalizzRif)
    	  {
    		  tipoLocalizzazione = "SUP";
    		  break;
    	  }
      }
      
      
      writer.writeStartElement("Interventi");
      BigDecimal totaleInvestimento = BigDecimal.ZERO;

      for (RigaElencoInterventi item : elenco)
      {
        if (NemboConstants.INTERVENTI.TIPO_OPERAZIONE_ELIMINAZIONE.equals(item.getFlagTipoOperazione()))
        {
          // Ignoro gli interventi eliminati
          continue;
        }
        writer.writeStartElement("Intervento");
        writeTag(writer, "Numero", String.valueOf((item.getProgressivo() != null) ? item.getProgressivo() : " "));
        writeTag(writer, "TipoIntervento", item.getDescTipoClassificazione());

        if (!GenericValidator.isBlankOrNull(item.getUlterioriInformazioni()))
        {
          writeTag(writer, "DescrizioneIntervento", item.getDescIntervento() + " - " + item.getUlterioriInformazioni());
        }
        else
        {
          writeTag(writer, "DescrizioneIntervento", item.getDescIntervento());
        }

        writeCDataTag(writer, "Comuni", (item.getDescComuni() != null) ? item.getDescComuni().replace("<br />", "\n") : " ");

        dato = "";
        valore = "";
        um = "";
        int count = 0;
        String sep = "";
        String datoValoreUM = "";
        
        for (InfoMisurazioneIntervento info : item.getMisurazioni())
        {
          if(count > 0)
          {
        	  sep = "\n";
          }
          
          dato = info.getDescMisurazione();
          if (info.isMisuraVisibile())
          {
            valore = NemboUtils.FORMAT.formatGenericNumber(info.getValore(), 4, false);
            um = ((info.getCodiceUnitaMisura() != null) ? info.getCodiceUnitaMisura() : " ");
            datoValoreUM += sep + dato + " " + valore + " " + um;
          }
          else
          {
            datoValoreUM += sep + dato;
          }
        }

        writeCDataTag(writer, "DatoValoreUM", datoValoreUM);
        final BigDecimal importoInvestimento = item.getImportoInvestimento().setScale(2, RoundingMode.HALF_UP);
        totaleInvestimento = NemboUtils.NUMBERS.add(totaleInvestimento, importoInvestimento);
        writeTag(writer, "Importo", NemboUtils.FORMAT.formatDecimal2(importoInvestimento));

        writer.writeEndElement(); // Intervento
      }
      writer.writeEndElement(); // Interventi
      writeTag(writer, "TotaleInvestimento", NemboUtils.FORMAT.formatDecimal2(totaleInvestimento));
      List<DatiLocalizzazioneParticellarePerStampa> listParticellare = ejbInterventi.getLocalizzazioneParticellePerStampa(idProcedimentoOggetto, NemboConstants.FLAGS.NO);
      if (bandoConPercentualeRiduzione)
      {
        InfoRiduzione infoRiduzione = ejbInterventi.getInfoRiduzione(idProcedimentoOggetto);
        writer.writeStartElement(TAG_NAME_RIBASSO);
        writeVisibility(writer, true);
        writeTag(writer, "PercentualeRiduzione", NemboUtils.FORMAT.formatDecimal2(infoRiduzione.getPercentuale()));
        writeTag(writer, "TotaleImportoRichiesto", NemboUtils.FORMAT.formatDecimal2(infoRiduzione.getTotaleRichiesto()));
        writer.writeEndElement(); // TAG_NAME_RIBASSO
      }
      writer.writeStartElement(TAG_NAME_LOCALIZZAZIONE);
      if (listParticellare!=null && !listParticellare.isEmpty())
      {
        writeVisibility(writer, true);
        writeTag(writer, "TipoLocalizzazione", tipoLocalizzazione);
        writer.writeStartElement(TAG_NAME_ELENCO_PARTICELLE);
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
          writeTag(writer, "SupImpegnoInt", NemboUtils.FORMAT.formatDecimal4(NemboUtils.NUMBERS.getBigDecimal(particella.getSuperficieImpegno())));
          writer.writeEndElement(); // TAG_NAME_DATI_PARTICELLA
        }
        writer.writeEndElement(); // TAG_NAME_ELENCO_PARTICELLE
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

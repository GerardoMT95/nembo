package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.dichiarazioni.DettaglioInfoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.dichiarazioni.GruppoInfoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.dichiarazioni.ValoriInseritiDTO;
import it.csi.nembo.nembopratiche.util.NemboConstants;

public class QuadroAllegati extends Fragment
{
  public static final String TAG_NAME_FRAGMENT_ALLEGATI = "QuadroAllegati";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU("CU-NEMBO-108");
    List<GruppoInfoDTO> dichiarazioni = quadroEJB.getDichiarazioniOggetto(procedimentoOggetto.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto(),
        procedimentoOggetto.getIdBandoOggetto());

    writer.writeStartElement(TAG_NAME_FRAGMENT_ALLEGATI);
    writeVisibility(writer, true);
    writer.writeStartElement("GruppiAllegati");
    for (GruppoInfoDTO gruppo : dichiarazioni)
    {
      writer.writeStartElement("GruppoAllegati");
      writeTag(writer, "TitoloGruppoAllegati", gruppo.getDescrizione());
      writer.writeStartElement("Allegati");
      for (DettaglioInfoDTO info : gruppo.getDettaglioInfo())
      {
    	  if(!info.isChecked())
   		   continue;
   	  
        writer.writeStartElement("Allegato");
        writeTag(writer, "FlagObbligatorio", String.valueOf(NemboConstants.FLAGS.SI.equals(info.getFlagObbligatorio())));
        writeTag(writer, "FlagSelezionato", String.valueOf(info.isChecked()));
        writeTag(writer, "TestoAllegato", getTestoDichiarazione(info));
        writer.writeEndElement(); // Allegato
      }
      writer.writeEndElement(); // Allegati
      writer.writeEndElement(); // GruppoAllegati
    }
    writer.writeEndElement(); // GruppiAllegati
    writer.writeEndElement(); // TAG_NAME_FRAGMENT_ALLEGATI
  }

  protected String getTestoDichiarazione(DettaglioInfoDTO info)
  {
    String testoAllegati = info.getDescrizione();
    if (testoAllegati.indexOf("$$") >= 0)
    {
      Matcher m = Pattern.compile("(\\$\\$INTEGER|\\$\\$STRING|\\$\\$NUMBER|\\$\\$DATE)").matcher(testoAllegati);
      int posizione = 1;
      StringBuilder sb=new StringBuilder(testoAllegati);
      int start=0;
      while (m.find())
      {
        String placeHolder=m.group(1);
        start=sb.indexOf(placeHolder, start);
        sb.replace(start, start+placeHolder.length(), getValore(info.getValoriInseriti(), posizione++));
      }
      testoAllegati=sb.toString();
    }
    return testoAllegati;
  }

  protected String getValore(List<ValoriInseritiDTO> valoriInseriti, int position)
  {
    if (valoriInseriti != null)
    {
      for (ValoriInseritiDTO val : valoriInseriti)
      {
        if (val.getPosizione() == position)
          return val.getValore();
      }
    }
    return "_______";
  }

}

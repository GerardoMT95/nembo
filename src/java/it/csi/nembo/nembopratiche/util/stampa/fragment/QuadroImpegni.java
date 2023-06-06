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

public class QuadroImpegni extends Fragment
{
  public static final String TAG_NAME_FRAGMENT_IMPEGNI = "QuadroImpegni";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU("CU-NEMBO-106-I");
    List<GruppoInfoDTO> dichiarazioni = quadroEJB.getDichiarazioniOggetto(procedimentoOggetto.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto(),
    procedimentoOggetto.getIdBandoOggetto());

    writer.writeStartElement(TAG_NAME_FRAGMENT_IMPEGNI);
    writeVisibility(writer, true);
    
    if(dichiarazioni!=null && dichiarazioni.size()>0)
    {
	    writer.writeStartElement("GruppiImpegni");
	    for (GruppoInfoDTO gruppo : dichiarazioni)
	    {
	      writer.writeStartElement("GruppoImpegni");
	      writeTag(writer, "TitoloGruppoImpegni", gruppo.getDescrizione());
	      writer.writeStartElement("Impegni");
	      for (DettaglioInfoDTO info : gruppo.getDettaglioInfo())
	      {
	        writer.writeStartElement("Impegno");
	        writeTag(writer, "FlagObbligatorio", String.valueOf(NemboConstants.FLAGS.SI.equals(info.getFlagObbligatorio())));
	        writeTag(writer, "FlagSelezionato", String.valueOf(info.isChecked()));
	        writeCDataTag(writer, "TestoImpegno", getTestoDichiarazione(info));
	        writer.writeEndElement(); // Dichiarazione
	      }
	      writer.writeEndElement(); // Dichiarazioni
	      writer.writeEndElement(); // GruppoDichiarazioni
	    }
	    writer.writeEndElement(); // GruppiDichiarazioni
    }
    writer.writeEndElement(); // TAG_NAME_FRAGMENT_DICHIARAZIONI
  }

  protected String getTestoDichiarazione(DettaglioInfoDTO info)
  {
    String testoDichiarazioni = info.getDescrizione();
    if (testoDichiarazioni.indexOf("$$") >= 0)
    {
      Matcher m = Pattern.compile("(\\$\\$INTEGER|\\$\\$STRING|\\$\\$NUMBER|\\$\\$DATE)").matcher(testoDichiarazioni);
      int posizione = 1;
      StringBuilder sb=new StringBuilder(testoDichiarazioni);
      int start=0;
      while (m.find())
      {
        String placeHolder=m.group(1);
        start=sb.indexOf(placeHolder, start);
        sb.replace(start, start+placeHolder.length(), getValore(info.getValoriInseriti(), posizione++));
      }
      testoDichiarazioni=sb.toString();
    }
    return testoDichiarazioni;
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

package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.danni.DanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.ParticelleDanniDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroDanni extends Fragment
{
  private static final String TAG_NAME_FRAGMENT_QUADRO_DANNI 					= "QuadroDanni";
  private static final String SEZIONE_VUOTA 									= "SezioneVuota";
  private static final String TAG_NAME_TITOLO_SEZIONE_DANNI 					= "TitoloSezioneDanni";
  private static final String TAG_NAME_DANNI 									= "Danni";
  private static final String TAG_NAME_TAB_DANNI 								= "TabDanni";
  private static final String TAG_NAME_RIGA_DANNI								= "RigaDanni";
  private static final String TAG_NAME_RIGA_TOTALE								= "RigaTotale";
  private static final String TAG_NAME_TOTALE_IMPORTO							= "TotImporto";
  private static final String TAG_NAME_PART_DANNI								= "PartDanni";
  private static final String TAG_NAME_VISIBILITY_PARTICELLARE					= "VisibilityParticellare";
  private static final String TAG_NAME_TAB_PART_DANNI							= "TabPartDanni";
  private static final String TAG_NAME_RIGA_PART_DANNI							= "RigaPartDanni";
  

	@Override
	public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB,
			String cuName) throws Exception
	{
		final IQuadroNemboEJB ejbQuadroNembo = NemboUtils.APPLICATION.getEjbQuadroNembo();
		final long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
		List<DanniDTO> listDanni = ejbQuadroNembo.getListaDanniByProcedimentoOggetto(idProcedimentoOggetto, ejbQuadroNembo.getIdProcedimentoAgricoloByIdProcedimentoOggetto(idProcedimentoOggetto));
		List<ParticelleDanniDTO> listConduzioni = ejbQuadroNembo.getListConduzioniDanni(idProcedimentoOggetto);
		writer.writeStartElement(TAG_NAME_FRAGMENT_QUADRO_DANNI);
		writeTag(writer, SEZIONE_VUOTA, "false");
		writeVisibility(writer, true);
		writeTag(writer, TAG_NAME_TITOLO_SEZIONE_DANNI, "Quadro - Danni");
		writer.writeStartElement(TAG_NAME_DANNI);
		writer.writeStartElement(TAG_NAME_TAB_DANNI);
		BigDecimal somma = new BigDecimal("0.0");
		if (listDanni != null && listDanni.size() > 0)
		{
			for (DanniDTO item : listDanni)
			{
				writer.writeStartElement(TAG_NAME_RIGA_DANNI);
				writeTag(writer, "Progressivo", Long.toString(item.getProgressivo()));
				writeTag(writer, "TipoDanno", item.getTipoDanno());
				writeTag(writer, "Denominazione", item.getDenominazioneFormatted());
				writeTag(writer, "DescEntitaDanneggiata", item.getDescEntitaDanneggiata());
				writeTag(writer, "DescDanno", item.getDescrizione());
				writeTag(writer, "Quantita", item.getQuantitaUnitaMisuraFormatter());
				writeTag(writer, "Importo", item.getImportoFormattedStampa());
				writer.writeEndElement(); // TAG_NAME_RIGA_PLV
				somma = somma.add(item.getImporto());
			}
		}
		String sommaFormatted = NemboUtils.FORMAT.formatDecimal2(somma) + " €";
		writer.writeStartElement(TAG_NAME_RIGA_TOTALE);		//TAG_NAME_RIGA_TOTALE
		writeTag(writer,TAG_NAME_TOTALE_IMPORTO,sommaFormatted);
		writer.writeEndElement(); 							// TAG_NAME_RIGA_TOTALE
		
		writer.writeEndElement(); 							// TAG_NAME_TAB_DANNI
		writer.writeEndElement(); 							// TAG_NAME_DANNI
		
		
		writer.writeStartElement(TAG_NAME_PART_DANNI);
		if(listConduzioni != null && listConduzioni.size()>0)
		{
			writeTag(writer, TAG_NAME_VISIBILITY_PARTICELLARE, "true");
			writer.writeStartElement(TAG_NAME_TAB_PART_DANNI);
			for(ParticelleDanniDTO conduzione: listConduzioni)
			{
				writer.writeStartElement(TAG_NAME_RIGA_PART_DANNI);
				writeTag(writer,"Progressivo",Long.toString(conduzione.getProgressivo()));
				writeTag(writer,"Provincia",conduzione.getDescProvincia());
				writeTag(writer,"Comune",conduzione.getDescComune());
				writeTag(writer,"Sezione",conduzione.getSezione());
				writeTag(writer,"Foglio",Long.toString(conduzione.getFoglio()));
				writeTag(writer,"Particella",Long.toString(conduzione.getParticella()));
				writeTag(writer,"Subalterno",conduzione.getSubalterno());
				writeTag(writer,"SupCatastale",conduzione.getSupCatastaleFormatted());
				writeTag(writer,"Superficie",conduzione.getSuperficieUtilizzataFormatted());
				writeTag(writer,"Utilizzo",conduzione.getDescTipoUtilizzo());
				writer.writeEndElement(); 
			}
			writer.writeEndElement(); 
		}
		else
		{
			writeTag(writer, TAG_NAME_VISIBILITY_PARTICELLARE, "false");
			writer.writeStartElement(TAG_NAME_TAB_PART_DANNI);	//TAG_NAME_TAB_PART_DANNI
			writer.writeStartElement(TAG_NAME_RIGA_PART_DANNI); //TAG_NAME_RIGA_PART_DANNI
			writeTag(writer,"Progressivo","");
			writeTag(writer,"Provincia","");
			writeTag(writer,"Comune","");
			writeTag(writer,"Sezione","");
			writeTag(writer,"Foglio","");
			writeTag(writer,"Particella","");
			writeTag(writer,"Subalterno","");
			writeTag(writer,"SupCatastale","");
			writeTag(writer,"Superficie","");
			writeTag(writer,"Utilizzo","");
			writer.writeEndElement(); 						// TAG_NAME_RIGA_PART_DANNI 
			writer.writeEndElement(); 						// TAG_NAME_TAB_PART_DANNI 
		}
		writer.writeEndElement();							// TAG_NAME_PART_DANNI 
		writer.writeEndElement(); 							// TAG_NAME_FRAGMENT_QUADRO_DANNI
	}
}

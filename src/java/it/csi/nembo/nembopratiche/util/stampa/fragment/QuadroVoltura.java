package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.VolturaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;

public class QuadroVoltura extends Fragment {
	public static final String TAG_NAME_FRAGMENT = "QuadroVoltura";

	@Override
	public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception {
		
		writer.writeStartElement(TAG_NAME_FRAGMENT);

		VolturaDTO voltura = quadroEJB.getVoltura(procedimentoOggetto.getIdProcedimentoOggetto());
		if (voltura != null) {
			writeVisibility(writer, Boolean.TRUE);
			writeTag(writer, "DenAzienda", voltura.getDenominazioneAzienda());
			writeTag(writer, "SedeLegale", voltura.getSedeLegale());
			writeTag(writer, "LegaleRappr", voltura.getRappresentanteLegale());
			writeTag(writer, "Motivazione", voltura.getNote());
		}
		else
			writeVisibility(writer, Boolean.FALSE);

		writer.writeEndElement();

	}

}

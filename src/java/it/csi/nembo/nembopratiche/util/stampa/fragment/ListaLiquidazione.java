package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.ImportiRipartitiListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class ListaLiquidazione extends Fragment {

	public static final String TAG_NAME_FRAGMENT = "ListaLiquidazione";

	@Override
	public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception {

	}

	@Override
	public void writeFragmentListaLiquidazione(XMLStreamWriter writer, RigaJSONElencoListaLiquidazioneDTO listaLiquidazione, long idListaLiquidazione,
			IListeLiquidazioneEJB listeLiquidazioneEJB, String cuName) throws Exception {

		BigDecimal totQuotaUE = BigDecimal.ZERO;
		BigDecimal totQuotaReg = BigDecimal.ZERO;
		BigDecimal totQuotaNaz = BigDecimal.ZERO;
		BigDecimal sommaTot = BigDecimal.ZERO;
		int i = 1;
		writer.writeStartElement(TAG_NAME_FRAGMENT);
		writeVisibility(writer, true);

		List<ImportiRipartitiListaLiquidazioneDTO> l = listeLiquidazioneEJB.getImportiRipartitiListaLiquidazione(idListaLiquidazione);
		writer.writeStartElement("TabListaLiquidazione");
		if (l != null)
			for (ImportiRipartitiListaLiquidazioneDTO item : l) {
				writer.writeStartElement("RigaListaLiq");
				writeTag(writer, "Progressivo", String.valueOf(i));
				writeTag(writer, "Operazione", item.getOperazione());
				writeTag(writer, "Identificativo", item.getIdentificativo());
				writeTag(writer, "CausalePagam", item.getCausalePagam());
				writeTag(writer, "Cuaa", item.getCuaa());
				writeTag(writer, "Denominazione", item.getDenominazione());
				if (item.getQuotaUe() != null) {
					totQuotaUE = totQuotaUE.add(item.getQuotaUe());
					writeTag(writer, "QuotaUE", NemboUtils.FORMAT.formatCurrency(item.getQuotaUe()));
				} else
					writeTag(writer, "QuotaUE", "");

				if (item.getQuotaReg() != null) {
					totQuotaReg = totQuotaReg.add(item.getQuotaReg());
					writeTag(writer, "QuotaReg", NemboUtils.FORMAT.formatCurrency(item.getQuotaReg()));

				} else
					writeTag(writer, "QuotaReg", "");
				if (item.getQuotaNaz() != null) {
					totQuotaNaz = totQuotaNaz.add(item.getQuotaNaz());
					writeTag(writer, "QuotaNaz", NemboUtils.FORMAT.formatCurrency(item.getQuotaNaz()));
				} else
					writeTag(writer, "QuotaNaz", "");

				if (item.getImportoTotale() != null)
					{
						sommaTot = sommaTot.add(item.getImportoTotale());
						writeTag(writer, "ImportoTotale", NemboUtils.FORMAT.formatCurrency(item.getImportoTotale()));
					}
				else writeTag(writer, "ImportoTotale", "");

				i++;
				writer.writeEndElement();
			}

		writer.writeEndElement();

		writer.writeStartElement("Totali");
		writeTag(writer, "TotQuotaUE", NemboUtils.FORMAT.formatCurrency(totQuotaUE));
		writeTag(writer, "TotQuotaReg", NemboUtils.FORMAT.formatCurrency(totQuotaReg));
		writeTag(writer, "TotQuotaNaz", NemboUtils.FORMAT.formatCurrency(totQuotaNaz));
		writeTag(writer, "SommaTotale", NemboUtils.FORMAT.formatCurrency(sommaTot));

		writer.writeEndElement(); 

		writer.writeEndElement();

	}

}

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
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.InfoRiduzione;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.RigaJSONInterventoQuadroEconomicoDTO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroEconomico extends Fragment
{
	public static final String TAG_NAME_FRAGMENT_ECONOMICO = "QuadroEconomico";
	  public static final String TAG_NAME_ELENCO_INTERVENTI = "InterventiQE";
	  public static final String TAG_NAME_LOCALIZZAZIONE = "LocalizzazioneQE";
	  public static final String TAG_NAME_ELENCO_PARTICELLE = "ElencoParticelleIntQE";
	  public static final String TAG_NAME_DATI_PARTICELLA = "DatiParticellaIntQE";
	  public static final String TAG_NAME_RIBASSO = "RibassoQE";

	  @Override
	  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
	  {
	    
	    
	  }
  
}

package it.csi.nembo.nembopratiche.util.stampa;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.ProcedimentoDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class AmmissioneFinanziamento extends Stampa
{
  public static final String ROOT_TAG = "Domanda";

  @Override
  public byte[] genera(long idProcedimentoOggetto, String cuName) throws Exception
  {
    /*
     * WARN: Questo metodo non dovrebbe mai essere richiamato direttamente in quanto bisognerebbe passare dal findStampaFinale() che mi dovrebbe restituire una
     * delle 2 stampe (ammissione positiva e negativa) possibili. Questa classe è solo di utilità per smistare su quella giusta
     * Comunque, l'implementazione sottostante dovrebbe non generare problemi
     */
    return findStampaFinale(idProcedimentoOggetto, cuName).genera(idProcedimentoOggetto, cuName);
  }

  protected String getCodiceModulo()
  {
    return "";
  }

  protected String getCodiceModello()
  {
    return "";
  }

  protected String getRifAdobe()
  {
    return "";
  }

  @Override
  public String getDefaultFileName()
  {
    return "Lettera ammissione finanziamento.pdf";
  }

  @Override
  public Stampa findStampaFinale(long idProcedimentoOggetto, String cuName) throws Exception
  {
    IQuadroEJB quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
    ProcedimentoOggetto po = quadroEJB.getProcedimentoOggetto(idProcedimentoOggetto);
    QuadroOggettoDTO quadro = po.findQuadroByCU("CU-NEMBO-166-V");
    
    boolean preavvisoRigettoPresente = quadroEJB.findOggettoByIdProcedimento(po.getIdProcedimento(), NemboConstants.OGGETTO.CODICE.PREAVVISO_RIGETTO);
    
    EsitoFinaleDTO esitoFinaleDTO = quadroEJB.getEsitoFinale(po.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto());
    
    int progressivoIstanza = 0;
    int progressivoIstanzaISAMM = 0;
    int progressivoIstanzaISAMB = 0;
    
    boolean isIsamm = false;
    
	ProcedimentoDTO procIter = quadroEJB.getIterProcedimento(po.getIdProcedimento());
	for(ProcedimentoOggettoDTO procOggDTO: procIter.getProcedimentoOggetto())
	{
		if(NemboConstants.OGGETTO.CODICE.ISTRUTTORIA_AMMISSIONE_FINANZIAMENTO.equals(procOggDTO.getCodOggetto()))
		{
			progressivoIstanzaISAMM++;
		}
		if(NemboConstants.OGGETTO.CODICE.ISTRUTTORIA_AMMISSIBILITA.equals(procOggDTO.getCodOggetto()))
		{
			progressivoIstanzaISAMB++;
		}
		if (procOggDTO.getIdProcedimentoOggetto() == idProcedimentoOggetto)
		{
			if(NemboConstants.OGGETTO.CODICE.ISTRUTTORIA_AMMISSIONE_FINANZIAMENTO.equals(procOggDTO.getCodOggetto()))
			{
				isIsamm = true;
			}
		    break;
		}
	}
	
	if(isIsamm){
		progressivoIstanza = progressivoIstanzaISAMM;
	}else{
		progressivoIstanza = progressivoIstanzaISAMB;
	}
	
	if(progressivoIstanza == 0){
		progressivoIstanza = 1;
	}
	
	if(progressivoIstanza > 2)
		throw new ApplicationException("Attenzione! si è verificato un errore durante la stampa dell' Ammissione al finanziamento. Contattare l'assistenza tecnica");

	
	
	boolean esitoPositivo = esitoFinaleDTO == null || NemboConstants.ESITO.TIPO.POSITIVO.equals(esitoFinaleDTO.getCodiceEsito());
    if (esitoPositivo)
    {
    	if(progressivoIstanza == 1)
    		return NemboUtils.STAMPA.getStampaFromCdU(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_1);
    	else if(progressivoIstanza == 2)
    		return NemboUtils.STAMPA.getStampaFromCdU(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_2);

    }
    else if(NemboConstants.ESITO.TIPO.PARZIALMENTE_POSITIVO.equals(esitoFinaleDTO.getCodiceEsito()))
    {
    	if(preavvisoRigettoPresente){
    		progressivoIstanza = 2;
    	}
    	
    	if(progressivoIstanza == 1)
    		return NemboUtils.STAMPA.getStampaFromCdU(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_1);
    	else if(progressivoIstanza == 2)
    		return NemboUtils.STAMPA.getStampaFromCdU(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_2);
    }
    else
    {
    	if(preavvisoRigettoPresente){
    		progressivoIstanza = 2;
    	}
    	
    	if(progressivoIstanza == 1)
    		return NemboUtils.STAMPA.getStampaFromCdU(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_1);
    	else if(progressivoIstanza == 2)
    		return NemboUtils.STAMPA.getStampaFromCdU(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_2);
    }
    
    return null;
  }
}

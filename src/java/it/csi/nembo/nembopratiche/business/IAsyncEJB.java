package it.csi.nembo.nembopratiche.business;

import javax.ejb.Local;

import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;

@Local
public interface IAsyncEJB extends INemboAbstractEJB
{
  public void generaStampa(long idProcedimentoOggetto, long idProcedimento,
      long idOggettoIcona) throws InternalUnexpectedException;

  public void generaStampePerProcedimento(long idProcedimentoOggetto,
      long idProcedimento) throws InternalUnexpectedException;

  public void generaStampaListaLiquidazione(long idListaLiquidazione)
      throws InternalUnexpectedException;
}

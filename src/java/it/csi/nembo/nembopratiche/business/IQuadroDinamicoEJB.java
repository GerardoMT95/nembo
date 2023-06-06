package it.csi.nembo.nembopratiche.business;

import java.util.Map;

import javax.ejb.Local;

import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadrodinamico.QuadroDinamicoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;

@Local
public interface IQuadroDinamicoEJB extends INemboAbstractEJB
{
  public QuadroDinamicoDTO getQuadroDinamico(String codiceQuadro,
      long idProcedimentoOggetto, Long numProgressivoRecord)
      throws InternalUnexpectedException;

  public QuadroDinamicoDTO getStrutturaQuadroDinamico(String codiceQuadro,
      long idProcedimentoOggettoPerCaricamentoVociElemento)
      throws InternalUnexpectedException;

  public String aggiornaInserisciRecordQuadroDinamico(long idQuadro,
      Map<Long, String[]> mapValues,
      LogOperationOggettoQuadroDTO logOperationDTO, Long numProgressivoRecord)
      throws InternalUnexpectedException;

  public String executeIstruzioneControlli(String istruzioneSqlControlli,
      String valoreElemento) throws InternalUnexpectedException;

  public String eliminaRecordQuadroDinamico(long idQuadro,
      int numProgressivoRecord, LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException;
}
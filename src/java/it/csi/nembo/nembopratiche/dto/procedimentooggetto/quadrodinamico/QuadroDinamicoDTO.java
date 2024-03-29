package it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadrodinamico;

import java.util.ArrayList;
import java.util.List;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboConstants;

public class QuadroDinamicoDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long               serialVersionUID = -7484529262705186914L;
  protected String                        flagVisualizzazioneElenco;
  protected String                        istruzSqlPostSalvataggio;
  protected List<ElementoQuadroDTO>       elementiQuadro   = new ArrayList<>();
  protected long                          idQuadro;
  protected String                        codice;
  protected List<DatiComuniElemQuadroDTO> dati;

  public long getIdQuadro()
  {
    return idQuadro;
  }

  public void setIdQuadro(long idQuadro)
  {
    this.idQuadro = idQuadro;
  }

  public String getCodice()
  {
    return codice;
  }

  public void setCodice(String codice)
  {
    this.codice = codice;
  }

  public String getFlagVisualizzazioneElenco()
  {
    return flagVisualizzazioneElenco;
  }

  public void setFlagVisualizzazioneElenco(String flagVisualizzazioneElenco)
  {
    this.flagVisualizzazioneElenco = flagVisualizzazioneElenco;
  }

  public String getIstruzSqlPostSalvataggio()
  {
    return istruzSqlPostSalvataggio;
  }

  public void setIstruzSqlPostSalvataggio(String istruzSqlPostSalvataggio)
  {
    this.istruzSqlPostSalvataggio = istruzSqlPostSalvataggio;
  }

  public List<String> getIntestazioniElenco()
  {
    List<String> list = new ArrayList<>();
    for (ElementoQuadroDTO elemento : elementiQuadro)
    {
      if (elemento.isPresenteInElenco())
      {
        list.add(elemento.getNomeLabel());
      }
    }
    return list;
  }

  public List<ElementoQuadroDTO> getGeneratedListElementiInElenco()
  {
    List<ElementoQuadroDTO> list = new ArrayList<>();
    for (ElementoQuadroDTO elemento : elementiQuadro)
    {
      if (elemento.isPresenteInElenco())
      {
        list.add(elemento);
      }
    }
    return list;
  }

  public List<DatiComuniElemQuadroDTO> getDati()
  {
    return dati;
  }

  public void setDati(List<DatiComuniElemQuadroDTO> dati)
  {
    this.dati = dati;
  }

  public long getNumRecords()
  {
    return dati == null ? 0 : dati.size();
  }


  public List<RaggruppamentoQuadroDinamico> getRecordSingolo()
  {
    DatiComuniElemQuadroDTO datiComuniElemQuadroDTO = (dati == null
        || dati.isEmpty()) ? null : dati.get(0);
    List<RaggruppamentoQuadroDinamico> raggruppamenti = new ArrayList<>();

    List<ElementoQuadroDTO> elementiGruppo = new ArrayList<>();
    String titolo = null;
    for (ElementoQuadroDTO elemento : elementiQuadro)
    {
      if (elemento.isTipoTIT())
      {
        final RaggruppamentoQuadroDinamico raggruppamento = new RaggruppamentoQuadroDinamico(
            elementiGruppo, datiComuniElemQuadroDTO);
        raggruppamento.setSezioneConTitolo(titolo != null);
        raggruppamento.setTitolo(titolo);
        raggruppamenti.add(raggruppamento);
        titolo = elemento.getNomeLabel();
        elementiGruppo.clear();
      }
      else
      {
        elementiGruppo.add(elemento);
      }
    }
    final RaggruppamentoQuadroDinamico raggruppamento = new RaggruppamentoQuadroDinamico(
        elementiGruppo, datiComuniElemQuadroDTO);
    raggruppamento.setSezioneConTitolo(titolo != null);
    raggruppamento.setTitolo(titolo);
    raggruppamenti.add(raggruppamento);
    return raggruppamenti;
  }

  public List<RaggruppamentoQuadroDinamico> getRecordMultipli()
  {
    List<RaggruppamentoQuadroDinamico> list = new ArrayList<>();
    List<ElementoQuadroDTO> elementiQuadro = getGeneratedListElementiInElenco();
    for (DatiComuniElemQuadroDTO datiComuniElemQuadroDTO : dati)
    {
      list.add(new RaggruppamentoQuadroDinamico(elementiQuadro,
          datiComuniElemQuadroDTO));
    }
    return list;
  }

  public void addDatiComuniElemQuadro(
      DatiComuniElemQuadroDTO datiComuniElemQuadroDTO)
  {
    if (dati == null)
    {
      dati = new ArrayList<>();
    }
    dati.add(datiComuniElemQuadroDTO);
  }

  public List<ElementoQuadroDTO> getElementiQuadro()
  {
    return elementiQuadro;
  }

  public int getCountElementiQuadroElenco()
  {
    int count = 0;
    if (elementiQuadro != null)
    {
      for (ElementoQuadroDTO elementoQuadroDTO : elementiQuadro)
      {
        if (NemboConstants.FLAGS.SI
            .equals(elementoQuadroDTO.getFlagPresenzaInElenco()))
        {
          count++;
        }
      }
    }
    return count;
  }

  public void setElementiQuadro(List<ElementoQuadroDTO> elementiQuadro)
  {
    this.elementiQuadro = elementiQuadro;
  }
}

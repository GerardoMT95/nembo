package it.csi.nembo.nembopratiche.dto.messaggistica;

import java.util.Date;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica.Messaggio;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class InfoMessaggio implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = -8960657311884107949L;
  protected long            idElencoMessaggi;
  protected long            idTipoMessaggio;
  protected String          titolo;
  protected boolean         letturaObbligatoria;
  protected boolean         letto;
  protected Date            dataInizioValidita;
  protected boolean         conAllegati;

  public InfoMessaggio(Messaggio m)
  {
    this.idElencoMessaggi = m.getIdElencoMessaggi();
    this.dataInizioValidita = NemboUtils.DATE
        .fromXMLGregorianCalendar(m.getDataInizioValidita());
    this.conAllegati = m.isConAllegati();
    this.idTipoMessaggio = m.getIdTipoMessaggio();
    this.letto = m.isLetto();
    this.letturaObbligatoria = m.isLetturaObbligatoria();
    this.titolo = m.getTitolo();
  }

  public boolean isConAllegati()
  {
    return conAllegati;
  }

  public void setConAllegati(boolean conAllegati)
  {
    this.conAllegati = conAllegati;
  }

  public long getIdElencoMessaggi()
  {
    return idElencoMessaggi;
  }

  public void setIdElencoMessaggi(long idElencoMessaggi)
  {
    this.idElencoMessaggi = idElencoMessaggi;
  }

  public long getIdTipoMessaggio()
  {
    return idTipoMessaggio;
  }

  public void setIdTipoMessaggio(long idTipoMessaggio)
  {
    this.idTipoMessaggio = idTipoMessaggio;
  }

  public String getTitolo()
  {
    return titolo;
  }

  public void setTitolo(String titolo)
  {
    this.titolo = titolo;
  }

  public Date getDataInizioValidita()
  {
    return dataInizioValidita;
  }

  public void setDataInizioValidita(Date dataInizioValidita)
  {
    this.dataInizioValidita = dataInizioValidita;
  }

  public boolean isLetturaObbligatoria()
  {
    return letturaObbligatoria;
  }

  public void setLetturaObbligatoria(boolean letturaObbligatoria)
  {
    this.letturaObbligatoria = letturaObbligatoria;
  }

  public boolean isLetto()
  {
    return letto;
  }

  public void setLetto(boolean letto)
  {
    this.letto = letto;
  }

}

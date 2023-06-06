package it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi;

import java.util.Date;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class DatiRappresentanteLegaleDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = 1720386358630481522L;
  private String            codiceFiscale;
  private String            cognome;
  private String            nome;
  private String            sesso;
  private String            indirizzoResidenza;
  private String            indirizzo;
  private String            comune;
  private String            cap;
  private String            istatComune;
  private Date              dataNascita;
  private String            luogoNascita;
  private String            telefono;
  private String            mail;
  
  

  
  public String getComune() {
	return comune;
}

public void setComune(String comune) {
	this.comune = comune;
}

public String getCap() {
	return cap;
}

public void setCap(String cap) {
	this.cap = cap;
}

public String getIstatComune() {
	return istatComune;
}

public void setIstatComune(String istatComune) {
	this.istatComune = istatComune;
}

public String getIndirizzo() {
	return indirizzo;
}

public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}

public String getCodiceFiscale()
  {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale)
  {
    this.codiceFiscale = codiceFiscale;
  }

  public String getCognome()
  {
    return cognome;
  }

  public void setCognome(String cognome)
  {
    this.cognome = cognome;
  }

  public String getNome()
  {
    return nome;
  }

  public void setNome(String nome)
  {
    this.nome = nome;
  }

  public String getSesso()
  {
    return sesso;
  }

  public void setSesso(String sesso)
  {
    this.sesso = sesso;
  }

  public String getIndirizzoResidenza()
  {
    return indirizzoResidenza;
  }

  public void setIndirizzoResidenza(String indirizzoResidenza)
  {
    this.indirizzoResidenza = indirizzoResidenza;
  }

  public Date getDataNascita()
  {
    return dataNascita;
  }

  public void setDataNascita(Date dataNascita)
  {
    this.dataNascita = dataNascita;
  }

  public String getLuogoNascita()
  {
    return luogoNascita;
  }

  public void setLuogoNascita(String luogoNascita)
  {
    this.luogoNascita = luogoNascita;
  }

  public String getTelefono()
  {
    return telefono;
  }

  public void setTelefono(String telefono)
  {
    this.telefono = telefono;
  }

  public String getMail()
  {
    return mail;
  }

  public void setMail(String mail)
  {
    this.mail = mail;
  }
}

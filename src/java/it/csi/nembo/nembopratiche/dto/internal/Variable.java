package it.csi.nembo.nembopratiche.dto.internal;

import java.io.Serializable;

/**
 * Classe di utilit� che rappresenta una variabile un metodo. Viene utilizzato
 * dai metodi di logging per ricevere l'elenco delle variabili di un metodo
 * quando si verifica un errore e bisogna stamparle.
 * 
 * @author Stefano Einaudi
 * @since 1.0
 */
public class Variable implements Serializable
{
  /**
   * Serial Version UID
   */
  private static final long serialVersionUID = -4835724540697548318L;
  /**
   * Nome del Variabile
   */
  private String            nome;
  /**
   * Valore del Variabile
   */
  private Object            valore;

  public Variable(String nome, Object valore)
  {
    this.nome = nome;
    this.valore = valore;
  }

  public Variable(String nome, long valore)
  {
    this.nome = nome;
    this.valore = valore;
  }

  public Variable(String nome, int valore)
  {
    this.nome = nome;
    this.valore = valore;
  }

  public Variable(String nome, boolean valore)
  {
    this.nome = nome;
    this.valore = valore;
  }

  public Variable(String nome, char valore)
  {
    this.nome = nome;
    this.valore = String.valueOf(valore);
  }

  public Variable(String nome, double valore)
  {
    this.nome = nome;
    this.valore = valore;
  }

  public Variable(String nome, float valore)
  {
    this.nome = nome;
    this.valore = valore;
  }

  public String getNome()
  {
    return nome;
  }

  public Object getValore()
  {
    return valore;
  }
}

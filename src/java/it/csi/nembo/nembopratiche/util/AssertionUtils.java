package it.csi.nembo.nembopratiche.util;

import java.util.List;

import it.csi.nembo.nembopratiche.exception.NemboAssertionException;

/**
 * Classe astratta per le funzioni di utilità sulle asserzioni (gestione
 * custom). La classe è abstract perchè non deve essere usata direttamente ma
 * solo dalla sua implementazione nella costante Utils.STRING
 * 
 * @author Stefano Einaudi (Matr. 70399)
 * 
 */
public class AssertionUtils
{
  public void notNull(Object obj, String name) throws NemboAssertionException
  {
    if (obj == null)
    {
      throw new NemboAssertionException("L'oggetto " + name + " è null");
    }
  }

  public void notEmpty(List<?> obj, String name)
      throws NemboAssertionException
  {
    if (obj == null || obj.size() == 0)
    {
      throw new NemboAssertionException(
          "La lista di oggetti " + name + " è vuota o null");
    }
  }
}

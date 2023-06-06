package it.csi.nembo.nembopratiche.integration;

public interface IIterableOfPersistent extends IPersistent
{
  public Iterable<IPersistent> getIterable();
}

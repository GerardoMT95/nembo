package it.csi.nembo.nembopratiche.util;

import it.csi.nembo.nembopratiche.dto.internal.LogParameter;
import it.csi.nembo.nembopratiche.dto.internal.LogVariable;
import it.csi.nembo.nembopratiche.exception.InternalException;

public class ExceptionUtils
{

  public ExceptionUtils()
  {
  }

  public InternalException processDAOException(Throwable t, String query,
      LogParameter parameters[], LogVariable variables)
  {
    return null;
  }

}

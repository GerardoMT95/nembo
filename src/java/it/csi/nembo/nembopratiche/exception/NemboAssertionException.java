package it.csi.nembo.nembopratiche.exception;

public class NemboAssertionException extends InternalUnexpectedException
{
  /** serialVersionUID */
  private static final long serialVersionUID = 1864038758131826585L;

  public NemboAssertionException(String message)
  {
    super(message, (Throwable) null);
  }

  public NemboAssertionException(String message, Throwable cause)
  {
    super(message, cause);
  }
}

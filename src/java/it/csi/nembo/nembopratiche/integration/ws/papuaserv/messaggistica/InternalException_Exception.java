
package it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica;

import javax.xml.ws.WebFault;

/**
 * This class was generated by Apache CXF 2.4.6-redhat-1
 * 2016-02-02T10:41:23.529+01:00 Generated source version: 2.4.6-redhat-1
 */

@SuppressWarnings("serial")
@WebFault(name = "InternalException", targetNamespace = "http://papuaserv.webservice.business.papuaserv.papua.csi.it/")
public class InternalException_Exception extends Exception
{

  private it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica.InternalException internalException;

  public InternalException_Exception()
  {
    super();
  }

  public InternalException_Exception(String message)
  {
    super(message);
  }

  public InternalException_Exception(String message, Throwable cause)
  {
    super(message, cause);
  }

  public InternalException_Exception(String message,
      it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica.InternalException internalException)
  {
    super(message);
    this.internalException = internalException;
  }

  public InternalException_Exception(String message,
      it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica.InternalException internalException,
      Throwable cause)
  {
    super(message, cause);
    this.internalException = internalException;
  }

  public it.csi.nembo.nembopratiche.integration.ws.papuaserv.messaggistica.InternalException getFaultInfo()
  {
    return this.internalException;
  }
}

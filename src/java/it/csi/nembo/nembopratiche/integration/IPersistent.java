package it.csi.nembo.nembopratiche.integration;

import java.io.Serializable;

/* Non ha bisogno di metodi o altro, serve solo per "marcare" gli oggetti che possono essere usati per fare update/insert su db tramite annotation */
public interface IPersistent extends Serializable
{

}

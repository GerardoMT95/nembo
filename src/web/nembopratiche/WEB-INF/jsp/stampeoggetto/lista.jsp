<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<!--LISTA_STAMPE-->
<%--IMPORTANTE: non rimuovere il commento precedente, serve al javascript per capire che la pagina è stata generata correttamente --%>
<form data-comment="<!--LISTA_STAMPE-->">
	<p:table list="${stampe}">
		<p:tcol property="idOggettoIcona" type="NO_TEXT" cssClass="center">
			<p:ticon cssClass="ico24 ico_add" tooltip="Inserisce una nuova stampa" href="#" onclick="return aggiungiStampaOggetto()" headerIcon="true"
				visible="${stampeUtenteVisibili}" />
			<%-- Le icone nomeIconaStampaVisualizza e nomeIconaStampaGenera sono mutuamente esclusive, una viene visualizzata quando il pdf è su index, l'altra quando è ancora da generare. 
			     Il tutto si ottiene dal fatto che getNomeIconaStampaVisualizza restituisce un nome di css solo quando getNomeIconaStampaGenera restituisce null e viceversa (se il nome css 
			     è null il custom tag NON visualizza l'icona) --%>
			<p:ticon cssClass="property:nomeIconaStampaVisualizza" tooltip="property:tooltipStampa" href="#"
				onclick="return visualizzaStampaOggetto({idOggettoIcona})" />
			<p:ticon cssClass="property:nomeIconaStampaGenera" tooltip="property:tooltipStampa" href="#" onclick="return visualizzaStampaOggetto({idOggettoIcona})" />
			<p:ticon cssClass="property:nomeIconaElimina" tooltip="property:tooltipStampa" href="#" onclick="return eliminaStampaOggetto({idOggettoIcona})"
				visible="${stampaCancellabile}" />
		</p:tcol>
		<p:tcol property="" label="Stato" type="NO_TEXT" cssClass="center">
			<p:ticon cssClass="property:nomeIconaStato" tooltip="property:tooltipStato" href="" />
		</p:tcol>
		<p:tcol property="descTipoDocumento" label="Nome stampa" />
		<p:tcol property="descUltimoAggiornamento" label="Ultimo aggiornamento" />
	</p:table>
</form>
<script type="text/javascript">
function aggiungiStampaOggetto()
{
  return openPageInPopup('../cunembo126i/inserisci.do', 'dlgStampeOggetto', 'Inserisci stampa oggetto', '');
}

function eliminaStampaOggetto(id)
{
  return openPageInPopup('../cunembo126e/conferma_elimina_'+id+'.do', 'dlgStampeOggetto', 'Elimina stampa oggetto', '');
}

function visualizzaStampaOggettoCallbackSuccess(data)
{
  if (data.indexOf('<error>')>=0)
  {
    showMessageBox("Errore", data.replace('<error>','').replace('</error>',''));
  }
  else
  {
    if (data.indexOf('<renew>')>=0)
    {
      var url=data.replace('<renew>','').replace('</renew>','');
      openPageInPopup(url, 'dlgStampeOggetto', 'Conferma rigenerazione stampa', '');
    }
    else
    {
      if (data.indexOf('<stampa>')>=0)
      {
        var url=data.replace('<stampa>','').replace('</stampa>','');
        window.location.href=url;
      }
    }
  }
}

function visualizzaStampaOggetto(id)
{
  $.ajax(
   {
     url:'../cunembo126l/visualizza_'+id+'.do',
     async:false, 
     method:'POST',
     success: visualizzaStampaOggettoCallbackSuccess,
     type:'text/html', 
     error:
       function(a, b)
       { 
         alert('Si è verificato un errore di sistema');
       }
    })
  return false;
}


function setDialogHtmlStampeOggetto(data) 
{
  $('#dlgStampeOggetto .modal-body').html(data);
  doErrorTooltip();
}

</script>
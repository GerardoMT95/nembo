<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-12">
  <m:error />
	<m:panel id="panelLettura" title="Dati messaggio" collapsible="false">
		<table id="tblRicercaConduzioni" class="table table-hover table-bordered tableBlueTh" style="margin-bottom: 0px">
			<tbody>
				<tr>
				<th>Titolo</th>
					<td colspan="1">${messaggio.titolo}</td>
				</tr>
				<tr>
					<th>Inserito da</th>
					<td>${messaggio.utenteAggiornamento.nome}${messaggio.utenteAggiornamento.cognome}</td>
				</tr>
				<tr>
					<th>Data inserimento</th>
					<td>${dataInserimento}</td>
				</tr>
				<tr>
				<th>Descrizione</th>
					<td colspan="1">${messaggio.testoMessaggio}</td>
				</tr>
				<tr>
					<td colspan="2" class="alert alert-warning"><form name="accetto" id="accetto" action="dettaglio_${idElencoMessaggi}.do"><m:checkBox id="confermoLettura" name="confermoLettura" value="S"
							chkLabel="Dichiaro di aver preso visione di
          quanto descritto nel presente messaggio" /></form></td>
				</tr>
			</tbody>
		</table>
	</m:panel>
	<br />
  <c:if test="${messaggio.allegati!=null && messaggio.allegati.size()>0}">
    <m:panel id="panelAllegati" title="Allegati" collapsible="false">
      <table id="allegati" class="table table-hover table-bordered tableBlueTh" style="margin-bottom: 0px">
        <tbody>
          <c:forEach items="${messaggio.allegati}" var="a">
            <tr>
              <td>${a.descrizione}</td>
              <td><a href="download_allegato_${messaggio.idElencoMessaggi}_${a.idAllegato}.do">${a.nomeFile}</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </m:panel>
  </c:if>
  <br />
</div>
<br />
<br />
<input type="button" class="btn btn-default" data-dismiss="modal" value="annulla" />
<input type="button" class="btn btn-primary pull-right" value="conferma" onclick="submitFormViaAjax('accetto', onConferma)" />
<script type="text/javascript">
  function onConferma(html)
  {
    var SUCCESS = "<success>"+"true"+"</success>";
    if (html.indexOf(SUCCESS)>=0)
    {
      closeModal();
      window.location.reload();
      return;
    }
    $('#'+_lastModalID+' .modal-body').html(html); 
    doErrorTooltip();
  }
</script>

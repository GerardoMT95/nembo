<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<m:error />
<form:form action="" modelAttribute="filtroAziendeForm" id="formInserisciOggetto" method="post" class="form-horizontal">
	<div class="container-fluid">
		
		<c:if test="${msgErrore != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-danger">
						<p>
							<strong>Attenzione!</strong><br />
							<c:out value="${msgErrore}" ></c:out>
						</p>
					</div>
				</div>
			</c:if>
		
		<div id="msgAttesa" class="stdMessagePanel stdMessageLoad" style="margin-top:30px;margin-bottom:50px;display: none">			
	         <div class="alert alert-info">
	          <p>Attenzione: il sistema sta effettuando la creazione dell' ${descrizioneTipo} selezionata; l'operazione potrebbe richiedere alcuni secondi.</p>
	         </div>
	         <span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
      	</div>
		
		
		<div class="stdMessagePanel" id="msgErrore"></div>
		<div id="controlliGravi"></div>
					
		<c:if test="${elenco != null}">	
			<div id="elencoOggetto">
				<table class="table table-hover table-striped table-bordered tableBlueTh">
					<colgroup>
						<col width="5%">
						<col width="95%">
					</colgroup>
					<thead>
						<tr>
							<th colspan="2">Seleziona ${descrizioneTipo}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${elenco}" var="a">
							<tr>
								<td colspan="2" class="noPadding center">
									<h5><i><c:out value="${a.descrizione}"></c:out></i></h5>
								</td>
							</tr>
							<c:forEach items="${a.oggetti}" var="b">
								<tr>
									<td>
										<input type="hidden" name="idLegameGruppoOggetto_${b.idBandoOggetto}" value="${b.idLegameGruppoOggetto}">
										<input type="radio" name="oggettoSelezionato" value="${b.idBandoOggetto}">
									</td>
									<td><c:out value="${b.descrizione}"></c:out></td>
								</tr>
							</c:forEach>
						</c:forEach>			
					</tbody>
				</table>
			</div>
			
			<div id="note-div">
				<m:textarea id="note_tot"
					placeholder="Inserire le note (al massimo 4000 caratteri) che compariranno nell'iter dell'Oggetto/Istanza"
					label="Note" 
					name="note" preferRequestValues="${prfvalues}">${note}</m:textarea>
			</div>	
		</c:if>
		<div class="form-group puls-group" style="margin-top: 1.5em">
			<div class="col-sm-12">
				<button type="button" data-dismiss="modal" class="btn btn-default" >Chiudi</button>
				<c:if test="${noConfirm == null}">
					<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="return submitConferma();">Conferma</button>
				</c:if>
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript">

	  function submitConferma()
	  {
		  $('#dlgNuovo #msgErrore').html(' ');
		  $("#elencoOggetto").hide();
		  $("#msgAttesa").show();
		  $("#note_div").hide();
		  submitFormViaAjaxAsync('formInserisciOggetto', visualizzaMessaggi); 
		  return false;
	  }
	  function visualizzaMessaggi(data, success)
	  {
		if (success)
		{
			if (data.indexOf("redirect:../procedimento/visualizza_procedimento") != -1)
		    {
				window.location.href = data.split(":")[1];
		        return false;
		    }
			else if(data.indexOf("controlliGravi") != -1)
			{
				$("#msgAttesa").hide();  
	  		    $("#elencoOggetto").show();
				$('#dlgNuovo #controlliGravi').html(data);
			}
			else
			{
				$("#msgAttesa").hide();  
	  		    $("#elencoOggetto").show();
        		$('#dlgNuovo #msgErrore').html('<div class=\"alert alert-danger\" ><p>'+data+'</p></div>');
			}
	    }
		else
		{
			$("#note_div").hide();
			$("#msgAttesa").hide();  
  		    $("#elencoOggetto").show();
			$('#dlgNuovo #msgErrore').html('<div class=\"alert alert-danger\" ><p>Si è verificato un errore interno del Server</p></div>');
		}
	  }
	</script>
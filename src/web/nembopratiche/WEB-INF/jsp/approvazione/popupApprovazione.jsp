<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<b:error />

<form:form action="../cunembo232/popupindex.do" modelAttribute="trasmissioneForm" id="formTrasmettiOggetto" method="post">
	<div class="container-fluid">
		
		<c:if test="${msgErrore != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-danger">
						<p>
							<strong>Attenzione!</strong><br />
							<c:out value="${msgErrore}"></c:out>
						</p>
					</div>
				</div>
			</c:if>
		
		<div id="msgAttesa" class="stdMessagePanel stdMessageLoad" style="margin-top:30px;margin-bottom:50px;display: none">			
	         <div class="alert alert-info">
	          <p>Attenzione: il sistema sta effettuando l'approvazione; l'operazione potrebbe richiedere alcuni secondi.</p>
	         </div>
	         <span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
      	</div>
		
		<div class="stdMessagePanel" id="msgErrore"></div>
		
		<div class="alert alert-warning elencoStampe">
			Proseguendo con l'operazione l'oggetto verrà approvato. Continuare?
		</div>
		<div class="noteX">
			<m:textarea id="note"
				placeholder="Inserire le note (al massimo 4000 caratteri) che compariranno nell'iter dell'Oggetto/Istanza"
				label="Note" 
				name="note" preferRequestValues="${prfvalues}">${note}</m:textarea>
		</div>	
			
		<br class="clear" />
		<br/>
		<c:if test="${lStampeInAttesaFirma != null || lStampeInAttesaFirmaElettr != null}">
			<span class="elencoStampe">Elenco documenti:<br/><br/></span>
		</c:if>			
		
		<c:if test="${lStampeInAttesaFirma != null}">	
			<input type="hidden" name="elencoStampeAttesa" id="elencoStampeAttesa" value="S">
			<div class="elencoStampe">
				<table class="table table-hover table-striped table-bordered tableBlueTh">
					<thead>
						<tr>
							<th>Stato</th>
							<th>Nome Stampa</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lStampeInAttesaFirma}" var="a">
							<tr>
								<td><c:out value="${a.id}"></c:out></td>
								<td><c:out value="${a.descrizione}"></c:out></td>
							</tr>
						</c:forEach>			
					</tbody>
				</table>
				<m:checkBox name="chkConsensoFirme" value="S" checked="${chkConsensoFirme == 'S'}" chkLabel="si dichiara che i documenti in attesa di firma grafometrica/su carta sono stati effettivamente firmati su carta"></m:checkBox>
			</div>
		</c:if>
		
		
		<div class="form-group puls-group elencoAzioni" style="margin-top: 1.5em">
			<div class="col-sm-12">
				<button type="button" data-dismiss="modal" class="btn btn-default" >Chiudi</button>
				<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right elencoStampe" onclick="return submitConferma();">Conferma</button>
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript">

	  function submitConferma()
	  {
		  $('#dlgApprova #msgErrore').html(' ');
		  $(".elencoStampe").hide();
		  $(".elencoAzioni").hide();
		  $("#msgAttesa").show();
		  submitFormViaAjaxAsync('formTrasmettiOggetto', visualizzaMessaggi); 
		  return false;
	  }
	  function visualizzaMessaggi(data, success)
	  {
		if (success)
		{  
			if (data.indexOf("redirect:../cunembo232/riepilogo.do") != -1)
		    {
				window.location.href = data.split(":")[1];
		        return false;
		    }
			else
			{
				$(".elencoAzioni").show();
				$("#msgAttesa").hide();  
	  		    $(".elencoStampe").hide();
        		$('#dlgApprova #msgErrore').html('<div class=\"alert alert-danger\" ><p>'+data+'</p></div>');
			}
	    }
		else
		{
			$(".elencoAzioni").show();
			$("#msgAttesa").hide();  
  		    $(".elencoStampe").show();
  		  $('#dlgApprova #msgErrore').html('<div class=\"alert alert-danger\" ><p>'+data+'</p></div>');
		}
	  }
	</script>
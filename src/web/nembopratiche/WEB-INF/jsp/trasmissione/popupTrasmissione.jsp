<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<b:error />

<form:form action="" modelAttribute="trasmissioneForm" id="formTrasmettiOggetto" method="post">
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
	          <p>Attenzione: il sistema sta effettuando la trasmissione dell'istanza selezionata; l'operazione potrebbe richiedere alcuni secondi.</p>
	         </div>
	         <span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
      	</div>
		
		<div class="stdMessagePanel" id="msgErrore"></div>
		
		<div class="alert alert-warning elencoStampe">
			Proseguendo con l'operazione l'oggetto verrà trasmesso. Continuare?
		</div>
		
		<div class="noteX">
			<m:textarea id="note"
				placeholder="Inserire le note (al massimo 4000 caratteri) che compariranno nell'iter dell'Oggetto/Istanza"
				label="Note" 
				name="note" preferRequestValues="${prfvalues}">${note}</m:textarea>
		</div>	
			
		<br class="clear" />
		<br/>
		
		
		
		
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
		  $('#dlgTrasmetti #msgErrore').html(' ');
		  $(".elencoStampe").hide();
		  $(".noteX").hide();
		  $(".elencoAzioni").hide();
		  $("#msgAttesa").show();
		  submitFormViaAjaxAsync('formTrasmettiOggetto', visualizzaMessaggi); 
		  return false;
	  }
	  function visualizzaMessaggi(data, success)
	  {
		if (success)
		{  
			if (data.indexOf("redirect:../cunembo140/riepilogo.do") != -1)
		    {
				window.location.href = data.split(":")[1];
		        return false;
		    }
			else
			{
				$(".elencoAzioni").show();
				$(".noteX").show();
				$("#msgAttesa").hide();  
	  		    $(".elencoStampe").hide();
        		$('#dlgTrasmetti #msgErrore').html('<div class=\"alert alert-danger\" ><p>'+data+'</p></div>');
			}
	    }
		else
		{
			$(".elencoAzioni").show();
			$(".noteX").show();
			$("#msgAttesa").hide();  
  		    $(".elencoStampe").show();
			$('#dlgTrasmetti #msgErrore').html('<div class=\"alert alert-danger\" ><p>Si è verificato un errore interno del Server</p></div>');
		}
	  }
	</script>
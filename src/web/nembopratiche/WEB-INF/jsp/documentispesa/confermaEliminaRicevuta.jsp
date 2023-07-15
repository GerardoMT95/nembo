<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">

	<!--  div class="">
		<div class="alert alert-danger">
			<p>
				<strong>Attenzione!</strong><br />
				<c:out value="L'importo della ricevuta di pagamento selezionata � gi� stata associata almeno in parte ad uno o pi� interventi; 
							eliminandola verranno eliminate anche queste associazioni. Si desidera proseguire?"></c:out>
			</p>
		</div>
	</div>-->
	<h4>${msgElimina}</h4>
	<input type="hidden" id="idDocSpesa" value="${idDocSpesa}">
	<input type="hidden" id="giaRend" value="${giaRend}">
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina(${idDettRicevutaPagamento})">conferma</button>
	</div>
	<script type="text/javascript">
    function confermaElimina()
    {
        let idDettRicevutaPagamento = '${idDettRicevutaPagamento}';
        $.ajax(
          {
            type : "GET",
            url : '../cunembo263e/eliminaRicevuta_'+idDettRicevutaPagamento+'.do',
            dataType : "html",
            async : false,
            success : function(html)
            {
              let COMMENT = '<success>';
              if (html != null && html.indexOf(COMMENT) >= 0)
              {
                  let giaRend = $("#giaRend").val();
                  let idDoc = $("#idDocSpesa").val();
                  
                  if(giaRend=='S')
                	  forwardToPage('../cunembo263m/elencoricevuteDocRendicontato_'+idDoc+'.do');
                  else
            	  	forwardToPage('../cunembo263m/elencoricevute.do');
            	  //location.reload();	
              }
              else
              {
                doErrorTooltip();
                writeModalBodyError("Il documento di spesa a cui � associata la ricevuta di pagamento � gi� stato utilizzato per la rendicontazione spese, per cui non � pi� possibile eliminarla");
              }
            },
            error : function(jqXHR, html, errorThrown)
            {
              writeModalBodyError("Si � verificato un errore grave nell'accesso alla funzionalit� di eliminazione. Se il problema persistesse si prega di contattare l'assistenza tecnica");
            }
          });
    }
  </script>
</form>
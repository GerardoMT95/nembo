<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="container-fluid">
		
		<div class="">
						<div class="alert alert-info">
							<p>
								<c:out value="${title}"></c:out>
							</p>
						</div>
					</div>
		
		<form method="post" class="form-horizontal" id="formModificaEsitoTecnico">
		<m:error/>
		
		<m:textarea label="Motivazioni" rows="4" id="note" name="note" preferRequestValues="${preferRequest}"></m:textarea>
		
		
		<div class="form-group puls-group" style="margin-top: 1.5em;margin-right:0px">
				<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
				<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaModifica()">conferma</button>
		</div>
		
		
		
		<script type="text/javascript">
		    function confermaModifica()
		    {
		      $
		          .ajax(
		          {
		            type : "POST",
		            url : '../cunembo283/popupsblocca_${idGruppoOggetto}_${idProcedimento}_${codRaggruppamento}.do',
		            data : $('#formModificaEsitoTecnico').serialize(),
		            dataType : "html",
		            async : false,
		            success : function(html)
		            {
		              var COMMENT = 'success';
		              if (html != null && html.indexOf(COMMENT) == 0)
		              {
		                window.location.reload();
		              }
		              else
		              {
		                $('#dlgSbloccoGruppo .modal-body').html(html);
		                doErrorTooltip();
		              }
		            },
		            error : function(jqXHR, html, errorThrown)
		            {
		              writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di aggiornamento. Se il problema persistesse si prega di contattare l'assistenza tecnica");
		            }
		          });
		    }
		  </script>
		
		</form>
		
		
		
	</div>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<m:error />
<p:set-cu-info/>
<form method="post" class="form-horizontal" id="formNuovaVisita">
	<m:textfield label="Data Visita *" maxlength="10" labelSize="3" controlSize="3" id="dataVisita" name="dataVisita" type="DATE" preferRequestValues="${preferRequest}"/>
	<m:selectchoice label="Funzionario controllore *" id="idTecnico" name="idTecnico" preferRequestValues="${preferRequest}" list="${tecnici}" listChoice="${ufficiZona}" selectedChoice="STESSO_UFFICIO"/>
	<m:select label="Esito *" id="idEsito" name="idEsito" preferRequestValues="${preferRequest}" list="${esiti}"/>
  <m:textarea label="Note" id="note" name="note" preferRequestValues="${preferRequest}">${note}</m:textarea>
  <m:textfield label="Data verbale" maxlength="10" labelSize="3" controlSize="3" id="dataVerbale" name="dataVerbale" value="${v.dataVerbale}" type="DATE" preferRequestValues="${preferRequest}"/>
  <m:textfield label="Numero verbale" id="numeroVerbale" name="numeroVerbale" value="${v.numeroVerbale}" preferRequestValues="${preferRequest}" style="text-transform:uppercase"/>

	<div class="form-group puls-group" style="margin-top: 1.5em;margin-right:0px">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaModifica()">conferma</button>
	</div>
	<script type="text/javascript">
  initializeDatePicker();
    function confermaModifica()
    {
      $.ajax(
          {
            type : "POST",
            url : '../cunembo${cuNumber}m/popup_inserisci_visita.do',
            data : $('#formNuovaVisita').serialize(),
            dataType : "html",
            async : false,
            success : function(html)
            {
              var COMMENT = '<success>'+'true'+'</success>';
              if (html != null && html.indexOf(COMMENT) >= 0)
              {
                window.location.reload();
              }
              else
              {
                $('#dlgInserisci .modal-body').html(html);
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
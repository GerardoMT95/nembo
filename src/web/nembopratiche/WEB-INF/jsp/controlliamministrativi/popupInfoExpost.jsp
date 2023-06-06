<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<m:error />
<form method="post" class="form-horizontal" id="formModificaInfoExpost">
	<m:select label="Rischio elevato" id="idRischioElevato" name="idRischioElevato" selectedValue="${infoExPosts.idRischioElevato}" preferRequestValues="${preferRequest}" list="${elencorischi}"/>
    <m:checkbox-list name="idAnnoExPosts" id="idAnnoExPosts" checkedProperty="checked" list="${elencoanni}" textProperty="valoreHtml" valueProperty="idAnnoExPosts" label="Anni successivi alla liquidazione in cui aumentare il criterio di rischio "></m:checkbox-list> 
    <m:textarea label="Note" id="note" name="note" preferRequestValues="${preferRequest}">${infoExPosts.note}</m:textarea>
	<i>N.B: Il campo Note è obbligatorio se si seleziona il rischio elevato</i>
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
            url : '../cunembo165m/popup_info_exposts.do',
            data : $('#formModificaInfoExpost').serialize(),
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
                $('#dlgModificaExposts .modal-body').html(html);
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
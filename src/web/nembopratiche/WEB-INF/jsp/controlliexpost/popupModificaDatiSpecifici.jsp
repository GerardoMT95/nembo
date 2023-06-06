<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<m:error />
<p:set-cu-info />
<form method="post" class="form-horizontal" id="formModificaDatiSpecifici">


	<c:if test="${datiSpecifici.flagControllo=='S'}">
		<div class="stdMessagePanel">
			<div class="alert alert-info">
				<p>
					<strong>Attenzione!</strong><br />
					 Deselezionando "Controllo ex post" verranno eliminati i tutti i dati relativi al controllo ex post.
				</p>
			</div>
		</div>
	</c:if>

  <%@include file="/WEB-INF/jsp/controlliinlocomisureinvestimento/include/tabellaEstrazione.jsp" %>
	<div class="col-sm-12" style="padding-right: 0px !important">
		<div class="row">
			<div class="form-group">
				<label class="control-label col-sm-3">Preavviso</label>
				<div class="col-sm-9">
					<input id="flagPreavviso" onchange="onChangeFlagPreavviso(this)" name="flagPreavviso" value="S" type="checkbox" data-toggle="bs-toggle"
						${datiSpecifici.flagPreavviso=='S'?'checked="checked"':''} />
				</div>
			</div>
		</div>
		<div id="datiPreavviso" style="${displayPreavviso}">
			<div class="row">
				<m:textfield id="dataPreavviso" name="dataPreavviso" type="DATE" value="${datiSpecifici.dataPreavviso}" label="Data preavviso" style="min-width:100px"
					controlSize="3" labelSize="3" preferRequestValues="${preferRequest}" />
			</div>
			<div class="row">
				<div class="col-sm-6" style="padding-left: 0px !important; padding-right: 5px !important">
					<m:select id="idTipologiaPreavviso" onchange="onChangeTipologia(this)" selectedValue="${datiSpecifici.idTipologiaPreavviso}" name="idTipologiaPreavviso" list="${tipologiePreavviso}"
						label="Descrizione preavviso" labelSize="6" controlSize="6" preferRequestValues="${preferRequest}" />
				</div>
				<div class="col-sm-6">
					<m:textfield id="descTipologiaPreavviso" style="${displayAltraDesc}" name="descTipologiaPreavviso" value="${datiSpecifici.descTipologiaPreavviso}"
						preferRequestValues="${preferRequest}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="row form-group">
				<label class="control-label col-sm-3">Controllo ex post</label>
				<div class="col-sm-9">
					<input name="flagControllo" id="flagControllo" onchange="popupAvvisoFlagControllo()" type="checkbox" data-toggle="bs-toggle" value="S" ${datiSpecifici.flagControllo=='S'?'checked="checked"':''} />
				</div>
			</div>
		</div>
	</div>
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="onConferma()">conferma</button>
	</div>
	<br class="clear" />
	<script type="text/javascript">
    $('#formModificaDatiSpecifici input[data-toggle="bs-toggle"]').bootstrapToggle();
    initializeDatePicker();

    function onConferma()
    {
      $.ajax(
          {
            type : "POST",
            url : '../cunembo${cuNumber}m/modifica_dati_specifici.do',
            data : $('#formModificaDatiSpecifici').serialize(),
            dataType : "html",
            async : false,
            success : function(html)
            {
              var COMMENT = '<success>' + 'true' + '</success>';
              if (html != null && html.indexOf(COMMENT) >= 0)
              {
                window.location.reload();
              }
              else
              {
                $('#' + _lastModalID + ' .modal-body').html(html);
                doErrorTooltip();
              }
            },
            error : function(jqXHR, html, errorThrown)
            {
              writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di aggiornamento. Se il problema persistesse si prega di contattare l'assistenza tecnica");
            }
          });
    }
    function onChangeFlagPreavviso(self)
    {
      if (self.checked)
      {
        $('#datiPreavviso').show();
      }
      else
      {
        $('#datiPreavviso').hide();
      }
    }

    function onChangeTipologia(self)
    {
      var $descTipologiaPreavviso = $("#descTipologiaPreavviso");
      if ($(self).val() == "1")
      {
        $descTipologiaPreavviso.val('');
        $descTipologiaPreavviso.show();
      }
      else
      {
        $descTipologiaPreavviso.val('');
        $descTipologiaPreavviso.hide();
      }
    }
  </script>
</form>
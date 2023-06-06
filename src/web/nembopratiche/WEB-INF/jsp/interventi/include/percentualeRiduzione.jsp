<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="modificaPercentuale">
  <m:error />
  <p:set-cu-info/>
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
		<script type="text/javascript">
      function confermaPercentuale()
      {
        $.ajax(
        {
          url : "../cunembo${cuNumber}m/modificaPercentuale.do",
          async : false,
          method : "POST",
          data : $('#percRiduzione').serialize(),
          success : function(data)
          {
            $('#modificaPercentuale').replaceWith(data);
          }
        });

        return false;
      }
      var totaleInterventiCorrente='${totaleInterventi}';
      function abilitaModificaPercentuale()
      {
        $('#ico_modifica_percentuale').hide();
        $('#percRiduzione').prop("disabled", false);
        $('#conferma_percentuale').show();
        $('#annulla_percentuale').show();
        return false;
      }
      function annullaModificaPercentuale()
      {
        $('#ico_modifica_percentuale').show();
        $('#percRiduzione').prop("disabled", true);
        $('#conferma_percentuale').hide();
        $('#annulla_percentuale').hide();
        if (totaleInterventiCorrente!=$('#totaleInterventi').val())
        {
          // E' cambiato il totale degli interventi (cioè qualcuno ha modificato gli interventi in contemporanea) ==> ricarico la pagina
          window.location.reload();
        }
        else
        {
          $('#percRiduzione').val('${salvaPercentuale}');
          $('#totaleRichiesto').val('${salvaRichiesto}');
          $('#error-box').remove();
          $('.has-error').removeClass('has-error');
          $('[data-toggle="error-tooltip"]').tooltip('disable');
        }
        return false;
      }
    </script>
	</p:abilitazione-azione>
	<m:panel id="ribasso" cssClass="form-inline">
		<div class="row col-sm-12">
			<label class="control-label">Percentuale Riduzione</label>&nbsp;&nbsp;
			<div class="form-group">
				<m:textfield id="percRiduzione" name="percRiduzione" disabled="${!inModifica}" style="max-width:72px" maxlength="6" value="${percRiduzione}">
					<m:input-addon left="false">&#37;</m:input-addon>
				</m:textfield>
			</div>
			<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
				<a id="ico_modifica_percentuale" href="#" onclick="return abilitaModificaPercentuale()" class="ico32 ico_modify"
					style="margin-bottom: 0px; vertical-align: middle<c:if test="${inModifica}">;display:none</c:if>"></a>
				<m:button id="conferma_percentuale" type="button" btnType="primary" cssClass="form-control" display="${inModifica}" value="conferma"
					onclick="confermaPercentuale()">conferma</m:button>
				<m:button id="annulla_percentuale" type="button" display="${inModifica}" cssClass="form-control" value="annulla"
					onclick="annullaModificaPercentuale()" >annulla</m:button>
				<br />
			</p:abilitazione-azione>
		</div>
		<div class="row col-sm-12" style="padding-top: 16px">
			<label class="">Totale importo richiesto: </label> <span id="totaleRichiesto">${totaleRichiesto}</span>
		</div>
	</m:panel>
	<c:if test="${errors!=null}">
	<script type="text/javascript">doErrorTooltip();</script>
	</c:if>
</div>


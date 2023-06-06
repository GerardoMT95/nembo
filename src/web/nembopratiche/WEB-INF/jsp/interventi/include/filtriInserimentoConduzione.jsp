<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<form id="formFiltri" action="" method="post" >
	<div id="hiddens" style="height: 1px"></div>
	<%-- Non modificare l'id del form perchè viene utilizzato da elencoConduzioni per capire cosa riceve via ajax quando l'utente preme il pulsante "cerca e inserisci" --%>
	<div class="col-sm-5" style="padding-right: 0px !important">
		<div style="padding-bottom: 8px">
			<m:select id="istatProvincia" list="${provincie}" name="istatProvincia" label="Provincia*" tabIndex="1"
				onchange="caricaComuniPerProvinciaConConduzioni($(this).val(), 'istatComune', true)" preferRequestValues="${preferRequest}">
			</m:select>
		</div>
		<div style="padding-bottom: 8px">
			<m:select id="istatComune" list="${comuni}" name="istatComune" label="Comune*" groupCssClass="pull-right" style="clear:left" tabIndex="2"
				disabled="${comuneDisabled}" preferRequestValues="${preferRequest}" onchange="caricaSezioniPerComuniConConduzioni($(this).val(),'sezione', true)">
			</m:select>
		</div>
	</div>
	<div class="col-sm-3" style="padding-right: 0px !important">
		<div style="padding-bottom: 8px">
			<m:select id="sezione" list="${sezione}" name="sezione" label="Sezione*" controlSize="8" labelSize="4" tabIndex="3" preferRequestValues="${preferRequest}"
				disabled="${sezioneDisabled}" />
		</div>
		<div style="padding-bottom: 8px">
			<m:textfield id="foglio" name="foglio" label="Foglio*" controlSize="8" labelSize="4" tabIndex="4" preferRequestValues="${preferRequest}" maxlength="4" />
		</div>
	</div>
	<div class="col-sm-3" style="padding-right: 0px !important">
		<div style="padding-bottom: 8px">
			<m:textfield id="particella" name="particella" label="Particella" controlSize="7" labelSize="5" tabIndex="5" preferRequestValues="${preferRequest}"
				maxlength="5" />
		</div>
		<div style="padding-bottom: 8px">
			<m:textfield id="subalterno" name="subalterno" label="Subalterno" controlSize="7" labelSize="5" tabIndex="6" preferRequestValues="${preferRequest}"
				maxlength="3" />
		</div>
	</div>
	<div id="pulsanteFiltri" class="col-sm-1">
		<div style="padding-bottom: 8px; padding-top: 14px">
			<a role="button" class="btn btn-primary pull-right" onclick="return openInsertPopup()">cerca <br />e<br /> inserisci
			</a>
		</div>
	</div>
</form>
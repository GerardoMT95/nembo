<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link type="text/css" rel="stylesheet" href="/nembopratiche/css/vertical-tabs.css"></link>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<div class="container-fluid" id="content">
		<m:panel id="mainPanel">
			<br />
			<table class="table table-hover table-bordered tableBlueTh">
				<thead>
					<tr>
						<th class="center">Operazione</th>
						<th class="center">Importo investimento</th>
						<th class="center">Importo ammesso</th>
						<th class="center">Contributo concesso</th>
						<th class="center">Percentuale anticipo</th>
						<th class="center">Importo anticipo</th>
					</tr>
				</thead>
				<c:forEach items="${datiAnticipo.ripartizioneAnticipo}" var="r">
				<tr <c:if test="${r.flagAnticipo=='S'}"> class="anticipabile"</c:if>>
					<td class="numero codiceLivello"><c:out value="${r.codiceLivello}" /></td>
					<td class="numero"><fmt:formatNumber value="${r.importoInvestimento}" pattern="###,##0.00" /> &euro;</td>
					<td class="numero"><fmt:formatNumber value="${r.importoAmmesso}" pattern="###,##0.00" /> &euro;</td>
					<td id="contributoConcesso" class="numero contributoConcesso"><fmt:formatNumber value="${r.importoContributo}"
							pattern="###,##0.00" /> &euro;</td>
          <td class="numero percentualeAnticipo" data-value="${datiAnticipo.percentualeAnticipo}">
            <c:if test="${r.flagAnticipo=='S'}"><fmt:formatNumber value="${datiAnticipo.percentualeAnticipo}" pattern="##0.00" /></c:if>
            <c:if test="${r.flagAnticipo!='S'}">0,00</c:if>
          </td>
          <td class="numero importoAnticipo" data-value="${r.importoAnticipo}"><fmt:formatNumber value="${r.importoAnticipo}" pattern="###,##0.00" /> &euro;</td>
				</tr>
				</c:forEach>
          <tr>
            <th class="numero">&nbsp;</th>
            <th class="numero"><fmt:formatNumber value="${datiAnticipo.totaleImportoInvestimento}" pattern="###,##0.00" /></th>
            <th class="numero"><fmt:formatNumber value="${datiAnticipo.totaleImportoAmmesso}" pattern="###,##0.00" /></th>
            <th class="numero"><fmt:formatNumber value="${datiAnticipo.totaleContributoConcesso}" pattern="###,##0.00" /></th>
            <th class="numero">&nbsp;</th>
            <th class="numero" id="totaleImportoAnticipoPerLivelli"><fmt:formatNumber value="${datiAnticipo.totaleImportoAnticipo}" pattern="###,##0.00" /></th>
          </tr>
			</table>
			<form name="modifica" method="post" class="form-horizontal">
				<m:error />
				<m:textfield preferRequestValues="${preferRequest}" id="percentualeAnticipo" name="percentualeAnticipo" label="Percentuale anticipo" controlSize="3"
					labelSize="3" onchange="cambioPercentuale()" value="${datiAnticipo.percentualeAnticipo}" maxlength="6" />
				<m:textfield preferRequestValues="${preferRequest}" id="importoAnticipo" name="importoAnticipo" label="Importo anticipo" onchange="cambioImporto()"
					type="EURO" value="${datiAnticipo.importoAnticipo}" maxlength="13" />
				<script type="text/javascript">
          var __percentualeAnticipo = document.getElementById('percentualeAnticipo').value;
          var __importoAnticipo = document.getElementById('importoAnticipo').value;
        </script>
				<m:panel id="panelFideiussione" title="Dati fideiussione">
					<m:textfield preferRequestValues="${preferRequest}" id="numeroFideiussione" name="numeroFideiussione" label="Numero fideiussione"
						value="${datiAnticipo.numeroFideiussione}" maxlength="30" />
					<m:textfield id="importoFideiussione" name="importoFideiussione" label="Importo fideiussione" type="EURO"
						value="" maxlength="13" disabled="true"/>
					<m:textfield preferRequestValues="${preferRequest}" id="dataStipulaFideiussione" name="dataStipulaFideiussione" label="Data stipula" type="DATE"
						groupCssClass="date-textfield" maxlength="10" value="${datiAnticipo.dataStipula}" />
					<m:textfield preferRequestValues="${preferRequest}" id="dataScadenzaFideiussione" name="dataScadenzaFideiussione" label="Data scadenza" type="DATE"
						groupCssClass="date-textfield" maxlength="10" value="${datiAnticipo.dataScadenza}" />
					<m:textfield id="beneficiarioFideiussione" name="beneficiarioFideiussione" label="Beneficiario fideiussione"
						value="${datiAnticipo.beneficiarioFideiussione}" maxlength="30" disabled="true"/>
				</m:panel>
				<m:panel id="istitutoBancario" title="Istituto">
					<m:error errorName="tipoIstituto" />
					<div style="min-width: 800px">
						<div style="position: relative; width: 20%; min-width: 180px; float: left">
							<ul class="nav nav-tabs vertical-tabs">
								<li <c:if test="${checkedBanca!=null}"> class="active"</c:if>><a href="#tabIstitutoBancario" id="hrefTabIstitutoBancario" data-toggle="tab"
									style="display: none"></a> <input type="radio" name="tipoIstituto" id="tipoIstitutoBanca" value="B" ${checkedBanca}
									onfocus="$('#hrefTabIstitutoBancario').click();$('#tipoIstitutoBanca').prop('checked',true)" /> <label for="tipoIstitutoBanca" style="cursor: pointer">Istituto
										Bancario</label></li>
								<li <c:if test="${checkedAltroIstituto!=null}"> class="active"</c:if>><a href="#tabAltroIstituto" id="hrefTabAltroIstituto" data-toggle="tab"
									style="display: none"></a><input type="radio" name="tipoIstituto" id="tipoIstitutoAltro" value="A" ${checkedAltroIstituto}
									onfocus="$('#hrefTabAltroIstituto').click();$('#tipoIstitutoAltro').prop('checked',true)" /><label for="tipoIstitutoAltro" style="cursor: pointer">Altro
										istituto</label></li>
							</ul>
						</div>
						<div style="position: relative; width: 75%; float: right; min-width: 120px">
							<div class="tab-content">
								<div id="tabIstitutoBancario" role="tabpanel" class="tab-pane<c:if test="${checkedBanca!=null}"> active</c:if>">
									<m:error errorName="extIdSportello" />
									<m:textfield id="abi" name="abi" label="ABI" disabled="true" value="${datiAnticipo.abi}" />
									<m:textfield id="denominazioneBanca" name="denominazioneBanca" label="Denominazione banca" disabled="true" value="${datiAnticipo.denominazioneBanca}" />
									<m:textfield id="cab" name="cab" label="CAB" groupCssClass="date-textfield" disabled="true" value="${datiAnticipo.cab}" />
									<m:textfield id="denominazioneSportello" name="denominazioneSportello" label="Denominazione sportello" disabled="true"
										value="${datiAnticipo.denominazioneSportello}" />
									<m:textfield id="indirizzoSportello" name="indirizzoSportello" label="Indirizzo" disabled="true" value="${datiAnticipo.indirizzoSportello}" />
									<m:textfield id="capSportello" name="capSportello" label="CAP" disabled="true" value="${datiAnticipo.capSportello}" />
									<m:textfield id="comuneSportello" name="comuneSportello" label="Comune sportello" disabled="true" value="${datiAnticipo.descrizioneComuneSportello}" />
									<m:textfield id="provinciaSportello" name="provinciaSportello" label="Provincia sportello" disabled="true"
										value="${datiAnticipo.siglaProvinciaSportello}" />
									<m:button btnType="primary" type="button" onclick="return ricercaSportello()">${azioneBanca}</m:button>
									<input type="hidden" id="extIdSportello" name=extIdSportello value="${datiAnticipo.extIdSportello}" />
								</div>
								<div id="tabAltroIstituto" class="tab-pane<c:if test="${checkedAltroIstituto!=null}"> active</c:if>">
									<m:error errorName="extIstatComune" />
									<m:textfield id="altroIstituto" name="altroIstituto" label="Denominazione istituto" value="${datiAnticipo.altroIstituto}" maxlength="100" />
									<m:textfield id="indirizzoAltroIstituto" name="indirizzoAltroIstituto" label="Indirizzo" value="${datiAnticipo.indirizzoAltroIstituto}" maxlength="200" />
									<m:textfield id="comuneAltroIstituto" name="comuneAltroIstituto" label="Comune" disabled="true" value="${datiAnticipo.descCompletaComuneAltroIstituto}">
										<m:input-addon left="false">
											<a href="#" onclick="return ricercaComuneAltroIstituto()"><i class="icon icon-folder-open"></i> Cambia</a>
										</m:input-addon>
									</m:textfield>
									<input type="hidden" name="extIstatComune" id="extIstatComune" value="${datiAnticipo.extIstatComune}" />
								</div>
							</div>
						</div>
					</div>
				</m:panel>
				<div class="col-sm-12">
					<a href="../cunembo169v/index.do" class="btn  btn-default pull-left">annulla</a>
					<button type="submit" class="btn  btn-primary pull-right">conferma</button>
				</div>
			</form>
			<br />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
	$('#importoFideiussione').val($('#importoAnticipo').val());
    function selezionaSportello(self, id)
    {
      var page = "dati_sportello_" + id + ".json";
      $.ajax(
      {
        url : page,
        async : false
      }).success(function(sportello)
      {
        $('#abi').val(sportello.abi);
        $('#denominazioneBanca').val(sportello.denominazioneBanca);
        $('#cab').val(sportello.cab);
        $('#denominazioneSportello').val(sportello.denominazioneSportello);
        $('#indirizzoSportello').val(sportello.indirizzoSportello);
        $('#capSportello').val(sportello.capSportello);
        $('#capSportello').val(sportello.capSportello);
        $('#comuneSportello').val(sportello.descrizioneComuneSportello);
        $('#provinciaSportello').val(sportello.siglaProvinciaSportello);
        $('#extIdSportello').val(sportello.idSportello);
        closeModal();
      }).error(function()
      {
        alert('Errore nel caricamento degli sportelli per la banca con id #' + $id)
      });
      return false;
    }

    function selezionaComune(self, istat)
    {
      $('#extIstatComune').val(istat);
      $('#comuneAltroIstituto').val($(self).html());
      closeModal();
      return false;
    }

    function ricercaSportello()
    {
      return openPageInPopup("popup_ricerca_sportello.do", "ricerca_sportello", "Elenco sportelli", 'modal-lg');
    }

    function ricercaComuneAltroIstituto()
    {
      return openPageInPopup("popup_ricerca_comune_altro_istituto.do", "ricerca_comuni_altro_istituto", "Elenco comuni", 'modal-lg');
    }
    function ricalcolaImportiPerLivello()
    {
      var importoAnticipabile = ${datiAnticipo.importoContributoAnticipabile};
      var $importiElement = $('.anticipabile');
      var $lastCodiceLivello = $importiElement.last().find('.codiceLivello').html();
      var totImporto = 0;
      var importoAnticipo=Number($('#importoAnticipo').val().replace(',','.'));
      $importiElement.each(
          function(idx, element)
		      {
  		      $element=$(element);
  		      var contributoConcessoHtml=$element.find('.contributoConcesso').html().replace(".","").replace(",",".");
  		      var contributoConcesso = parseFloat(contributoConcessoHtml);
  		      $codiceLivello =  $element.find('.codiceLivello').html();
  		      var importoAnticipoLivello = 0;
  		      if ($codiceLivello==$lastCodiceLivello)
   		      {
              importoAnticipoLivello = importoAnticipo-totImporto;
   		      }
  		      else
    		    {
              importoAnticipoLivello = contributoConcesso*importoAnticipo/importoAnticipabile;
    		    }
  		      if (isNaN(importoAnticipoLivello))
    		    {
      		    importoAnticipo = '0,00';
      		  }
  		      else
    		    {
  		        totImporto+=importoAnticipoLivello;
      		  }
      		  var percentuale = Number($('#percentualeAnticipo').val().replace(',','.'));
  		      var elemImportoAnticipo = $element.find('.importoAnticipo');
  		      var elemPercentualeAnticipo = $element.find('.percentualeAnticipo');
  		      if (isNaN(percentuale))
    		    {
  		        percentuale = 0;
      		  }
  		      elemImportoAnticipo.html(importoAnticipoLivello.formatCurrency()+" &euro;");
  		      if (Number(elemImportoAnticipo.data('value'))!=importoAnticipo)
    		    {
  		        elemImportoAnticipo.addClass("red-bold");
    		    }
  		      else
   		      {
              elemImportoAnticipo.removeClass("red-bold");
   		      }
            elemPercentualeAnticipo.html(percentuale.formatCurrency());
  		      if (Number(elemPercentualeAnticipo.data('value'))!=percentuale)
    		    {
  		        elemPercentualeAnticipo.addClass("red-bold");
    		    }
  		      else
   		      {
  		        elemPercentualeAnticipo.removeClass("red-bold");
   		      }
		      }
      );
      $('#totaleImportoAnticipoPerLivelli').html(importoAnticipo.formatCurrency());
    }
    function cambioPercentuale()
    {
      var sPercentuale = $('#percentualeAnticipo').val();
      if (__percentualeAnticipo == sPercentuale)
      {
        return false;
      }
      __percentualeAnticipo = percentuale;
      var percentuale = Number(sPercentuale.replace(',', '.'));
      if (isNaN(percentuale))
      {
        $('#importoAnticipo').val('0');
      }
      else
      {
        var contributoConcesso = ${datiAnticipo.importoContributoAnticipabile};
        var importoAnticipo = Math.floor(contributoConcesso * percentuale * 100) / 10000.0;
        importoAnticipo = importoAnticipo.formatCurrency();
        importoAnticipo = importoAnticipo.replace(new RegExp('\\.', 'g'), '');
        $('#importoAnticipo').val(importoAnticipo);
      }
      $('#importoFideiussione').val($('#importoAnticipo').val());
      ricalcolaImportiPerLivello();
    }

    function cambioImporto()
    {
      var sImportoAnticipo = $('#importoAnticipo').val();
      if (__importoAnticipo == sImportoAnticipo)
      {
        return false;
      }
      __importoAnticipo = sImportoAnticipo;
      var importoAnticipo = Number(sImportoAnticipo.replace(',', '.'));
      if (isNaN(importoAnticipo))
      {
        $('#percentualeAnticipo').val(0);
      }
      else
      {
        var contributoConcesso = ${datiAnticipo.importoContributoAnticipabile};
        var percentuale = importoAnticipo / contributoConcesso * 100;
        percentuale = percentuale.formatCurrency(2);
        percentuale = percentuale.replace(new RegExp('\\.', 'g'), '');
        percentuale = percentuale.replace(new RegExp('(0)+$', 'g'), '');
        while (percentuale.length - percentuale.lastIndexOf(',') < 3)
        {
          percentuale += '0';
        }
        $('#percentualeAnticipo').val(percentuale);
      }
      $('#importoFideiussione').val($('#importoAnticipo').val());
      ricalcolaImportiPerLivello();
    }
    var savePercentualeAnticipo = '';
    var saveImportoAnticipo = '';
    $(document).ready(function()
    {
      $('#importoAnticipo').keyup(function(event)
      {
        var currentVal = $(this).val();
        if (saveImportoAnticipo != currentVal)
        {
          saveImportoAnticipo = currentVal;
          cambioImporto();
        }
      })
    });
    $(document).ready(function()
    {
      $('#percentualeAnticipo').keyup(function(event)
      {
        var currentVal = $(this).val();
        if (savePercentualeAnticipo != currentVal)
        {
          savePercentualeAnticipo = currentVal;
          cambioPercentuale();
        }
      });
     /* <c:if test="${errors!=null}"> */
     ricalcolaImportiPerLivello();
     /* </c:if> */
    });
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
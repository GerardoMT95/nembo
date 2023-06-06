<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:set-cu-info />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<div class="container-fluid" id="content">
		<m:panel id="panelFiliere">
			<m:error />



			<m:panel title="ESITO CONTROLLO DICHIARAZIONI" id="controlliAmministrativi">

				
				
				<table summary="Dati specifici" class="table table-hover table-bordered table-condensed tableBlueTh vcenter" data-show-columns="true">
					<tbody>
						<tr>
							<td colspan="6" style="background-color: #DDD"><strong>Estrazione</strong></td>
						</tr>
						<tr>
							<th>Domanda sottoposta a estrazione</th>
							<td class="center" style="cursor: not-allowed"><input type="checkbox" disabled="disabled" data-toggle="bs-toggle"
								${datiSpecifici.flagSottopostaEstrazione=='S'?'checked="checked"':''} /></td>
							<th>Data estrazione</th>
							<td><fmt:formatDate pattern="dd/MM/YYYY" value="${datiSpecifici.dataEstrazione}" /></td>
							<th>Riferimento estrazione</th>
							<td>${datiSpecifici.descTipoEstrazioneNumLotto}</td>
						</tr>
						<tr>
							<th >Domanda estratta per controllo a campione delle dichiarazioni sostitutive ex DPR 445/2000</th>
							<td class="center" style="cursor: not-allowed"><input type="checkbox"
										disabled="disabled" data-toggle="bs-toggle" ${flagEstratta!=null && flagEstratta!='N'?'checked="checked"':''} /></td>
							<td colspan="4"> </td>
						</tr>
					</tbody>
				</table>
				
				<%@include file="/WEB-INF/jsp/controlliperdichiarazioni/include/tabellaEsitoTecnicoIstruttoria.jsp"%>
				
			</m:panel>


			<div style="padding-top: 1em;">
				<table class="myovertable table table-hover table-condensed table-bordered">
					<colgroup>
						<col width="10%">
						<col width="90%">
					</colgroup>
					<tbody>
						<tr>
							<th>Ultima modifica</th>
							<td><c:out value="${ultimaModifica}"></c:out></td>
						</tr>
					</tbody>
				</table>
			</div>

		</m:panel>


	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
	<script type="text/javascript">
		$('input[data-toggle="bs-toggle"]').bootstrapToggle();
		function selectAll(self, name) {
			$("input[name='" + name + "']").prop("checked", $(self).prop('checked'));
		}

		  function modificaEsitoTecnico()
		  {
		    return openPageInPopup('../cunembo282m/popupEsitoTecnico.do', 'dlgModificaEsitoTecnico', 'Modifica esito tecnico', 'modal-lg',
		        false);
		  }  
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
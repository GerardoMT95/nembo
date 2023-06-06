<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-165-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-165-M" />
	<form name="mainForm" method="post" action="aggiorna_visite_luogo.do">
		<div class="container-fluid" id="content">
			<m:panel id="panelModificaInterv">
				<m:error />
				<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh">
					<thead>
						<tr>
							<th class="center">Data visita</th>
							<th class="center">Funzionario istruttore</th>
							<th class="center">Esito</th>
							<th class="center">Note</th>
							<th class="center">Data verbale</th>
							<th class="center">Numero verbale</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${visite}" var="v">
							<tr>
								<td style="vertical-align: middle; width: 150px"><input type="hidden" name="idVisitaLuogo" value="${v.idVisitaLuogo}" /> <m:textfield
										id="dataVisita_${v.idVisitaLuogo}" name="dataVisita_${v.idVisitaLuogo}" type="DATE" value="${v.dataVisita}" preferRequestValues="${preferRequest}" /></td>
								<td style="vertical-align: middle">
									<m:selectchoice id="idTecnico_${v.idVisitaLuogo}" name="idTecnico_${v.idVisitaLuogo}"
										preferRequestValues="${preferRequest}" list="${tecnici}" selectedValue="${v.extIdTecnico}" listChoice="${ufficiZona}"/>
								</td>
								<td style="vertical-align: middle; width: 140px"><m:select id="idEsito_${v.idVisitaLuogo}" name="idEsito_${v.idVisitaLuogo}"
										preferRequestValues="${preferRequest}" list="${esiti}" selectedValue="${v.idEsito}" /></td>
								<td style="vertical-align: middle"><m:textarea id="note_${v.idVisitaLuogo}" name="note_${v.idVisitaLuogo}" preferRequestValues="${preferRequest}">${v.note}</m:textarea></td>
								<td style="vertical-align: middle; width: 150px"><m:textfield id="dataVerbale_${v.idVisitaLuogo}" name="dataVerbale_${v.idVisitaLuogo}" value="${v.dataVerbale}" type="DATE" preferRequestValues="${preferRequest}"/></td>
								<td style="vertical-align: middle; width: 220px"><m:textfield id="numeroVerbale_${v.idVisitaLuogo}" name="numeroVerbale_${v.idVisitaLuogo}" value="${v.numeroVerbale}" preferRequestValues="${preferRequest}" style="text-transform:uppercase" maxlength="20"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<a role="button" class="btn btn-primary" href="../cunembo165d/index.do">Indietro</a>
				<input type="submit" name="confermaModificaInterventi" role="s" class="btn btn-primary pull-right" value="conferma" />
				<br class="clear" />
			</m:panel>
		</div>
	</form>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
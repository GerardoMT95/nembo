<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li class="active"><a href="../index.do">Home</a></li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica/>
	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelMessaggi" title="Elenco messaggi con lettura obbligatoria rivolti agli utenti del Sistema Informativo Agricoltura del Piemonte">
			<table class="table table-hover table-striped table-bordered tableBlueTh">
				<thead>
					<tr>
						<th class="center">Titolo</th>
						<th class="center">Data inserimento</th>
						<th class="center">Allegati</th>
					</tr>
				</thead>
				<c:if test="${messaggiDaLeggere.size()>0}">
					<tr>
					<th colspan="3">Messaggi da Leggere</th>
					</tr>
					<c:forEach items="${messaggiDaLeggere}" var="m">
						<tr>
							<td><a href="dettaglio_${m.idElencoMessaggi}.do"
								onclick="return openPageInPopup('dettaglio_${m.idElencoMessaggi}.do','dlgMessaggio','Visualizzazione messaggio','modal-lg',false)">${m.titolo}</a></td>
							<td class="center"><fmt:formatDate value="${m.dataInizioValidita}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
							<td class="center">${m.conAllegati?"Si":"No"}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${messaggiLetti.size()>0}">
					<tr>
					<th colspan="3">Messaggi letti</th>
					</tr>
					<c:forEach items="${messaggiLetti}" var="m">
						<tr>
							<td><a href="dettaglio_${m.idElencoMessaggi}.do"
								onclick="return openPageInPopup('dettaglio_${m.idElencoMessaggi}.do','dlgMessaggio','Visualizzazione messaggio','modal-lg',false)">${m.titolo}</a></td>
							<td class="center"><fmt:formatDate value="${m.dataInizioValidita}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
							<td class="center">${m.conAllegati?"Si":"No"}</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</m:panel>
		<a href="../index.do" role="button" class="btn btn-default">indietro</a>
		<br />
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
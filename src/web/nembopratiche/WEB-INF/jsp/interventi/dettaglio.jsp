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
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<body>
  <p:set-cu-info/>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelPrincipale">
			<m:panel title="Intervento" id="intervento">
				<table summary="Elenco Interventi" class="table table-hover table-bordered " data-show-columns="true">
					<tbody>
						<c:if test="${intervento.idDannoAtm != null}">
							<tr>
									<th>Tipo danno</th>
									<td>${intervento.descTipoDanno}</td>
							</tr>
							<tr>
								<th>Danno</th>
								<td>${intervento.descDanno}</td>
							</tr>
						</c:if>
						<tr>
							<th>Progressivo</th>
							<td>${intervento.progressivo}</td>
						</tr>
						<tr>
							<th>Tipo classificazione</th>
							<td>${intervento.descTipoClassificazione}</td>
						</tr>
						<tr>
							<th>Tipo intervento</th>
							<td>${intervento.descTipoAggregazione}</td>
						</tr>
            <tr>
              <th>Intervento</th>
              <td>${intervento.descIntervento}</td>
            </tr>
            <c:if test="${attPart!=null}">
            <tr>
              <th>Attivit&agrave;</th>
              <td>${attPart[0]}</td>
            </tr>
            <tr>
              <th>Partecipante</th>
              <td>${attPart[1]}</td>
            </tr>            
            </c:if>
            
            <c:if test="${intervento.cuaaPartecipante!=null}">
            <tr>
              <th>Beneficiario intervento</th>
              <td>${intervento.cuaaPartecipante}</td>
            </tr>
                        
            </c:if>
            
						<tr>
							<th>Ulteriori informazioni</th>
							<td>${intervento.ulterioriInformazioni}</td>
						</tr>
						<c:forEach var="m" items="${intervento.misurazioni}">
							<c:if test="${m.misuraVisibile}">
								<tr>
									<th>${m.descMisurazione}</th>
									<td><fmt:formatNumber value="${m.valore}" minFractionDigits="2" /> ${m.codiceUnitaMisura}</td>
								</tr>
							</c:if>
						</c:forEach>
						<c:if test="${intervento.importoUnitario != null}">
							<tr>
								<th>Importo unitario</th>
								<td><fmt:formatNumber value="${intervento.importoUnitario}" minFractionDigits="2" /></td>
							</tr>
						</c:if>
						<tr>
							<th>Importo investimento</th>
							<td><fmt:formatNumber value="${intervento.importoInvestimento}" minFractionDigits="2" /></td>
						</tr>
						<tr>
							<th>Operazione</th>
							<td><c:out value="${intervento.operazione}" /></td>
						</tr>
						
						<c:if test="${intervento.spesaAmmessa != null}">
							<tr>
								<th>Spesa ammessa</th>
								<td><fmt:formatNumber value="${intervento.spesaAmmessa}" minFractionDigits="2" /></td>
							</tr>
						</c:if>
						<c:if test="${intervento.percentualeContributo != null}">
							<tr>
								<th>Percentuale contributo</th>
								<td><fmt:formatNumber value="${intervento.percentualeContributo}" minFractionDigits="2" />%</td>
							</tr>
						</c:if>
						<c:if test="${intervento.contributoConcesso != null}">
							<tr>
								<th>Contributo concesso</th>
								<td><fmt:formatNumber value="${intervento.contributoConcesso}" minFractionDigits="2" /></td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</m:panel>
			<c:if test="${intervento.idTipoLocalizzazione==5}">
				<m:panel title="Allegato" id="allegato">
					<table id="tblMappeFile" summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
						data-url="../json/mappe_file_${idIntervento}.json" data-undefined-text=''>
						<thead>
							<tr>
								<th data-field="nomeLogico">Nome allegato</th>
								<th data-field="nomeFisico" data-formatter="fileFormatter">File</th>
							</tr>
						</thead>
					</table>
				</m:panel>
			</c:if>
			<c:if test="${intervento.idTipoLocalizzazione==1 || intervento.idTipoLocalizzazione==2 || intervento.idTipoLocalizzazione==7}">
				<m:panel title="Localizzazione - Comuni" id="comune">
					<table class="table table-hover table-bordered">
						<tbody>
							<c:forEach items="${comuni}" var="c">
								<tr>
									<td><label>${c.descrizione} (${c.codice})</label></td>
								</tr>
							</c:forEach>
							<c:if test="${comuni==null || comuni.isEmpty()}">
								<tr>
									<td>Nessun comune inserito
							</c:if>
						</tbody>
					</table>
				</m:panel>
			</c:if>
			<c:if test="${intervento.idTipoLocalizzazione==4}">
				<m:panel title="Localizzazione - Particelle da catasto" id="partecelleCatasto">
					<table id="tblParticelle" class="table table-hover tableBlueTh" data-toggle="table" data-url="./json/elenco_particelle_${idIntervento}.json"
						data-undefined-text='' data-checkbox-header="true">
						<thead>
							<tr>
								<th data-field="descComune">Comune</th>
								<th data-field="sezione">Sezione</th>
								<th class="alignRight" data-field="foglio">Foglio</th>
								<th class="alignRight" data-field="particella">Particella</th>
								<th data-field="subalterno">Subalterno</th>
								<th class="alignRight" data-field="supCatastale">Sup. catastale</th>
						</thead>
					</table>
				</m:panel>
			</c:if>
			<c:if test="${intervento.idTipoLocalizzazione==3 || intervento.idTipoLocalizzazione==8}">
				<m:panel title="Localizzazione - Particelle aziendali" id="partecelleAziendali">
					<table id="tblConduzioni" class="table table-hover tableBlueTh" data-toggle="table" data-url="./json/elenco_conduzioni_${idIntervento}.json"
						data-undefined-text='' data-checkbox-header="true">
						<thead>
							<tr>
								<th data-field="descComune">Comune</th>
								<th data-field="sezione">Sez.</th>
								<th class="alignRight" data-field="foglio">Foglio</th>
								<th class="alignRight" data-field="particella">Part.</th>
								<th data-field="subalterno">Sub.</th>
								<th class="alignRight" data-field="supCatastale">Sup.<br/>catastale</th>
                <th data-field="descTipoUtilizzo" data-title="Occupazione<br/>del suolo" title="Occupazione del suolo" data-valign="middle"></th>
                <th data-field="descrizioneDestinazione" data-title="Destinazione" title="Destinazione" data-valign="middle"></th>
                <th data-field="descTipoDettaglioUso" data-title="Uso" title="Uso" data-valign="middle"></th>
                <th data-field="descrizioneQualitaUso" data-title="Qualit&agrave;" title="Qualit&agrave;" data-valign="middle"></th>
                <th data-field="descTipoVarieta" data-title="Variet&agrave;" title="Variet&agrave;" data-valign="middle"></th>
                <th class="alignRight" data-field="superficieUtilizzata" data-title="Sup.<br/>utilizzata"></th>
								<c:if test="${intervento.idTipoLocalizzazione==8}">
								<th class="alignRight" data-field="superficieImpegno">Superficie<br/>ammessa</th>
								</c:if>
						</thead>
					</table>
				</m:panel>
			</c:if>
			<c:if test="${intervento.idTipoLocalizzazione==9}">
				<m:panel title="Localizzazione - Comuni e Opere Danneggiate" id="comune">
					<h3>Comuni dell'intervento</h3>
					<table class="table table-hover table-bordered">
						<tbody>
							<c:forEach items="${comuni}" var="c">
								<tr>
									<td><label>${c.descrizione} (${c.codice})</label></td>
								</tr>
							</c:forEach>
							<c:if test="${comuni==null || comuni.isEmpty()}">
								<tr>
									<td>Nessun comune inserito
							</c:if>
							</tbody>
					</table>
					<h3>Opere danneggiate</h3>
					<table class="table table-hover table-bordered">
						<tbody>
						<c:forEach items="${danni}" var="c">
								<tr>
									<td><label>${c}</label></td>
								</tr>
							</c:forEach>
							<c:if test="${danni==null || danni.isEmpty()}">
								<tr>
									<td>Nessun comune inserito
							</c:if>
							</tr>					
						</tbody>
					</table>
				</m:panel>
			</c:if>
			<a role="button" class="btn btn-primary" href="../cunembo${cuNumber}l/index.do">Indietro</a>
			<br class="clear" />
			<br />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
    function fileFormatter($value, row, index)
    {
      var href = 'visualizza_allegato_' + $
      {
        idIntervento
      }
      +"_" + row['idFileAllegatiIntervento'] + '.do';
      return '<table cellspacing="0" cellpadding="0" style="border:none !important"><tr><td style="border:none !important;width:32px"><a href="'+href+'" class="ico32 '+row['iconClassMimeType']+'"></a></td><td style="border:none !important;vertical-align:middle"><a href="'+href+'" style="padding-left:8px">'
          + $value + '</a></td></tr></table>';
    }
  </script>
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
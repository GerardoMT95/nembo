<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../cunembo227/index.do">Liste di liquidazione</a><span class="divider">/</span></li>
					<li class="active">Nuova lista di liquidazione</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<div class="container-fluid" id="content">
		<m:error />
		<form class="form-horizontal" method="post" action="approva_${idListaLiquidazione}.do" enctype="multipart/form-data" id="mainForm">
			<br />
			<c:if test="${errorMsg!=null}">
				<div class="alert alert-danger">
					<strong>${errorMsg}</strong>
				</div>
				<br style="clear: left" />
			</c:if>
			<m:textfield id="bando" name="bando" value="${livelliBando.denominazioneBando}" label="Bando" disabled="true" />
			<m:select id="livelli" list="${livelliBando.livelli}" name="livelli" label="Misura / Sottomisura / Operazione" disabled="true" multiple="true" header="false"
				textProperty="codiceDescrizione" size="10" />
			<m:textfield id="ammCompetenza" name="ammCompetenza" value="${descAmmCompetenza}" label="Amministrazione (Organismo Delegato)" disabled="true" />
			<m:textfield id="ammCompetenza" name="tipoImporto" value="${descTipoImporto}" label="Tipo importo" disabled="true" />
			<m:textfield id="idTecnico" name="idTecnico" label="Tecnico liquidatore" value="${descTecnico}" disabled="true" />
			<table id='tblRisorse' summary="Elenco Risorse" class="table table-hover table-bordered tableBlueTh ">
				<thead>
					<tr>
						<th>Operazione</th>
						<th>Causale pagamento</th>
						<th>Numero pagamenti</th>
						<th>Importi da liquidare</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${riepilogo}" var="r">
						<tr>
							<td><c:out value="${r.codiceLivello}" /></td>
							<td><c:out value="${r.causalePagamento}" /></td>
							<td class="numero">${r.numPagamenti}</td>
							<td class="numero"><fmt:formatNumber pattern="#,##0.00" value="${r.importoLiquidato}" />&nbsp;&euro;</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<th class="right" colspan="2">Totali</th>
						<th class="numero">${totaleNumPagamenti}</th>
						<th class="numero"><fmt:formatNumber pattern="#,##0.00" value="${totaleImporto}" />&nbsp;&euro;</th>
					</tr>
				</tfoot>
			</table>
			<br />
			<c:choose>
				<c:when test="${lista.flagStatoLista=='B'}">
					<div class="col-md-12">
						<a href="../cunembo227/index.do" class="btn  btn-default pull-left">annulla</a> <a href="da_approvare_${idListaLiquidazione}.do"
							class="btn  btn-primary pull-right">conferma</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-md-12 form-group">
						<a href="stampa_da_firmare_${idListaLiquidazione}.do" class="btn btn-primary pull-right">Stampa lista di liquidazione</a>
					</div>
					<div class="col-md-12 form-group" style="clear: left">
						<label>Si prega di scaricare la stampa dalla lista di liquidazione e riallegarla firmata</label>
					</div>
					<!-- b:file START--><b:file label="Selezionare la stampa firmata" name="stampaFirmata" id="stampaFirmata"/><!-- b:file END-->
					<br />
          <div class="col-md-12 form-group">
            <button class="btn btn-primary pull-right" onclick="return visualizzaFile()">Visualizza stampa selezionata</button>
          </div>
					<br />
					<div class="col-md-12">
						<a href="../cunembo227/index.do" class="btn  btn-default pull-left">annulla</a>
						<button type="submit" class="btn  btn-primary pull-right" onclick="return singleClick()">conferma</button>
					</div>
					<br style="clear: left" />
				</c:otherwise>
			</c:choose>
		</form>
		<br />
	</div>
	<br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
	  function visualizzaFile()
	  { 
		  var $stampaFirmata=$('#stampaFirmata');
		  if ($stampaFirmata.get(0).files.length==0)
			{
		    showMessageBox("Error", "<strong>Per utilizzare questa funzionalit&agrave; bisogna aver selezionato un file per l'upload</strong>", "modal-large");
		  }
		  else
		  {
			  var action=$('#mainForm').attr('action');
		    $('#mainForm').attr("action","visualizza_file.do");
		    $('#mainForm').submit();
	      $('#mainForm').attr("action",action);
		  }
		  return false;
		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-263-I" />
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-263-I" />
	
	
	<div class="container-fluid" id="content">
		<m:panel id="panelDocumenti">
		
		<div class="col-sm-12" >
	  	
			<form name="inserisciForm" id="elencoForm" method="post" action="../cunembo263i/inserisciricevuta.do" class="form-horizontal" style="margin-top:2em" enctype="multipart/form-data">
				<m:panel collapsible="false" title="Documento" id="panelRiepilogoDoc">
					<p>${documentoSpesaVO.ragioneSociale }-${documentoSpesaVO.dataDocumentoSpesaStr }-${documentoSpesaVO.numeroDocumentoSpesa } -${documentoSpesaVO.descrTipoDocumento }</p>
					<div>
					<table  id="riepilogoImporti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh">
					<thead><tr><th>Importo Netto</th><th>Importo Iva</th><th>Importo Lordo</th><th>Importo Pagamento Lordo</th></tr></thead>
					<tbody><tr><td>${documentoSpesaVO.importoSpesaStr}</td><td>${documentoSpesaVO.importoIvaStr}</td><td>${documentoSpesaVO.importoLordoStr}</td><td>${documentoSpesaVO.importoLordoPagamentoStr}</td></tr></tbody>
					</table> 
					</div>
				</m:panel>
				
				<m:textfield label="Estremi pagamento (es. CRO, n° assegno, prot. telematico F24, ecc...) *" value="${ricevutaPagamentoVO.numero}" id="numero" name="numero" preferRequestValues="${prfReqValues}"></m:textfield>
				<m:textfield label="Data pagamento *" value="${ricevutaPagamentoVO.dataPagamento}" type="DATE" id="dataPagamento" name="dataPagamento" preferRequestValues="${prfReqValues}"></m:textfield>
				<m:select label="Modalità pagamento *" selectedValue="${ricevutaPagamentoVO.idModalitaPagamento}" id="idModalitaPagamento" list="${elencoModalitaPagamento}" name="idModalitaPagamento" preferRequestValues="${prfReqValues}">></m:select>
				<m:textfield label="Importo pagamento lordo *" value="${ricevutaPagamentoVO.importoPagamento}" id="importoPagamento" name="importoPagamento"  preferRequestValues="${prfReqValues}"></m:textfield>
				<m:textarea name="note" id="note" rows="6" cols="10"  label="Note" preferRequestValues="${prfReqValues}">${ricevutaPagamentoVO.note}</m:textarea>
					
				<div class="col-sm-12" >
				<div class="puls-group" style="margin-top:2em">
				 <div class="pull-left">
			        <button type="button" onclick="forwardToPage('../cunembo263m/elencoricevuteDocRendicontato_${idDocSpesa}.do');"  class="btn btn-default">indietro</button>
			      </div>
			      <div class="pull-right">  
			        <button type="submit" name="conferma" id="conferma" class="btn btn-primary">conferma</button>
			      </div>
			    </div> 
			    </div><br><br><br><br><br><br>
			</form>
			</div>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script type="text/javascript">
$( document ).ready(function() {

	$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li><span class="divider">/ </span><a href="../cunembo263l/index.do">Documenti spesa</a></li><li class="active"><span class="divider">/</span>Gestione ricevuta</li>');

});
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

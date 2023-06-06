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
		
		<div class="stepwizard">
		    <div class="stepwizard-row setup-panel">
		      <div class="stepwizard-step">
		        <a href="#step-1" type="button" class="btn btn-primary btn-circle">1</a>
		        <p>Dati Documento</p>
		      </div>
		      <div class="stepwizard-step">
		        <a href="#step-2" type="button" class="btn btn-default btn-circle" >2</a>
		        <p>Scelta fornitore</p>
		      </div>
		      <div class="stepwizard-step">
		        <a href="#step-4" type="button" class="btn btn-default btn-circle" >3</a>
		        <p>Ricevute Pagamento</p>
		      </div>
		    </div>
		  </div>
		  
		
			<form name="inserisciForm" id="elencoForm" method="post" action="" class="form-horizontal" style="margin-top:2em" enctype="multipart/form-data">

					<m:select id="idTipoDocumento" selectedValue="${documentoSpesaVO.idTipoDocumentoSpesa}" headerKey="0" onchange="visualizzaDettagli()" list="${elencoTipiDocumenti}" name="idTipoDocumento" label="Tipo Documento *" preferRequestValues="${prfReqValues}"></m:select>
	
					<c:if test="${tipoDocumentoSpesaVO != null}">
						<c:if test="${tipoDocumentoSpesaVO.flagDataDocumentoSpesa == 'F'}">
							<m:textfield label="Data Documento" value="${documentoSpesaVO.dataDocumentoSpesa}" type="DATE" id="dataDocumento" name="dataDocumento" preferRequestValues="${prfReqValues}"></m:textfield>
						</c:if>
						<c:if test="${tipoDocumentoSpesaVO.flagDataDocumentoSpesa == 'S' || tipoDocumentoSpesaVO.flagDataDocumentoSpesa == 'K' || tipoDocumentoSpesaVO.flagDataDocumentoSpesa == 'K'}">
							<m:textfield label="Data Documento *" value="${documentoSpesaVO.dataDocumentoSpesa}" type="DATE" id="dataDocumento" name="dataDocumento" preferRequestValues="${prfReqValues}"></m:textfield>
						</c:if>
						
						<c:if test="${tipoDocumentoSpesaVO.flagNumeroDocumentoSpesa == 'F'}">
							<m:textfield label="Numero Documento" value="${documentoSpesaVO.numeroDocumentoSpesa}" id="numeroDocumento" name="numeroDocumento" preferRequestValues="${prfReqValues}"></m:textfield>
						</c:if>
						<c:if test="${tipoDocumentoSpesaVO.flagNumeroDocumentoSpesa == 'S' || tipoDocumentoSpesaVO.flagNumeroDocumentoSpesa == 'K'}">
							<m:textfield label="Numero Documento *" value="${documentoSpesaVO.numeroDocumentoSpesa}" id="numeroDocumento" name="numeroDocumento" preferRequestValues="${prfReqValues}"></m:textfield>
						</c:if>
	
						<c:if test="${tipoDocumentoSpesaVO.flagDataPagamento == 'F'}">
							<m:textfield label="Data primo/unico pagamento" type="DATE" value="${documentoSpesaVO.dataPagamento}" id="dataPagamento" name="dataPagamento" preferRequestValues="${prfReqValues}"></m:textfield>
						</c:if>
						<c:if test="${tipoDocumentoSpesaVO.flagDataPagamento == 'S' || tipoDocumentoSpesaVO.flagDataPagamento == 'K'}">
							<m:textfield label="Data primo/unico pagamento *" type="DATE" value="${documentoSpesaVO.dataPagamento}" id="dataPagamento" name="dataPagamento" preferRequestValues="${prfReqValues}"></m:textfield>
						</c:if>
						
						<c:if test="${tipoDocumentoSpesaVO.flagIdModalitaPagamento == 'F'}">
							<m:select label="Modalità Pagamento" selectedValue="${documentoSpesaVO.idModalitaPagamento}" id="idModalitaPagamento" list="${elencoModalitaPagamento}" name="idModalitaPagamento" preferRequestValues="${prfReqValues}">></m:select>
						</c:if>
						<c:if test="${tipoDocumentoSpesaVO.flagIdModalitaPagamento == 'S' || tipoDocumentoSpesaVO.flagIdModalitaPagamento == 'K'}">
							<m:select label="Modalità Pagamento *" selectedValue="${documentoSpesaVO.idModalitaPagamento}" id="idModalitaPagamento" list="${elencoModalitaPagamento}" name="idModalitaPagamento" preferRequestValues="${prfReqValues}">></m:select>
						</c:if>
						
						
						<m:textfield onkeyup="this.onchange();" onchange="calculateImportoLordo();" label="Importo netto documento spesa *" value="${documentoSpesaVO.importoSpesa}" id="importoSpesa" name="importoSpesa"  preferRequestValues="${prfReqValues}"></m:textfield>
						<m:textfield onkeyup="this.onchange();" onchange="calculateImportoLordo();" label="Importo IVA *" value="${documentoSpesaVO.importoIva}" id="importoIva" name="importoIva"  preferRequestValues="${prfReqValues}"></m:textfield>

						<m:textfield disabled="true" label="Importo lordo" value="${documentoSpesaVO.importoLordo}" id="importoLordo" name="importoLordo"  preferRequestValues="${prfReqValues}"></m:textfield>
						
						
						<c:if test="${tipoDocumentoSpesaVO.flagNote == 'F'}">
							<m:textarea name="note" id="note" rows="4"  label="Note" preferRequestValues="${prfReqValues}">${documentoSpesaVO.note}</m:textarea>
						</c:if>
						<c:if test="${tipoDocumentoSpesaVO.flagNote == 'S' || tipoDocumentoSpesaVO.flagNote == 'K'}">
							<m:textarea name="note" id="note" rows="4"  label="Note *" preferRequestValues="${prfReqValues}">${documentoSpesaVO.note}</m:textarea>
						</c:if>
						
						<div style="clear: both; height: 3em"></div>
						<c:if test="${tipoDocumentoSpesaVO.flagFileDocumentoSpesa != 'N'}">
							<div>
								<fieldset>
									<legend>Documento</legend>
									<c:if test="${tipoDocumentoSpesaVO.flagFileDocumentoSpesa == 'F'}">
										<m:textfield id="nomeAllegato" value="${documentoSpesaVO.nomeFileLogicoDocumentoSpe}" label="Nome allegato" name="nomeAllegato" preferRequestValues="${prfReqValues}"  />
									</c:if>
									<c:if test="${tipoDocumentoSpesaVO.flagFileDocumentoSpesa == 'S' || tipoDocumentoSpesaVO.flagFileDocumentoSpesa == 'K'}">
										<m:textfield id="nomeAllegato" value="${documentoSpesaVO.nomeFileLogicoDocumentoSpe}" label="Nome allegato *" name="nomeAllegato" preferRequestValues="${prfReqValues}"  />
									</c:if>
									<c:if test="${not empty documentoSpesaVO.nomeFileLogicoDocumentoSpe}">		
										<div class="form-group">
											<label class="control-label col-sm-3">Scarica allegato</label>
											<div class="col-sm-9">
												<a href="download_${documentoSpesaVO.idDocumentoSpesaFile}.do" title="${documentoSpesaVO.nomeFileLogicoDocumentoSpe}"><c:out value="${documentoSpesaVO.nomeFileLogicoDocumentoSpe}"/></a>
											</div>
										</div> 
									</c:if>
									<div class="form-group">
                      <span class="col-sm-3"></span>
                      <div class="col-sm-9" >
                      <span class="text-danger">Allegare in un unico file il documento spesa e i documenti necessari per la tracciabilità dei pagamenti, così come previsti dai bandi.</span>
                      </div>
                    </div>
									<b:file label="Documento Spesa" name="fileDaAllegare" id="fileDaAllegare"  />
								</fieldset>
							</div>
						</c:if>
					</c:if>
				
				<div class="col-sm-12" >
				<div class="puls-group" style="margin-top:2em">
				 <div class="pull-left">
			        <button type="button" onclick="forwardToPage('../cunembo263l/index.do');"  class="btn btn-default">indietro</button>
			      </div>
			      <div class="pull-right">  
			        <button type="button" name="conferma" id="conferma" onclick="confermaDatiDocumento()" class="btn btn-primary">avanti</button>
			      </div>
			    </div> 
			    </div><br><br><br><br><br><br>
			</form>
			</div>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script type="text/javascript">

	function calculateImportoLordo(){

		var netto = $("#importoSpesa").val();
		var iva = $("#importoIva").val();
		netto = netto.replace(",",".");
		iva = iva.replace(",",".");
		var lordo = parseFloat(netto)+parseFloat(iva);
		
		if(isNaN(parseFloat(iva)))
			lordo = netto;
		if(isNaN(parseFloat(netto)))
			lordo = iva;
		
		$("#importoLordo").val(lordo.toFixed(2).replace(".", ","));
	}

	$( document ).ready(function() {
	
		$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li><span class="divider">/ </span><a href="../cunembo263l/index.do">Documenti spesa</a></li><li class="active"><span class="divider">/</span>Dati documento</li>');
		calculateImportoLordo();
	});

	function visualizzaDettagli()
	{
		$('#elencoForm').attr('action', 'getDatiInserimento.do').submit();
	}
	function confermaDatiDocumento()
	{
		$('#elencoForm').attr('action', 'confermaDatiDocumento.do').submit();
	}
    
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

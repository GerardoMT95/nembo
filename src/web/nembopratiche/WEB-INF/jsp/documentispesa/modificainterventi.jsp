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
	<p:breadcrumbs cdu="CU-NEMBO-263-M" />
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-263-M" />
	
	
	<div class="container-fluid" id="content">
		<m:panel id="panelDocumenti">
		<div class="col-sm-12" >
		
		<div class="stepwizard col-md-offset-right-1">
		    <div class="stepwizard-row setup-panel">
		      <div class="stepwizard-step">
		        <a href="#step-2" type="button" class="btn btn-primary btn-circle" >1</a>
		        <p>Scelta Interventi</p>
		      </div>
		      <div class="stepwizard-step">
		        <a href="#step-3" type="button" class="btn btn-default btn-circle">2</a>
		        <p>Inserimento Importi</p>
		      </div>
		    </div>
		  </div>
		
		
			<c:if test="${msgErrore != null}">
		       	<div class="stdMessagePanel">	
		             <div class="alert alert-danger">
		                 <p><strong>Attenzione!</strong><br/>
		                <c:out value="${msgErrore}"></c:out></p>
		              </div>
		          </div>
		   	</c:if>
			<form name="inserisciForm" id="elencoForm" method="post" action="" class="form-horizontal" style="margin-top:2em" >
				<div id="selectedHiddenInterventi"></div>
				<input type="hidden" name="idDocumentoSpesa" id="idDocumentoSpesa" value="${idDocumentoSpesa}">
				<div id="dual-list-box1" class="form-group row">
					<select style="display: none" id="interventiDualList" multiple="multiple" 
						data-title="Interventi" 
						data-sortText = "false" 
						data-horizontal = true   
						data-source="json/load_elenco_interventi.json"      
						data-sourceselected="json/load_elenco_interventi_selezionati.json"
						data-value="id" data-text="descrizione" data-addcombo="true" data-labelcombo="Tipo intervento" 
						data-labelfilter="Filtro" data-horizontal="false" data-toggle="true"></select> 
				</div>   
				<div class="puls-group" style="margin-top:2em">
				<div class="pull-left">
			        <button type="button" onclick="forwardToPage('../cunembo263l/index.do');"  class="btn btn-default">indietro</button>
			      </div>
			      <div class="pull-right">  
			        <button type="button" name="conferma" id="conferma" onclick="avanti()" class="btn btn-primary">avanti</button>
			      </div>
			    </div>
			    <br/><br/><br/>
			</form>
			</div>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script src="../js/dual-list-box.js"></script>
<script type="text/javascript">
$( document ).ready(function() {

	$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li><span class="divider">/ </span><a href="../cunembo263l/index.do">Documenti spesa</a></li><li class="active"><span class="divider">/</span>Modifica interventi</li>');

});

$('#interventiDualList').DualListBox();
	function avanti()
	{
		//reperisco value selezionati nella popup
		var arraySelectedValue = []; 
	    $('#dual-list-box-Interventi #selectedList option').each(function(index) {
	    	arraySelectedValue[$(this).val()] = $(this).text();
	    });
	    
		//popolo select principale
	    for (index = 0; index < arraySelectedValue.length; ++index) {
		    if(arraySelectedValue[index] != null)
			 {
		    	$('#selectedHiddenInterventi').append('<input type="hidden" name="idInterventiHidden"value="'+index+'">');
			 }
		}


	    $.ajax({
            type: "POST",
            url: '../cunembo263m/controllaRimozioneInterventi.do',
            data: $('#elencoForm').serialize(),
            dataType: "html",
            async:false,
            success: function(html) 
            {
                var COMMENT = 'continue';
                if (html.indexOf(COMMENT) >= 0) 
                {
            		$('#elencoForm').attr('action', 'confermainterventi.do').submit();
                } 
                else 
                {
            		openPageInPopup('confermaRimuoviInterventi.do', 'dlgElimina', 'Conferma operazione', 'modal-lg', false);
					return false;
                }            
            },
            error: function(jqXHR, html, errorThrown) 
             {
            	openPageInPopup('confermaRimuoviInterventi.do', 'dlgElimina', 'Conferma operazione', 'modal-lg', false);
				return false;
				}  
        });

        
	}
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

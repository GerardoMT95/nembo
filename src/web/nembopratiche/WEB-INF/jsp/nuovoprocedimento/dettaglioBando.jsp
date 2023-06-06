<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
<p:utente/>
	<div class="container-fluid">
        <div class="row">
            <div class="moduletable">
                <ul class="breadcrumb">
				  <li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				  <li><a href="elencobando.do">Elenco bandi</a> <span class="divider">/</span></li>
				  <li class="active">Dettaglio bando</li>
				</ul>
            </div>
        </div>           
    </div>
<p:messaggistica/>
	<div class="container-fluid" id="content" style="margin-bottom:3em">
	<h3><c:out value="${bando.denominazione}"></c:out></h3>
      
	<div class="stdMessagePanel stdMessageLoad" style="display:none">			
         <div class="alert alert-info">
          <p>Attendere prego: Il sistema sta effettuando il controllo sui dati inseriti; l'operazione potrebbe richiedere alcuni secondi...</p> 
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
	 <b:error />
	<form:form action="ricercaBandoSingolo.do" modelAttribute="nuovaDomanda" method="post" class="form-horizontal" style="margin-top:2em">
	    <input type="hidden" name="msgConfermaNuovoProcedimento" id="msgConfermaNuovoProcedimento" value="${msgConfermaNuovoProcedimento}" />
	    <input type="hidden" name="msgConfermaNuovoProcedimentoAdUnoEsistente" id="msgConfermaNuovoProcedimentoAdUnoEsistente" value="${msgConfermaNuovoProcedimentoAdUnoEsistente}" />
	    <input type="hidden" id="idAziendaSel" name="idAziendaSel" value="${idAziendaSel}" />
		<input type="hidden" name="idBando" value="${idBando}" />
		<input type="hidden" name="idBandoOggetto" value="${idBandoOggetto}" />
		<input type="hidden" name="idLegameGruppoOggetto" value="${idLegameGruppoOggetto}" />
	    <b:panel title="Nuova domanda (selezione attraverso il CUAA)" id="ricercaSingola">
		    <div class="pageContainer">
				 <div class="form-group">
				    <label for="cuaa" class="col-sm-2 control-label">CUAA</label>
				    <div class="col-sm-10">
				      <m:textfield  value="${cuaa}" name="cuaa" id="cuaa" cssClass="form-control"></m:textfield>
				    </div>
				  </div>
		    </div>
		    <div class="puls-group" style="margin-top:2em">
		      <div class="pull-left">
		        <button type="button" onclick="forwardToPage('elencobando.do');"  class="btn btn-default">indietro</button>
		      </div>
		      <div class="pull-right">
		        <button type="submit" onclick="$('.stdMessagePanel').hide();$('.stdMessageLoad').show();" name="prosegui" id="prosegui" class="btn btn-primary">prosegui</button>
		      </div>
		    </div>
	    </b:panel>
	</form:form>

	<c:if test="${descrizioneFiltro !=null && descrizioneFiltro!= ''}">
		<form:form action="ricercaBandoMultipla.do" modelAttribute="nuovaDomanda" method="post" class="form-horizontal" style="margin-top:2em">
			<input type="hidden" name="idBando" value="${idBando}" />
			<input type="hidden" name="idBandoOggetto" value="${idBandoOggetto}" />
			<input type="hidden" name="idLegameGruppoOggetto" value="${idLegameGruppoOggetto}" />
		    <b:panel title="Nuova domanda (selezione attraverso le aziende che soddisfano le caratteristiche peculiari del bando selezionato)" id="ricercaMultipla">
			    <div class="pageContainer">
					<div class="title"><c:out value="${descrizioneFiltro}" escapeXml="false"></c:out></div>
				</div>
			    <div class="puls-group" style="margin-top:2em">
			      <div class="pull-left">
			        <button type="button" onclick="forwardToPage('elencobando.do');"  class="btn btn-default">indietro</button>
			      </div>
			      <div class="pull-right">  
			        <button type="submit" onclick="$('.stdMessagePanel').hide();$('.stdMessageLoad').show();" name="prosegui" id="prosegui" class="btn btn-primary">prosegui</button>
			      </div>
			    </div>
			</b:panel>		
		</form:form>		
	</c:if>	
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>

<script type="text/javascript">

		function creaProcedimento(idAzienda)
		{
		    return openPageInPopup('confermaNuovoProcedimento_'+idAzienda+'.do','dlgProcedimento','Conferma Nuovo Procedimento', 'modal-large');
		}
		
		function creaProcedimentoPerMedesimaAzienda(idAzienda)
		{
		    return openPageInPopup('confermaNuovoProcedimentoAdUnoEsistente_'+idAzienda+'.do','dlgProcedimento','Conferma Nuovo Procedimento', 'modal-large');
		}

		$( document ).ready(function() {
			if($('#msgConfermaNuovoProcedimento').val() == 'S')
			{
				creaProcedimento($('#idAziendaSel').val());
			}
			if($('#msgConfermaNuovoProcedimentoAdUnoEsistente').val() == 'S')
			{
				creaProcedimentoPerMedesimaAzienda($('#idAziendaSel').val());
			}
		});
    	
	</script>
 
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

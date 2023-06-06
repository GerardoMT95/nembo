<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />


<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-309-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-309-M" />
	<p:set-cu-info />
	
	<form id="mainForm" name="mainForm" method="post" action="../cunembo309m/confermaModifica.do">
		<div class="container-fluid" id="content">	
		
		<m:panel id="panelReferenteExt">		
		<m:error></m:error>
			<h3>Modifica referente progetto</h3>
			
				<div class="col-sm-12">
					<m:textfield preferRequestValues="${prefReqValues}"  id="cognome" name="cognome" label="Cognome*" value="${referente.cognome}" />
					<br/><br/>
					<m:textfield preferRequestValues="${prefReqValues}"  id="nome" name="nome" label="Nome*" value="${referente.nome}" />
					<br/><br/>
					<m:textfield preferRequestValues="${prefReqValues}" id="codiceFiscale" name="codiceFiscale" label="Codice Fiscale*" value="${referente.codiceFiscale}" />
					<br/><br/>
					<m:textfield preferRequestValues="${prefReqValues}" id="comune" name="comune" label="Comune*" disabled="true" value="${referente.cap} - ${referente.descrizioneComune}">
						<m:input-addon left="false">
							<a href="#" onclick="return ricercaComune()"><i class="icon icon-folder-open"></i> Cambia</a>
						</m:input-addon>
					</m:textfield>
					<br/><br/>
					<input type="hidden" 		name="extIstatComune" id="extIstatComune" 	value="${extIstatComune}" />
					<m:textfield preferRequestValues="${prefReqValues}"  id="cap" 		name="cap" 			label="CAP*" 			value="${referente.cap}" 		 />
					<br/><br/>
					<m:textfield preferRequestValues="${prefReqValues}" id="telefono" 	name="telefono" 	label="Telefono Fisso" value="${referente.telefono}" 	 />
					<br/><br/>
					<m:textfield preferRequestValues="${prefReqValues}" id="cellulare" name="cellulare" 	label="Cellulare" 		value="${referente.cellulare}" 	 />
					<br/><br/>
					<m:textfield preferRequestValues="${prefReqValues}" id="email" 	name="email" 		label="Email" 			value="${referente.email}" 		 />	
					<br/><br/>		
				</div>
			
			<div>
			<br/>
				<a role="button" class="btn btn-default" href="../cunembo309l/index.do">Indietro</a>
				<input type="submit" name="confermaReferente" role="s" class="btn btn-primary pull-right" onclick="return inviaDati();" value="conferma" />
			</div>
				
			</m:panel>
		</div>
	</form>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script type="text/javascript">
	doErrorTooltip();
	 function selezionaComune(self, istat)
	    {
	      $('#extIstatComune').val(istat);
	      $('#comune').val($(self).html());
	      closeModal();
	      return false;
	    }

		
	 function ricercaComune()
	    {
	      return openPageInPopup("popup_ricerca_comune.do", "ricerca_comuni", "Elenco comuni", 'modal-lg');
	    }


		
	 function inviaDati()
	    {
		    debugger;
		  $("#comune").prop("disabled", false);
		  $("#mainForm").submit();
		  $("#comune").prop("disabled", true);
	      return false;
	    }
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.WizardPianoGraficoTag"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-285-S" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-285-S" />
		
	 		
	<div class="container-fluid" id="content">
		<m:panel id="panelPianoGraficoHome">
			<p:wizardPianoGraficoTag activeStep="${currentStepWizard}"></p:wizardPianoGraficoTag>

			<div id="msgAttesa" class="stdMessagePanel stdMessageLoad" style="margin-top:10em;margin-bottom:5em;display: none">			
		         <div class="alert alert-info">
		          <p>Attenzione: il sistema sta effettuando lo sblocco della Domanda Grafica; l'operazione potrebbe richiedere alcuni secondi.</p>
		         </div>
		         <span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
	      	</div>
			<div id="confirmPanel" style="margin-top:10em">
				<form action="" method="post" id="myForm">
					<div class="stdMessagePanel">
						<div id="messaggio" class="alert alert-warning">
							<p>Procedere con lo sblocco della domanda grafica?</p>
						</div>
					</div>
					<div class="form-group puls-group" style="margin-top: 3em">
						<div class="col-sm-12">
							<button type="button" onclick="forwardToPage('../cunembo285v/index.do');"  class="btn btn-default">Indietro</button>
							<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="prepareSubmit();">Conferma</button>
						</div>
					</div>
				</form>
			</div>
		</m:panel>
	</div>			
	
	
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
	function prepareSubmit()
	{
		$('#confirmPanel').hide();
		$('#msgAttesa').show();
		$('#myForm').submit();
	}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
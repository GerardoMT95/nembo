<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<style >
.tooltip-inner {
  min-width: 300px;
  min-height: 50px;
}
.tooltip{
  font-size: 13px;
}
</style>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
<p:utente/>

	<div class="container-fluid">
        <div class="row">
            <div class="moduletable">
                <ul class="breadcrumb">
				  <li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				  <li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li>
				  <li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li>
				  <li><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a> <span class="divider">/</span></li>
				  <li class="active">Gestione notifiche</li>
				</ul>
            </div>
        </div>           
    </div>

	<p:messaggistica/><p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-112"/>

	<div class="container-fluid" id="content" style="margin-bottom:3em">
				<m:error />
		
		<h3>Inserimento nuova notifica</h3>

		<m:panel id="inserimentoNuovaNotifica">
		<form:form action="" modelAttribute="" method="post"
				class="form-horizontal" style="margin-top:2em">
					
					<div class="col-sm-12" style ="padding-bottom:2em;">
						<button type="button"
							onclick="forwardToPage('../cunembo110/index_${idProcedimento}.do');"
							class="btn btn-default">indietro</button>
						<button type="submit" name="conferma" id="conferma"
							class="btn btn-primary pull-right">conferma</button>
					</div>
				
				<m:textarea id="descrizione"
					placeholder="Inserire la notifica (al massimo 4000 caratteri)"
					label="Notifica *" name="descrizione" preferRequestValues="${prfvalues}">${notifica.note}</m:textarea>
				 
				 <m:radio-list id="idGravita" name="gravita" list="${elencoGravita}"
					label="Gravità *" inline="true"  labelSize="2" controlSize="10" valueProperty="id" textProperty="gravita" preferRequestValues="${prfvalues}"/>
					
				<m:select label="Visibile a *" name="visibilita"
					list="${elencoVisibilita}" id="id"
					 valueProperty="id"
					selectedValue="${notifica.idVisibilita}" preferRequestValues="${prfvalues}"/>
							
	</form:form>
	</m:panel>
	</div>
	

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 <script type="text/javascript">
$( document ).ready(function() {

	$("#idGravita_1").parent().after("&nbsp;<span style=\"padding-right:2em;\"><a style=\"vertical-align: middle;\" class=\"ico24 ico_warning \" rel=\"tooltip\" data-placement=\"right\"  title=\"Le notifiche warning non bloccano l'operatività sugli oggetti del procedimento.\"></a></span>");
	$("#idGravita_3").parent().after("&nbsp;<span style=\"padding-right:2em;\"><a style=\"vertical-align: middle;\"  class=\"ico24 ico_errorB\" rel=\"tooltip\" data-placement=\"right\"  title=\"Le notifiche bloccanti bloccano l'operatività su tutti gli oggetti di tipo istruttoria del procedimento.\"></a></span>");
	$("#idGravita_2").parent().after("&nbsp;<span style=\"padding-right:2em;\"><a style=\"vertical-align: middle;\"  class=\"ico24 ico_errorG\" rel=\"tooltip\" data-placement=\"right\"  title=\"Le notifiche gravi bloccano l'operatività su tutti gli oggetti del procedimento.\"></a></span>");
	
	 $(function () {
	        $("[rel='tooltip']").tooltip();
	    });
});  
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

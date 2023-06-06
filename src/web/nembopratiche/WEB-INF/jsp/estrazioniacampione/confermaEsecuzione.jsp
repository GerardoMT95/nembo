<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<style>

.chkRegistra{
	width: 1.3em;
    height: 1.3em;
   }
</style>
<body>
  <p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente/>
	<div class="container-fluid">
        <div class="row">
            <div class="moduletable">
                <ul class="breadcrumb">
				  <li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				  <li><a href="../cunembo217/index.do">Gestione estrazioni</a> <span class="divider">/</span></li>
				  <li><a href="../cunembo219/index_${idNumeroLotto}.do">Dettaglio estrazione</a> <span class="divider">/</span></li>
				  <li class="active">Estrazione</li>
				</ul>
            </div>
        </div>           
    </div>
<p:messaggistica/>

	<div class="container-fluid" id="content">
		<br />
		<div class="alert alert-warning" role="alert">Proseguendo il sistema effettuer&agrave; l'elaborazione dell'estrazione a campione. Continuare?</div>
				<div class="col-sm-12" >
		<m:checkBox cssClass="chkRegistra" chkLabel="Sono già state raggiunte le percentuali previste" name="chkRegistra" value="S" preferRequestValues="${prfvalues}"></m:checkBox>
		</div>
		
		<div class="col-sm-6" style="margin-top: 2em">
			<button type="button" class="btn btn-default" onclick="history.go(-1)">annulla</button>
		</div>
		<div class="col-sm-6">
			<button type="button" class="btn btn-primary pull-right" onclick="confermaregistra()">conferma</button>
		</div>
		<br class="clear" />
	</div>
	<br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
	  var chiusuraInCorso=false;
    function confermaregistra()
    {
      if (chiusuraInCorso)
      {
        return false;
      }
      chiusuraInCorso=true;

      var idStatoEstrazione =  ${idStatoEstrazione};
      openPageInPopup('attendere.do', 'dlgChiusura', 'Attendere prego...', 'modal-large', true);
      callEseguiRegistra();
    }

    function callEseguiRegistra()
    {
    	var chk = "N";

		if($('.chkRegistra').is(':checked')){
		 chk = "S";
		}
		
    	$.ajax(
	      {
	        url : "esegui_${idNumeroLotto}_${idTipoEstrazione}_"+chk+".do",
	        method : 'POST',
	        async: true,
	        success : function(data)
	        {
	          if("attendere prego..."  == data)
	          {
	        	  window.location.href = '../cunembo219/index_${idNumeroLotto}.do'; 
	          }  
			  if($('#dlgChiusura').length <= 0)
			  {
				  showRedirectMessageBox("Errore", data, 'modal-large','../cunembo219/index_${idNumeroLotto}.do');
		      } 
			  else
			  {
	          	$('#dlgChiusura  .modal-body').html(data);
			  }
	          chiusuraInCorso=false;
	        },
	        error : function(xhr, status, text)
	        {
	          $('#dlgChiusura').modal('hide');
	          showRedirectMessageBox("Errore", "Si è verificato un errore di sistema e non è possibile completare l'estrazione, se il problema persiste contattare l'assistenza.", 'modal-large','../cunembo219/index_${idNumeroLotto}.do');
	          chiusuraInCorso=false;
	        }
	      });
    }
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
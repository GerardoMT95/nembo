<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
  <p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica/><p:testata cu="${useCaseController}"/>

	<form method="post" id="formChiusura">
	<div class="container-fluid" id="content">
		<br />
		<div class="alert alert-warning" role="alert">Attenzione: si sta per chiudere l'oggetto corrente rendendolo non pi&ugrave; modificabile, si &egrave;
			sicuri di voler proseguire?</div>
		<div>
		<m:textarea id="note"
			placeholder="Inserire le note (al massimo 4000 caratteri) che compariranno nell'iter dell'Oggetto/Istanza"
			label="Note" 
			name="note" preferRequestValues="${prfvalues}">${note}</m:textarea>
		</div>	
			
		<br class="clear" />
		<br/>
		<div class="col-sm-6">
			<button type="button" class="btn btn-default" onclick="history.go(-1)">annulla</button>
		</div>
		<div class="col-sm-6">
			<button type="button" class="btn btn-primary pull-right" onclick="chiudiOggetto()">conferma</button>
		</div>
		<br class="clear" />
	</div>
	</form>
	<br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
	  var chiusuraInCorso=false;
    function chiudiOggetto()
    {
      if (chiusuraInCorso)
      {
        return false;
      }
      chiusuraInCorso=true;
      openPageInPopup('../cunembo${cuNumber}/attendere.do', 'dlgChiusura', 'Attendere prego...', 'modal-large', true);
      $.ajax(
      {
        url : "../cunembo${cuNumber}/esegui_controlli.do",
        data : $('#formChiusura').serialize(),
        method : 'POST',
        async: true,
        success : function(data)
        {
          $('#dlgChiusura  .modal-body').html(data);
          chiusuraInCorso=false;
        },
        error : function(xhr, status, text)
        {
          $('#dlgChiusura').modal('hide');
          showMessageBox("Errore", "Si è verificato un problema grave nel richiamo dei controlli dell'oggetto, se il problema persistesse contattare l'assistenza tecnica e comunicare il seguente messaggio:<br /> errore nella chiamata ajax<br/>codice di errore = "+xhr.status+"<br />messaggio = "+text, 'modal-large');
          chiusuraInCorso=false;
        }
      });
    }
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
  <p:set-cu-info/>
  <p:breadcrumbs cdu="${useCaseController}" />
  <p:messaggistica/><p:testata cu="${useCaseController}" />
	<div class="container-fluid" id="content">
	<b:panel>
		<br />
      <%@include file="/WEB-INF/jsp/controlliamministrativi/include/tabellaControlli.jsp"%>
      <%@include file="/WEB-INF/jsp/controlliamministrativi/include/tabellaVisiteLuogo.jsp"%>
      <%@include file="/WEB-INF/jsp/controlliamministrativi/include/tabellaEsitoTecnicoIstruttoria.jsp"%>
      <c:if test="${TIPO_PAGAMENTO_SIGOP_SALDO}">
      	<%@include file="/WEB-INF/jsp/controlliamministrativi/include/tabellaInformazioniExPosts.jsp"%>
      </c:if>
		<br />
	</b:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
  <script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
	<script type="text/javascript">
	$('input[data-toggle="bs-toggle"]').bootstrapToggle();
  function selectAll(self, name)
  {
    $("input[name='"+name+"']").prop("checked", $(self).prop('checked'));
  }

  function modificaMultiplaControlli()
  {
    if ($("input[name='idQuadroOggControlloAmm']:checked").length > 0)
    {
      submitFormTo($('#elencoControlli'), '../cunembo165m/modifica_multipla.do');
    }
    else
    {
      alert("Selezionare almeno un controllo tecnico amministrativo");
    }
    return false;
  }

  function modificaMultiplaVisiteLuogo()
  {
    if ($("input[name='idVisitaLuogo']:checked").length > 0)
    {
      submitFormTo($('#formVisiteLuogo'), '../cunembo165m/modifica_multipla_visite_luogo.do');
    }
    else
    {
      alert("Selezionare almeno una visita sul luogo");
    }
    return false;
  }

  function eliminaMultiplaVisiteLuogo()
  {
    if ($("input[name='idVisitaLuogo']:checked").length > 0)
    {
      return openPageInPopup('../cunembo165m/conferma_elimina_visite_luogo.do', 'dlgEliminaVisita', 'Elimina visita sul luogo', '', false, $('#formVisiteLuogo').serialize());
    }
    else
    {
      alert("Selezionare almeno una visita sul luogo");
    }
    return false;
  }  
  
  function nuovaVisita()
  {
    openPageInPopup('../cunembo165m/popup_nuova_visita.do','dlgInserisci','Inserisci nuova visita sul luogo','modal-lg',false)
    return false;
  }

  function eliminaVisitaLuogo(idVisitaLuogo)
  {
    return openPageInPopup('../cunembo165m/conferma_elimina_visita_luogo_' + idVisitaLuogo + '.do', 'dlgEliminaVisita', 'Elimina visita sul luogo', '',
        false);
  }  

  function modificaEsitoTecnico()
  {
    return openPageInPopup('../cunembo165m/popup_esito_tecnico.do', 'dlgModificaEsitoTecnico', 'Modifica esito tecnico', 'modal-lg',
        false);
  }  
  function modificaInfoExPosts()
  {
    return openPageInPopup('../cunembo165m/popup_info_exposts.do', 'dlgModificaExposts', 'Modifica informazioni ex-post', 'modal-lg',
        false);
  } 
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
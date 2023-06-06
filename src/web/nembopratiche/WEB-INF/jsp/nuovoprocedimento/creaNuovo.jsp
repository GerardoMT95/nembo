<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				  <li><a href="elencobando.do">Elenco bando</a> <span class="divider">/</span></li>
				  <li><a href="dettaglioBando.do">Dettaglio bando</a> <span class="divider">/</span></li>
				  <li class="active">Creazione procedimento</li>
				</ul>
            </div>
        </div>           
    </div>
<p:messaggistica/>
	<div class="container-fluid" id="content" style="margin-bottom:3em">
	<h3>Creazione procedimento</h3>
      
	<c:if test="${msgAttesaNuovoProcedimento != null}">
       	<div class="stdMessagePanel stdMessageLoad" style="margin-top:30px;margin-bottom:50px">			
	         <div class="alert alert-info">
	          <p>Attenzione: il sistema sta effettuando la creazione del procedimento con tutti i dati necessari dichiarati dall'azienda in anagrafe; l'operazione potrebbe richiedere alcuni secondi.</p>
	         </div>
	         <span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
      	</div>
       	
		<form:form action="creaProcedimento.do" id="creaProcedimento" method="post">
			<input type="hidden" name="idAzienda" value="${idAzienda}" />
			<input type="hidden" name="idBando" value="${idBando}" />
			<input type="hidden" name="idBandoOggetto" value="${idBandoOggetto}" />
			<input type="hidden" name="idLegameGruppoOggetto" value="${idLegameGruppoOggetto}" /> 							
		</form:form>
	</c:if>
	
	<c:if test="${msgAttesaNuovoProcedimentoAdUnoEsistente != null}">
       	<div class="stdMessagePanel stdMessageLoad" style="margin-top:30px;margin-bottom:50px">			
	         <div class="alert alert-info">
	          <p>Attenzione: il sistema sta effettuando la creazione del procedimento con tutti i dati necessari dichiarati dall'azienda in anagrafe; l'operazione potrebbe richiedere alcuni secondi.</p>
	         </div>
	         <span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
      	</div>
       	
		<form:form action="creaProcedimentoAdUnoEsistente.do" id="creaProcedimento" method="post">
			<input type="hidden" name="idAzienda" value="${idAzienda}" />
			<input type="hidden" name="idBando" value="${idBando}" />
			<input type="hidden" name="idBandoOggetto" value="${idBandoOggetto}" />
			<input type="hidden" name="idLegameGruppoOggetto" value="${idLegameGruppoOggetto}" /> 							
		</form:form>
	</c:if>
	
	
	<c:if test="${msgErrore != null}">
		<div class="stdMessagePanel">	
             <div class="alert alert-danger">
                 <p><strong>Attenzione!</strong><br/>
                <c:out value="${msgErrore}" escapeXml="false"></c:out></p>
              </div>
          </div>
	</c:if>
	
	<c:if test="${controlliGravi != null}">
		<div class="stdMessagePanel">	
             <div class="alert alert-danger">
                 <p><strong>Attenzione!</strong><br/>
                Operazione non consentita a causa del rilevamento delle seguenti anomalie:</p>
              </div>
          </div>
                  
                  <!-- style="background-color:#E0E9F2;color:#444E56;text-align: left;border: 1px solid #91A2B2" -->
         <table summary="Bando" class="table table-hover table-striped table-bordered tableBlueTh">
			<thead>
			  <tr>
				<th>Codice</th>
				<th>Descrizione</th>
				<th>Messaggio</th>
			  </tr>
			</thead>
			<tbody>
			  <c:forEach items="${controlliGravi.controlli}" var="a">
				  <tr>
					<td scope="col" class=""><c:out value="${a.codice}"/></td>
					<td scope="col" class=""><c:out value="${a.descrizione}"/></td>
					<td scope="col" class=""><c:out value="${a.messaggioErrore}"/></td>
				  </tr>
			  </c:forEach>
			</tbody>
		</table>
		
		<div class="col-sm-12" style="padding-bottom: 2em;margin-top:2em">
			<button type="button"
				onclick="forwardToPage('dettaglioBando.do');"
				class="btn btn-default">indietro</button>
		</div>
	</c:if>	
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 <script type="text/javascript">
			$( document ).ready(function() {
				$('#creaProcedimento').submit();
			});
		</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

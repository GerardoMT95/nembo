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
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  
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
     

	<p:messaggistica/><p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-110"/>

	

	<div class="container-fluid" id="content" style="margin-bottom:3em">
		<c:if test="${msgErrore != null}">
          	<div class="stdMessagePanel">	
                      <div class="alert alert-danger">
                          <p><strong>Attenzione!</strong><br/>
                         <c:out value="${msgErrore}"></c:out></p>
                       </div>
                   </div>
           </c:if>
				<h3>Notifiche del procedimento</h3>
				
				<c:if test="${empty notifiche}">
				Non sono presenti notifiche sul procedimento
				</c:if>
				
					<table class="table table-hover table-striped table-bordered tableBlueTh" style="margin-top:2em;"><thead>
					<tr>
					<th>
					<p:abilitazione-cdu  codiceCdu="CU-NEMBO-112">
					<a href="../cunembo112/index.do" style="text-decoration: none;"><i class="ico24 ico_add" title="Crea nuova notifica"></i></a>
					</p:abilitazione-cdu>
					</th>
					<th>Notifica</th>
					<th>Gravità</th>
					<th>Visibile a</th>
					<th>Data inizio</th>
					<th>Data fine</th>
					<th>Utente</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${notifiche}" var="n">
								<tr>
								<td>
								<c:if test="${empty n.dataFine && n.utenteCorrenteAbilitato}">
								<p:abilitazione-cdu  codiceCdu="CU-NEMBO-111">
								<a href = "../cunembo111/index_${n.idNotifica}.do" >
								<i class="ico24 ico_modify" title="Modifica" style="font-size:1.4em;\"></i>
								</a>
								</p:abilitazione-cdu>
								<p:abilitazione-cdu  codiceCdu="CU-NEMBO-113">
								<a href="../cunembo113/canDelete_${n.idNotifica}.do" onclick="return openPageInPopup('../cunembo113/popupindex_${n.idNotifica}.do','dlgElimina','Elimina Notifica','modal-lg',false)" style="text-decoration: none;"><i class="ico24 ico_trash" title="Elimina Notifica"></i></a>	
								</p:abilitazione-cdu>
								</c:if> 
								</td>
								<td><c:out value="${n.note}"></c:out></td>
								<td>
									<c:if test="${n.gravita == 'Warning'}">
										<i class="ico24 ico_warning" title="Warning" style="font-size:1.4em;"></i>
									</c:if>
									<c:if test="${n.gravita == 'Bloccante'}">
										<i class="ico24 ico_errorB" title="Bloccante" style="font-size:1.4em;"></i>						
									</c:if>
									<c:if test="${n.gravita == 'Grave'}">
										<i class="ico24 ico_errorG" title="Grave" style="font-size:1.4em;"></i>						
									</c:if>
								</td>
								<td><c:out value="${n.descrizione}"></c:out></td>
								<td><c:out value="${n.dataInizioStr}"></c:out></td>
								<td><c:out value="${n.dataFineStr}"></c:out></td>
								<td><c:out value="${n.utente}"></c:out></td>
								</tr>
							</c:forEach>
							</tbody>
							</table>
				
			<div class="form-group puls-group">
      <div class="pull-left">
        <a type="button"  href="../cunembo129/index_${idProcedimento}.do" class="btn btn-default">Indietro</a>
      </div>
    </div>	

	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />
<style>
.card-img-top {
    width: 100%;
    height: 15vw;
    object-fit: cover;
}

.card-text {
    width: 100%;
    height: 5vw;
}


.card-img-wrap {
  overflow: hidden;
  position: relative;
}
.card-img-wrap:after {
  content: '';
  position: absolute;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(255,255,255,0.3);
  opacity: 0;
  transition: opacity .25s;
}
.card-img-wrap img {
  transition: transform .25s;
  width: 100%;
}
.card-img-wrap:hover img {
  transform: scale(1.2);
}
.card-img-wrap:hover:after {
  opacity: 1;
}

</style>
<body>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/header.html" />

	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<h3>Selezionare la funzionalit&agrave; a cui si desidera accedere</h3>

		<form method="post">
			<form:errors path="ruolo"
				cssClass="stdMessagePanel alert alert-danger" element="div"
				htmlEscape="false" />
				
			<div class="container-fluid">
            <div class="row">
			<div style="margin-top: 2em" class="col-xs-12">
				<c:forEach var="p" items="${procedimentiAgricoli}">
				
				<div class="card border col-sm-4" style="margin-top: 2em; margin-bottom: 2em; ">
				
				  <!-- Card image -->
				  <a href="seleziona_ruolo_${p.idProcedimentoAgricolo}.do">
					  <div class="view overlay">
					    <img class="card-img-top" src="visualizza_immagine_${p.idProcedimentoAgricolo}.do" alt="Card image cap">
					  </div>
				  </a>
				
				  <!-- Card content -->
				  <div class="card-body">
				
				    <!-- Title -->
				    <h4 class="card-title text-center">${p.descrizione}</h4>
				    <!-- Text -->
				    <p class="card-text text-center">${p.descrizioneEstesa}</p>
				    <!-- Button -->
				    <a href="seleziona_ruolo_${p.idProcedimentoAgricolo}.do" class=" center-block btn btn-primary">accedi</a>
				
				  </div>
				
				</div>
				<!-- Card -->
				
				
				
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-12"> -->
<%-- 						<h3><a href="seleziona_ruolo_${p.idProcedimentoAgricolo}.do"><c:out value="${p.descrizione}" /></a> --%>
<%-- 						<a href="seleziona_ruolo_${p.idProcedimentoAgricolo}.do" class="pull-right btn btn-primary">accedi</a></h3> --%>
<%-- 						<c:out value="${p.descrizioneEstesa}" /> --%>
<!-- 						</div> -->
<!-- 					</div> -->
				</c:forEach>
			</div>
			</div>
			</div>
		</form>
	</div>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />
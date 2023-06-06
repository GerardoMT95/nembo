+<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">Gestione sistema</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />

	<div class="container-fluid" id="content" style="margin-bottom: 3em">
    <table
      style="width: 100%; background-size: contain; background-image: url('img/nembo-piemonte.jpg'); background-position: center; background-repeat: no-repeat; background-opacity: 0.5">
      <c:forEach items="${links}" var="link">
        <tr style="background-color: rgba(255, 255, 255, 0.8)">
          <td><p:link href="${link.link}" useCase="${link.useCase}" readWrite="${link.readWrite}" title="${link.title}" description="${link.description}"  onClick="${link.onclick}"/></td>
        </tr>
      </c:forEach>
    </table>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
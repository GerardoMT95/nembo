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
				  <li><a href="elencobando.do">Elenco bandi</a> <span class="divider">/</span></li>
				  <li><a href="dettaglioBando.do">Dettaglio bando</a> <span class="divider">/</span></li>
				  <li class="active">Scelta tipo domandae</li>
				</ul>
            </div>
        </div>           
    </div>
    <p:messaggistica/>
	  
	<div class="container-fluid" id="content" style="margin-bottom:3em;position:relative">
		<div class="stdMessagePanel"> 
			<div class="alert alert-warning">
				<p>
					La validazione presente in anagrafe risulta essere di tipo grafico. Che tipo di domanda si desidera creare ?
				</p>
			</div>
		</div>
		
		<div class="form-group puls-group" style="margin-top: 1.5em;margin-right:0px">
				<button type="button" class="btn btn-primary" onclick="window.location.href='../nuovoprocedimento/proseguitipodomanda_A_${idAzienda}.do'">Domanda alfanumerica</button>
				<button type="button" class="btn btn-primary pull-right" onclick="window.location.href='../nuovoprocedimento/proseguitipodomanda_G_${idAzienda}.do'">Domanda Grafica</button>
		</div>
		
		
		
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>

<script type="text/javascript">

		
	</script>


<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>

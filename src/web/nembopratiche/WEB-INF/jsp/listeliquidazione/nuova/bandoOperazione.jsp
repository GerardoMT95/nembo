<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../cunembo227/index.do">Liste di liquidazione</a><span class="divider">/</span></li>
					<li class="active">Nuova lista di liquidazione</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<div class="container-fluid" id="content">
		<form class="form-horizontal" method="post" action="elenco_operazioni_bando_${idBando}.do">
			<m:textfield id="bando" name="bando" value="${livelliBando.denominazioneBando}" label="Bando" disabled="true" />
			<m:select id="livelli" list="${livelliBando.livelli}" name="livelli" label="Misura / Sottomisura / Operazione" disabled="true" multiple="true" header="false"
				textProperty="codiceDescrizione" size="10" />
			<m:radio-list id="extIdAmmCompetenza" list="${listAmmCompetenza}" name="extIdAmmCompetenza" label="Amministrazione (Organismo Delegato)"
				textProperty="descrizione" valueProperty="id" selectedValue="${idAmmCompSel}" preferRequestValues="${preferRequest}" />
			<m:radio-list id="idTipoImporto" list="${listTipiImporto}" name="idTipoImporto" label="Tipo importo" textProperty="descrizione" valueProperty="id"
				selectedValue="${idTipoImportoSel}" preferRequestValues="${preferRequest}" />
			<br /> <br />
			<div class="col-sm-12">
				<a href="../cunembo226/elenco_bandi.do" class="btn  btn-default pull-left">annulla</a>
				<button type="submit" class="btn  btn-primary pull-right">conferma</button>
			</div>
		</form>
	</div>
	<br />
	<br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<style type="text/css">
.blueColor, .blueColor:hover{
	background-color: #428BCA !important;
}


.borderInfo, .borderInfo:hover{
  border-bottom:none !important;
  border-right:none !important;
  border-left :none !important;
  border-top :none !important;
  width:1px !important;
}

.innerTable{
	padding-left:4em !important;
}

</style>

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">Ricerca procedimento</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />


	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<h3>Ricerca procedimento</h3>
		<m:error />
		<div class="stdMessagePanel stdMessageLoad" style="display: none">
			<div class="alert alert-info">
				<p>Attendere prego: Il sistema sta effettuando la ricerca procedimenti; l'operazione potrebbe richiedere alcuni secondi...</p>
			</div>
		</div>
		<c:if test="${msgErrore != null}">
			<div class="stdMessagePanel">
				<div class="alert alert-danger">
					<p>
						<strong>Attenzione!</strong><br />
						<c:out value="${msgErrore}"></c:out>
					</p>
				</div>
			</div>
		</c:if>

		<m:panel title="Ricerca procedimento" id="ricercaDomanda">
			<form id="ricercaDomanda" class="form-horizontal" action="ricercapuntuale.do" method="post">
				<div class="pageContainer">
					<div class="form-group">
						<label for="cuaa" class="col-sm-2 control-label">Codice Domanda</label>
						<div class="col-sm-10">
							<m:textfield value="${identificativo}" name="identificativo" id="identificativo" cssClass="form-control"></m:textfield>
						</div>
					</div>
					<div class="form-group">
						<label for="cuaa" class="col-sm-2 control-label">CUAA (Codice fiscale)</label>
						<div class="col-sm-10">
							<m:textfield value="${cuaa}" name="cuaa" id="cuaa" cssClass="form-control"></m:textfield>
						</div>
					</div>
					<div class="puls-group" style="margin-top: 1em">
						<div class="pull-right">
							<button type="submit" title="ricerca procedimento" onclick="$('.stdMessageError').hide();$('.stdMessageLoad').show();" name="prosegui"
								id="prosegui" class="btn btn-primary">ricerca</button>
						</div>
					</div>
					<br />
				</div>
			</form>
		</m:panel>

		<m:panel title="Ricerca procedimenti" id="ricercaProcedimenti">
			<form id="ricercaProcedimenti" class="form-horizontal" action="ricercaprocedimenti.do" method="post">
				<div class="pageContainer">
					<div class="form-group">
						<label data-toggle="modal" data-target="#livelliModal" onclick="selezionaPopupOptions('misura')" for="misura"
							class="col-sm-2 control-label link"> Misura / Sottomisura / Operazione <span style="text-decoration: none;"
							class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-10">
							<m:select name="misura" onclick="return false;" disabledOptions="true" id="misura" header="false" list="${livelli}" size="5"
								valueProperty="idLivello" textProperty="descrEstesa" readonly="true" multiple="true"></m:select>
						</div>
					</div>
					<c:if test="${isAvversitaAtmosferica == true }">
						<div class="form-group">
							<label data-toggle="modal" data-target="#eventiModal" onclick="selezionaPopupOptions('evento')" for="evento" class="col-sm-2 control-label link">
								Evento<br/>calamitoso <span style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
							</label>
							<div class="col-sm-10">
								<m:select name="evento" onclick="return false;" addOptionsTitle="true" disabledOptions="true" id="evento" header="false" list="${eventi}" size="5"
									valueProperty="idEventoCalamitoso" textProperty="descrizione" multiple="true" readonly="true"></m:select>
							</div>
						</div>
					</c:if>					
					
					<div class="form-group">
						<label data-toggle="modal" data-target="#bandiModal" onclick="selezionaPopupOptions('bando')" for="bando" class="col-sm-2 control-label link">
							Bando <span style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-10">
							<m:select name="bando" onclick="return false;" addOptionsTitle="true" disabledOptions="true" id="bando" header="false" list="${bandi}" size="5"
								valueProperty="idBando" textProperty="descrizione" multiple="true" readonly="true"></m:select>
						</div>
					</div>
					<div class="form-group">
						<label data-toggle="modal" data-target="#amministrazioniModal"
							onclick="reloadAmministrazioniPopup(false);selezionaPopupOptions('amministrazione')" for="amministrazione" class="col-sm-2 control-label link">
							Amministrazione (Organismo Delegato) <span style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-10">
							<m:select name="amministrazione" onclick="return false;" disabledOptions="true" header="false" id="amministrazione" size="5"
								list="${amministrazioni}" valueProperty="idAmmCompetenza" textProperty="descrizione" multiple="true" readonly="true"></m:select>
						</div>
					</div>
					<div class="form-group">
						<label data-toggle="modal" data-target="#statiModal" for="stato" class="col-sm-2 control-label link"> Stato del procedimento <span
							style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-10">
							<m:select name="stato" id="stato" onclick="return false;" disabledOptions="true" header="false" list="${statiProcedimento}" size="5"
								valueProperty="idStatoOggetto" textProperty="descrStatoOggetto" multiple="true" readonly="true"></m:select>
						</div>
					</div>
					<div class="form-group">
						<input type="hidden" name="tipoFiltroOggetti" id="tipoFiltroOggetti" value="${fn:escapeXml(tipoFiltroOggetti)}"> <label
							data-toggle="modal" data-target="#oggettoModal" for="oggetti" class="col-sm-2 control-label link"> Gruppi/Oggetti procedimento <span
							style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-10">						
								<c:forEach items="${gruppiProcedimento}" var="a">
									<c:if test="${a.selected}">
										<c:forEach items="${a.stati}" var="b">
											<c:if test="${b.selected}">
												<input type="hidden" name="hGruppi" data-oggetto="${a.idGruppoOggetto}" value="${a.idGruppoOggetto}&&${b.idEsito}" />
											</c:if>
										</c:forEach>
									</c:if>
									
									<c:if test="${!a.selected }">
										<c:forEach items="${a.oggetti}" var="o">
										<c:if test="${o.selected && fn:length(o.esitiOggetto)>0}">
											<c:forEach items="${o.esitiOggetto}" var="b">
												<c:if test="${b.selected}">
													<input type="hidden" name="hOggetti" data-oggetto="${o.idLegameGruppoOggetto}" value="${o.idLegameGruppoOggetto}&&${b.idEsito}" />
												</c:if>
											</c:forEach>
										</c:if>
										</c:forEach>
									</c:if>
								</c:forEach>

								
							<span id="txtTipoFiltroOggetti"> <c:if test="${tipoFiltroOggetti == 'OR' }"> Procedimenti che contengono almeno un gruppo/oggetto tra quelli selezionati: </c:if>
								<c:if test="${tipoFiltroOggetti == 'AND' }"> Procedimenti che contengono tutti i gruppi/oggetti selezionati: </c:if>
							</span>
							
							<m:select name="oggetti" onclick="return false;" id="oggetti" disabledOptions="true" header="false" list="${selectGuppiRicerca}"
								valueProperty="id" textProperty="descrizione" multiple="true" size="5" readonly="true" preferRequestValues="preferRequestValues"></m:select>
						</div>
					</div>

					<p:ablitazione-macrocdu codiceMacroCdu="ESTRAZIONE_CAMPIONE">
						<div class="form-group">
							<label data-toggle="modal" data-target="#estrazioneModal" for="stato" class="col-sm-2 control-label link"> Estrazione Campione <span
								style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
							</label>
							<div class="col-sm-10">
								<m:select name="flagEstrazione" id="flagEstrazione" onclick="return false;" disabledOptions="true" header="false" list="${flagFiltrati}"
									size="5" valueProperty="idEstrazione" textProperty="descrizione" multiple="true" readonly="true"></m:select>
							</div>
						</div>
					</p:ablitazione-macrocdu>
					
					<p:ablitazione-macrocdu codiceMacroCdu="ESTRAZIONE_CAMPIONE">
						<div class="form-group">
							<label data-toggle="modal" data-target="#estrazioneexpostModal" for="stato" class="col-sm-2 control-label link"> Estrazione Campione Ex Post <span
								style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
							</label>
							<div class="col-sm-10">
								<m:select name="flagEstrazioneExPost" id="flagEstrazioneExPost" onclick="return false;" disabledOptions="true" header="false" list="${flagFiltratiExPost}"
									size="5" valueProperty="idEstrazione" textProperty="descrizione" multiple="true" readonly="true"></m:select>
							</div>
						</div>
					</p:ablitazione-macrocdu>

					<div class="form-group">
						<label for="stato" class="col-sm-2 control-label link"> Ultima istanza trasmessa </label>
						<div class="col-sm-5">
							<m:textfield type="DATE" id="istanzaDataDa" name="istanzaDataDa" label="Da" value="${istanzaDataDa}"></m:textfield>
						</div>
						<div class="col-sm-5">
							<m:textfield type="DATE" id="istanzaDataA" name="istanzaDataA" label="A" value="${istanzaDataA}"></m:textfield>
						</div>
					</div>

					<div class="form-group">
						<label data-toggle="modal" data-target="#notificheModal" for="stato" class="col-sm-2 control-label link"> Notifiche del procedimento <span
							style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-10">
							<m:select name="notifica" id="notifica" onclick="return false;" disabledOptions="true" header="false" list="${notificheProcedimento}" size="5"
								valueProperty="id" textProperty="gravita" multiple="true" readonly="true"></m:select>
						</div>
					</div>

					<fieldset>
						<legend>Dati anagrafici</legend>
						<div class="form-group">
							<label for="cuaaProcedimenti" class="col-sm-2 control-label">CUAA (Codice fiscale)</label>
							<div class="col-sm-10">
								<m:textfield value="${cuaaProcedimenti}" name="cuaaProcedimenti" id="cuaaProcedimenti" cssClass="form-control"></m:textfield>
							</div>
						</div>
						<div class="form-group">
							<label for="pivaProcedimenti" class="col-sm-2 control-label">Partita IVA</label>
							<div class="col-sm-10">
								<m:textfield value="${pivaProcedimenti}" name="pivaProcedimenti" id="pivaProcedimenti" cssClass="form-control"></m:textfield>
							</div>
						</div>
						<div class="form-group">
							<label for="denominazioneProcedimenti" class="col-sm-2 control-label">Denominazione</label>
							<div class="col-sm-10">
								<m:textfield value="${denominazioneProcedimenti}" name="denominazioneProcedimenti" id="denominazioneProcedimenti" cssClass="form-control"></m:textfield>
							</div>
						</div>
						<div class="form-group">
							<label for="provProcedimenti" class="col-sm-2 control-label">Provincia sede legale</label>
							<div class="col-sm-6">
								<m:textfield value="${provSceltaComune}" name="provSceltaComune" id="provSceltaComune" cssClass="form-control"></m:textfield>
							</div>
							<div class="col-sm-4">
								<button type="button" class="btn btn-default" name="btnComune" onclick="openPopupComuni('provSceltaComune','comuneSceltaComune')">Cerca</button>
							</div>
						</div>
						<div class="form-group">
							<label for="comuneProcedimenti" class="col-sm-2 control-label">Comune sede legale</label>
							<div class="col-sm-10">
								<m:textfield value="${comuneSceltaComune}" name="comuneSceltaComune" id="comuneSceltaComune" cssClass="form-control"></m:textfield>
							</div>
						</div>
					</fieldset>
					<input type="hidden" name="serializedLivello" id="serializedLivello" value="" /> 
					<input type="hidden" name="serializedEvento" id="serializedEvento" value="" /> 
					<input type="hidden" name="serializedBando" id="serializedBando" value="" /> 
					<input type="hidden" name="serializedAmministrazione" id="serializedAmministrazione" value="" /> 
					<input type="hidden" name="serializedStato" id="serializedStato" value="" /> 
					<input type="hidden" name="serializedEstrazione" id="serializedEstrazione" value="" /> 
					<input type="hidden" name="serializedEstrazioneExPost" id="serializedEstrazioneExPost" value="" />
					<input type="hidden" name="serializedNotifica" id="serializedNotifica" value="" />

					<div class="puls-group" style="margin-top: 1em">
						<div class="pull-right">
							<button type="submit" title="ricerca procedimenti"
								onclick="$('.stdMessageError').hide();$('.stdMessageLoad').show();prepareRicercaHiddenField();" name="prosegui" id="prosegui"
								class="btn btn-primary">ricerca</button>
						</div>
						<div class="pull-right" style="margin-right:1em">
							<button title="Annulla filtri"
								onclick="window.location.href = 'pulisciFiltri.do';return false;" name="annullafiltri" id="annullafiltri"
								class="btn btn-default">annulla filtri</button>
						</div>
						
					</div>
					<br />
				</div>

				<!-- POPUP COMUNI INI -->
				<div class="modal fade" id="comuniModal" tabindex="-1" role="dialog" aria-labelledby="livelliModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Seleziona Comune</h4>
							</div>
							<div class="modal-body" style="max-height: 500px; overflow-y: scroll">
								<table id="popupComuniTable" class="myovertable table table-hover table-condensed table-bordered tableBlueTh">
									<thead>
										<tr>
											<th data-property="chk"></th>
											<th data-property="descrizioneComune">Comune</th>
											<th data-property="siglaProvincia">Prov.</th>
											<th data-property="istatComune">Istat Comune</th>
											<th data-property="istatProvincia">Istat Provincia</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="popolaComuneProvincia();$('#comuniModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP COMUNI FINE -->

				<!-- POPUP LIVELLI INI -->
				<div class="modal fade" id="livelliModal" tabindex="-1" role="dialog" aria-labelledby="livelliModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro Misura / Sottomisura / Operazione</h4>
							</div>
							<div class="modal-body">
								<div style="display: inline-block;" class="dropdown">
									<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
										Selezione misure <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li class="${m.classTipoBando}"><a href="#"><input type="checkbox" id="selectAllCheckbox" onclick="selectAllCheck(this);">
												Seleziona tutte le misure</a></li>
										<c:forEach items="${all_misure}" var="m">
											<li class="${m.classTipoBando}"><a href="#"><input type="checkbox" class="checkboxMisure ${m.classTipoBando}"
													value="${m.idLivello}"> ${m.codice} - ${m.descrizione}</a></li>
										</c:forEach>
									</ul>
								</div>
								<div style="display: inline-block;" class="dropdown">
									<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
										Selezione tipo misura <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li class="radio "><a href="#"><label><input name="optRadio" checked="checked" type="radio" class="radioTipoMisure "
													value=""> Tutte le tipologie</label></a></li>
										<c:forEach items="${tipiMisure}" var="m">
											<li class="radio ${m.codice}"><a href="#"><label><input name="optRadio" type="radio" class="radioTipoMisure ${m.id}"
														value="${m.codice}"> ${m.descrizione}</label></a></li>
										</c:forEach>
									</ul>

								</div>
								<div>
									<br> <a href="javascript:selectAll('all_misura', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
										href="javascript:selectAll('all_misura', false);" class="link">Deseleziona tutto</a>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<m:select name="all_misura" id="all_misura" header="false" list="${all_livelli}" valueProperty="idLivello" textProperty="descrEstesa"
											multiple="true" size="20"></m:select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="filtra('all_misura');$('#livelliModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP LIVELLI FINE -->

				<c:if test="${isAvversitaAtmosferica == true }">
					<!-- POPUP EVENTI INI -->
					<div class="modal fade" id="eventiModal" tabindex="-1" role="dialog" aria-labelledby="eventiModalLabel" aria-hidden="true">
						<div class="modal-dialog" style="width: 850px">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Filtro Eventi Calamitosi</h4>
								</div>
								<div class="modal-body">
									<div class="form-group col-sm-12">
										<div class="col-sm-5">
											Anno evento calamitoso &nbsp;
											<a href="javascript:selectAllCheck('chk_anno_evento', true);" class="link">tutti</a>&nbsp;
											<a href="javascript:selectAllCheck('chk_anno_evento', false);" class="link">nessuno</a>
										</div>
										<div id="annoEventoCnt" class="col-sm-7">
											<m:checkbox-list onclick="reloadPopupEventi();" name="chk_anno_evento" id="chk_anno_evento" list="${eventi}"
												valueProperty="annoEvento" textProperty="annoEvento" inline="true" checkedProperty="defaultChecked">
											</m:checkbox-list>
										</div>
									</div>
									<div>
										 <a href="javascript:selectAll('all_evento', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
											href="javascript:selectAll('all_evento', false);" class="link">Deseleziona tutto</a>
									</div>
									<div class="form-group">
										<div class="col-sm-12">
											<m:select name="all_evento" id="all_evento" addOptionsTitle="true" header="false" list="${all_eventi}" valueProperty="idEventoCalamitoso"
												textProperty="descrizione" multiple="true" size="20"></m:select>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<div class="puls-group" style="margin-top: 1em">
										<div class="pull-left">
											<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
										</div>
										<div class="pull-right">
											<button type="button" onclick="filtra('all_evento');$('#eventiModal').modal('hide');" class="btn btn-primary">Conferma</button>
										</div>
									</div>
	
								</div>
							</div>
						</div>
					</div>
					<!-- POPUP EVENTI FINE -->
				</c:if>
				
				
				<!-- POPUP BANDI INI -->
				<div class="modal fade" id="bandiModal" tabindex="-1" role="dialog" aria-labelledby="bandiModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro Bandi</h4>
							</div>
							<div class="modal-body">
								<div class="form-group col-sm-12">
									<div class="col-sm-5">
										Anno campagna/competenza &nbsp;<a href="javascript:selectAllCheck('chk_anno_campagna', true);" class="link">tutti</a>&nbsp;<a
											href="javascript:selectAllCheck('chk_anno_campagna', false);" class="link">nessuno</a>
									</div>
									<div id="annoCampagnaCnt" class="col-sm-7">
										<m:checkbox-list onclick="reloadPopupBandi();" name="chk_anno_campagna" id="chk_anno_campagna" list="${all_bandi}"
											valueProperty="annoCampagnaValNVL" textProperty="annoCampagnaNVL" inline="true" checkedProperty="defaultChecked"></m:checkbox-list>
									</div>
								</div>
								<div>
									<a href="javascript:selectAll('all_bando', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
										href="javascript:selectAll('all_bando', false);" class="link">Deseleziona tutto</a>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<m:select name="all_bando" id="all_bando" addOptionsTitle="true" header="false" list="${all_bandi}" valueProperty="idBando"
											textProperty="descrizione" multiple="true" size="20"></m:select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="filtra('all_bando');$('#bandiModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP BANDI FINE -->

				<!-- POPUP AMMINISTRAZIONI INI -->
				<div class="modal fade" id="amministrazioniModal" tabindex="-1" role="dialog" aria-labelledby="amministrazioniModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro Amministrazioni</h4>
							</div>
							<div class="modal-body">
								<div>
									<c:choose>
										<c:when test="${utenteAbilitazioni!=null && utenteAbilitazioni.ruolo.utentePA}">
											<m:checkBox name="chkTutteAmministrazioni" value="tutteAmministrazioniChk" chkLabel="Tutte le amministrazioni"
												checked="${tutteAmministrazioniChecked}"></m:checkBox>
										</c:when>
										<c:otherwise>
											<m:checkBox disabled="true" readonly="true" name="chkTutteAmministrazioni" value="tutteAmministrazioniChk"
												chkLabel="Tutte le amministrazioni"></m:checkBox>
										</c:otherwise>
									</c:choose>

								</div>
								<div>
									<div class="form-group">
										<label style="padding-top: 5px" class="control-label col-sm-4">Tipologia amministrazione:</label>
										<div class="col-sm-8">
											<div class="checkbox" id="tipologiaamm"></div>
										</div>
									</div>

								</div>
								<div>
									<a href="javascript:selectAll('all_amministrazione', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
										href="javascript:selectAll('all_amministrazione', false);" class="link">Deseleziona tutto</a>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<m:select name="all_amministrazione" addOptionsTitle="true" id="all_amministrazione" header="false" list="${all_amministrazioni}"
											valueProperty="idAmmCompetenza" textProperty="descrizione" multiple="true" size="20"></m:select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="filtra('all_amministrazione');$('#amministrazioniModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP AMMINISTRAZIONI FINE -->

				<!-- POPUP STATI PROCEDIMENTO INI -->
				<div class="modal fade" id="statiModal" tabindex="-1" role="dialog" aria-labelledby="livelliModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro stati procedimento</h4>
							</div>
							<div class="modal-body">
								<div>
									<a href="javascript:selectAll('all_stato', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
										href="javascript:selectAll('all_stato', false);" class="link">Deseleziona tutto</a>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<m:select name="all_stato" id="all_stato" header="false" list="${all_statiProcedimento}" valueProperty="idStatoOggetto"
											textProperty="descrStatoOggetto" multiple="true" size="20"></m:select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="filtra('all_stato');$('#statiModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP STATI PROCEDIMENTO FINE -->

				<!-- POPUP Estrazione INI -->
				<div class="modal fade" id="estrazioneModal" tabindex="-1" role="dialog" aria-labelledby="estrazioneModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro Tipo Estrazione</h4>
							</div>
							<div class="modal-body">
								<div>
									<a href="javascript:selectAll('all_flagEstrazione', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
										href="javascript:selectAll('all_flagEstrazione', false);" class="link">Deseleziona tutto</a>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<m:select name="all_flagEstrazione" id="all_flagEstrazione" header="false" list="${all_flagEstrazione}" valueProperty="idEstrazione"
											textProperty="descrizione" multiple="true" size="20"></m:select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="filtraSelezionati('all_flagEstrazione');$('#estrazioneModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP Estrazione FINE -->


				<!-- POPUP Estrazione ex post INI -->
				<div class="modal fade" id="estrazioneexpostModal" tabindex="-1" role="dialog" aria-labelledby="estrazioneexpostModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro Tipo Estrazione</h4>
							</div>
							<div class="modal-body">
								<div>
									<a href="javascript:selectAll('all_flagEstrazioneExPost', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
										href="javascript:selectAll('all_flagEstrazioneExPost', false);" class="link">Deseleziona tutto</a>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<m:select name="all_flagEstrazioneExPost" id="all_flagEstrazioneExPost" header="false" list="${all_flagEstrazioneExPost}" valueProperty="idEstrazione"
											textProperty="descrizione" multiple="true" size="20"></m:select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="filtraSelezionati('all_flagEstrazioneExPost');$('#estrazioneexpostModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>popup
				</div>
				<!-- POPUP Estrazione ex post FINE -->

				<!-- POPUP OGGETTI INI -->
				<div class="modal fade" id="oggettoModal" tabindex="-1" role="dialog" aria-labelledby="livelliModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 900px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro oggetti procedimento</h4>
							</div>
							<div class="modal-body">
								<input type="radio" name="popupTipoFiltroOggetti" value="OR" <c:if test="${tipoFiltroOggetti == 'OR' }"> checked="checked" </c:if> />&nbsp;Procedimenti
								che contengono almeno un gruppo tra quelli selezionati<br /> <input type="radio" name="popupTipoFiltroOggetti" value="AND"
									<c:if test="${tipoFiltroOggetti == 'AND' }"> checked="checked" </c:if> />&nbsp;Procedimenti che contengono tutti i gruppi selezionati

								<table class="myovertable table table-hover table-condensed table-bordered tableBlueTh" style="margin-top: 1em">
									<thead>
										<tr>
											<th><input type="checkbox" name="selectAllGruppiChk" id=selectAllGruppiChk onclick="selectAllGruppi();" /></th>
											<th>Gruppi</th>
											<th>Stati</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${all_gruppiProcedimento}" var="a" varStatus="i">
											<c:choose>
												<c:when test="${i.index == 0}">
													<c:set var="oldDescrGruppo" scope="session" value="${a.descrizione}" />
													<tr class="info borderInfo">
														<td class="info borderInfo" >
															<input type="checkbox" name="popupGruppoChk" id="popupGruppoChk_${a.idGruppoOggetto}" onclick="abilitaStatiGruppo('${a.idGruppoOggetto}')"
																value="${a.idGruppoOggetto}" <c:if test="${a.selected && fn:length(a.stati)>1}"> checked="checked"</c:if> <c:if test="${fn:length(a.stati)<=1 }">disabled="disabled" </c:if> />
														</td>
														
														<td class="info borderInfo">																
															<input type="hidden" id="descrGruppoChk_${a.idGruppoOggetto}" value="${a.descrizione}" /> 
															<c:out value="${a.descrizione}" />
														</td>
														<td class="info borderInfo">
														
														<a class="pull-right content_gruppo_${a.idGruppoOggetto} mostraNascondi_${a.idGruppoOggetto} panel-collapse collapse" href="javascript:void(0)" data-toggle="collapse" data-target=".content_oggetti_${a.idGruppoOggetto}" onclick="changeText(${a.idGruppoOggetto});">
																	Mostra oggetti</a>
																	
														<div id="tdStatiGruppo_${a.idGruppoOggetto}" class="panel-collapse">
															<c:if test="${fn:length(a.stati)>1 }">
																<c:forEach items="${a.stati}" var="b">
																	<input type="checkbox" name="popupStatiGruppoChk_${a.idGruppoOggetto}" data-text="<c:out value='${b.descrizione}'></c:out>"
																		value="${b.idEsito}" <c:if test="${b.selected}"> checked="checked"</c:if> />&nbsp;<c:out value="${b.descrizione}"></c:out>
																		<br />
																</c:forEach>
															</c:if>
														</div>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:if test="${a.descrizione != oldDescrGruppo }">
														<c:set var="oldDescrGruppo" scope="session" value="${a.descrizione}" />
														<tr class="info borderInfo">
															<td>
																<input type="checkbox" name="popupGruppoChk" id="popupGruppoChk_${a.idGruppoOggetto}" onclick="abilitaStatiGruppo('${a.idGruppoOggetto}')"
																	value="${a.idGruppoOggetto}" <c:if test="${a.selected && fn:length(a.stati)>1}"> checked="checked"</c:if> <c:if test="${fn:length(a.stati)<=1 }">disabled="disabled" </c:if>/>
															</td>
															<td>
																<input type="hidden" id="descrGruppoChk_${a.idGruppoOggetto}" value="${a.descrizione}" /> 
																<c:out value="${a.descrizione}" />
															</td>
															<td>
															
															<a class="pull-right content_gruppo_${a.idGruppoOggetto} mostraNascondi_${a.idGruppoOggetto} panel-collapse collapse" href="javascript:void(0)" data-toggle="collapse" data-target=".content_oggetti_${a.idGruppoOggetto}" onclick="changeText(${a.idGruppoOggetto});">
																	Mostra oggetti</a>
																	
															<div id="tdStatiGruppo_${a.idGruppoOggetto}" class="panel-collapse">
															<c:if test="${fn:length(a.stati)>1 }">
															
																<c:forEach items="${a.stati}" var="b">
																<input type="checkbox" name="popupStatiGruppoChk_${a.idGruppoOggetto}" data-text="<c:out value='${b.descrizione}'></c:out>"
																	value="${b.idEsito}" <c:if test="${b.selected}"> checked="checked"</c:if> />&nbsp;<c:out value="${b.descrizione}"></c:out>
																	<br />
																	</c:forEach>
																</c:if>
															</div>
															</td>
														</tr>
													</c:if>
												</c:otherwise>
											</c:choose>
												<tr  class="content_oggetti content_oggetti_${a.idGruppoOggetto} panel-collapse collapse blueColor">
													<td colspan="3" class="info">
													<div class="innerTable"><table class="myovertable table table-hover table-condensed table-bordered tableBlueTh innerTable" >
														<thead>
															<tr>
																<th>
																	<input type="checkbox" name="selectAllOggettiGruppo" id="selectAllOggettiGruppo_${a.idGruppoOggetto}" onclick="selezionaAllOggettiGruppo('${a.idGruppoOggetto}');" />

																</th>
		 														<th>Oggetti</th>
																<th>Esiti</th>
															</tr>
															</thead>
															<tbody id="content_oggetti_${a.idGruppoOggetto}" class="content_oggetti content_oggetti_${a.idGruppoOggetto} panel-collapse collapse in">
																<c:forEach items="${a.oggetti}" var="o">
																	<c:if test="${fn:length(o.esitiOggetto)>0}">
																		<tr>
																			<td>
																				<input type="checkbox" name="popupOggChk" id="popupOggChk_${a.idGruppoOggetto}_${o.idLegameGruppoOggetto}" onclick="abilitaStatiOggetto('${a.idGruppoOggetto}','${o.idLegameGruppoOggetto}')"
																					value="${o.idLegameGruppoOggetto}" <c:if test="${o.selected}"> checked="checked"</c:if>  <c:if test="${a.selected}">disabled="disabled"</c:if> />
																			</td>
																			<td>
																				<input type="hidden" id="descrChk_${o.idLegameGruppoOggetto}" value="${o.descrizione}" /> 
																				<c:out value="${o.descrizione }"></c:out>
																			</td>
																			<td>
																				<c:forEach items="${o.esitiOggetto}" var="c">
																					<input type="checkbox" name="popupStatiChk_${o.idLegameGruppoOggetto}" data-text="<c:out value='${c.descrizione}'></c:out>" value="${c.idEsito}"
																						<c:if test="${c.selected}"> checked="checked"</c:if> />&nbsp;<c:out value="${c.descrizione}"></c:out>
																					<br />
																				</c:forEach>
																			</td>
																		</tr>
																	</c:if>
																</c:forEach>
															</tbody>
														</table></div>
												</td></tr>
											<tr><td colspan="5"></td></tr>
											</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="if(filtraGruppi()){$('#oggettoModal').modal('hide');}" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP OGGETTI FINE -->

				<!-- POPUP NOTIFICHE INI -->
				<div class="modal fade" id="notificheModal" tabindex="-1" role="dialog" aria-labelledby="notificheModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 850px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro notifiche procedimento</h4>
							</div>
							<div class="modal-body">
								<div>
									<a href="javascript:selectAll('all_notifica', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
										href="javascript:selectAll('all_notifica', false);" class="link">Deseleziona tutto</a>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<m:select name="all_notifica" id="all_notifica" header="false" list="${all_notifica}" valueProperty="id" textProperty="gravita"
											multiple="true" size="20"></m:select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="filtraSelezionati('all_notifica');$('#notificheModal').modal('hide');" class="btn btn-primary">Conferma</button>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- POPUP NOTIFICHE FINE -->

			</form>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<script>
		$(document).ready(function() {
			
			//get checked checkbox for misure
			$.ajax({
				type : "POST",
				url : 'ajaxIdMisureSelezionate.do',
				dataType : "json",
				async : false,
				success : function(data) {
					//ritorna una mappa <id_bando, descrizione>
					for (key in data) {
						if (data[key] != "")
							$(".checkboxMisure[value=" + data[key] + "]").attr("checked", true);
					}
					if (data != null && data[0] != "")
						reloadLivelliPopup();
				}
			});

			//reloadAmministrazioniPopup();
			//Ajax popup Filtro Amministrazioni - tutte le amministrazioni 
			$("input[name = 'chkTutteAmministrazioni']").click(function() {
				reloadAmministrazioniPopup(true);
			});

			//Ajax popup Filtro Misure/Livelli
			$(".checkboxMisure").click(function() {
				reloadLivelliPopup();
			});
			//Ajax popup Filtro Misure/Livelli
			$(".radioTipoMisure").click(function() {
				reloadLivelliPopup();
			});

			sistemaCheckBoxGruppi();
		});

		//Ajax popup Filtro Bandi 
		function reloadPopupBandi() {
			var dati = $('input[name="chk_anno_campagna"]:checked').serialize();
			$.ajax({
				type : "POST",
				url : 'ajaxFiltraPopupBandi.do',
				dataType : "json",
				data : dati,
				async : false,
				success : function(data) {
					//ritorna una mappa <id_bando, descrizione>
					$('#all_bando').find('option').remove();
					for (key in data) {
						var value = data[key];
						$('#all_bando').append(
								"<option value=\""+key+"\" title=\""+value+"\" selected=\"selected\"\>" + value + "</option>");
					}
				}
			});
		};
		
		function reloadPopupEventi() {
//TODO:		
			var dati = $('input[name="chk_anno_evento"]:checked').serialize();
			$.ajax({
				type : "POST",
				url : 'ajaxFiltraPopupEventi.do',
				dataType : "json",
				data : dati,
				async : false,
				success : function(data) {
					//ritorna una mappa <id_bando, descrizione>
					$('#all_evento').find('option').remove();
					for (key in data) {
						var value = data[key];
						$('#all_evento').append(
								"<option value=\""+key+"\" title=\""+value+"\" selected=\"selected\"\>" + value + "</option>");
					}
				}
			});
		}

		function selectAllOggetti() {
			if (document.getElementById('selectAllOggettiChk').checked) {
				$("input[name='popupOggChk']").attr('checked', 'checked');
				$('input[name^="popupStatiChk"]').removeAttr('disabled');
			} else {
				$("input[name='popupOggChk']").removeAttr('checked');
				$('input[name^="popupStatiChk"]').attr('disabled', 'disabled');
			}
		}

		function selezionaAllOggettiGruppo(idGruppoOggetto) {

			if($("#selectAllOggettiGruppo_"+idGruppoOggetto)[0].checked)
				{
					$("input[id^='popupOggChk_"+idGruppoOggetto+"']").attr('checked', 'checked');
				}
			else
				{
					$("input[id^='popupOggChk_"+idGruppoOggetto+"']").removeAttr('checked');
				}

			//Abilito stati oggetto
			$('input[id^="popupOggChk_' + idGruppoOggetto + '"]').each(
					function() {
						abilitaStatiOggetto(idGruppoOggetto, this.id.split('_')[2])		;	
					});
		}

		
		function selectAllGruppi() {
			if (document.getElementById('selectAllGruppiChk').checked) {
				$("input[name='popupGruppoChk']:enabled").attr('checked', 'checked');
				$('input[name^="popupStatiGruppoChk"]').removeAttr('disabled');

				$("input[name='popupGruppoChk']:disabled").removeAttr('checked');
				$('input[name^="popupStatiChk"]').removeAttr('disabled');
			} else {
				$("input[name='popupGruppoChk']:enabled").removeAttr('checked');
				$('input[name^="popupStatiGruppoChk"]').attr('disabled', 'disabled');

				$("input[name='popupGruppoChk']:disabled").removeAttr('checked');
				$('input[name^="popupStatiChk"]').attr('disabled', 'disabled');			
			}

			sistemaCheckBoxGruppi();
		}


		function reloadAmministrazioniPopup(ischkTutteAmministrazioniClickEvnt) {
			var dati = $('input[name="chkTutteAmministrazioni"]:checked').serialize() + "&"
					+ $('input[name="chkTipoAmministraz"]:checked').serialize();

			if (ischkTutteAmministrazioniClickEvnt)
				dati = dati + "&ischkTutteAmministrazioniClickEvnt=TRUE";

			$("#tipologiaamm").html(' ');
			$
					.ajax({
						type : "POST",
						url : 'ajaxFiltraPopupAmministrazioni.do',
						dataType : "json",
						data : dati,
						async : false,
						success : function(data) {
							//ritorna una mappa <id_bando, descrizione>
							$('#all_amministrazione').find('option').remove();
							var keys = [];
							var key = '';
							var i = 0;
							for (key in data) {
								keys[keys.length] = key;
							}
							keys.sort();
							for (i = 0; i < keys.length; i++) {
								var key = keys[i];
								if (key.indexOf("DESCR&&") < 0) {
									$('#all_amministrazione').append(
											"<option title=\""+data[key]+"\" value=\""+key+"\" selected=\"selected\"\>" + data[key]
													+ "</option>");
								} else {
									// In questo caso devo creare la check per "Tipologia amministrazione", se value è TRUE allora sarà checked di default
									var descrEstesa = key.split('&&')[1];
									var strChecked = " ";
									if (data[key] == 'TRUE') {
										strChecked = " checked=\"checked\" ";
									}
									$('#tipologiaamm')
											.append(
													"<label style=\"margin-right:1em\" >"
															+ "<input type=\"checkbox\" value=\""
															+ descrEstesa
															+ "\" name = \"chkTipoAmministraz\" onclick=\"reloadAmministrazioniPopup(false);\" aria-describedby = \"chkTipoAmministraz-status\" "
															+ strChecked + " />" + descrEstesa + "</label>")

								}

							}
						}
					});
		}

		function abilitaStatiOggetto(idGruppoOggetto, idLegameGruppoOggetto) {
			
			if ($('#popupOggChk_' + idGruppoOggetto + "_" + idLegameGruppoOggetto).is(':checked') && $('#popupOggChk_' + idGruppoOggetto + "_" + idLegameGruppoOggetto).is(':enabled'))
				{
					$('input[name="popupStatiChk_' + idLegameGruppoOggetto + '"]').removeAttr('disabled');
				}
			else
				{
					$('input[name="popupStatiChk_' + idLegameGruppoOggetto + '"]').attr('disabled', 'disabled');
				}
			
		}

		function changeText(idGruppoOggetto){

			if(!$(".content_oggetti_" + idGruppoOggetto).hasClass("collapse in"))
				$(".mostraNascondi_" + idGruppoOggetto).text("Nascondi oggetti");
			else
				$(".mostraNascondi_" + idGruppoOggetto).text("Mostra oggetti");

			}

		function sistemaCheckBoxGruppi() {
			$('input[name="popupGruppoChk"]').each(
					function() {
						abilitaStatiGruppo(this.id.split('_')[1]);
					});
		}

		
		function abilitaStatiGruppo(idGruppoOggetto) {

			//se abilito un gruppo ALLORA i suoi oggetti non sono selzionabili
			if ($('#popupGruppoChk_' + idGruppoOggetto).is(':checked'))
				{
					//Gruppo abilitato : oggetti non visibili. Stati del gruppo visibili.
					$('#tdStatiGruppo_'+ idGruppoOggetto).removeClass("collapse");
					$('#tdStatiGruppo_'+ idGruppoOggetto).addClass("collapse in");
					$('.content_gruppo_'+ idGruppoOggetto).removeClass("collapse in ");
					$('.content_gruppo_'+ idGruppoOggetto).addClass("collapse");
					
					$('input[name="popupStatiGruppoChk_' + idGruppoOggetto + '"]').removeAttr('disabled');
					$('input[id^="popupOggChk_' + idGruppoOggetto + '"]').attr('disabled', 'disabled');
					
					$('input[id^="popupOggChk_' + idGruppoOggetto + '"]').each(
							function() {
								abilitaStatiOggetto(idGruppoOggetto, this.id.split('_')[2])		;	
							});
					//hide i content_oggetti
					$(".content_oggetti_" + idGruppoOggetto).removeClass("collapse in").addClass("collapse");
					$(".mostraNascondi_" + idGruppoOggetto).text("Mostra oggetti");
					
				}
			else //se lo disabilito riattivo gli oggetti e nascondo gli stati del gruppo e disabilito tutti gli oggetti
				{
					$('#tdStatiGruppo_'+ idGruppoOggetto).removeClass("collapse in");
					$('#tdStatiGruppo_'+ idGruppoOggetto).addClass("collapse");
					$('.content_gruppo_'+ idGruppoOggetto).removeClass("collapse");
					$('.content_gruppo_'+ idGruppoOggetto).addClass("collapse in");

					$('input[name="popupStatiGruppoChk_' + idGruppoOggetto + '"]').attr('disabled', 'disabled');
					$('input[id^="popupOggChk_' + idGruppoOggetto + '"]').removeAttr('disabled');
					//$('input[id^="popupOggChk_' + idGruppoOggetto + '"]').attr('checked', false);
					$('input[id^="popupOggChk_' + idGruppoOggetto + '"]').each(
							function() {
									abilitaStatiOggetto(idGruppoOggetto, this.id.split('_')[2]);	
							});

					$(".content_oggetti_" + idGruppoOggetto).removeClass("collapse in").addClass("collapse");
					$(".mostraNascondi_" + idGruppoOggetto).text("Mostra oggetti");
				}
		}
		

		function filtraGruppi() {
			$('#oggetti').find('option').remove();

			var a = $('input[name="popupTipoFiltroOggetti"]:checked').val();
			$('#tipoFiltroOggetti').val(a);
			$('#tipoFiltroOggetti').val().replace(/[^0-9a-z]/gi, '');

			if (a == 'OR') {
				$('#txtTipoFiltroOggetti').html('Procedimenti che contengono almeno un oggetto tra quelli selezionati:');
			} else if (a == 'AND') {
				$('#txtTipoFiltroOggetti').html('Procedimenti che contengono tutti gli oggetti selezionati:');
			}
			var returnfnt = true;

			$('input[name="hGruppi"]').remove();
			$('input[name="hOggetti"]').remove();
			
			var statiOgg = '';
			var statiGruppo = '';

			var erroreOggetti = false;
			$('input[name="popupGruppoChk"]').each(
					function() {
						statiGruppo = "";
						//Per ogni chk selezionato devo cercare tutti i relativi stati selezionati e popolare la list principale e caricare i campi di ricerca hidden
						var idGruppoOggetto = $(this).attr('id').replace('popupGruppoChk_', '');
						statiGruppo = $('#descrGruppoChk_' + idGruppoOggetto).val();
						var find = false;

						//se seleziono un gruppo, devo almeno selezionare un suo stato
						if ($('input[name="popupStatiGruppoChk_' + idGruppoOggetto + '"]:checked').length <= 0 
								&& $('input[id="popupGruppoChk_' + idGruppoOggetto + '"]:checked').length!=0 ) {
							alert("Hai selezionato alcuni gruppi senza specificare nessuno stato.");
							returnfnt = false
							return false;
						}

						if(this.checked && !this.disabled)
						$('input[name="popupStatiGruppoChk_' + idGruppoOggetto + '"]:checked').each(
								
								function(index) {
									
									find = true;
									if (index == 0) {
										statiGruppo = statiGruppo + " (";
									} else {
										statiGruppo = statiGruppo + ", ";
									}
									statiGruppo = statiGruppo + $(this).attr('data-text');
									$(
											'<input type=\"hidden\" name=\"hGruppi\" data-oggetto=\"' + idGruppoOggetto + '\"  value=\"' + idGruppoOggetto + '&&'
													+ $(this).val() + '\" />').insertBefore("#oggetti");
								});
						if (find)
							statiGruppo = statiGruppo + ")";


						if(this.checked && !this.disabled)
							$('#oggetti').append($('<option>', {
								value : '',
								text : statiGruppo,
								disabled : 'disabled'
							}));

					if(!erroreOggetti)
						$('input[name="popupOggChk"]:checked:enabled').each(
								function() {
									stati="";
									//Per ogni chk selezionato devo cercare tutti i relativi stati selezionati e popolare la list principale e caricare i campi di ricerca hidden
									var idS = $(this).attr('id').replace('popupOggChk_', '');
									var idG = idS.split('_')[0];
									var idO = idS.split('_')[1];
									stati = $('#descrChk_' + idO).val();
									var find = false;
									if ($('input[name="popupStatiChk_' + idO + '"]:checked').length <= 0) {
										alert("Hai selezionato alcuni oggetti senza specificare nessun esito");
										erroreOggetti = true;
										returnfnt = false
										return false;
									}

									if(idG == idGruppoOggetto)
									{
										if(this.checked && !this.disabled)
											$('input[name="popupStatiChk_' + idO + '"]:checked').each(
												function(index) {
													find = true;
													if (index == 0) {
														stati = stati + " (";
													} else {
														stati = stati + ", ";
													}
													stati = stati + $(this).attr('data-text');
													$(
															'<input type=\"hidden\" name=\"hOggetti\" data-oggetto=\"' + idO + '\"  value=\"' + idO + '&&'
																	+ $(this).val() + '\" />').insertBefore("#oggetti");
												});
										if (find)
											stati = stati + ")";

										if(this.checked && !this.disabled)
											$('#oggetti').append($('<option>', {
												value : '',
												text : stati,
												disabled : 'disabled'
											}));
									}
								});
								
						
					});

			return returnfnt;
		}

		
		$(".dropdown-menu").on('click', function(event) {
			event.stopPropagation();
		});

		function selectAllCheck(x) {
			if ($('#selectAllCheckbox').is(':checked')) {
				$('.checkboxMisure').prop("checked", true);
				reloadLivelliPopup();
			} else {
				$('.checkboxMisure').prop("checked", false);
				reloadLivelliPopup();
			}
		};

		function reloadLivelliPopup() {
			//creo una variabile "data" che contiene gli idLivello selezionati separati da &
			var data = "";
			var radioSel = $("input[name=optRadio]:checked").val();
			if (radioSel !== undefined && radioSel != null && radioSel != "")
				data += radioSel + "&";
			else
				data += "X&";

			$(".checkboxMisure").each(function(i) {
				if (this.checked) {
					data += this.value.toString() + "&";
				}
			});
			if (data.length == 2) {
				$(".checkboxMisure").each(function(i) {
					data += this.value.toString() + "&";
				});
			}

			$("#all_misura").html(' ');
			$
					.ajax({
						type : "POST",
						url : 'ajaxFiltraPopupLivelli.do',
						dataType : "json",
						data : data,
						async : false,
						success : function(data) {
							//ritorna una mappa <id_livello, codice + " " + descrizione>

							var keys = [];
							var key = '';
							var i = 0;
							for (key in data) {
								keys[keys.length] = key;
							}

							for (i = 0; i < keys.length; i++) {
								var key = keys[i];
								$('#all_misura').append(
										"<option value=\""+data[key].idLivello+"\" \>" + data[key].codiceDescrizione + "</option>");
							}
						}
					});
		}
	</script>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
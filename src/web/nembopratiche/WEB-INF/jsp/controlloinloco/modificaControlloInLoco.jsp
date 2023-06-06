
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<style type="text/css">
span.tab-space {
	padding-left: 0.4em;
}

textarea{
 min-width: 25em;
}
input{
 min-width: 10em;
}
select{
 min-width: 15em;
}
</style>
<body>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-180" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-180" />


	<div class="container-fluid" id="content">
		<m:panel id="panelPosizioni">
			<m:error />

			<div class="container-fluid " id="content" style="margin-bottom: 3em">


				<form:form action="" modelAttribute="" method="post"
					class="form-horizontal" style="margin-top:2em">

		
					<c:choose>
						<c:when test="${procedimentoEstratto !=null}">
							<table summary="dettaglio" style="margin-top: 4px"
								class="myovertable table table-hover table-condensed table-bordered">
								<colgroup>
									<col width="20%">
									<col width="80%">
								</colgroup>
								<tbody>
									<tr>
										<th>Pratica estratta a campione</th>
										<c:choose>
											<c:when test="${procedimentoEstratto.flagEstrazione =='N'}">
												<td>No</td>
											</c:when>
											<c:otherwise>
												<td>Si</td>
											</c:otherwise>
										</c:choose>
									</tr>
									<tr>
										<th>Data estrazione</th>
										<td>${procedimentoEstratto.dataEstrazioneStr}</td>
									</tr>
									<c:if test="${procedimentoEstratto.flagEstrazione !='N'}">
										<tr>
											<th>Modalità di selezione</th>
											<td>${procedimentoEstratto.statoEstrazione}</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<div class="stdMessagePanel">
								<div class="alert alert-warning">Pratica mai sottoposta a
									estrazione a campione</div>
							</div>
						</c:otherwise>
					</c:choose>
			<div class="container-fluid table-responsive"  style="margin-bottom: 3em">

					<table id="tableOperazioni"
						class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh ">
						<thead>
							<tr>
								<th><a href="#" id="aggiungi"
									onclick="aggiungiRiga();return false;"><i
										class="ico24 ico_add" title="Inserisci operazione"></i></a></th>
								<th>Operazione</th>
								<th>Esecuzione controllo in loco</th>
								<th>Data inizio controllo in loco</th>
								<th>Data sopralluogo</th>
								<th>Verbale N</th>
								<th>Funzionario controllore</th>
								<th>Inadempienza tecnica NON<BR> vincolata a controllo di
									<BR>ammissibilità superfici</th>
								<th>Inadempienza tecnica<BR> condizionata all'esito di<BR>
									ammissibilità superfici</th>
							    <th>Motivazione</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${operazioni}" var="o" varStatus="idx">
								<tr class="riga_capo">

									<td><a href="javascript:void(0);"
										id="elimina_${idx.index}"
										onclick="eliminaRiga('${idx.index}');return false;"
										style="text-decoration: none;"><i class="ico24 ico_trash"
											title="Elimina"></i> </a></td>
									<td><m:select id="operazioni_${idx.index}"
											onchange="updateComboLivelliPossibili()"
											list="${livelliPossibili}" name="operazioni_${idx.index}"
											selectedValue="${o.idLivello}"
											textProperty="codiceDescrizione" headerKey="-1"
											valueProperty="idLivello"
											preferRequestValues="${preferValuesProva}"></m:select></td>

									<td><m:radio-list onTable="true"
											id="controlloInLoco_${idx.index}"
											name="controlloInLoco_${idx.index}" list="${radio}"
											inline="false" controlSize="10" valueProperty="value"
											textProperty="label"
											preferRequestValues="${preferValuesProva}"
											selectedValue="${o.flagControllo}" /> <c:if
											test="${o.flagControllo == null}">
											<input style="visibility: hidden" type="radio"
												id="controlloInLoco_${idx.index}"
												name="controlloInLoco_${idx.index}" checked="checked"
												value="X">
										</c:if></td>

									<td style="width: 15em;"><m:textfield type="date"
											id="dataInizio_${idx.index}" name="dataInizio_${idx.index}"
											value="${o.dataInizioControllo}"
											preferRequestValues="${preferValuesProva}"></m:textfield></td>
									<td style="width: 15em;"><m:textfield type="date"
											id="dataSopralluogo_${idx.index}"
											name="dataSopralluogo_${idx.index}"
											value="${o.dataSopralluogo}"
											preferRequestValues="${preferValuesProva}"></m:textfield></td>
									<td><m:textfield id="verbale_${idx.index}"
											name="verbale_${idx.index}" value="${o.numeroVerbale}"
											preferRequestValues="${preferValuesProva}"></m:textfield></td>

									<td style="width: 20em;">
										<m:selectchoice
											id="funzionario_${idx.index}" list="${funzionari}"
											name="funzionario_${idx.index}" textProperty="descrizione"
											valueProperty="id" selectedValue="${o.extIdTecnico}"
											preferRequestValues="${preferValuesProva}"
											listChoice="${ufficiZona}"
											selectedChoice="STESSO_UFFICIO"
										/>
									</td>
											
											
									<td><m:radio-list onTable="true"
											id="inadempVincolata_${idx.index}"
											name="inadempVincolata_${idx.index}" list="${radio}"
											inline="false" controlSize="10" valueProperty="value"
											textProperty="label"
											preferRequestValues="${preferValuesProva}"
											selectedValue="${o.flagInadempVincolata}" />
											<m:textarea  id="noteInadempVincolata_${idx.index}"
											name="noteInadempVincolata_${idx.index}"
											placeholder="Inserire note"
											preferRequestValues="${preferValuesProva}">${o.noteInadempVincolata}</m:textarea>
											</td>	
											
									<td><m:radio-list onTable="true"
											id="inadempCondizionata_${idx.index}"
											name="inadempCondizionata_${idx.index}" list="${radio}"
											inline="false" controlSize="10" valueProperty="value"
											textProperty="label"
											preferRequestValues="${preferValuesProva}"
											selectedValue="${o.flagInadempCondizionata}" />
											<m:textarea id="noteInadempCondizionata_${idx.index}"
											name="noteInadempCondizionata_${idx.index}" 											
											placeholder="Inserire note"
											preferRequestValues="${preferValuesProva}">${o.noteInadempCondizionata}</m:textarea>
											</td>				
																		
									
					<td>
									<m:textarea id="motivazione_${idx.index}"
											name="motivazione_${idx.index}" 											
											placeholder="Inserire motivazione"
											preferRequestValues="${preferValuesProva}">${o.motivazione}</m:textarea>
											</td>	
								</tr>
							</c:forEach>

							<tr id="operazioneNew" style="display: none;">

								<td><a href="#" id="elimina_$$index"
									onclick="eliminaRiga('$$index');return false;"
									style="text-decoration: none;"><i class="ico24 ico_trash"
										title="Elimina"></i> </a></td>
								<td><m:select id="operazioni_$$index"
										list="${livelliPossibili}" name="operazioni_$$index"
										onchange="updateComboLivelliPossibili()"
										textProperty="codiceDescrizione" valueProperty="idLivello"
										headerKey="-1" preferRequestValues="${preferValuesProva}"></m:select></td>

								<td><m:radio-list onTable="true"
										id="controlloInLoco_$$index" name="controlloInLoco_$$index"
										list="${radio}" inline="false" controlSize="10"
										valueProperty="value" textProperty="label"
										preferRequestValues="${preferValuesProva}" /> <input
									style="visibility: hidden" type="radio"
									id="controlloInLoco_$$index" name="controlloInLoco_$$index"
									checked="checked" value="X"></td>

								<td style="width: 15em;"><m:textfield type="date"
										id="dataInizio_$$index" name="dataInizio_$$index" value=""
										preferRequestValues="${preferValuesProva}"></m:textfield></td>
								<td style="width: 15em;"><m:textfield type="date"
										id="dataSopralluogo_$$index" name="dataSopralluogo_$$index"
										value="" preferRequestValues="${preferValuesProva}"></m:textfield></td>
								<td><m:textfield id="verbale_$$index"
										name="verbale_$$index" value=""
										preferRequestValues="${preferValuesProva}"></m:textfield></td>

								<td><m:select id="funzionario_$$index" list="${funzionari}"
										name="funzionario_$$index" textProperty="descrizione"
										valueProperty="id" preferRequestValues="${preferValuesProva}"></m:select></td>

								<td><m:radio-list onTable="true"
											id="inadempVincolata_$$index"
											name="inadempVincolata_$$index" list="${radio}"
											inline="false" controlSize="10" valueProperty="value"
											textProperty="label"
											preferRequestValues="${preferValuesProva}"
											selectedValue="${o.flagInadempVincolata}" />
											<input
											style="visibility: hidden" type="radio"
											id="inadempVincolata_" name="inadempVincolata_$$index"
											checked="checked" value="X">
											<m:textarea id="noteInadempVincolata_$$index"
											name="noteInadempVincolata_$$index"
											placeholder=""
											preferRequestValues="${preferValuesProva}"></m:textarea>
											</td>	
											
									<td><m:radio-list onTable="true"
											id="inadempCondizionata_$$index"
											name="inadempCondizionata_$$index" list="${radio}"
											inline="false" controlSize="10" valueProperty="value"
											textProperty="label"
											preferRequestValues="${preferValuesProva}"
											 />
											<input
											style="visibility: hidden" type="radio"
											id="inadempCondizionata_$$index" name="inadempCondizionata_$$index"
											checked="checked" value="X">
											<m:textarea id="noteInadempCondizionata_$$index"
											name="noteInadempCondizionata_$$index" 					
											placeholder=""
											preferRequestValues="${preferValuesProva}"></m:textarea>
											</td>
											<td>
									<m:textarea id="motivazione_$$index"
											name="motivazione_$$index" 											
											placeholder=""
											preferRequestValues="${preferValuesProva}"></m:textarea>
											</td>					
							</tr>
						</tbody>
					</table>
				</div>

					<input type="hidden" id="numRighe" value="" name="numeroRighe">
					<p:abilitazione-azione codiceQuadro="CNTLO" codiceAzione="MODIFICA">
						<div class="col-sm-12" style="padding-bottom: 2em;">
							<button type="button"
								onclick="forwardToPage('../cunembo179/index.do');"
								class="btn btn-default">indietro</button>
							<button type="submit" name="conferma" id="conferma"
								class="btn btn-primary pull-right">conferma</button>
						</div>
					</p:abilitazione-azione>
				</form:form>
			</div>
		</m:panel>
	</div>


	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
		var maxVal = 0;
		$(document)
				.ready(
						function() {

							maxVal = parseInt($('.riga_capo').length) - 1;

							document.getElementById("numRighe").value = maxVal + 1;

							var i = 0;
							if (parseInt(document.getElementById("numRighe").value) >= "${numeroMassimoLivelli}")
								$("#aggiungi").hide();


							disableInadempienze();
							
							updateComboLivelliPossibili();


						});

		function disableInadempienze() {

			$( "input[name^='inademp']" ).removeAttr('disabled');
			$( "textarea" ).removeAttr('disabled');
			$( "input[name^='motivazione']" ).attr('disabled','enabled');

			var operazioni = $( "[name^='operazioni_']" );
			var i =0;
			for (i=0;i<operazioni.length;i++)
					if(operazioni[i].value == 155){
						var res = operazioni[i].name.split("_");
						var j = res[1];
						var s = "inadempVincolata_"+j;
						$( "[name^="+s+"]" ).attr('disabled','disabled');
						var s = "noteInadempVincolata_"+j;
						$( "[name^="+s+"]" ).attr('disabled','disabled');
						var s = "inadempCondizionata_"+j;
						$( "[name^="+s+"]" ).attr('disabled','disabled');
						var s = "noteInadempCondizionata_"+j;
						$( "[name^="+s+"]" ).attr('disabled','disabled');
					}
		}


		
		function aggiungiRiga() {
			//distruggo tutti i datepicker (altrimenti ho duplicati e non funziona)
			$(".datepicker").datepicker("destroy");

			maxVal = parseInt(maxVal) + 1;
			var newLine = $('#operazioneNew').html().replace(/\\$\\$index/g,
					maxVal);
			$("#tableOperazioni tbody").append(
					'<tr class="riga_capo">' + newLine + '</tr>');

			//ricreo tutti i datepicker 
			$(".datepicker").datepicker();
			document.getElementById("numRighe").value = parseInt(document
					.getElementById("numRighe").value) + 1;

			//se ci sono già tutti i livelli possibili, nasconto il tasto +
			if (parseInt(document.getElementById("numRighe").value) >= "${numeroMassimoLivelli}")
				$("#aggiungi").hide();

			//aggiorno le combo
			updateComboLivelliPossibili();

		}

		function eliminaRiga(idRiga) {
			$(".datepicker").datepicker("destroy");

			if (parseInt(document.getElementById("numRighe").value) >= 0) {
				$('#operazioni_' + idRiga).closest('tr').remove();
				document.getElementById("numRighe").value = parseInt(document
						.getElementById("numRighe").value) - 1;

				if (document.getElementById("numRighe").value < "${numeroMassimoLivelli}")
					$("#aggiungi").show();

				//ogni volta che elimino una riga, riduco gli indici degli id e il name delle righe successive
				var ops = [];
				ops = document.querySelectorAll('*[id^="operazioni_"]');
				var i = 0;
				for (i = 0; i < ops.length; i++) {
					var myvar = ops[i].id;
					myvar = myvar.split('_');

					if (myvar[1] >= idRiga) {
						var x = myvar[1] - 1;
						var c = document.getElementById('elimina_' + myvar[1]);
						$('#elimina_' + myvar[1])
								.replaceWith(
										'<a href="javascript:void(0);" id="elimina_'
												+ x
												+ '" onclick="eliminaRiga('
												+ x
												+ ');return false;" style="text-decoration: none;"><i class="ico24 ico_trash" title="Elimina"></i> </a>');

						$('#operazioni_' + myvar[1]).attr("name",
								'operazioni_' + x);
						$('#operazioni_' + myvar[1]).attr("id",
								'operazioni_' + x);

						$('#controlloInLoco_' + myvar[1]).attr("name",
								'controlloInLoco_' + x);
						$('#controlloInLoco_' + myvar[1]).attr("id",
								'controlloInLoco_' + x);

						$('#controlloInLoco_' + myvar[1] + "_1").attr("name",
								'controlloInLoco_' + x);
						$('#controlloInLoco_' + myvar[1] + "_2").attr("name",
								'controlloInLoco_' + x);
						$('#controlloInLoco_' + myvar[1] + "_1").attr("id",
								'controlloInLoco_' + x + "_1");
						$('#controlloInLoco_' + myvar[1] + "_2").attr("id",
								'controlloInLoco_' + x + "_2");

						$('#dataInizio_' + myvar[1]).attr("name",
								'dataInizio_' + x);
						$('#dataInizio_' + myvar[1]).attr("id",
								'dataInizio_' + x);

						$('#dataSopralluogo_' + myvar[1]).attr("name",
								'dataSopralluogo_' + x);
						$('#dataSopralluogo_' + myvar[1]).attr("id",
								'dataSopralluogo_' + x);

						$('#verbale_' + myvar[1]).attr("name", 'verbale_' + x);
						$('#verbale_' + myvar[1]).attr("id", 'verbale_' + x);

						$('#funzionario_' + myvar[1]).attr("name",
								'funzionario_' + x);
						$('#funzionario_' + myvar[1]).attr("id",
								'funzionario_' + x);

						$('#inadempVincolata_' + myvar[1]).attr("name",
								'inadempVincolata_' + x);
						$('#inadempVincolata_' + myvar[1]).attr("id",
								'inadempVincolata_' + x);

						$('#noteInadempVincolata_' + myvar[1]).attr("name",
								'noteInadempVincolata_' + x);
						$('#noteInadempVincolata_' + myvar[1]).attr("id",
								'noteInadempVincolata_' + x);

						$('#inadempVincolata_' + myvar[1] + "_1").attr("name",
								'inadempVincolata_' + x);
						$('#inadempVincolata_' + myvar[1] + "_2").attr("name",
								'inadempVincolata_' + x);
						$('#inadempVincolata_' + myvar[1] + "_1").attr("id",
								'inadempVincolata_' + x + "_1");
						$('#inadempVincolata_' + myvar[1] + "_2").attr("id",
								'inadempVincolata_' + x + "_2");
						



						
						$('#inadempCondizionata_' + myvar[1]).attr("name",
								'inadempCondizionata_' + x);
						$('#inadempCondizionata_' + myvar[1]).attr("id",
								'inadempCondizionata_' + x);

						$('#noteInadempCondizionata_' + myvar[1]).attr("name",
								'noteInadempCondizionata_' + x);
						$('#noteInadempCondizionata_' + myvar[1]).attr("id",
								'noteInadempCondizionata_' + x);


						$('#inadempCondizionata_' + myvar[1] + "_1").attr("name",
								'inadempCondizionata_' + x);
						$('#inadempCondizionata_' + myvar[1] + "_2").attr("name",
								'inadempCondizionata_' + x);
						$('#inadempCondizionata_' + myvar[1] + "_1").attr("id",
								'inadempCondizionata_' + x + "_1");
						$('#inadempCondizionata_' + myvar[1] + "_2").attr("id",
								'inadempCondizionata_' + x + "_2");

						
						$('#motivazione_' + myvar[1]).attr("name",
								'motivazione_' + x);
						$('#motivazione_' + myvar[1]).attr("id",
								'motivazione_' + x);
						
					}
					
				}
			}
			//riduco il contatore di righe presenti
			maxVal = parseInt(maxVal) - 1;
			//aggiorno le combo
			updateComboLivelliPossibili();

			//ricreo tutti i datepicker 
			$(".datepicker").datepicker();
		}

		function updateComboLivelliPossibili() {
			var livelli = [];
			livelli = $('[id^="operazioni_"]');

			var options = $("option"); //get all "option" tag

			var i = 0;
			j = 0;
			for (j = 0; j < options.length; j++)
				if ( $(options[j]).parent().is( "span" ) )
					$(options[j]).unwrap("<span>").show();

			//nascondo le "option" che sono selezionate in altre righe
			for (i = 0; i < livelli.length; i++)
				if (livelli[i].id.indexOf("$$index") == -1)
					for (j = 0; j < options.length; j++)
						if (options[j].text.indexOf("selezionare") == -1
								&& options[j].id.indexOf("$$index") == -1) {
							var l = livelli[i].value;
							var o = options[j].value;
							if (l == o && livelli[i].id != options[j].id)
								if(!$(options[j]).is(':selected'))
									$(options[j]).wrap('<span>').hide();
							
						}
			disableInadempienze();

			
		}
	</script>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />
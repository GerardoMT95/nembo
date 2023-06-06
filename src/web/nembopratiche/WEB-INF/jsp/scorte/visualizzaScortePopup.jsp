<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoScorte">
		<br/>
		<h4>Selezionare la scorta per la quale si intende segnalare un danno:</h4>
		<span id="errorBox"></span>
		<br/>
		
		<form name="formVisualizzaScorteMagazzino" id="formVisualizzaScorteMagazzino" action="">
				<c:set var ="tableName"  value ="tableElencoScortePopup"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->				
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
					data-toggle="table" 
					data-url="../cunembo297l/get_elenco_scorte.json"
					>
					<thead>
			        	<tr>
							<th data-field="idScortaMagazzino" data-formatter="selezionaScortaFormatter"></th>
			        		<th data-field="progressivo" data-sortable="true">Progressivo</th>
			        		<th data-field="descrizioneScorta" data-sortable="true">Tipologia scorta</th>
			        		<th data-formatter="quantitaFormatter" data-sortable="true">Quantità</th>
			        		<th data-field="descrizione" data-sortable="true">Descrizione</th>
			        	</tr>
				    </thead>
		    		<tbody></tbody>
	    		</table>
	    		<button type="button" data-dismiss="modal" class="btn btn-default">Annulla</button>
	    		<a id="btnAvanti" class="btn btn-primary pull-right" onclick="selezionaScorta()">Avanti</a>
	    		</form>
	    		<br/>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<script src="/nembopratiche/js/nembotableformatter.js"></script>

	<script type="text/javascript">
	
		function quantitaFormatter($value, row, index)
		{
			var risultato = row['quantita'] + '-' + row['descUnitaMisura'];
			return risultato;
		}
		</script>
		
		<script type="text/javascript">		
		function selezionaScortaFormatter($value, row, index)
		{
			var risultato;
			risultato = '<input type="radio" name="idScortaMagazzinoPopup" value="'+ $value + '"></input>';
			return risultato;
		}
		
		function selezionaScorta()
		{
			var elem = $('input[name=idScortaMagazzinoPopup]:checked', '#formVisualizzaScorteMagazzino').val()
	          	$.ajax(
		        {
		            type : "POST",
		            url : '../cunembo298i/inserisci_danno_dettagli.do',
		            data : $('#formVisualizzaScorteMagazzino').serialize(),
		            dataType : "html",
		            async : false,
		            success : function(html)
		            {
		              $('#errorBoxDiv').remove();
		              $('#errorBox').html(html);
		            },
		            error : function(jqXHR, html, errorThrown)
		            {
		              writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di aggiornamento. Se il problema persistesse si prega di contattare l'assistenza tecnica");
		            }
		        });
		}
		</script>
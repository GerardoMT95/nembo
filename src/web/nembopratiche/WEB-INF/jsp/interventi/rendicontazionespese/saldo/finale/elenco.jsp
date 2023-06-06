<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<div class="container-fluid" id="content">
		<m:panel id="panelInterventi">
			<form name="elencoForm" id="elencoForm" method="post" action="">
				<c:if test="${msgIVA!=null}">
					<strong><c:out value="${msgIVA}" /></strong>
				</c:if>
				<table id='tblInterventi' summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh show-totali" data-toggle="table"
					data-url="json/elenco.json" data-undefined-text='' data-show-columns="true">
					<thead>
						<tr>
							<th data-totale="false" style="max-width: 72px" data-switchable="false" data-field="idIntervento" data-formatter="iconeFormatter"><p:abilitazione-azione
									codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
									<c:if test="${modificaMultipla}">
										<a href="../cunembo${cuNumber}m/modifica.do" onclick="return modificaMultipla()" class="ico24 ico_modify"></a>
									</c:if>
								</p:abilitazione-azione> <%-- Rimosso rendicontazione multipla temporaneamente dato che più bootrstaptableFilter nella stessa pagina presentano problemi --%>
								<%--                        
				        <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="RENDICONTA_DOC_SPESA">
				        <a href="../cunembo${cuNumber}r/rendicontazione_documenti.do" class="ico24 ico_euro" onclick="return rendicontazioneMultipla()"></a>
				        </p:abilitazione-azione>
 --%></th>
							<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
								<c:if test="${modificaMultipla}">
									<th data-switchable="false" data-field="idIntervento" data-totale="false" data-formatter="selezioneMultiplaCheckboxFormatter"><input
										type="checkbox" name="chkSelectAll" id="chkSelectAll" onclick="selectAll()" /></th>
								</c:if>
							</p:abilitazione-azione>
							<th data-field="progressivo" data-formatter="progressivoFormatter" data-totale="false">Progr.</th>
							<th data-field="descTipoIntervento" data-visible="false" data-totale="false">Tipo<br />intervento
							</th>
							<th data-field="descIntervento" data-totale="false">Intervento</th>
							<th data-field="ulterioriInformazioni" data-visible="false" data-totale="false">Ulteriori<br />informazioni
							</th>
							<th data-field="spesaAmmessa" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder">Spesa<br />ammessa
							</th>
							<th data-field="percentualeContributo" data-totale="false" data-visible="false" data-formatter="numberFormatter2" data-adder="importoAdder">Percentuale</th>
							<th data-field="importoContributo" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder">Contributo</th>

							<th data-field="speseSostenute" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="false">Spese gi&agrave;<br />rendicontate
							</th>
							<th data-field="speseAccertate" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="false">Spese gi&agrave;<br />riconosciute
							</th>
							<th data-field="importoSpesa" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder">Spesa rendicontata<br />attuale
							</th>
							<th data-field="importoNonRiconosciutoPrec" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="false">Importo
								non<br />riconosciuto<br />sanzionabile
							</th>
							<th data-field="importoDisponibilePrec" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="false">Importo
								non<br />riconosciuto<br />non sanzionabile
							</th>
							<th data-field="contributoRichiesto" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder">Contributo<br />richiesto
							</th>
							<th data-field="flagInterventoCompletato" data-totale="false" data-formatter="flagInterventoCompletatoFormatter">Intervento<br />completato
							</th>
							<th data-field="note" data-totale="false">Note</th>
						</tr>
					</thead>
				</table>
				<br />
				<m:panel id="totaliContributo">
					<table class="pull-right">
						<tr>
							<th>Potenziale contributo erogabile</th>
							<td id="potenzialeContributoErogabile" style="padding-left: 20px" class="numero"><fmt:formatNumber pattern="###,##0.00"
									value="${potenzialeErogabile}" /> &euro;</td>
						</tr>
						<tr>
							<th>Totale Sanzioni/Riduzioni precedenti</th>
							<td id="totaleSanzioni" style="padding-left: 20px" class="numero"><fmt:formatNumber pattern="###,##0.00" value="${totaliSanzioni}" /> &euro;</td>
						</tr>
					</table>
				</m:panel>
				<br />
			</form>
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
	<script type="text/javascript">
    function importoAdder(rows, field)
    {
      var __sum = 0;
      $(rows).each(function(index, currentRow)
      {
        var value = currentRow[field];
        if (value != null && !isNaN(value) && value.length != 0)
        {
          __sum += parseFloat(value);
        }
      });
      return __sum;
    }
    function flagInterventoCompletatoFormatter($value, row, index)
    {
      try
      {
        var checked = "";
        if ($value == 'S')
        {
          checked = ' checked="checked" ';
        }

        return '<div class="center"><input '+ checked+' disabled="disabled"'
          + ' data-toggle="bs-toggle" type="checkbox" value="S"/></div>';
      }
      catch (e)
      {

      }
    }
    $('#tblInterventi').on('load-success.bs.table', function()
    {
      $(function()
      {
        $('input[data-toggle="bs-toggle"]').bootstrapToggle();
      });
    });
    $('#tblInterventi').on('column-switch.bs.table', function()
    {
      $(function()
      {
        $('input[data-toggle="bs-toggle"]').bootstrapToggle()
      });
    });
    function progressivoFormatter($value, row, index)
    {
      if ($value == null || $value == '')
      {
        $value = '&nbsp;&nbsp;';
      }
      $value = '<span class="badge" style="background-color:white;color:black;border:1px solid black">' + $value + '</span>';
      return '<div style="text-align:center">' + $value + '</div>';
    }
    function iconeFormatter($value, row, index)
    {
      var html = '';
      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
       */
      if (row['usaDocumentiSpesa'] != 'S')
      {
        html += '<a href="../cunembo${cuNumber}m/modifica_singola_'+$value+'.do" class="ico24 ico_modify"></a>';
      }
      /*
      </p:abilitazione-azione>
       */
      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="RENDICONTA_DOC_SPESA">
       */
      if (row['usaDocumentiSpesa'] == 'S')
      {
        html += '<a  href="../cunembo${cuNumber}r/rendicontazione_documenti_singola_' + $value + '.do" class="ico24 ico_modify_euro_';
        if (row['hasDocumentoSpesa'] == 'S')
        {
          html += 'blue"></a>';
        }
        else
        {
          html += 'red"></a>';
        }
      }
      /*
      </p:abilitazione-azione>
       */
      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="VISUALIZZA_DOC_SPESA">
       */
      if (row['usaDocumentiSpesa'] == 'S' && row['hasDocumentoSpesa'] == 'S')
      {
        html += '<a href="../cunembo${cuNumber}v/visualizza_'+$value+'.do" class="ico24 ico_magnify"></a>';
      }
      /*
      </p:abilitazione-azione>
       */

       /*
       <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="LOCALIZZA">
        */
        if (row['idTipoLocalizzazione']==8)
        {
          html += '<a href="../cunembo255b/modifica_'+$value+'.do" class="ico24 ico_map_point"></a>';
        }
       /*
       </p:abilitazione-azione>
        */
       /*
       <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="DETTAGLIO">
        */
        if (row['idTipoLocalizzazione']==8)
        {
          html += '<a href="../cunembo255db/dettaglio_'+$value+'.do" class="ico24 ico_magnify"></a>';
        }
       /*
       </p:abilitazione-azione>
        */
        
      if (html == '')
      {
        html = '&nbsp;&nbsp;';
      }
      return '<div style="text-align:center">' + html + '</div>';
    }

    function selezioneMultiplaCheckboxFormatter($value, row, index)
    {
      var html = '<input type="checkbox" name="idIntervento" value="' + $value + '" ';
      if (row['usaDocumentiSpesa'] == 'S')
      {
        html += ' disabled="disabled"';
      }
      html += ' />';
      return html;
    }

    function selectAll()
    {
      $("input[name='idIntervento']").prop("checked", $('#chkSelectAll').prop('checked'));
    }
    $(function()
    {
      $('[data-toggle="popover"]').popover()
    });

    function noDocumentiSpesa()
    {
      showMessageBox('Errore', 'Non ci sono documenti di spesa associati a questo intervento. Impossibile modificare la rendicontazione.');
      return false;
    }
  </script>
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
		<script type="text/javascript">
      function modificaMultipla()
      {
        if ($("input[name='idIntervento']:checked").length > 0)
        {
          submitFormTo($('#elencoForm'), '../cunembo${cuNumber}m/modifica_multipla.do');
        }
        else
        {
          alert("Selezionare almeno un intervento");
        }
        return false;
      }
    <%--
      function rendicontazioneMultipla()
      {
        if ($("input[name='idIntervento']:checked").length > 0)
        {
          submitFormTo($('#elencoForm'), "../cunembo${cuNumber}r/rendicontazione_documenti.do");
        }
        else
        {
          alert("Selezionare almeno un intervento");
        }
        return false;
      }
      --%>
      
    </script>
	</p:abilitazione-azione>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
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
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-163-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-163-L" />
	<div class="container-fluid" id="content">
		<m:panel id="panelInterventi">
			
			<table class="table table-hover table-bordered tableBlueTh">
				<tbody>
					<tr>
						<th class="col-sm-6">Zona Altimetrica</th> 
						<td class="col-sm-6">${zonaAltimetrica.descZonaAltimetrica}</td>
					</tr>
					<tr>
						<th>Percentuale Contributo per Zona altimetrica</th>
						<td>${zonaAltimetrica.percContrZonaAltimetricaFormatted}</td>
					
					</tr>
				</tbody>
			</table>
		
			<form name="elencoForm" id="elencoForm" method="post" action="">

				<table id='tblInterventi' summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
					data-url="json/elenco.json" data-undefined-text='' data-row-style='rowStyleFormatter' data-show-columns="true">
					<thead>
						<tr>
							<th rowspan="2" class="vcenter" data-totale="false" data-width="50" data-switchable="false" data-field="icone" data-formatter="iconeFormatter"><p:abilitazione-azione
									codiceQuadro="QECON" codiceAzione="MODIFICA" visible="${i.flagTipoOperazione!='D'}">
									<a href="../cunembo163m/modifica.do" onclick="return modificaMultipla()" class="ico24 ico_modify"></a>
									<a href="../cunembo163m/conferma_modifica_percentuale_multipla.do" onclick="return modificaPercentualeMultipla()" class="ico24 ico_percent"></a>
								</p:abilitazione-azione></th>
							<p:abilitazione-azione codiceQuadro="QECON" codiceAzione="MODIFICA">
								<th rowspan="2" class="vcenter center" data-switchable="false" data-field="idIntervento" data-totale="false"
									data-formatter="selezioneMultiplaCheckboxFormatter" data-width="50"><input type="checkbox" name="chkSelectAll" id="chkSelectAll" onclick="selectAll()" /></th>
							</p:abilitazione-azione>
							<th rowspan="2" class="vcenter" data-field="progressivo" data-formatter="progressivoFormatter" data-totale="false" data-width="72px">Progr.</th>
							<th rowspan="2" class="vcenter" data-field="descIntervento" data-totale="false">Intervento</th>
							<th rowspan="2" class="vcenter" data-field="ulterioriInformazioni" data-visible="false" data-totale="false">Ulteriori informazioni</th>
							<th colspan="4" data-totale="false" class="center">Importo</th>
						</tr>
						<tr>
							<th data-field="importoInvestimento" data-formatter="euroFormatter" data-switchable="false" data-totale="true" data-adder="importoAdder" data-width="136px">Investimento</th>
							<th data-field="importoAmmesso" data-formatter="euroFormatter" data-switchable="false" data-totale="true" data-adder="importoAdder" data-width="136px">Ammesso</th>
							<th data-field="percentualeContributo" data-formatter="percentFormatter" data-switchable="false" data-totale="false" data-width="136px">Percentuale</th>
							<th data-field="importoContributo" data-formatter="euroFormatter" data-switchable="false" data-totale="true" data-adder="importoAdder" data-width="136px">Contributo</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<br />
			</form>
			<blockquote>
				Legenda<br /> <span class="badge" style="margin-left: 12px; margin-top: 10px; background-color: green; border: 1px solid black">&nbsp;&nbsp;</span> Nuovo
				intervento<br /> <span class="badge" style="margin-left: 12px; background-color: orange; border: 1px solid black">&nbsp;&nbsp;</span> Intervento modificato<br />
				<span class="badge"
					style="margin-left: 12px; background-color: white; color: black; border: 1px solid black">&nbsp;&nbsp;</span> Intervento consolidato<br />
			</blockquote>
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script src="../js/dual-list-box.js"></script>
	<script type="text/javascript">

	 function addTotali()
	  {
	   //$('#tblInterventi').
	   //    $('#tblInterventi').each($('#tblInterventi')
	   var countVisible=0;
	   var column=
	   $.each($('#tblInterventi').bootstrapTable("getOptions").columns[0], function(index, column)
	   {
	     if (column.visible)
       {
       countVisible++;
       }
	   });
	   --countVisible; // Tolgo la colonna multipla Importo
	   var rows=$('#tblInterventi').bootstrapTable("getData");
     var html='         <tr><th colspan="'+countVisible+'" class="numero">Totale</th>'+
       '<th class="numero">'+importoAdder(rows,'importoInvestimento')+' &euro;</th>'+
       '<th class="numero">'+importoAdder(rows,'importoAmmesso')+' &euro;</th>'+
       '<th>&nbsp;</th>'+
       '<th class="numero">'+importoAdder(rows,'importoContributo')+' &euro;</th>'+
       '</tr>';
     $('#tblInterventi').append($(html));
   }
	   
	$('#tblInterventi').on('load-success.bs.table', addTotali);
	$('#tblInterventi').on('column-switch.bs.table', addTotali);
     
    function importoAdder(rows, field)
    {
      var __sum = 0;
      $(rows).each(function(index, currentRow)
      {
        if (currentRow['flagTipoOperazione'] != 'D')
        {
          var value = Number(currentRow[field]);
          if (!isNaN(value) && value.length != 0)
          {
            __sum += parseFloat(value);
          }
        }
      });
      return __sum.formatCurrency();
    }

    function rowStyleFormatter(row, index)
    {
      if (row['flagTipoOperazione'] == 'D')
      {
        var retValue =
        {
          classes : 'danger'
        };
        return retValue;
      }
      return {};
    }
    
    function progressivoFormatter($value, row, index)
    {
      if ($value == null || $value == '')
      {
        $value = '&nbsp;&nbsp;';
      }
      switch (row['flagTipoOperazione'])
      {
        case 'I':
          $value = '<div style="text-align:center"><span class="badge" style="background-color:green;border:1px solid black">' + $value + '</span>';
          break;
        case 'U':
          $value = '<span class="badge" style="background-color:orange;border:1px solid black">' + $value + '</span>';
          break;
        case 'D':
          $value = '<span class="badge" style="border:1px solid black">' + $value + '</span>';
          break;
        default:
          $value = '<span class="badge" style="background-color:white;color:black;border:1px solid black">' + $value + '</span>';
          break;
      }
      return '<div style="text-align:center">' + $value + '</div>';
    }
    function selezioneMultiplaCheckboxFormatter($value, row, index)
    {
      return row['flagTipoOperazione'] != 'D' ? '<input type="checkbox" name="idIntervento" value="'+$value+'" />' : '';
    }
    var ICONE = [];
    ICONE['MO'] = '<a href="../cunembo163m/modifica_singola_:ID_INTERVENTO.do" class="ico24 ico_modify"></a>';
    function iconeFormatter($value, row, index)
    {
      $html = '';
      if ($value)
      {
        while ($value.length > 1)
        {
          var icona = $value.substr(0, 2);
          $value = $value.substr(2);
          $htmlIcona = ICONE[icona];
          if ($htmlIcona)
          {
            $html += $htmlIcona.replace(/:ID_INTERVENTO/g, row['idIntervento']);
          }
        }
      }
      return $html;
    }
    function misurazioniFormatter($value, row, index)
    {
      var $html = '';
      for ($i = 0; $i < $value.length; ++$i)
      {
        if ($i > 0)
        {
          $html += "<br/>"
        }
        var descMisurazione = $value[$i]['descMisurazione'];
        if (descMisurazione != null)
        {
          $html += descMisurazione;
        }
        var codiceUnitaMisura = $value[$i]['codiceUnitaMisura'];
        if (codiceUnitaMisura != 'NO_MISURA')
        {
          var valore = $value[$i]['valore'];
          $number = Number(valore);
          if (!isNaN($number))
          {
            $html += "&nbsp;" + $number + "&nbsp;";
          }
          if (codiceUnitaMisura != null)
          {
            $html += codiceUnitaMisura;
          }
        }
      }
      return $html;
    }
    function selectAll()
    {
      $("input[name='idIntervento']").prop("checked", $('#chkSelectAll').prop('checked'));
    }
    $(function()
    {
      $('[data-toggle="popover"]').popover()
    });
  </script>
	<p:abilitazione-azione codiceQuadro="QECON" codiceAzione="MODIFICA">
		<script type="text/javascript">
      function modificaMultipla()
      {
        if ($("input[name='idIntervento']:checked").length > 0)
        {
          submitFormTo($('#elencoForm'), '../cunembo163m/modifica_multipla.do');
        }
        else
        {
          alert("Selezionare almeno un intervento");
        }
        return false;
      }
      function modificaPercentualeMultipla()
      {
        if ($("input[name='idIntervento']:checked").length > 0)
        {
          openPageInPopup("../cunembo163m/conferma_modifica_percentuale_multipla.do", "dlgModificaPercentualeMultipla", "Modifica percentuale", 'modal-lg', false,  $('#elencoForm').serialize());
        }
        else
        {
          alert("Selezionare almeno un intervento");
        }
        return false;
      }
    </script>
	</p:abilitazione-azione>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
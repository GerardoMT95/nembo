<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<li class="active">Liste di liquidazione</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<div class="container-fluid" id="content">
		<div id="filter-bar" style="position: relative; top: 46px"></div>
		<table id='listeLiquidazione' summary="Liste di liquidazione" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
			data-url="json/elenco.json" data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top" data-show-columns="true"
			data-undefined-text='' data-row-style='rowStyleFormatter'>
			<thead>
				<tr>
					<th class="vcenter center" data-field="idListaLiquidazione" data-formatter="iconeFormatter" data-switchable="false"><p:abilitazione-cdu
							codiceCdu="<%=NemboConstants.USECASE.LISTE_LIQUIDAZIONE.CREA_NUOVA%>">
							<a href="../cunembo226/elenco_bandi.do" class="ico24 ico_add_white"></a>
						</p:abilitazione-cdu></th>
					<th class="vcenter center" data-sortable="true" data-field="numListaStr">N.<br />lista
					</th>
					<th class="vcenter center" data-class="center" data-field="note" data-visible="false">Note</th>
					<th class="vcenter center" data-class="center" data-sortable="true" data-field="denominazioneBando">Bando</th>
					<th class="vcenter center" data-sortable="true" data-field="annoCampagnaBando">Anno<br />di<br />rif.
					</th>
					<th class="vcenter center" data-sortable="true" data-field="dataCreazione" data-sorter="dateSorterddmmyyyyHHmmss">Data<br />creazione
					</th>
					<th class="vcenter center" data-sortable="true" data-field="descStatoLista">Stato<br />lista
					</th>
					<th class="vcenter center" data-field="elencoMisureHtml" data-visible="false">Misura
					</th>
					<th class="vcenter center" data-class="center" data-field="elencoCodiciLivelliMisureHtml" data-visible="true">Operazione
					</th>
					<th class="vcenter center" data-field="numPagamenti">N.<br />pagamenti
					</th>
					<th class="vcenter center" data-field="importo" data-formatter="euroFormatterConcat">Importo</th>
					<th class="vcenter center" data-sortable="true" data-field="descTipoImporto">Tipo<br />importo
					</th>
					<th class="vcenter center" data-sortable="true" data-field="dataApprovazione" data-sorter="dateSorterddmmyyyyHHmmss">Data<br />approvazione
					</th>
					<th class="vcenter center" data-sortable="true" data-field="descTecnicoLiquidatore">Tecnico<br />liquidatore
					</th>
					<th class="vcenter center" data-sortable="true" data-field="flagInvioSigop" data-formatter="siNoFormatter">Inviata<br />ad<br />ARPEA
					</th>
					<th class="vcenter center" data-sortable="true" data-field="dataInvioSigop" data-sorter="dateSorterddmmyyyyHHmmss">Data<br />invio
					</th> 
					 
					<th class="vcenter center" data-class="vcenter" data-field="numeroProtocollo">Numero<br />Protocollo
					</th>
					<th class="vcenter center" data-sortable="true" data-field="dataProtocollo" data-sorter="dateSorterddmmyyyy">Data<br />protocollo
					</th>
					<th class="vcenter center" data-switchable="false" data-visible="false" data-field="extIdAmmCompetenza">extIdAmmCompetenza</th>
					<th class="vcenter center" data-sortable="true" data-field="organismoDelegato">Organismo<br />delegato
					</th>
					<th class="vcenter center" data-class="vcenter" data-field="descOrganismoDelegato" data-visible="false">Descrizione<br />O.D.
					</th>
					<th class="vcenter center" data-class="center" data-field="descUtenteAggiornamento" data-visible="false">Utente<br />aggiornamento
					</th>
					<th class="vcenter center" data-class="center" data-field="elencoCodiciLivelli" data-visible="false" data-switchable ="false">
					</th>
					<th class="vcenter center" data-class="center" data-field="elencoCodiciLivelliMisure" data-visible="false" data-switchable ="false">
					</th>
				</tr>
			</thead>
		</table>
		<br />
	</div>
	<div id="filtroJSON" style="display:none">${filtroIniziale}</div>
	<input type="hidden" id="numberOfDateFilter" value="-1">
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>

	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
	
	    
	function elimina(idListaLiquidazione, flagStatoLista) {
		if(flagStatoLista == "A")
			showMessageBox( "Attenzione!", "La lista in questo stato non può essere eliminata.", 'modal-large');
		else
			return openPageInPopup('../cunembo229/confermaElimina_'+idListaLiquidazione+'.do', 'dlgEliminaLista',
				'Elimina', '', false);
	}
	
    function iconeFormatter($value, row, index)
    {
      var html = '<a href="#" onclick="visualizzaStampa('+$value+')"><i class="ico24 ico_print"></i></a>';
      html += '<a href="#" onclick="return openPageInPopup(\'json/elenco_pratiche_nuova_lista_'+$value+'.do\', \'popup_elenco_pratiche_nuova_lista\', \'Elenco pratiche\', \'modal-lg\')"><i class="ico24 ico_magnify"></i></a>';
      if (row['flagStatoLista'] == 'B' || row['flagStatoLista'] == 'D')
      {
        var idStatoStampa = Number(row['idStatoStampa']);
        /*<p:abilitazione-cdu codiceCdu="<%=NemboConstants.USECASE.LISTE_LIQUIDAZIONE.APPROVA%>">*/
          html += '<a href="../cunembo228/approva_'+$value+'.do"><i class="ico24 ico_ok"></i></a>';
        /*</p:abilitazione-cdu>*/
        /*<p:abilitazione-cdu codiceCdu="<%=NemboConstants.USECASE.LISTE_LIQUIDAZIONE.ELIMINA%>">*/
        html += '<a href="#" onclick="elimina('+$value+',\''+row.flagStatoLista+'\')"><i class="ico24 ico_trash"></i></a>';
        /*</p:abilitazione-cdu>*/
      }
      return html;
    }

	function initFilter(){

		   $('#filter-bar').bootstrapTableFilter(
				      {
				        filters : [
				        {
				          field : 'numListaStr',
				          label : 'N. lista',
				          type : 'search'
				        },
				        {
				          field : 'denominazioneBando',
				          label : 'Bando',
				          type : 'search'
				        },
				        {
				          field : 'annoCampagnaBando',
				          label : 'Anno di rif.',
				          type : 'search'
				        },
				        {
				          field : 'extIdAmmCompetenza',
				          label : 'Organismo delegato',
				          type : 'ajaxSelect',
				          source : 'json/ammCompetenzeListe.json'
				        },
				        {
				          field: 'flagStatoLista',    
				          label: 'Stato lista',   
				          type: 'select',
				          values: [
				                   {id: 'B', label: 'Bozza'},
				                   {id: 'A', label: 'Approvata'},
				                   {id: 'D', label: 'Da approvare'},
				                   {id: 'T', label: 'Trasmessa'}
				               ],
				        }   ,
				          {
				              field : 'elencoCodiciLivelliMisure',
				              label : 'Misura',
				              type : 'ajaxSelectList',
					          source : 'getElencoCodiciLivelliMisureJson.json'
				            } 
				          ,
				          {
				              field : 'elencoCodiciLivelli',
				              label : 'Operazione',
				              type : 'ajaxSelectList',
					          source : 'getElencoCodiciOperazioneJson.json'
				            } ,
				        {
				          field : 'descTipoImporto',
				          label : 'Tipo importo',
				          type : 'search'
				        }        ,
				        {
				          field : 'descTecnicoLiquidatore',
				          label : 'Tecnico',
				          type : 'search'
				        } ,
				        {
				            field : 'numeroProtocollo',
				            label : 'Numero protocollo',
				            type : 'search'
				          },
				          {
				              field : 'dataProtocollo',
				              label : 'Data protocollo',
				              type : 'date'
				            } 
				       
				        ],
				        connectTo : '#listeLiquidazione',
				        onSubmit : function()
				        {
				          var data = $('#filter-bar').bootstrapTableFilter('getData');
				          var elabFilter = JSON.stringify(data);
				          $.ajax(
				          {
				            type : "POST",
				            url : '../session/salvaFiltri.do',
				            data : "key=elencoListeLiquidazione&filtro=" + elabFilter,
				            success : function(data)
				            {
				            }
				          });
				        }
				      });
				      var filterJSON = $('#filtroJSON').html();
				      $('#filter-bar').bootstrapTableFilter("setupFilterFromJSON", filterJSON);

				      $('.btn-refresh').click();


		}
    $(document).ready(function()
    {

        initFilter();
        
   
    });

    function visualizzaStampaCallbackSuccess(data)
    {
      if (data.indexOf('<error>')>=0)
      {
        showMessageBox("Errore", data.replace('<error>','').replace('</error>',''));
      }
      else
      {
        if (data.indexOf('<renew>')>=0)
        {
          var url=data.replace('<renew>','').replace('</renew>','');
          openPageInPopup(url, 'dlgStampeOggetto', 'Conferma rigenerazione stampa', '');
        }
        else
        {
          if (data.indexOf('<stampa>')>=0)
          {
            var url=data.replace('<stampa>','').replace('</stampa>','');
            window.location.href=url;
          }
        }
      }
    }

    function visualizzaStampa(id)
    {
      $.ajax(
       {
         url:'../cunembo230/visualizza_'+id+'.do',
         async:false, 
         method:'POST',
         success: visualizzaStampaCallbackSuccess,
         type:'text/html', 
         error:
           function(a, b)
           { 
             alert('Si è verificato un errore di sistema');
           }
        })
      return false;
    }


    function setDialogHtmlStampeOggetto(data) 
    {
      $('#dlgStampeOggetto .modal-body').html(data);
      doErrorTooltip();
    }

  
    
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
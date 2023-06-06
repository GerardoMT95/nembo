var typewatch = function(){
    var timer = 0;
    return function(callback, ms){
        clearTimeout (timer);
        timer = setTimeout(callback, ms);
    }  
}();

function forwardToPage(page)
{
	window.location.href = page;
}

function expandAllPageArrows()
{
	$('.espandi a').each(function(){  
		$(this).click();
	});
}
 
function getSelectValueString(idSelect)
{
	var ser = '';
	$('#'+idSelect+'  option').each(function(index) {
    	ser = ser + '&' + $(this).val() + '=' + $(this).html();
    });
	
	return ser;
}


function toggleRow(id, hide)
{
	var children=$('.toggle_'+id);
	if (hide===true)
	{
		children.hide();
		var tmp=$('#freccia_'+id);
		tmp.addClass("freccia");
		tmp.removeClass("freccia_giu");
	}
	else
	{
		children.toggle();
		var tmp=$('#freccia_'+id);
		if (children.is(':visible'))
		{
			tmp.addClass("freccia_giu");
			tmp.removeClass("freccia");
		}
		else
		{
			tmp.addClass("freccia");
			tmp.removeClass("freccia_giu");
		}
	}
	
	if( children.is(':visible') ) 
	{
		var tmp=$('.toggle_'+id+" .freccia");
		tmp.addClass("freccia");
		tmp.removeClass("freccia_giu");
	}
	else 
	{
		var tmp=$('.toggle_'+id+" .freccia_giu");
		tmp.addClass("freccia_giu");
		tmp.removeClass("freccia");
		children.each(function(index, element)
				{
					toggleRow(element.id, true);
				})
	}
	return false;
}

var currentDialog = null;

function closeDialog()
{
	if (currentDialog)
	{
	  currentDialog.dialog('close');
	}
	return false;
}

function closeModal()
{
  if (_lastModalID)
  {
    $('#'+_lastModalID).modal('hide');
  }
  return false;
}

function destroyModal()
{
  if (_lastModalID)
  {
    $('#'+_lastModalID).modal( 'hide' ).data('modal', null);
  }
  return false;
}

var lastShowDialogStatusOK = false;

function showDialog(url, title)
{
	lastShowDialogStatusOK = false;
	$('#currentDialog').remove();
	$('body').append("<div id='currentDialog' style='display:none'></div>");
	var html='';
	$.ajax({
		url: url,
		context: document.body,
		async:false
		}).done(function(data) 
	    {
		  html=data;
		  $('#currentDialog').html(data);
		});
	if (html.indexOf('dialogPanel')>-1)
	{
		currentDialog=$('#currentDialog').dialog({
	        width: 600, 
	        modal: true, 
	        bgiframe: false, 
	        resizable: false, 
	        draggable: false,
	        title: title,
	        dialogClass: "no-close-dialog"
	    });
		currentDialog.show();
		lastShowDialogStatusOK=true;
	}
	else
	{
		if (html.indexOf('paginaSessioneScaduta')>-1)
		{
			alert("Sessione scaduta! Si prega di rieseguire il login");
		}
		else
		{
			if (html.indexOf("<body")>-1)
			{
				alert("Errore di sistema");
			}
			else
			{
				alert(html);
			}
		}
	}
	return false;
	
}

function changeInPleaseWait(messaggio)
{
	$('#messaggio').removeClass("alert-warning");
	$('#messaggio').addClass("alert-info");
	if (messaggio)
	{
		$('#messaggio').html("<p>"+messaggio+"</p>");
	}
	return false; // Per il return sull'onclick
}


function postDialogToPage($form, $page)
{
	var datastring = $($form).serialize();
	var htmlData = null;
	$.ajax({
	            type: "POST",
	            url: $page,
	            data: datastring,
	            dataType: "text",
	            async:false,
	            success: function(data) {
	            	htmlData=data;
	            }
	        });
	return htmlData;
}


function switchLabelToTextBox(idCell, confirmHref, cancellHref)
{
	if( $( "#"+idCell ).html().indexOf("<input") < 0 ) 
	{
		$( "#"+idCell ).html(
		    "<input type=\"text\" id=\"txt_"+idCell+"\" value=\"" + $( "#"+idCell ).html().trim() + "\" />" +
		    "<span class=\"cancella\"><a href=\"#\" onclick=\"forwardToPage('"+cancellHref+"')\" title=\"Annulla\" ><span class=\"nascosto\">elimina</span></a></span> " +
		    "<span class=\"ok\"><a href=\"#\" title=\"Salva modifica\" onclick=\"forwardToSavePage('"+idCell+"','"+confirmHref+"')\"><span class=\"nascosto\">consolida</span></a></span>"
		);
	}
}

function forwardToSavePage(idCell, confirmHref)
{
	forwardToPage(confirmHref+"?txt="+$("#txt_"+idCell).val());
}

function doErrorTooltip()
{
	$('[data-toggle="error-tooltip"]').tooltip({
		container : 'body'
	});
	$('[data-toggle="tooltip"]').tooltip({
		container : 'body'
	});
	
	$('[data-toggle="txt-tooltip"]').tooltip({
		container : 'body'
	});
	
	$('[data-toggle="txt-tooltip"]').hover(function() {
		$('.tooltip').addClass('txt-tooltip')
	}, function() {
		$('.tooltip').removeClass('tooltipPhoto')
	});
	
	$('[data-toggle="error-tooltip"]').hover(function() {
		$('.tooltip').addClass('red-tooltip')
	}, function() {
		$('.tooltip').removeClass('tooltipPhoto')
	});
}
$('document').ready(function(){
	doErrorTooltip();
	$('#iterModal').on('show.bs.modal', function (event) {
		$.ajax({
            type: "POST",
            url: '../cunembo104/index.do',
            dataType: "html",
            async:false,
            success: function(data) {
            	$('#iterModal .modal-body').html(data);
            }
        });
	});
	$('#checklistModal').on('show.bs.modal', function (event) {
		$.ajax({
            type: "POST",
            url: '../cunembo270/index.do',
            dataType: "html",
            async:false,
            success: function(data) {
            	$('#checklistModal .modal-body').html(data);
            }
        });
	});
});	

function fileUploadChangeName(fileTag, textTag) {
	var fileName = $(fileTag).val();
	if (fileName) {
		fileName = fileName.replace(/^.*[\\\/]/, '');
	}
	$(textTag).val(fileName);
}

function selectAll( id, isToSelect){
	var selectBox = document.getElementById(id);
	var optNum = document.getElementById(id).length;
    for (var i = 0; i < optNum; i++) { 
         selectBox.options[i].selected = isToSelect; 
    } 
}

function selectAllCheck( name, isToSelect){

	var x = document.getElementsByName(name);
	var i;
	for (i = 0; i < x.length; i++) {
	    if (x[i].type == "checkbox") {
	        x[i].checked = isToSelect;
	    }
	}
	fireEvent(document.getElementsByName(name)[0], 'click');
}

function fireEvent(element,event){
    if (document.createEventObject){
	    // dispatch for IE
	    var evt = document.createEventObject();
	    return element.fireEvent('on'+event,evt)
    }
    else{
	    // dispatch for firefox + others
	    var evt = document.createEvent("HTMLEvents");
	    evt.initEvent(event, true, true ); // event type,bubbling,cancelable
	    return !element.dispatchEvent(evt);
    }
}

//Funzione richiamata dalle popup per popolare le select della pagina principale. Alla base del ragionamento
// viene inteso che gli ID delle select sono del tipo  all_<ID> per le popoup e <ID> per la pagina principale
function filtra(idAllSelect)
{
	//pulisco select principale
	var idSelect = idAllSelect.split('all_')[1];
	$('#'+idSelect).find('option').remove();

	//reperisco value selezionati nella popup
	var arraySelectedValue = [];
	var arraySelectedIndex = [];
    $('#'+idAllSelect+'  option:selected').each(function(index) {
    	arraySelectedIndex[index] = $(this).val();
    	arraySelectedValue[$(this).val()] = $(this).text();
    });
    
    
    //se non ho selezionato niente, è come se avessi selezionato tutte le voci presenti
    if(arraySelectedValue.length == 0)
    {
    	$('#'+idAllSelect+'  option').each(function(index) {
    		arraySelectedIndex[index] = $(this).val();
        	arraySelectedValue[$(this).val()] = $(this).text();
        });
    }
    
  //popolo select principale
    for (index = 0; index < arraySelectedIndex.length; ++index) {
	    if(arraySelectedIndex[index] != null)
		 {
	    	$('#'+idSelect).append($('<option>', { value: arraySelectedIndex[index], text: arraySelectedValue[arraySelectedIndex[index]], disabled: 'disabled' }));
		 }
	}
    
    var listRef = '';
	if(idSelect == 'misura')
	{
		listRef = 'misura';
	}
	else if(idSelect == 'evento')
	{
		listRef = 'evento';
	}
	else if(idSelect == 'bando')
	{
		listRef = 'bando';
	}
	else if(idSelect == 'amministrazione')
	{
		listRef = 'amministrazione';
	}
	else if(idSelect == 'stato')
	{
		listRef = 'stato';
	}
	
	if(listRef != '')
	{
		var ser = $("#ricercaProcedimenti").serialize() + serializeField();
		$.ajax({
			  type: 'POST',
			  url: 'ricalcolalistbox_'+listRef+'.json',
			  data: ser,
			  success: function(data, textStatus) {
		    	  if(textStatus == 'success')
			      {
		    		  if(idSelect == 'misura'){
		    			  $('#evento').html('');
		    			  $('#all_evento').html('');
		    			  
		    			  $('#bando').html('');
		    			  $('#all_bando').html('');
		    		  }
		    		  if(idSelect == 'evento')
	    			  {
		    			  $('#bando').html('');
		    			  $('#all_bando').html('');	    			  
	    			  }
		    		  if((idSelect != 'amministrazione') &&(idSelect != 'stato')){
		    			  $('#amministrazione').html('');
		    			  $('#all_amministrazione').html('');
		    		  }
		    		  if(idSelect != 'stato'){
		    			  $('#stato').html('');
		    		  }
		    		  $('#oggetti').html('');
		    		  elaboraJsonRicerca(data);
				  }
		      },
			  dataType: 'json',
			  async:false
			});
	}
}

//TODO: FIXME: 
function filtraSelezionati(idAllSelect)
{
	//pulisco select principale
	var idSelect = idAllSelect.split('all_')[1];
	$('#'+idSelect).find('option').remove();

	//reperisco value selezionati nella popup
	var arraySelectedValue = [];
	var arraySelectedIndex = [];
    $('#'+idAllSelect+'  option:selected').each(function(index) {
    	arraySelectedIndex[index] = $(this).val();
    	arraySelectedValue[$(this).val()] = $(this).text();
    });
       
  //popolo select principale
    for (index = 0; index < arraySelectedIndex.length; ++index) {
	    if(arraySelectedIndex[index] != null)
		 {
	    	$('#'+idSelect).append($('<option>', { value: arraySelectedIndex[index], text: arraySelectedValue[arraySelectedIndex[index]], disabled: 'disabled' }));
		 }
	}
    
//TODO: FIXME:    
    var listRef = '';
	if(idSelect == 'misura')
	{
		listRef = 'misura';
	}
	else if(idSelect == 'bando')
	{
		listRef = 'bando';
	}
	else if(idSelect == 'amministrazione')
	{
		listRef = 'amministrazione';
	}
	else if(idSelect == 'stato')
	{
		listRef = 'stato';
	}
	
	if(listRef != '')
	{
		var ser = $("#ricercaProcedimenti").serialize() + serializeField();
		$.ajax({
			  type: 'POST',
			  url: 'ricalcolalistbox_'+listRef+'.json',
			  data: ser,
			  success: function(data, textStatus) {
		    	  if(textStatus == 'success')
			      {
		    		  if(idSelect == 'misura'){
		    			  $('#bando').html('');
		    			  $('#all_bando').html('');
		    		  }
		    		  if((idSelect != 'amministrazione') &&(idSelect != 'stato')){
		    			  $('#amministrazione').html('');
		    			  $('#all_amministrazione').html('');
		    		  }
		    		  if(idSelect != 'stato'){
		    			  $('#stato').html('');
		    		  }
		    		  $('#oggetti').html('');
		    		  elaboraJsonRicerca(data);
				  }
		      },
			  dataType: 'json',
			  async:false
			});
	}
}

function serializeField()
{
	return serializeSelectField("misura")+
		   serializeSelectField("evento")+
	  	   serializeSelectField("bando")	+
	  	   serializeSelectField("stato") +
	  	 serializeSelectField("amministrazione")+
	  	 serializeSelectField("notifica");
}

function prepareRicercaHiddenField()
{
	$('#serializedLivello').val(serializeSelectField("misura"));
	$('#serializedEvento').val(serializeSelectField("evento"));
	$('#serializedBando').val(serializeSelectField("bando"));
	$('#serializedAmministrazione').val(serializeSelectField("amministrazione"));
	$('#serializedStato').val(serializeSelectField("stato"));
	$('#serializedEstrazione').val(serializeSelectField("flagEstrazione"));
	$('#serializedEstrazioneExPost').val(serializeSelectField("flagEstrazioneExPost"));
	$('#serializedNotifica').val(serializeSelectField("notifica"));
}

function serializeSelectField(idSelect)
{
	var ser = '';
	var name = idSelect.split('-')[0];
	$('#'+idSelect+'  option').each(function(index) {
    	ser = ser + '&' + name + '=' + $(this).val();
    });
	
	return ser;
}

function elaboraJsonRicerca(data)
{
	$('#oggetti').html('');

	for(key in data) 																																			
	  {
		  var value=data[key];  
		  if (key.indexOf('optBandi')==0)
		  {
			  $('#bando').html(value.replace(/option/g,'option disabled '));
			  $('#all_bando').html(value);
			  
			  $.ajax({
				  type: 'GET',
				  url: 'reloadAnnoCampagna.do',
				  success: function(data) {
					  $('#annoCampagnaCnt').html(data);
			      },
				  dataType: 'html',
				  async:false
				});
			  
			  
		  }
		  else if (key.indexOf('optEventi')==0)
		  {
			  $('#evento').html(value.replace(/option/g,'option disabled '));
			  $('#all_evento').html(value);
			  	
			  $.ajax({
				  type: 'GET',
				  url: 'reloadAnnoEvento.do',
				  success: function(data) {
					  $('#annoEventoCnt').html(data);
				  },
				  dataType: 'html',
				  async:false
			  });
			  
			  
		  }
		  else if (key.indexOf('optAmministrazioni')==0)
		  {
			  $('#amministrazione').html(value.replace(/option/g,'option disabled '));
			  $('#all_amministrazione').html(value);
		  }
		  else if (key.indexOf('optProcedimenti')==0)
		  {
			  $('#stato').html(value.replace(/option/g,'option disabled '));
			  $('#all_stato').html(value);
		  }
		  else if (key.indexOf('optGruppi')==0 || key.indexOf('optOggetti')==0)
		  {
			  $('#oggetti').append(value.replace(/option/g,'option disabled '));
			  $.ajax({
					type : "GET",
					url : 'reloadOggettoModal.do',
					dataType : "html",
					async : false,
					success : function(data) {
						$('#oggettoModal').html(data);
					}
				});
			  sistemaCheckBoxGruppi();
		  }
		  else if (key.indexOf('inOggettiHidden')==0)
		  {
			  $('input[name="hOggetti"]').remove();
			  $(value).insertBefore("#oggetti");
		  }
		  else if (key.indexOf('inGruppiHidden')==0)
		  {
			  $('input[name="hGruppi"]').remove();
			  $(value).insertBefore("#oggetti");
		  }
		  
	  }
}

function initializeDatePicker()
{
  $("input[data-date-picker='true']").datepicker();
}

$( document ).ready(function() 
{
	$('.custom-collapsible-panel').on('hidden.bs.collapse', function (e) {
	    $('#' + e.currentTarget.id+' .panel-title i').removeClass('icon-collapse').addClass('icon-expand');
	    return false;
	});
	$('.custom-collapsible-panel').on('shown.bs.collapse', function (e) {
	    $('#' + e.currentTarget.id+' .panel-title i').removeClass('icon-expand').addClass('icon-collapse');
	    return false;
	});

  $('#testata').on('hidden.bs.collapse', function (e) {
      $('#header_icon_collapse').removeClass('glyphicon-collapse-up').addClass('glyphicon-collapse-down');
  });
  $('#testata').on('shown.bs.collapse', function (e) {
    $('#header_icon_collapse').removeClass('glyphicon-collapse-down').addClass('glyphicon-collapse-up');
  });
  	
	initializeDatePicker();
});


function openPopupComuni (idProv, idComune)
{
	$("#"+idProv).val($("#"+idProv).val().replace(/[^0-9a-z]/gi, ''));
	$("#"+idComune).val($("#"+idProv).val().replace(/[^0-9a-z]/gi, ''));
	var ser = $("#"+idProv).serialize() +"&"+ $("#"+idComune).serialize();
	$.ajax({
		  type: 'POST',
		  url: 'searchComuni.json',
		  data: ser,
		  success: function(data) {
			  if (data["isValidJSON"]=="true" && data["oneResult"]=="true") 
			  {
				  $("#"+idProv).val(data["prov"]);
				  $("#"+idComune).val(data["comune"]); 
			  }
			  else
			  {
				  elaboraDatiComuni(data, idProv, idComune);  
			  }
	      },
		  dataType: 'json',
		  async:false
		});
}

function elaboraDatiComuni(data, idPiva, idComune)																																		
{		
	   var elemntiPerPagina;
	   if (data["isValidJSON"]=="true") 																															
	   { 
		 $("#"+idPiva).val('');
		 $("#"+idComune).val('');
		 
		 for(key in data) 																																			
		 { 																																							
			var value=data[key];   																																	
			if (key.indexOf('comuniDTO')==0)
			{ 
				$('#popupComuniTable tbody').html('');			
				 for (var count in value) {																																
					 appendRowComuni(value[count]); 																																						
				} 																																						
			} 																																							
	    }
		
		 $('#comuniModal').modal();
	}
}

function appendRowComuni(obj)
{
		var tr = '<tr> \n';
		$('#popupComuniTable thead th').each(function() {
			var idTh = obj[$(this).attr('data-property')];
			if(idTh == undefined)
				idTh = ' ';
			
			if($(this).attr('data-property') == 'chk')
			{
				var inputHidden = '<input type="hidden" id="istatComuneHidden'+obj['istatComune']+'" value="'+obj['istatComune']+'" />'
								+ '<input type="hidden" id="provinciaHidden'+obj['istatComune']+'" value="'+obj['siglaProvincia']+'" />'
								+ '<input type="hidden" id="comuneHidden'+obj['istatComune']+'" value="'+obj['descrizioneComune']+'" />';
				tr = tr + '<th scope="row" style="border: 1px solid #91A2B2;padding: 5px;"><input type="radio" name="chkComuni" id="'+obj['istatComune']+'" /> '+inputHidden+' </th>  \n';
			}
			else
			{
				tr = tr + '<td class=\"\" style=\"border: 1px solid #91A2B2;padding: 5px;\">'+idTh+'</td>  \n';
			}
		});
		tr = tr + '</tr> \n';
		$('#popupComuniTable tbody').append(tr);
}

function popolaComuneProvincia ()
{
	if($('input[name=chkComuni]:checked'))
	{
		var idSelezionato = $('input[name=chkComuni]:checked').attr('id');
		$('#provSceltaComune').val($('#provinciaHidden'+idSelezionato).val());
		$('#comuneSceltaComune').val($('#comuneHidden'+idSelezionato).val());
	}
}


function loadProcedimentoAndOpenPageInPopup (idProcedimento, page, id, title, modalClass, hideCloseButton, formData)
{
	$.ajax({
        type: "GET",
        url: "../session/loadProcedimento_"+idProcedimento+".do",
        dataType: "html",
        async:false,
        success: function(data) {
        	return openPageInPopup(page, id, title, modalClass, hideCloseButton, formData);
        }
    }); 
	return false;
}

function openPageInPopupMethod(page, id, title, modalClass, hideCloseButton, formData, method)
{
	var html=
		   "<div class=\"modal fade :modalClass\" id=\":id\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"dlgTitle_:id\" aria-hidden=\"true\">"
		 + "		<div class=\"modal-dialog :modalDialogClass\">"
		 + "			<div class=\"modal-content\">"
		 + "				<div class=\"modal-header\">";
		 if (!hideCloseButton)
		 {
		   html += "					<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Chiudi\">"
		        + "            <span aria-hidden=\"true\">&times;</span>";
		 }
		 html +=  "					</button>"
		 + "					<h4 class=\"modal-title\" id=\"dlgTitle_:id\">:title</h4>"
		 + "				</div>"
		 + "				<div class=\"modal-body\"></div>"
		 + "			</div>"
		 + "		</div>"
		 + "	</div>";
	  _lastModalID=id;
		html=html.replace(':id',id);
		html=html.replace(':title',title);
		var modalDialogClass='';
		if (modalClass==null || modalClass==undefined)
		{
			modalClass="";
		}
		else
		  {
		    if (modalClass=='modal-lg' || modalClass=='modal-sm')
		    {
		      modalDialogClass=modalClass;
		      modalClass='';
		    }
		  }
		html=html.replace(':modalClass',modalClass);
	  html=html.replace(':modalDialogClass',modalDialogClass);
		if ($('#'+id).length!=0)
		{
		  $('#'+id).replaceWith(html);
		}
		else
		{
	    	$('body').append(html);
		}
		if (page && page!='')
	  {
	  	$.ajax({
	  		type : method,
	  		url : page,
	  		dataType : "html",
	  		data: formData,
	  		async : false,
	  		success : function(data) {
	  			$('#'+id+' .modal-body').html(data);
	  			doErrorTooltip();
	  		}
	  	});
	  }
		$('#'+id).modal();
		return false;

}

function openPageInPopup(page, id, title, modalClass, hideCloseButton, formData)
{
	return openPageInPopupMethod(page, id, title, modalClass, hideCloseButton, formData, "GET");
}

function asyncOpenPageInPopup(page, id, title, modalClass, hideCloseButton, formData)
{
  var html=
    "<div class=\"modal fade :modalClass\" id=\":id\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"dlgTitle_:id\" aria-hidden=\"true\">"
    + "		<div class=\"modal-dialog :modalDialogClass\">"
    + "			<div class=\"modal-content\">"
    + "				<div class=\"modal-header\">";
  if (!hideCloseButton)
  {
    html += "					<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Chiudi\">"
      + "            <span aria-hidden=\"true\">&times;</span>";
  }
  html +=  "					</button>"
    + "					<h4 class=\"modal-title\" id=\"dlgTitle_:id\">:title</h4>"
    + "				</div>"
    + "				<div class=\"modal-body\"><h3>Caricamento in corso...</h3></div>"
    + "			</div>"
    + "		</div>"
    + "	</div>";
  _lastModalID=id;
  html=html.replace(':id',id);
  html=html.replace(':title',title);
  var modalDialogClass='';
  if (modalClass==null || modalClass==undefined)
  {
    modalClass="";
  }
  else
  {
    if (modalClass=='modal-lg' || modalClass=='modal-sm')
    {
      modalDialogClass=modalClass;
      modalClass='';
    }
  }
  html=html.replace(':modalClass',modalClass);
  html=html.replace(':modalDialogClass',modalDialogClass);
  if ($('#'+id).length!=0)
  {
    $('#'+id).replaceWith(html);
  }
  else
  {
    $('body').append(html);
  }
  if (page && page!='')
  {
    $.ajax({
      type : "GET",
      url : page,
      dataType : "html",
      data: formData,
      async : true,
      success : function(data) {
        $('#'+id+' .modal-body').html(data);
        doErrorTooltip();
      }
    });
  }
  $('#'+id).modal();
  return false;
}

var _lastModalID=null;


function showConfirmMessageBox(title, message, modalClass, hrefChiudi, onclickconfirm)
{
  var id='__std_messagebox__';
  var html=
     "<div class=\"modal fade :modalClass\" id=\":id\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"dlgTitle_:id\" aria-hidden=\"true\">"
   + "    <div class=\"modal-dialog\">"
   + "      <div class=\"modal-content\">"
   + "        <div class=\"modal-header\">"
   + "          <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Chiudi\">"
   + "            <span aria-hidden=\"true\">&times;</span>"
   + "          </button>"
   + "          <h4 class=\"modal-title\" id=\"dlgTitle_:id\">:title</h4>"
   + "        </div>"
   + "        <div class=\"modal-body\">:message"
   +"         <div class=\"form-group puls-group\" style=\"margin-top: 2em;margin-bottom:5em\"> "
   +"         <div class=\"col-sm-12\">"
   + "         <button type=\"button\" class=\"btn btn-default btn-default pull-left\" onclick=\"$('#"+id+"').modal('hide');window.location.href = '"+hrefChiudi+"'; \">Chiudi</button>                                             "
   + "         <button type=\"button\" class=\"btn btn-default btn-primary pull-right\" onclick=\""+onclickconfirm+"\" >Conferma</button>                                             "
   + "        </div> "
   + "        </div> "
   + "        </div> "
   + "      </div>"
   + "    </div>"
   + "  </div>";
  _lastModalID=id;
  html=html.replace(':id',id);
  html=html.replace(':title',title);
  html=html.replace(':message',message);
  if (modalClass==null || modalClass==undefined)
  {
    modalClass="";
  }
  html=html.replace(':modalClass',modalClass);
  if ($('#'+id).length!=0)
  {
    $('#'+id).replaceWith(html);
  }
  else
  {
      $('body').append(html);
  }
  $('#'+id).modal();
  return false;
}


function showRedirectMessageBox(title, message, modalClass, hrefChiudi)
{
  var id='__std_messagebox__';
  var html=
     "<div class=\"modal fade :modalClass\" id=\":id\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"dlgTitle_:id\" aria-hidden=\"true\">"
   + "    <div class=\"modal-dialog\">"
   + "      <div class=\"modal-content\">"
   + "        <div class=\"modal-header\">"
   + "          <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Chiudi\">"
   + "            <span aria-hidden=\"true\">&times;</span>"
   + "          </button>"
   + "          <h4 class=\"modal-title\" id=\"dlgTitle_:id\">:title</h4>"
   + "        </div>"
   + "        <div class=\"modal-body\">:message</div>"
   +"         <div class=\"modal-footer\">                                                                                                           "
   + "         <button type=\"button\" class=\"btn btn-default btn-primary\" onclick=\"window.location.href = '"+hrefChiudi+"'; \" >Chiudi</button>                                             "
   + "        </div> "
   + "      </div>"
   + "    </div>"
   + "  </div>";
  _lastModalID=id;
  html=html.replace(':id',id);
  html=html.replace(':title',title);
  html=html.replace(':message',message);
  if (modalClass==null || modalClass==undefined)
  {
    modalClass="";
  }
  html=html.replace(':modalClass',modalClass);
  if ($('#'+id).length!=0)
  {
    $('#'+id).replaceWith(html);
  }
  else
  {
      $('body').append(html);
  }
  $('#'+id).modal();
  return false;
}


function showMessageBox(title, message, modalClass)
{
  var id='__std_messagebox__';
  var html=
     "<div class=\"modal fade :modalClass\" id=\":id\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"dlgTitle_:id\" aria-hidden=\"true\">"
   + "    <div class=\"modal-dialog\">"
   + "      <div class=\"modal-content\">"
   + "        <div class=\"modal-header\">"
   + "          <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Chiudi\">"
   + "            <span aria-hidden=\"true\">&times;</span>"
   + "          </button>"
   + "          <h4 class=\"modal-title\" id=\"dlgTitle_:id\">:title</h4>"
   + "        </div>"
   + "        <div class=\"modal-body\">:message</div>"
   +"         <div class=\"modal-footer\">                                                                                                           "
   + "         <button type=\"button\" class=\"btn btn-default btn-primary\" data-dismiss=\"modal\">Chiudi</button>                                             "
   + "        </div> "
   + "      </div>"
   + "    </div>"
   + "  </div>";
  _lastModalID=id;
  html=html.replace(':id',id);
  html=html.replace(':title',title);
  html=html.replace(':message',message);
  if (modalClass==null || modalClass==undefined)
  {
    modalClass="";
  }
  html=html.replace(':modalClass',modalClass);
  if ($('#'+id).length!=0)
  {
    $('#'+id).replaceWith(html);
  }
  else
  {
      $('body').append(html);
  }
  $('#'+id).modal();
  return false;
}


function showMessageBoxContinuaAnnulla(title, message, modalClass, operationSuccess, operationFailure)
{
  var id='__std_messagebox__';
  var html=
     "<div class=\"modal fade :modalClass\" id=\":id\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"dlgTitle_:id\" aria-hidden=\"true\">"
   + "    <div class=\"modal-dialog\">"
   + "      <div class=\"modal-content\">"
   + "        <div class=\"modal-header\">"
   + "          <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Annulla\">"
   + "            <span aria-hidden=\"true\">&times;</span>"
   + "          </button>"
   + "          <h4 class=\"modal-title\" id=\"dlgTitle_:id\">:title</h4>"
   + "        </div>"
   + "        <div class=\"modal-body\">:message</div>"
   +"         <div class=\"modal-footer\">                                                                                                           "
   + "         <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\" style=\"float:left;\" onclick=\":operationFailure\">Annulla</button>                                             "
   + "         <button type=\"button\" class=\"btn btn-default btn-primary\" onclick=\":operationSuccess\">Continua</button>                                             "
   + "        </div> "
   + "      </div>"
   + "    </div>"
   + "  </div>";
  _lastModalID=id;
  html=html.replace(':id',id);
  html=html.replace(':title',title);
  html=html.replace(':message',message);
  html=html.replace(':operationSuccess',operationSuccess);
  html=html.replace(':operationFailure',operationFailure);
  if (modalClass==null || modalClass==undefined)
  {
    modalClass="";
  }
  html=html.replace(':modalClass',modalClass);
  if ($('#'+id).length!=0)
  {
    $('#'+id).replaceWith(html);
  }
  else
  {
      $('body').append(html);
  }
  $('#'+id).modal();
  return false;
}


function submitFormViaAjax(formId, callback)
{
  $form=$('#'+formId);
  $.ajax({
		type : "POST",
		url : $form.attr('action'),
		data: $form.serialize(),
		dataType : "html",
		async : false,
		success : function(data) 
		{
			callback(data, true);
		},
		fail : function( jqXHR, textStatus )
		{
			callback(jqXHR, false);	
	    }
		});	
}

function submitFormViaAjaxAsync(formId, callback)
{
  $form=$('#'+formId);
  $.ajax({
		type : "POST",
		url : $form.attr('action'),
		data: $form.serialize(),
		dataType : "html",
		async : true,
		success : function(data) 
		{
			callback(data, true);
		},
		fail : function( jqXHR, textStatus )
		{
			callback(jqXHR, false);	
	    }
		});	
}

function initializeControlsForPopup()
{
  doErrorTooltip();
  initializeDatePicker();
}

function callbackPannelloStampe() 
{
  var html="\n<span class=\"please_wait\" style=\"vertical-align:middle\"></span> Attendere prego, caricamento dell'elenco stampe in corso...";
  $('#pannello_stampe .panel-body').html(html);
  var href=window.location.href;
  href=href.substr(0,
      href.indexOf("/", href.indexOf("/", href.indexOf("//") + 2) + 1));
  var contesto=href.substr(href.lastIndexOf('/'));
  $('#pannello_stampe').off('show.bs.collapse', callbackPannelloStampe);
  $.ajax(
  {
    url:contesto+"/cunembo126l/lista.do",
    method:'POST',
    success:function (data)
    {
      if (data.indexOf('<!--LISTA_STAMPE-->')>=0)
      {
        $('#pannello_stampe .panel-body').html(data);
      }
      else
      {
        $('#pannello_stampe .panel-body').html("<div class=\"alert alert-danger\" role=\"alert\">Errore! Impossibile reperire l'elenco delle stampe</div>");
        $('#pannello_stampe').on('show.bs.collapse', callbackPannelloStampe);
      }
    },
    error: function()
    {
      $('#pannello_stampe .panel-body').html("<div class=\"alert alert-danger\" role=\"alert\">Errore! Impossibile reperire l'elenco delle stampe</div>");
      $('#pannello_stampe').on('show.bs.collapse', callbackPannelloStampe);      
    }
  });
}

$('#pannello_stampe').on('show.bs.collapse', callbackPannelloStampe);

function visualizzaStampaOggettoCallbackSuccess(data)
{
  if (data.indexOf('<error>')>=0)
  {
    alert(data.replace('<error>','').replace('</error>',''));
  }
  else
  {
    if (data.indexOf('<stampa>')>=0)
    {
      var url=data.replace('<stampa>','').replace('</stampa>','');
      window.location.href=url;
    }
    else
    {
    
    }
  }
}

function visualizzaStampaOggetto(id)
{
  $.ajax(
   {
     url:'../cunembo126l/visualizza_'+id+'.do', 
     async:false, 
     method:'POST',
     success: visualizzaStampaOggettoCallbackSuccess, 
     error:
       function(jqXHR, textStatus, errorThrown )
       { 
         alert('Si è verificato un errore di sistema '+c);
       }
    })
  return false;
}

function selezionaPopupOptions(idSelect)
{
	var arraySelectedValue = [];
	$('#'+idSelect+'  option').each(function(index) {
    	 arraySelectedValue[$(this).val()] = $(this).val();
    });
	
	$('#all_'+idSelect).val(arraySelectedValue);
}


Number.prototype.formatCurrency = function(c){
  var n = this, 
      c = isNaN(c = Math.abs(c)) ? 2 : c, 
      d = "," , 
      t = ".", 
      s = n < 0 ? "-" : "", 
      i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", 
      j = (j = i.length) > 3 ? j % 3 : 0;
     return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
   };
   
  function writeModalBodyError(errorMessage, modalID)
  {
     if (!modalID)
     {
       modalID=_lastModalID;
     }
     var html='<span class="fail_big" style="vertical-align: middle; float:left"></span><h4>'+errorMessage+
     '</h4><br /><br/><button type="button" class="btn btn-primary pull-right" data-dismiss="modal">Chiudi</button><br style="clear:left"/>';
     $('#'+modalID+' .modal-body').html(html);   
  }
  
  function writeSuccessConfirmation(message, modalID)
  {
    if (!modalID)
    {
      modalID=_lastModalID;
    }
    var html='<span class="success_big" style="vertical-align: middle; float:left"></span><h4>'+message+
    '</h4><br /><br/><button type="button" class="btn btn-primary pull-right" data-dismiss="modal">Chiudi</button><br style="clear:left"/>';
    $('#'+modalID+' .modal-body').html(html);   
  }

  function submitFormTo($form, page)
  {
    $oldAction=$form.attr('action');
    $form.attr('action',page);
    $form.submit();
    $form.attr('action',$oldAction);
    return false;
  }
  

  function reorderSelectOptions(idSelect)
  {
    var options = $(idSelect+' option');
      var arr = options.map(function(_, o) { return { t: $(o).text(), v: o.value }; }).get();
      arr.sort(function(o1, o2) { return o1.t > o2.t ? 1 : o1.t < o2.t ? -1 : 0; });
      options.each(function(i, o) {
        o.value = arr[i].v;
        $(o).text(arr[i].t);
      });
  }

  $(function()
  {
    try
    {
      $messaggiTestata = $('#messaggi-testata');
      if ($messaggiTestata.length > 0)
      {
        startMessaggistica($messaggiTestata);
      }
    }
    catch(e)
    {
      //Ignoro tutto
    }
  });
  var _messaggistica_rolling=true;
  var _messaggistica_end_time = null;
  function startMessaggistica($messaggiTestata)
  {
    var testoMessaggio=$messaggiTestata.html();
    var regex=/http[s]*:\/\/[-a-zA-Z0-9@:%._\+~#=]+[^.]/;
    var match=testoMessaggio.match(regex);
    var time=$('#banner_messaggistica').data('time');
    if (time)
    {
      _messaggistica_end_time=new Date(new Date().getTime()+time).getTime();
    }
    else
    {
      // Nessun limite di tempo
      _messaggistica_end_time=new Date(new Date().getTime()+14400000).getTime();
    }
    if (match!=null && match.length>0)
    {
      var hash=Array();
      for(_i=0;_i<match.length;_i++)
      {
        hash[match[_i]]=match[_i];
      }
      for(url in hash)
      {
        testoMessaggio = testoMessaggio.replace(url, '<a href="'+url+'" target="_blank">'+url+'</a>');
      }
    }
    
    var offset=$messaggiTestata.offset();
    var pos={left:$('#banner_messaggistica').width(),top:offset.top};
    $messaggiTestata.html(testoMessaggio);
    $messaggiTestata.offset({left:pos.left,top:pos.top});
    setTimeout(runMessaggistica,20);
  }
  
  function doneMessaggistica()
  {
  }
  function turnOffMessaggistica()
  {
    $('#container-messaggistica').remove();
    $.ajax('../cunembo203/turnoff.do');
  }
  
  function runMessaggistica()
  {
    try
    {
      if (new Date().getTime()>_messaggistica_end_time)
      {
        $('#container-messaggistica').remove();
        return;
      }
      if (_messaggistica_rolling)
      {
          var $messaggiTestata=$('#messaggi-testata');
          var offset=$messaggiTestata.offset();
          var pos={left:offset.left-1,top:offset.top};
          if (pos.left+$messaggiTestata.width()<-1)
          {
            pos.left=$('#banner_messaggistica').width();
          }
          $messaggiTestata.offset({left:pos.left,top:pos.top});
      }
      setTimeout(runMessaggistica,20);
    }
    catch(e)
    {
      //Ignoro tutto
    }
  }
  
  
  function toggleHelp(codCDU)
  {
	  if($("#help_container").is(":visible"))
	  {
		  $.ajax({
	            type: "POST",
	            url: "../help/cleanhelp_"+codCDU+".do",
	            dataType: "html",
	            async:false,
	            success: function(data) {
	            	 $("#icona_help").css("background-color","#FFFFFF");
	       		  	 $("#help_container").hide();
	            }
	        }); 
	  }
	  else
	  {
		  $.ajax({
	            type: "POST",
	            url: "../help/gethelp_"+codCDU+".do",
	            dataType: "html",
	            async:false,
	            success: function(data) {
	            	$("#icona_help").css("background-color","yellow");
	            	
	            	//cerco se il primo figlio di #content è un panel panel-primary e lo aggancio li dentro
	            	if($( "#content div:first-child" ).hasClass( "panel-primary" ))
	            	{
	            		if($( "#content div:first-child #help_container" ).length <=0 ){ 
	            			$("#help_container").prependTo($( "#content div:first-child" ));
	            		}
	            	}
	            	
	            	$("#help_text").html(data);
	            	$("#help_container").show();
	            },
	            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            	$("#icona_help").css("background-color","yellow");
	            	
	            	//cerco se il primo figlio di #content è un panel panel-primary e lo aggancio li dentro
	            	if($( "#content div:first-child" ).hasClass( "panel-primary" ))
	            	{
	            		if($( "#content div:first-child #help_container" ).length <=0 ){ 
	            			$("#help_container").prependTo($( "#content div:first-child" ));
	            		}
	            	}
	            	
	            	$("#help_text").html("<div class=\"stdMessagePanel\"><div class=\"alert alert-danger\">Si &egrave; verificato un errore durante il reperimento dell' Help.Contattare l'assistenza tecnica.</div></div>");
	            	$("#help_container").show();
	             }
	        }); 
	  }
	  
	  
  }
  
  function openDocumentale(url)
  {
    var sh=screen.height;
    var sw=screen.width;
    var h=Math.round(sh*0.9);
    var w=Math.round(sw*0.9);
    var left =Math.round((sw-w)/2);
    var top =Math.round((sh-h)/2);
    window.open(url, "nembo_documentale","left="+left+",top="+top+",toolbar=no,width="+w+",height="+h+",location=no,menubar=no,scrollbars=yes,resizable=yes");
    return false;
  }
  
  var __click_allowed__=true;
  function singleClick()
  {
    if (__click_allowed__)
    {
      __click_allowed__=false;
      return true;
    }
    return false;
  }
  
  
  function showPleaseWait()   
  {
	  return "<div class=\"col-md-5 col-md-offset-5\"  >"
				+"<div id=\"loadingdiv\" > "
					+"<div id=\"floatBarsG_1\" class=\"floatBarsG\"></div>"
					+"<div id=\"floatBarsG_2\" class=\"floatBarsG\"></div>"
					+"<div id=\"floatBarsG_3\" class=\"floatBarsG\"></div>"
					+"<div id=\"floatBarsG_4\" class=\"floatBarsG\"></div>"
					+"<div id=\"floatBarsG_5\" class=\"floatBarsG\"></div>"
					+"<div id=\"floatBarsG_6\" class=\"floatBarsG\"></div>"
					+"<div id=\"floatBarsG_7\" class=\"floatBarsG\"></div>"
					+"<div id=\"floatBarsG_8\" class=\"floatBarsG\"></div>"
				+"</div>"
			+"</div>";
  }

  function ajaxReplaceElement(page, formData, idToReplace)
  {
    $.post(
      page, 
      formData,
      function(data) 
      {
        $('#'+idToReplace).replaceWith(data);
        
      }
    ).fail(function(){alert("Errore nel richiamo della funzionalità")});
  }
  
  
	/**
	 * Funzione che verifica se, dato un form con N checkboxes, ciascuna con nome 'checkboxesName',
	 * tutte le checkbox non sono selezionate.
	 * Ritorna: - false se almeno una checkbox è selezionata
	 * 			- true se tutte le checkboxes non sono selezionate
	 * @param checkboxesName
	 * @returns {Boolean}
	 */
	function isAllCheckboxUnchecked(checkboxesName) 
	{
		var allUnchecked = true;
		$("input[name="+checkboxesName+"]").each(function() 
		{
			if ($(this).prop("checked") == true) 
			{
				allUnchecked = false;
			}
		});
		return(allUnchecked);
	}
	
	  /**
	   * 
	   * @param idSelect : id della select su cui si vuole applicare il filtro
	   * @param dataCode : valore del data-code che si vuole visualizzare, se NULL permette di visualizzarli tutti
	   * @returns
	   */
	  function showOptionSelectChoice(idSelect, dataCode, htmlSelectId) {
		$('a[name=' + idSelect + '_a_choice] span').replaceWith('<span style="margin-right:19px;" aria-hidden="true"></span>');
		$('#' + idSelect + '_a_choice_' + dataCode + ' span').replaceWith('<span class="glyphicon glyphicon-ok-circle" style="margin-right:5px;" aria-hidden="true"></span>');
		$('#' + idSelect + '_choice').val(dataCode);
		 
		var optionDefault = '<option data-code="CODE-DEFAULT" value="">-- selezionare --</option>';
		
		var dataCodeSelected = $('#' + idSelect + ' option:selected').attr("data-code");
		var valueSelected = $('#' + idSelect + ' option:selected').val();
		$('#' + idSelect).children('option').remove();  																	//rimuove tutti gli elementi
		$('#' + idSelect).append(htmlSelectId);
		
		$('#' + idSelect + ' option:selected').removeAttr("selected");
		
		//seleziono tutti
		if (dataCode == '') {																								//voglio inserire i funzionare di tutti gli uffici di zona
			$('#' + idSelect + ' option[value="' + valueSelected + '"]').attr("selected",true);
		
		}else{																												//singolo ufficio zona
			//seleziono solo quelli con lo specifico codice
			$('#' + idSelect + ' [data-code!='+dataCode +']').remove(); 													//rimuove quelli di altri uffici di zona
			$(optionDefault).prependTo('#' + idSelect);																		//inserisce quello di default
			if(dataCodeSelected != dataCode && dataCodeSelected != 'CODE_DEFAULT')											//se cambio il la scelta e il valore precedentemente selezionato non è più disponibile metti quello di default
			{
				$('#' + idSelect + ' option[value=""]').attr("selected",true);
			}
			else
			{
				$('#' + idSelect + ' option[value="' + valueSelected + '"]').attr("selected",true);
			}
		}
	  }
	

	

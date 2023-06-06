<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="dlgLocalizza_errorBox" style="display:none" class="alert alert-danger alert-error">
<a href="#" onclick="return false" class="close" data-dismiss="alert"></a><strong id="dlgLocalizza_errorMessage"></strong></div>
<div id="dual-list-box1" class="form-group row">
	<select style="display: none" id="interventiDualList" multiple="multiple" 
	data-title="Comuni" data-source="${json_source}"
		data-value="istatComune" data-text="descrizioneComune" data-addcombo="true" data-labelcombo="Provincia" 
		data-labelfilter="Comuni" data-sourceselected="../cunembo${cuNumber}z/load_comuni_selezionati_${param['idIntervento']}.json"></select>
</div>
<input type="button" class="btn btn-default" data-dismiss="modal" value="annulla"/>
<input type="button" class="btn btn-primary pull-right" value="conferma" onclick="onProsegui()" />
<script type="text/javascript">
  function onProsegui()
  {
    
    var formData = {istatComune : new Array(), conferma:'true', idIntervento:"${param['idIntervento']}" };
    $('#dual-list-box-Comuni #selectedListHidden option').each(function(index) {
      formData.istatComune[index] = $(this).val();
    });
    if (formData.istatComune.length>4000)
    {
      alert("Hai superato il limite massimo di 4000 elementi!");
      return false;
    }
    $.ajax({
      type: "POST",
      url: '${submit_url}',
      data: $.param(formData, true),
      dataType: "text",
      async:false,
      success: function(data) {
        if (data.indexOf('<success>true</success>')==0)
        {
          $('#'+_lastModalID).modal('hide');
          window.location.reload();
        }
        else
          {
	          if (data.indexOf('<error>')==0)
	          {
		          var message=data.replace("<error>","");
		          message=data.replace("</error>","");
              $('#dlgLocalizza_errorBox').show();
              $('#dlgLocalizza_errorMessage').html(message);
	          }
	          else
		          {
	             $('#'+_lastModalID+" .modal-body").html(data);
		          }
          }
      }
  });
  };

	//rimuovo dall'elenco di sx quelle presenti nella select principale
	$('#Comune option').each(
		function() {
			$('#dual-list-box-Comuni #unselectedList option[value="'+ $(this).val() + '"]').remove();
			$('#dual-list-box-Comuni #unselectedListHidden option[value="'+ $(this).val() + '"]').remove();
		});
	reorderSelectOptions('#dual-list-box-Comuni #selectedList');
	
	$('#interventiDualList').DualListBox();
	/**/
</script>

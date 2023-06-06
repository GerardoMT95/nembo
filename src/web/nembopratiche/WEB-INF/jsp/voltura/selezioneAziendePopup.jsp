<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>

<m:error />

<form method="post" class="form-horizontal" id="formAziende" action="">
	<div class="container-fluid" id="popupContent">
		<input type="hidden" name="errorCuaa" value="" /> <span id="errorBox"></span>
		<div id="popupFilters">
			<div class="col-sm-12">
				<m:textfield id="cuaaFiltro" name="capofila" label="CUAA:" controlSize="9" labelSize="3" maxlength="16"></m:textfield>
			</div>
			<div class="col-sm-12">
				<m:textfield id="denominazioneAziendaFiltro" name="denominazioneAzienda" label="Denominazione azienda: " controlSize="9" labelSize="3"></m:textfield>
			</div>
			<div class="col-sm-12">
				<a type="button" class="btn btn-primary pull-right" id="cerca" onclick="popup_cercaAzienda(event);">Cerca Azienda</a>
			</div>
		</div>
		<br style="clear: left"> <br />
		<div id="tableBox" style="overflow-y: scroll; max-height: 360px">
			<table id='tblAziende' summary="Elenco Aziende" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
				data-undefined-text=''>
				<thead>
					<tr>
						<th data-width="50" data-switchable="false" data-field="idAzienda" data-formatter="radioFormatter"></th>
						<th data-field="cuaa">CUAA</th>
						<th data-field="denominazione">Denominazione</th>
						<th data-field="denominazioneIntestazione">Intestazione partita IVA</th>
						<th data-field="sedeLegale">Sede legale</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		<button type="button" data-dismiss="modal" id="annulla" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaModifica()">conferma</button>
	</div>
</form>

<script type="text/javascript">
  $("#tblAziende").bootstrapTable();

  $(document).ready(function(){	

	  $(".fixed-table-loading").hide();  
	  });

  function popup_cercaAzienda(event)
  {
	  
	//event.preventDefault();
	$(".fixed-table-header").show();
	$(".fixed-table-loading").show();  

    $('#errorBox').html("");
    var cuaa = $.trim($('#cuaaFiltro').val());
    var denominazioneAzienda = $.trim($('#denominazioneAziendaFiltro').val());
    var ok = true;
    if (cuaa == "" && denominazioneAzienda == "")
    {
      $('#errorBox').html("<div class='alert alert-danger'>Inserire un filtro di ricerca</div>");
      ok = false;
    }
    else
    {
      if (cuaa != '')
      {
        if (cuaa.length != 11 && cuaa.length != 16)
        {
          $('#errorBox').html("<div class='alert alert-danger'>Cuaa non valido</div>");
          ok = false;
        }
      }
      else
      {
        if (denominazioneAzienda.length < 4)
        {
          $('#errorBox').html("<div class='alert alert-danger'>Inserire almeno 4 caratteri in denominazione azienda</div>");
          ok = false;
        }
      }
    }
    if (ok)
    {
       
      $.ajax(
      {
        type : "POST",
        url : "elenco.json",
        data : $('#formAziende').serialize(),
        dataType : "json",
        async : false,
        success : function(data)
        {
          $("#tblAziende").bootstrapTable('load', data);

          
        }
      });
    }

    $(".fixed-table-header").show();
    $(".fixed-table-loading").hide();
  }

  

  
  function confermaModifica()
  {
    var idAzienda = $('input[name=optradio]:checked').val();

    if (idAzienda == null || idAzienda === undefined || idAzienda == "")
    {
      showMessageBox("Errore", "Selezionare un'azienda", 'modal-large');
      return false;
    }

    var data = $('#tblAziende').bootstrapTable("getData");

    $.each(data, function(index, row)
    {
      var id = row['idAzienda'];
      if (id == idAzienda)
      {
        $("#azienda").val(row['denominazione']);
        $("#denominazioneHidden").val(row['denominazione']);
        $("#sedeLegaleHidden").val(row['sedeLegale']);
        $("#sedeLegale").val(row['sedeLegale']);
        $("#rappresentanteLegale").val(row['rappresentanteLegale']);
        $("#rappresentanteLegaleHidden").val(row['rappresentanteLegale']);
        $("#cuaa").val(row['cuaa']);
        $("#idAzienda").val(row['idAzienda']);
        
      }
    });

    $("#annulla").click();
  }
</script>

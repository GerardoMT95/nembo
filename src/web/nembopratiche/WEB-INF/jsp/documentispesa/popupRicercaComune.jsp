<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<div>
	<m:error />
  <div class="row">
	<div class="col-md-9">
		<label>Comune/Stato estero</label>
		<m:textfield id="descComune" name="descComune"/>
	</div>
	<div class="col-md-3">
		<button type="button" name="btncercacomune" id="btncercacomune" style="margin-top:1.8em" onclick="ricercaComuni()"
						class="btn btn-primary">cerca</button>
	</div>
  </div>
  <div class="row">
  <div class="col-md-12">
	<label>Elenco comuni/Stati estero</label>
	</div>
	</div>
	<div class="row">
	<div class="col-md-12" >
	  <m:list name="comuni" id="comuni" list=""></m:list>
	</div>
	</div>
	<br /> <br />
</div>
<br style="clear: left" />
<m:include-scripts/>
<script type="text/javascript">

  function ricercaComuni()
  {
    var $listComuni=$('#comuni');
    $val = $('#descComune').val();
    if ($val=='')
    {
      $listComuni.empty();
      return;
    }
    var page="elenco_comuni_attivi.json";
    
    $.ajax({
        type: "POST",
        url: "../datigeografici/"+page,
        data: "descComune="+$val,
        async:false,
    }).success(function(data) {
        	$listComuni.empty();
            $(data).each(function(index, comune)
            {
              myList_addItem($listComuni,comune.descrizioneComune + " ("+comune.siglaProvincia+ ")", comune.istatComune, "return selezionaComune(this,'"+comune.istatComune+"','"+comune.cap+"','"+$val+"')");
            });
        }).error(function(){alert('Errore nel caricamento di comuni per la provincia con istat '+istatProvincia)});
    return false;
  }
</script>

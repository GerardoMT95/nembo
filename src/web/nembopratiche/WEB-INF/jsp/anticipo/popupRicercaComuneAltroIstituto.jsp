<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<div>
	<m:error />
  <div class="row">
  <span style="font-size:12px;font-style:italic;padding-left:30px;padding-bottom:8px">Selezionare la <strong>Provincia</strong> e filtrare l'elenco indicando nel <strong>Comune</strong> CAP o denominazione comune, anche parzialmente</span>
  </div>
	<div class="col-md-6">
		<label>Provincia</label>
		<m:dropdown id="province" list="${province}" name="province" onchange="return onSelezionaProvincia()"/>
	</div>
	<div class="col-md-6">
		<label>Comune</label>
		<m:textfield id="descComune" name="descComune" onkeyup="myList_filterList(this.value, $('#comuni'))"/>
	</div>
	
  <div class="col-md-12">
	<label>Elenco comuni</label>
	</div>
	<div class="col-md-12" >
	  <m:list name="comuni" id="comuni" list=""></m:list>
	</div>
	<br /> <br />
</div>
<br style="clear: left" />
<m:include-scripts/>
<script type="text/javascript">

  function onSelezionaProvincia()
  {
    $('#descComune').val('');
    var $val=$('#province').data('value');
    var $listComuni=$('#comuni');
    if ($val=='')
    {
      $listComuni.empty();
      return;
    }
    var page="elenco_comuni_attivi_";
    page+=$val;
    
    page+=".json";
    $.ajax({
      url: "../datigeografici/"+page,
      async:false
      }).success(function(data) 
        {
        $listComuni.empty();
        $(data).each(function(index, comune)
        {
          myList_addItem($listComuni,comune.codice+" - "+ comune.descrizione, comune.id, "return selezionaComune(this,'"+comune.id+"')");
        });
      }).error(function(){alert('Errore nel caricamento di comuni per la provincia con istat '+istatProvincia)});
    return false;
  }
</script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<div>
	<m:error />
	<div class="row">
	<span style="font-size:12px;font-style:italic;padding-left:30px;padding-bottom:8px">Selezionare l'<strong>Istituto bancario</strong> e filtrare l'elenco indicando nello <strong>Sportello</strong> ABI o denominazione sportello, anche parzialmente</span>
	</div>
	<div class="col-md-6 form-horizontal">
		<label>Istituto bancario</label>
		<m:dropdown id="banche" list="${banche}" name="banche" onchange="onChangeBanca()"></m:dropdown>
	</div>
	<!-- /btn-group -->
</div>
<div class="col-md-6">
	<label>Sportello</label>
	<m:textfield id="filtroSportello" name="filtroSportello" onkeyup="myList_filterList(this.value, $('#sportelli'))" />
</div>
<div class="col-md-12">
	<label>Elenco sportelli</label>
</div>
<div class="col-md-12">
	<m:list name="sportelli" id="sportelli" list="" multipleSelection="true"></m:list>
</div>
<br />
<br />
<br style="clear: left" />
<m:include-scripts/>
<script type="text/javascript">
  function onChangeBanca()
  {
    $('#filtroSportello').val('');
    var $val=$('#banche').data('value');
    var $listSportelli=$('#sportelli');
    if ($val=='')
    {
      $listSportelli.empty();
      return;
    }
    var page="elenco_sportelli_"+$val+".json";
    $.ajax({
      url: page,
      async:false
      }).success(function(data) 
        {
        $listSportelli.empty();
        $(data).each(function(index, sportello)
        {
          myList_addItem($listSportelli,sportello.codice+" - "+ sportello.descrizione, sportello.id, "return selezionaSportello(this,'"+sportello.id+"')");
        });
        //applyFilterDescrizione();
      }).error(function(){alert('Errore nel caricamento degli sportelli per la banca con id #'+$val)});
    return false;
  }
</script>


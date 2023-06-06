  
  function caricaComuniPerProvincia(istatProvincia, idComboComuni, disabledIfEmpty, estinti)
  {
    if (istatProvincia=='')
    {
      var $select=$('#'+idComboComuni);
      $select.empty();
      $select.append($("<option>",{value:'',text:'-- selezionare--'}));
      if (disabledIfEmpty)
      {
        $select.prop("disabled",true);
      }
      return;
    }
    var page=estinti?"elenco_comuni_":"elenco_comuni_attivi_";
    page+=istatProvincia;
    page+=".json";
    $.ajax({
      url: "../datigeografici/"+page,
      async:false
      }).success(function(data) 
        {
        var $select=$('#'+idComboComuni);
        $select.prop("disabled",false);
        if (disabledIfEmpty)
        {
          $select.empty();
        }
        $select.append($("<option>",{value:'',text:'-- selezionare --'}));
        $(data).each(function(index, comune)
        {
          $select.append($("<option>",{value:comune['id'],text:comune['descrizione']}));
        });
      }).error(function(){alert('Errore nel caricamento di comuni per la provincia con istat '+istatProvincia)});
    
  }
  
  function caricaComuniPerProvinciaConConduzioniDanniSuperficiColture(istatProvincia, idComboComuni, disabledIfEmpty)
  {
	  var url = "../cunembo298i/elenco_comuni_conduzioni_superfici_"  + istatProvincia + ".do";
	  return caricaComuniPerProvinciaConConduzioni(istatProvincia, idComboComuni, disabledIfEmpty, url)
  }
  
  function caricaComuniPerProvinciaConConduzioni(istatProvincia, idComboComuni, disabledIfEmpty, url)
  {
	  if(url === undefined || url == null)
	  {
		  url = "../datigeografici/elenco_comuni_conduzioni_"+istatProvincia+".json";
	  }
	  else
	  {
		  console.log("url: " + url);
	  }
	  if (istatProvincia=='')
	  {
	      var $select=$('#'+idComboComuni);
	      $select.empty();
	      $select.append($("<option>",{value:'',text:'-- selezionare--'}));
	      if (disabledIfEmpty)
	      {
	        $select.prop("disabled",true);
	      }
	      return;
	  }
      $.ajax({
        url: url,
        async:false
      }).success(function(data) 
      {
	      var $select=$('#'+idComboComuni);
	      $select.prop("disabled",false);
	      if (disabledIfEmpty)
	      {
	        $select.empty();
	      }
	      $select.append($("<option>",{value:'',text:'-- selezionare --'}));
	      $(data).each(function(index, comune)
	          {
	        $select.append($("<option>",{value:comune['id'],text:comune['descrizione']}));
	          });
	        }).error(function(){alert('Errore nel caricamento di comuni per la provincia con istat '+istatProvincia)});
    
  }
  
  function __caricaSezioni(page, istatComune, idComboSezioni, disabledIfEmpty)
  {
    if (istatComune=='')
    {
      var $select=$('#'+idComboSezioni);
      $select.empty();
      $select.append($("<option>",{value:'',text:'-- selezionare--'}));
      if (disabledIfEmpty)
      {
        $select.prop("disabled",true);
      }
      return;
    }
    $.ajax({
      url: page,
      async:false
    }).success(function(data) 
        {
      var $select=$('#'+idComboSezioni);
      $select.prop("disabled",false);
      if (disabledIfEmpty)
      {
        $select.empty();
      }
      $select.append($("<option>",{value:'',text:'-- selezionare --'}));
      $(data).each(function(index, sezione)
          {
        $select.append($("<option>",{value:sezione['id'],text:sezione['descrizione']}));
          });
        }).error(function(){alert('Errore nel caricamento delle sezioni per il comune con istat '+istatComune)});
    
  }  
  
  function caricaSezioniPerComuniConConduzioni(istatComune, idComboSezioni, disabledIfEmpty)
  {
    return __caricaSezioni("../datigeografici/elenco_sezioni_condotte_"+istatComune+".json", istatComune, idComboSezioni, disabledIfEmpty);
  }
  
  function caricaSezioniPerComuni(istatComune, idComboSezioni, disabledIfEmpty)
  {
    return __caricaSezioni("../datigeografici/elenco_sezioni_"+istatComune+".json",istatComune, idComboSezioni, disabledIfEmpty);
  }
  
  function caricaSezioniPerComuniConConduzioniDanniSuperficiColture(istatComune, idComboSezioni, disabledIfEmpty)
  {
	  return __caricaSezioni("../cunembo298i/elenco_sezioni_comune_"+istatComune+".json", istatComune, idComboSezioni, disabledIfEmpty);
  }

function siNoFormatter($value, row, index)
{
  return '<span>'+($value=='S'?"Si":"No")+'</span>';
}

function numberFormatter($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
      return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency(0)+'</span>';
  }
  catch(e)
  {
    
  }
}

function numberFormatter1($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
    	return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency(1)+'</span>';
  }
  catch(e)
  {
    
  }
}

function numberFormatterGeneric($value, row, index, nDecimalDigits)
{
	  try
	  {
	    $number=Number($value);
	    if (isNaN($number))
	    {
	    	return '<span class="pull-right">'+$value+'</span>';
	    }
	    return '<span class="pull-right">'+$number.formatCurrency(nDecimalDigits)+'</span>';
	  }
	  catch(e)
	  {
	    
	  }
}

function numberFormatter0($value, row, index)
{
	return numberFormatterGeneric($value, row, index,0);
}

function numberFormatter1($value, row, index)
{
	return numberFormatterGeneric($value, row, index,1);
}

function numberFormatter2($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
    	return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency(2)+'</span>';
  }
  catch(e)
  {
    
  }
}

function numberFormatter4($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
    	return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency(4)+'</span>';
  }
  catch(e)
  {
    
  }
}

/**
 * Funzione che deve essere usata come data-adder nel caso in cui sia utilizzato data-formatter="numberFormatterX" in una bootstrap table
 * @param rows
 * @param field
 * @returns {Number}
 */
function dataAdderForNumberFormatter(rows, field) {
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

function toggleButtonFormatter($value, row, index)
{
  try
  {
    return '<div class="center"><input data-toggle="bs-toggle" type="checkbox" value="'+$value+'" /></div>';
  }
  catch(e)
  {
    
  }
}

function euroFormatter($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
      return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency()+' &euro;</span>';
  }
  catch(e)
  {
    
  }
}

function euroFormatterConcat($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
      return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency()+'&euro;</span>';
  }
  catch(e)
  {
    
  }
}
function percentFormatter($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
      return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency()+' &#37;</span>';
  }
  catch(e)
  {
    
  }
}

function numberFormatter4($value, row, index)
{
  try
  {
    $number=Number($value);
    if (isNaN($number))
    {
    	return '<span class="pull-right">'+$value+'</span>';
    }
    return '<span class="pull-right">'+$number.formatCurrency(4)+'</span>';
  }
  catch(e)
  {
    
  }
}

function numberFormatterAcceptNull($value, row, index)
{
	if($value == null)
	{
		return '<span class="pull-right"></span>';
	}
	else{
		return numberFormatter($value, row, index);
	}
}

function numberFormatter2AcceptNull($value, row, index)
{
	if($value == null)
	{
		return '<span class="pull-right"></span>';
	}
	else{
		return numberFormatter2($value, row, index);
	}
}

function numberFormatter4AcceptNull($value, row, index)
{
	if($value == null)
	{
		return '<span class="pull-right"></span>';
	}
	else{
		return numberFormatter4($value, row, index);
	}
}

function dateSorterddmmyyyy(a, b) {
	//date in formato dd/mm/yyyy
    a = a.split('/')[2]+a.split('/')[1]+a.split('/')[0];
    b = b.split('/')[2]+b.split('/')[1]+b.split('/')[0];
    if (a > b) return 1;
    if (a < b) return -1;
    return 0;
}


function dateSorterddmmyyyyHHmmss(a, b) {
	//date in formato dd/mm/yyyy hh:mm:ss
	
	if(a!=null && b!=null && a!="" && b!=""){

	values=a.split("/");
	day=values[0];
	month=values[1];
	
	//resto is yyyy hh:mm:ss
	resto = values[2];

	values=resto.split(' ');
	year = values[0];
	time = values[1].split(':');
	hour = time[0];
	minutes = time[1];
	seconds = time[2];
	
	a = year+month+day+hour+minutes+seconds;
    
	values=b.split('/');
	day=values[0];
	month=values[1];
	
	resto = values[2];

	values=resto.split(' ');
	year = values[0];
	time = values[1].split(':');
	hour = time[0];
	minutes = time[1];
	seconds = time[2];

	b = year+month+day+hour+minutes+seconds;

    if (a > b) return 1;
    if (a < b) return -1;
	}
    return 0;
}

function dataSorterNumeroUnitaMisura(a, b) {
    nA = parseFloat(a.split(' ')[0].replace(/[.]/g,'').replace(/[,]/g,'.'));
    nB = parseFloat(b.split(' ')[0].replace(/[.]/g,'').replace(/[,]/g,'.'));
    umA = a.split(' ')[1];
    umB = b.split(' ')[1];
    if(!isNaN(nA) && !isNaN(nB))
	{
    	if (nA > nB) return 1;
    	if (nA < nB) return -1;    	
	}
    if(umA > umB) return 1;
    if(umA < umB) return -1;
    return 0;
}


function totalFormattedAdder(rows, field)
{
  var __sum=null;
  $(rows).each(function(index, currentRow)
   {
    var value = currentRow[field];
    
    if(value != null)
    {
    	value = value.toString().replace(',','');
    }
    
    if(value!=null && !isNaN(value) && value.length != 0) 
    {
      __sum += parseFloat(value);
    }
  });
  return __sum;
}

function totalNumeriUserInputAdder(rows, field)
{
  var __sum=null;
  $(rows).each(function(index, currentRow)
   {
    var value = currentRow[field];
    
    if(value != null)
    {
      value = value.toString().replace(',','.');
    }
    
    if(value!=null && !isNaN(value) && value.length != 0) 
    {
      __sum += parseFloat(value);
    }
  });
  return __sum;
}

function plainNumberAdder(rows, field)
{
  var __sum=null;
  $(rows).each(function(index, currentRow)
      {
    var value = Number(currentRow[field]);
    
    if(value!=null && !isNaN(value)) 
    {
      __sum += parseFloat(value);
    }
      });
  return __sum;
}
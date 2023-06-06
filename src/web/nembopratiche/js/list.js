function initDropDown()
{
  $('.my-dropdown').on('show.bs.dropdown', function()
  {
    var $dropdown = $(this);
    var $filter = $dropdown.find(".dropdown-filter");
    $input = $filter.val("");
    applyFiltroDropdown($filter.get(0));
  });
}
$(document).ready(initDropDown);

function selectDropdownItem(self)
{
  var $this = $(self);
  var $li=$this.closest('li');
  $li.closest('ul').find('li').removeClass('active');
  $li.addClass('active');
  var $dropdown = $this.closest('.dropdown');
  var $dropdownValue = $dropdown.find('button').find('.dropdown-value');
  $dropdownValue.html($this.html());
  var value=$this.data('value');
  $dropdown.data('value', value);
  $dropdown.find('.my-dropdown-hidden').data('value', value);
  $dropdown.trigger("change");
  return false;
}

function applyFiltroDropdown(self)
{
  var $this = $(self);
  var filtro = jQuery.trim($this.val()).toLowerCase();
  var countVisibili = 0;

  $this.closest('ul').find('li').each(function(index, object)
  {
    $a = $(object).find("a");
    if ($a.length == 0)
    {
      return;
    }
    if (filtro == '')
    {
      $a.css('display', 'block');
      return;
    }
    if ($a.html().toLowerCase().indexOf(filtro) > -1 && $a.data('searchable'))
    {
      countVisibili++;
      $a.css('display', 'block');
    }
    else
    {
      $a.css('display', 'none');
    }
  });
}

function myList_defaultOnClickItem(self, value)
{
  $this=$(self);
  var $list=$this.closest('.my-list');
  if (!$list.data('multiple-selection'))
  {
    $($list.find('a').removeClass('active'));
  }
  $this.toggleClass('active');
  
  return false;
}


function myList_filterList(filter, $list)
{
  filter=jQuery.trim(filter).toLowerCase();
  var countVisibili=0;
  
  $list.children('a').each(function(index, object)
    {
      $object=$(object);
      if ($object.html().toLowerCase().indexOf(filter)>-1)
      {
        countVisibili++;
        $object.css('display','block');
      }
      else
      {
        $object.css('display','none');
      }
    });
}

function dropdown_onKeydown(self, event)
{
  var ch=event.which || event.keyCode;
  if (ch > 47)
  {
    var $this=$(self);
    var $filter = $this.find(".dropdown-filter");
    $filter.focus();
  }
}

function myList_addItem($list, text, value, onclick)
{
  if (onclick==null)
  {
    onclick="return selectDropdownItem(this)";
  }
  if (value==null)
  {
    value="";
  }
  if (text==null)
  {
    text="";
  }
  var title=text.replace(new RegExp('"', 'g'), '&quot;');
  $a=$("<a href='#' class=\"list-group-item\" title=\""+title+"\"data-value=\""+value+"\" onclick=\""+onclick+"\">"+text+"</a>");
  $list.append($a);
}
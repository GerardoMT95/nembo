function initDropDown()
{
  $('.my-dropdown').on('show.bs.dropdown', function()
  {
    let $dropdown = $(this);
    let $filter = $dropdown.find(".dropdown-filter");
    let $input = $filter.val("");
    applyFiltroDropdown($filter.get(0));
  });
}
$(document).ready(initDropDown);

function selectDropdownItem(self)
{
  let $this = $(self);
  let $li=$this.closest('li');
  $li.closest('ul').find('li').removeClass('active');
  $li.addClass('active');
  let $dropdown = $this.closest('.dropdown');
  let $dropdownValue = $dropdown.find('button').find('.dropdown-value');
  $dropdownValue.html($this.html());
  let value=$this.data('value');
  $dropdown.data('value', value);
  $dropdown.find('.my-dropdown-hidden').data('value', value);
  $dropdown.trigger("change");
  return false;
}

function applyFiltroDropdown(self)
{
  let $this = $(self);
  let filtro = jQuery.trim($this.val()).toLowerCase();
  let countVisibili = 0;

  $this.closest('ul').find('li').each(function(index, object)
  {
    let $a = $(object).find("a");
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
  let $this=$(self);
  let $list=$this.closest('.my-list');
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
  let countVisibili=0;
  
  $list.children('a').each(function(index, object)
    {
      let $object=$(object);
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
  let ch=event.which || event.keyCode;
  if (ch > 47)
  {
    let $this=$(self);
    let $filter = $this.find(".dropdown-filter");
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
  let title=text.replace(new RegExp('"', 'g'), '&quot;');
  let $a=$("<a href='#' class=\"list-group-item\" title=\""+title+"\"data-value=\""+value+"\" onclick=\""+onclick+"\">"+text+"</a>");
  $list.append($a);
}
/**
 * jQuery DualListBox plugin with Bootstrap styling v1.0
 * http://www.geodan.nl
 *
 * Copyright (c) 2014 Geodan B.V.
 * Created by: Alex van den Hoogen
 * Modify by: Massimo Durando
 *
 * Usage:
 *   Create a <select> and apply this script to that select via jQuery like so:
 *   $('select').DualListBox(); - the DualListBox will than be created for you.
 *
 *   Options and parameters can be provided through html5 data-* attributes or
 *   via a provided JavaScript object. Optionally already selected items can
 *   also be provided and this script can download the JSON to fill the
 *   DualListBox when a valid URI is provided.
 *
 *   See the default parameters (below) for a complete list of options.
 */

(function($) {
    /** Initializes the DualListBox code as jQuery plugin. */
    $.fn.DualListBox = function(paramOptions, selected) {
        return this.each(function () {
            let defaults = {
                element:    $(this).context,    // Select element which creates this dual list box.
                uri:        'local.json',       // JSON file that can be opened for the data.
                value:      'id',               // Value that is assigned to the value field in the option.
                text:       'name',             // Text that is assigned to the option field.
                title:      'Example',          // Title of the dual list box.
                json:       true,               // Whether to retrieve the data through JSON.
                timeout:    500,                // Timeout for when a filter search is started.
                horizontal: false,              // Whether to layout the dual list box as horizontal or vertical.
                addCombo : false,
                sortText : true,
                textLength: 70,                 // Maximum text length that is displayed in the select.
                moveAllBtn: true,               // Whether the append all button is available.
                maxAllBtn:  512,                // Maximum size of list in which the all button works without warning. See below
                toggle: false,               
                warning:    'Hai superato il limite massimo di 512 elementi!'
            };

            let htmlOptions = {
                element:    $(this).context,
                uri:        $(this).data('source'),
                uriSelected: $(this).data('sourceselected'),
                value:      $(this).data('value'),
                text:       $(this).data('text'),
                title:      $(this).data('title'),
                sortText:      $(this).data('sorttext'),
                json:       $(this).data('json'),
                timeout:    $(this).data('timeout'),
                horizontal: $(this).data('horizontal'),
                textLength: $(this).data('textlength'),
                moveAllBtn: $(this).data('moveallbtn'),
                maxAllBtn:  $(this).data('maxallbtn'),
                toggle:  $(this).data('toggle'),
                actionRefresh:	 $(this).data('refresh'),
                addCombo: $(this).data('addcombo'),
                labelfilter: $(this).data('labelfilter'),
                labelcombo: $(this).data('labelcombo')
            };

            let options = $.extend({}, defaults, htmlOptions, paramOptions);

            $.each(options, function(i, item) {
                if (item === undefined || item === null) { throw 'DualListBox: ' + i + ' is undefined.'; }
            });

            options['parent'] = 'dual-list-box-' + options.title;
            options['parentElement'] = '#' + options.parent;

            selected = $.extend([{}], selected);
           
            if (options.json) {
               addElementsViaJSON(options, selected);     
              pleaseWait(options);
            } else {
                construct(options);
            }   
            let tmpOptions = options;
            //gestione dell'elenco "selected" della dual list
            $('body').unbind('nembopratiche.dualistbox.loaded');
            $('body').on('nembopratiche.dualistbox.loaded', function() {
            	if(!options)
            		options = tmpOptions;
            	if(htmlOptions.uriSelected) {      
            		$.getJSON(htmlOptions.uriSelected, function(json) {
            			if(!options)
                    		options = tmpOptions;
           			   $(options.parentElement + " #selectedList").html('');
           			   $(options.parentElement + " #selectedListHidden").html('');
           			   $.each(json, function(key, item) {
           				   if(htmlOptions.addCombo){
           					   $('<option>', {
           	    	                 value: item[htmlOptions.value],
           	    	                 text: item[htmlOptions.text]
           	    	           }).attr("data-group",item['gruppo']).appendTo($(options.parentElement + ' #selectedList'));
           					   $('<option>', { 
               		                 value: item[htmlOptions.value],
               		                 text: item[htmlOptions.text]
               		            }).attr("data-group",item['gruppo']).appendTo($(options.parentElement + ' #selectedListHidden'));
           				   } else {
           					   $('<option>', {
         	    	                 value: item[htmlOptions.value],
         	    	                 text: item[htmlOptions.text]
         	    	               }).appendTo($(options.parentElement + ' #selectedList'));
           					   $('<option>', {
               		                 value: item[htmlOptions.value],
               		                 text: item[htmlOptions.text]
               		           }).appendTo($(options.parentElement + ' #selectedListHidden'));
           				   }
           				   
           	           });
         	           		
           			   $(options.parentElement + " #selectedList").filterByText($(options.parentElement + '#input.filter.form-control filter-selected'),500,null);
           			   
           			   let options = [];
           	           $(options.parentElement + " #selectedList").find('option').each(function() {
           	             options.push({value: $(this).val(), text: $(this).text()});
           	           });
           			
           	           $(options.parentElement + " #selectedList").data('options', options);
           	           $(options.parentElement + " .selected-count").text(options.length);
           	           loadSelectedComboBox(options.parentElement);
           	           $(options.parentElement + ' #selectedComboBox').val("0");
           	        });
            	}
      			$('.stdMessageLoad').hide();
      	        $('body').trigger('dualistbox.loaded', []);

      		});
        })
    };
    
    function pleaseWait(options)
    {
      $(options.element).parent().append('<span id="'+options.element.id+'-wait-please" class="col-sm-12"><h4>Attendere prego, caricamento in corso...</h4></span>');
    }

    /** Retrieves all the option elements through a JSON request. */
    function addElementsViaJSON(options, selected) {
        let multipleTextFields = false;

        if (options.text.indexOf(':') > -1) {
            let textToUse = options.text.split(':');

            if (textToUse.length > 1) {
                multipleTextFields = true;
            }
        }

        $.getJSON(options.uri, function(json) {
            $.each(json, function(key, item) {
                let text = '';

                if (multipleTextFields) {
                    textToUse.forEach(function (entry) { text += item[entry] + ' '; });
                } else {
                    text = item[options.text];
                }

                $('<option>', { value: item[options.value], text: text, selected: (selected.some(function (e) { return e[options.value] === item[options.value] }) === true) }).attr("data-group",item['gruppo']).appendTo(options.element);
               
            });

            construct(options);
            
            //BUGFIX firefox 
            $(options.parentElement + ' #unselectedComboBox').val("0");
            $(options.parentElement + ' #selectedComboBox').val("0");
            $('body').trigger('nembopratiche.dualistbox.loaded', [$(options.element).attr('id')]);
            $('#'+options.element.id+"-wait-please").hide();
            
        });
    }
    
    /** Adds the event listeners to the buttons and filters. */
    function addListeners(options) {
        let unselected = $(options.parentElement + ' .unselected');
        let selected = $(options.parentElement + ' .selected');

        $(options.parentElement).find('button').bind('click', function() {
            switch ($(this).data('type')) {
                case 'str': /* Selected to the right. */
                	if(unselected.find('option:selected').length >= options.maxAllBtn)
                		alert(options.warning);
                	else
                	{
                		unselected.find('option:selected').clone().appendTo($(options.parentElement + ' #selectedListHidden'));
                		unselected.find('option:selected').each(function (){
                			$(options.parentElement + ' #unselectedListHidden').find('option[value="'+$(this).val()+'"]').remove();
                		});
	                	unselected.find('option:selected').appendTo(selected);
	                    $(this).prop('disabled', true);
	                    eval(options.actionRefresh);
	                    if(options.addCombo)
	                    {
	                    	loadSelectedComboBox(options.parentElement);
	                    	if($(options.parentElement + " #unselectedList option").size()<=0){
	                    		initUnSelectedList(options.parentElement);	
	                    	}
	                    }
	                    else
	                    {
	                    	initUnSelectedList(options.parentElement);
	                    }
                	}
                    break;
                case 'atr': /* All to the right. */
                	if(unselected.find('option').length >= options.maxAllBtn)
                		alert(options.warning);
                	else
                	{
	                    if (unselected.find('option').length >= options.maxAllBtn && confirm(options.warning) ||
	                        unselected.find('option').length < options.maxAllBtn) {
	                        unselected.find('option').each(function () {
	                            if ($(this).isVisible()) {
	                            	$(this).clone().appendTo($(options.parentElement + ' #selectedListHidden'));
	                            	$(options.parentElement + ' #unselectedListHidden').find('option[value="'+$(this).clone().val()+'"]').remove();
	                                $(this).remove().appendTo(selected);
	                            }
	                        });
	                        eval(options.actionRefresh);
	                        if(options.addCombo)
		                    {
		                    	loadSelectedComboBox(options.parentElement);
		                    	if($(options.parentElement + " #unselectedList option").size()<=0){
		                    		initUnSelectedList(options.parentElement);	
		                    	}
		                    }
		                    else
		                    {
		                    	initUnSelectedList(options.parentElement);
		                    }
	                    }
                	}
                    break;
                case 'stl': /* Selected to the left. */
                	if(selected.find('option:selected').length >= options.maxAllBtn)
                		alert(options.warning);
                	else
                	{
                		selected.find('option:selected').each(function () {
                			$(options.parentElement + ' #selectedListHidden').find('option[value="'+$(this).val()+'"]').remove();
                        });
                		
	                	selected.find('option:selected').clone().appendTo($(options.parentElement + ' #unselectedListHidden'));
	                	selected.find('option:selected').remove().appendTo(unselected);
	                    $(this).prop('disabled', true);
	                    eval(options.actionRefresh);
	                    loadUnSelectedComboBox(options.parentElement);
	                    if(options.addCombo && $(options.parentElement + " #selectedList option").size()<=0){
	                		initSelectedList(options.parentElement);	
	                	}
                	}
                    break;
                case 'atl': /* All to the left. */
                	if(selected.find('option').length >= options.maxAllBtn)
                		alert(options.warning);
                	else
                	{
	                    if (selected.find('option').length >= options.maxAllBtn && confirm(options.warning) ||
	                        selected.find('option').length < options.maxAllBtn) {
	                        selected.find('option').each(function () {
	                            if ($(this).isVisible()) {
	                            	$(options.parentElement + ' #selectedListHidden').find('option[value="'+$(this).val()+'"]').remove();
	                            	$(this).clone().appendTo($(options.parentElement + ' #unselectedListHidden'));
	                            	$(this).remove().appendTo(unselected);
	                            }
	                        });
	                        eval(options.actionRefresh);
	                        loadUnSelectedComboBox(options.parentElement);
	                        if(options.addCombo &&  $(options.parentElement + " #selectedList option").size()<=0){
	                    		initSelectedList(options.parentElement);	
	                    	}
	                    }
                	}
                    break;
                case 'tgl': /* Modifica visualizzazione orizzontale - verticale */
                	if(options.horizontal == false)
                	{
                		options.horizontal = true;
                	}
                	else
                	{
                		options.horizontal = false;
                	}
                	
                	let bckSelectedValues = $(options.parentElement + ' #selectedList').html();
                	let bckUnSelectedValues = $(options.parentElement + ' #unselectedList').html();
                	let bckUnselectedComboBox = $(options.parentElement + ' #unselectedComboBox').html();
                	let bckValUnselectedComboBox = $(options.parentElement + ' #unselectedComboBox').val();
                	
                	let bckSelectedComboBox = $(options.parentElement + ' #selectedComboBox').html();
                	let bckValSelectedComboBox = $(options.parentElement + ' #selectedComboBox').val();
                	let bckUnselectedListHidden = $(options.parentElement + ' #unselectedListHidden').html();
                	let bckSelectedListHidden = $(options.parentElement + ' #selectedListHidden').html();
                	
                	$(options['parentElement']).html('');
                    
                    if (options.json) {
                       addElementsViaJSON(options, [{}]);
                      pleaseWait(options);
                    } else {
                        construct(options);
                    }
                    
                    $('body').on('nembopratiche.dualistbox.loaded', function()
                    {
	                    $(options.parentElement + ' #selectedList').html(bckSelectedValues);
	                    $(options.parentElement + ' #unselectedList').html(bckUnSelectedValues);
	                    $(options.parentElement + ' #unselectedComboBox').html(bckUnselectedComboBox);
	                    $(options.parentElement + ' #unselectedComboBox').val(bckValUnselectedComboBox);
	                    $(options.parentElement + ' #selectedComboBox').html(bckSelectedComboBox);
	                    $(options.parentElement + ' #selectedComboBox').val(bckValSelectedComboBox);
	                    $(options.parentElement + ' #unselectedListHidden').html(bckUnselectedListHidden);
	                    $(options.parentElement + ' #selectedListHidden').html(bckSelectedListHidden);
                    });
                    break;  
                default: break;
            }

            if(options.sortText)
            {
	            unselected.filterByText($(options.parentElement + ' .filter-unselected'), options.timeout, options.parentElement).scrollTop(0).sortOptions();
	            selected.filterByText($(options.parentElement + ' .filter-selected'), options.timeout, options.parentElement).scrollTop(0).sortOptions();
            }
            else
            {
	            unselected.filterByText($(options.parentElement + ' .filter-unselected'), options.timeout, options.parentElement).scrollTop(0);
	            selected.filterByText($(options.parentElement + ' .filter-selected'), options.timeout, options.parentElement).scrollTop(0);
            }
            
            handleMovement(options);
        });

        $(options.parentElement).closest('form').submit(function() {
            selected.find('option').prop('selected', true);
        });

        $(options.parentElement).find('input[type="text"]').keypress(function(e) {
            if (e.which === 13) {
                event.preventDefault();
            }
        });

        if(options.sortText)
        {
            selected.filterByText($(options.parentElement + ' .filter-selected'), options.timeout, options.parentElement).scrollTop(0).sortOptions();
            unselected.filterByText($(options.parentElement + ' .filter-unselected'), options.timeout, options.parentElement).scrollTop(0).sortOptions();
        }
        else
        {
            selected.filterByText($(options.parentElement + ' .filter-selected'), options.timeout, options.parentElement).scrollTop(0);
            unselected.filterByText($(options.parentElement + ' .filter-unselected'), options.timeout, options.parentElement).scrollTop(0);
        }
    }

    /** Constructs the jQuery plugin after the elements have been retrieved. */
    function construct(options) {
        createDualListBox(options);
        parseStubListBox(options);
        addListeners(options);
        $(options.parentElement + " #unselectedList").find('option').each(function(){
    		$(this).clone().appendTo(options.parentElement + ' #unselectedListHidden');
    	});
        loadUnSelectedComboBox(options.parentElement);
    }

    

    /** Creates a new dual list box with the right buttons and filter. */
    function createDualListBox(options) {
        $(options.element).parent().attr('id', options.parent);

        $(options.parentElement).addClass('row').append(
        		(options.toggle == false ? ' ' : ('   <div class="col-md-12">'+
        			' <button type="button" title="Cambia tipo visualizzazione" class="btn btn-default pull-right" data-type="tgl" ><span class="glyphicon glyphicon-retweet"></span></button> </div> ' 
        			)) +
        		
                (options.horizontal == false ? '   <div class="col-md-5">' : '   <div class="col-md-12">') +
                '       <h4><span class="unselected-title"></span> <small>- mostrati <span class="unselected-count"></span></small></h4>' +
                
                ((options.labelfilter) ? '<label>'+options.labelcombo+'</label>' : '') +
                ((options.addCombo)
                		? ' <select class="unselectedComboBox form-control" style="margin-bottom: 5px;" id="unselectedComboBox"   name="unselectedComboBox" onchange="filterUnSelectedList(\''+options.parentElement+'\')" style="width: 100%;"><option value="0" selected="true">--seleziona--</option></select>' : ''
                ) +
                
                ((options.labelfilter) ? '<label>'+options.labelfilter+'</label>' : '') +
                '       <input class="filter form-control filter-unselected" type="text" placeholder="Filtra" style="margin-bottom: 5px;">' +
                //ORIGINALE (options.horizontal == false ? '' : createHorizontalButtons(1, options.moveAllBtn)) +
                '       <select class="unselected" id="unselectedList" style="height: 200px; width: 100%;" multiple></select>' +
                '       <select class="unselectedHidden" name="unselectedListHidden" id="unselectedListHidden" style="display:none"></select>' +
                '   </div>' +
                
                (options.horizontal == false ? createVerticalButtons(options.moveAllBtn) : '') + 
                (options.horizontal == false ? '' : '<div class="col-md-12">') +
                (options.horizontal == false ? '' : createHorizontalButtons(1, options.moveAllBtn)) +
                (options.horizontal == false ? '' : createHorizontalButtons(2, options.moveAllBtn)) +
                (options.horizontal == false ? '' : '</div>') +
                
                (options.horizontal == false ? '   <div class="col-md-5">' : '  <div class="col-md-12">') +
                '       <h4><span class="selected-title"></span> <small>- mostrati <span class="selected-count"></span></small></h4>' +
                ((options.labelfilter) ? '<label>'+options.labelcombo+'</label>' : '') +
                ((options.addCombo)
                		? ' <select class="selectedComboBox form-control" style="margin-bottom: 5px;" name="selectedComboBox" id="selectedComboBox"   onchange="filterSelectedList(\''+options.parentElement+'\')" style="width: 100%;"><option value="0" selected="true">--seleziona--</option></select>' : ''
                ) +
                ((options.labelfilter) ? '<label>'+options.labelfilter+'</label>' : '') +
                '       <input class="filter form-control filter-selected" type="text" placeholder="Filtra" style="margin-bottom: 5px;">' +
                
                //ORIGINALE (options.horizontal == false ? '' : createHorizontalButtons(2, options.moveAllBtn)) +
                
                '       <select class="selected" name="selectedList" id="selectedList" style="height: 200px; width: 100%;" multiple></select>' +
                '       <select class="selectedHidden" name="selectedListHidden" id="selectedListHidden" style="display:none"></select>' +
                '   </div>');

        $(options.parentElement + ' .selected').prop('name', $(options.element).prop('name'));
        $(options.parentElement + ' .unselected-title').text(options.title+ ' disponibili');
        $(options.parentElement + ' .selected-title').text(options.title+ ' selezionati');
    }

    function createHorizontalButtons(number, copyAllBtn) {
        if (number == 1) {
            return '<div class="col-md-6" style="margin-top:1em;margin-bottom:1em">' + (copyAllBtn ? '       <button type="button" title="Sposta tutti gli elementi" class="btn btn-default  col-md-6 atr" data-type="atr" style="margin-bottom: 5px;"><span class="glyphicon glyphicon-list"></span> <span class="glyphicon glyphicon-chevron-down"></span></button>': '') +
                '       <button type="button" title="Sposta gli elementi selezionati" class="btn btn-default ' + (copyAllBtn ? 'pull-right col-md-6' : 'col-md-12') + ' str" data-type="str" style="margin-bottom: 5px;" disabled><span class="glyphicon glyphicon-chevron-down"></span></button></div>';
        } else {
            return '  <div class="col-md-6" style="margin-top:1em;margin-bottom:1em">     <button type="button" title="Elimina tutti gli elementi" class="btn btn-default ' + (copyAllBtn ? 'col-md-6' : 'col-md-12') + ' stl" data-type="stl" style="margin-bottom: 5px;" disabled><span class="glyphicon glyphicon-chevron-up"></span></button>' +
                (copyAllBtn ? '       <button type="button" title="Elimina gli elementi selezionati" class="btn btn-default col-md-6 pull-right atl" data-type="atl" style="margin-bottom: 5px;"><span class="glyphicon glyphicon-chevron-up"></span> <span class="glyphicon glyphicon-list"></span></button>' : '') +'</div>';
        }
    }

    
    /** Creates the buttons when the dual list box is set in vertical mode. */
    function createVerticalButtons(copyAllBtn) {
        return '   <div class="col-md-2 center-block" style="margin-top: ' + (copyAllBtn ? '80px' : '130px') +'">' +
            (copyAllBtn ? '       <button type="button" title="Sposta tutti gli elementi" class="btn btn-default col-md-8 col-md-offset-2 atr" data-type="atr" style="margin-bottom: 10px;"><span class="glyphicon glyphicon-list"></span> <span class="glyphicon glyphicon-chevron-right"></span></button>' : '') +
            '       <button type="button" title="Sposta gli elementi selezionati" class="btn btn-default col-md-8 col-md-offset-2 str" data-type="str" style="margin-bottom: 20px;" disabled><span class="glyphicon glyphicon-chevron-right"></span></button>' +
            '       <button type="button" title="Elimina gli elementi selezionati"  class="btn btn-default col-md-8 col-md-offset-2 stl" data-type="stl" style="margin-bottom: 10px;" disabled><span class="glyphicon glyphicon-chevron-left"></span></button>' +
            (copyAllBtn ? '       <button type="button" title="Elimina tutti gli elementi" class="btn btn-default col-md-8 col-md-offset-2 atl" data-type="atl" style="margin-bottom: 10px;"><span class="glyphicon glyphicon-chevron-left"></span> <span class="glyphicon glyphicon-list"></span></button>' : '') +
            '   </div>';
    }

    /** Specifically handles the movement when one or more elements are moved. */
    function handleMovement(options) {
        $(options.parentElement + ' .unselected').find('option:selected').prop('selected', false);
        $(options.parentElement + ' .selected').find('option:selected').prop('selected', false);

        $(options.parentElement + ' .filter').val('');
        $(options.parentElement + ' select').find('option').each(function() { $(this).show(); });

        countElements(options.parentElement);
    }

    /** Parses the stub select / list box that is first created. */
    function parseStubListBox(options) {
        let textIsTooLong = false;

        $(options.element).find('option').text(function (i, text) {
            $(this).data('title', text);
            
            if (text.length > options.textLength) {
                textIsTooLong = true;
                //return text.substr(0, options.textLength) + '...';
                return text;
            }
        }).each(function () {
            if (textIsTooLong) {
                $(this).prop('title', $(this).data('title'));
            }

            if ($(this).is(':selected')) {
                $(this).appendTo(options.parentElement + ' .selected');
            } else {
                $(this).appendTo(options.parentElement + ' .unselected');
            }
        });

        $(options.element).remove();
        handleMovement(options);
    }

    /**
     * Filters through the select boxes and hides when an element doesn't match.
     *
     * Original source: http://www.lessanvaezi.com/filter-select-list-options/
     */
    $.fn.filterByText = function(textBox, timeout, parentElement) {
    	return this.each(function() {
            let select = this;
            let options = [];

            $(select).find('option').each(function() {
                options.push({value: $(this).val(), text: $(this).text()});
            });

            $(select).next().data('options', options);

            $(textBox).bind('keyup', function() {
                delay(function() 
                {
                	let options = $(select).next().data('options');
                    if(options != '')
                    {
                    	$(select).html('');
                    }
                    let search = $.trim($(textBox).val());
                    let regex = new RegExp(search.replace('%',''),'gi');
                    $.each(options, function(i) {
                    	
                    	if(search.indexOf('%') === 0) {
                    		if(options[i].text.match(regex) === null) {
                    			//$(select).next().find($('option[value="' + options[i].value + '"]')).hide();
                            } else {
                            	$(select).append($(select).next().find($('option[value="' + options[i].value + '"]')).clone());
                            }
                    	}
                    	else
                    	{
	                    	if(options[i].text.toUpperCase().indexOf(search.toUpperCase()) === 0) {
	                    		$(select).append($(select).next().find($('option[value="' + options[i].value + '"]')).clone());
	                        } else {
	                            //$(select).next().find($('option[value="' + options[i].value + '"]')).hide();
	                        }
                    	}
                    });
                	
                    if(!$('option[value="' + options[0].value + '"]').data('group'))
                    {
                    	$(select).sortOptions();
                    }
                    
                    countElements(parentElement);
                }, timeout);
            });
        });
    };
    
    //created by Michele Macagno
    $.fn.updateData = function() {
        console.log("updateData");
        let options = $.extend({}, this.defaults, this.htmlOptions, this.paramOptions);
        options['parent'] = 'dual-list-box-' + options.title;
        options['parentElement'] = '#' + options.parent;

        selected = $.extend([{}], selected);
       
        if (options.json) {
           addElementsViaJSON(options, selected);     
          pleaseWait(options);
        } else {
            construct(options);
        }   
        return "ciao";
    };

    /** Checks whether or not an element is visible. The default jQuery implementation doesn't work. */
    $.fn.isVisible = function() {
        return !($(this).css('visibility') == 'hidden' || $(this).css('display') == 'none');
    };

    /** Sorts options in a select / list box. */
    $.fn.sortOptions = function() {
        return this.each(function() {
            $(this).append($(this).find('option').remove().sort(function(a, b) {
                let at = $(a).text(), bt = $(b).text();
                return (at > bt) ? 1 : ((at < bt) ? -1 : 0);
            }));
        });
    };

    /** Simple delay function that can wrap around an existing function and provides a callback. */
    let delay = (function() {
        let timer = 0;
        return function (callback, ms) {
            clearTimeout(timer);
            timer = setTimeout(callback, ms);
        };
    })();
})(jQuery);

function initSelectedList(parentElement)
{
	$(parentElement+" #selectedList").html('');
	$(parentElement+ ' #selectedListHidden option').each(function(){
		let $options = $(this).clone();
		$options.appendTo(parentElement+" #selectedList");
	});
	countElements(parentElement);
}

function initUnSelectedList(parentElement)
{
	$(parentElement+" #unselectedList").html('');
	$(parentElement+' #unselectedListHidden option').each(function(){
		let $options = $(this).clone();
		$options.appendTo(parentElement+" #unselectedList");
	});
	countElements(parentElement);
}

function filterUnSelectedList(parentElement)
{
	//cerco nella hidden list tutte le option che abbiano questo gruppo e le copio nella lista visibile
	$(parentElement+" #unselectedList").html('');
	let val = $(parentElement+" #unselectedComboBox").val();
	
	if( val == '0')
	{
		if($(parentElement+' #selectedListHidden option[value="'+val+'"]').length <= 0){
			$(parentElement+" #unselectedListHidden option").each(function(){
				let $options = $(this).clone();
				$options.appendTo(parentElement+" #unselectedList");
			});
		}
	}
	else
	{
		if($(parentElement+' #selectedListHidden option[value="'+val+'"]').length <= 0){
			$(parentElement+' #unselectedListHidden option[data-group="'+val+'"]').each(function(){
				let $options = $(this).clone();
				$options.appendTo(parentElement+" #unselectedList");
			});
		}
	}
	
	countElements(parentElement);
}

/** Counts the elements per list box/select and shows it. */
function countElements(parentElement) {
    let countUnselected = 0, countSelected = 0;

    $(parentElement + ' .unselected').find('option').each(function() { if ($(this).isVisible()) { countUnselected++; } });
    $(parentElement + ' .selected').find('option').each(function() { if ($(this).isVisible()) { countSelected++ } });

    $(parentElement + ' .unselected-count').text(countUnselected);
    $(parentElement + ' .selected-count').text(countSelected);

    toggleButtons(parentElement);
}

/** Toggles the buttons based on the length of the selects. */
function toggleButtons(parentElement) {
    $(parentElement + ' .unselected').change(function() {
        $(parentElement + ' .str').prop('disabled', false);
    });

    $(parentElement + ' .selected').change(function() {
        $(parentElement + ' .stl').prop('disabled', false);
    });

    if ($(parentElement + ' .unselected').has('option').length == 0) {
        $(parentElement + ' .str').prop('disabled', true);
       	$(parentElement + ' .atr').prop('disabled', false);
    } else {
        $(parentElement + ' .atr').prop('disabled', false);
    }

    if ($(parentElement + ' .selected').has('option').length == 0) {
        $(parentElement + ' .stl').prop('disabled', true);
    } else {
        $(parentElement + ' .atl').prop('disabled', false);
    }
}

function filterSelectedList(parentElement)
{
	//cerco nella hidden list tutte le option che abbiano questo gruppo e le copio nella lista visibile
	$(parentElement+" #selectedList").html('');
	let val = $(parentElement+" #selectedComboBox").val();
	
	if( val == '0')
	{
		$(parentElement+" #selectedListHidden option").each(function(){
			let $options = $(this).clone();
			$options.appendTo(parentElement+" #selectedList");
		});
	}
	else
	{
		$(parentElement+' #selectedListHidden option[data-group="'+val+'"]').each(function(){
			let $options = $(this).clone();
			$options.appendTo(parentElement+" #selectedList");
		});
	}
	countElements(parentElement);
}

function loadSelectedComboBox(parentElement)
{
	$(parentElement+" #selectedComboBox").html('');
	$('<option value="0" selected="selected">--seleziona--</option>').appendTo($(parentElement+" #selectedComboBox"));
	
	let tmp = "";
	
	$(parentElement+" #selectedListHidden option").each(function(){
		let gruppo = $(this).attr("data-group");
		if(tmp.indexOf("&&"+gruppo+"&_&") == -1)
		{
			tmp = tmp + "&&"+gruppo+"&_&";
			$('<option>', { value: gruppo, text: gruppo }).appendTo($(parentElement+" #selectedComboBox"));
		}
	});
	
	$(parentElement+" #selectedComboBox").sortOptions();
	$(parentElement+" #selectedList").sortOptions();
	
	
}

function loadUnSelectedComboBox(parentElement)
{
	$(parentElement+" #unselectedComboBox").html('');
	$('<option value="0" selected="selected">--seleziona--</option>').appendTo($(parentElement+" #unselectedComboBox"));
	
	let tmp = "";
	
	$(parentElement+" #unselectedListHidden option").each(function(){
		let gruppo = $(this).attr("data-group");
		if(tmp.indexOf("&&"+gruppo+"&_&") == -1)
		{
			tmp = tmp + "&&"+gruppo+"&_&";
			$('<option>', { value: gruppo, text: gruppo}).appendTo($(parentElement+" #unselectedComboBox"));
		}
	});
	
	$(parentElement+" #unselectedComboBox").sortOptions();
	
	
}

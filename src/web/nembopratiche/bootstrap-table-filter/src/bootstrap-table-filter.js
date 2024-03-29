!function($) {

    'use strict';

    // TOOLS DEFINITION
    // ======================
    

    let rowLabel = function(el) {
        let ret = el;
        if (typeof el === 'object') {
            ret = el.label;
            if (typeof el.i18n === 'object') {
                $.each(el.i18n, function(key, val) { ret = ret.replace('{%' + key + '}', val) });
            }
        }
        return ret;
    };
    let rowId = function(id, el) {
        return typeof el === 'object' ? el.id : id;
    };
    let getOptionData = function($option) {
        let val = false;
        let name;
        let data = {}, cnt = 0;
        let $chck = $option.find('.filter-enabled');
        $(':input', $option).each(function() {
            let $this = $(this);
            if ($this.is($chck)) {
                return;
            }
            name = $this.attr('data-name');
            if (name) {
                data[name] = $this.val();
            }
            val = $this.val();
            cnt++;
        });
        return $.isEmptyObject(data) ? val : data;
    };


    // FILTER CLASS DEFINITION
    // ======================
    let BootstrapTableFilter = function(el, options) {

        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.timeoutId_ = 0;
        this.filters = {};

        this.init();
    };

    BootstrapTableFilter.DEFAULTS = { filters: [], connectTo: false,
        filterIcon: '<span class="glyphicon glyphicon-filter"></span>',
        refreshIcon: '<span class="glyphicon glyphicon-ok"></span>',
        clearAllIcon: '<span class="glyphicon glyphicon-remove"></span>',

        formatRemoveFiltersMessage: function() {
            return 'Remove all filters';
        },
        formatSearchMessage: function() {
            return 'Search';
        },

        onAll: function(name, args) {
            return false;
        },
        onFilterChanged: function(data) {
            return false;
        },
        onResetView: function() {
            return false;
        },
        onAddFilter: function(filter) {
            return false;
        },
        onRemoveFilter: function(field) {
            return false;
        },
        onEnableFilter: function(field) {
            return false;
        },
        onDisableFilter: function(field) {
            return false;
        },
        onSelectFilterOption: function(field, option, data) {
            return false;
        },
        onUnselectFilterOption: function(field, option) {
            return false;
        },
        onDataChanged: function(data) {
            return false;
        },
        onSubmit: function(data) {
            return false;
        },
        onRefreshSuccess: function(data) {
            return false;
        },
        onSubmitClick: function(data) {
            return false;
        } 
    };

    BootstrapTableFilter.EVENTS = {
        'all.bs.table.filter': 'onAll',
        'reset.bs.table.filter': 'onResetView',
        'add-filter.bs.table.filter': 'onAddFilter',
        'remove-filter.bs.table.filter': 'onRemoveFilter',
        'enable-filter.bs.table.filter': 'onEnableFilter',
        'disable-filter.bs.table.filter': 'onDisableFilter',
        'select-filter-option.bs.table.filter': 'onSelectFilterOption',
        'unselect-filter-option.bs.table.filter': 'onUnselectFilterOption',
        'data-changed.bs.table.filter': 'onDataChanged',
        'submit.bs.table.filter': 'onSubmit',
        'refresh-success.bs.table.filter': 'onRefreshSuccess',
        'submit-click-button.bs.table.filter': 'onSubmitClick'

    };


    BootstrapTableFilter.FILTER_SOURCES = {
        range: {
            search: false,
            rows: [
                {id: 'lte', label: '{%msg} <input class="form-control" type="text">', i18n: {msg: 'Less than'}},
                {id: 'gte', label: '{%msg} <input class="form-control" type="text">', i18n: {msg: 'More than'}},
                {id: 'eq', label: '{%msg} <input class="form-control" type="text">', i18n: {msg: 'Equals'}}
            ],
            check: function(filterData, value) {
                if (typeof filterData.lte !== 'undefined' && parseInt(value) > parseInt(filterData.lte)) {
                    return false;
                }
                if (typeof filterData.gte !== 'undefined' && parseInt(value) < parseInt(filterData.gte)) {
                    return false;
                }
                if (typeof filterData.eq !== 'undefined' && parseInt(value) != parseInt(filterData.eq)) {
                    return false;
                }
                return true;
            }
        }, 
        date: {
            search: false,
            rows: [
                 {id: 'lte', label: '{%msg} <div class="input-group input-append" onclick="showDatepickerLess(IDTOSUBSTITUTE);"><input  type="text"  style="width:100%;min-width:15em;" class="form-control datepickerInput datepickerInputLess" name="date" id="datepickerInputLessIDTOSUBSTITUTE"><span class="input-group-addon  add-on"><span class="glyphicon glyphicon-calendar"></span></span></input> </div> <div class="datepickerFilter" id="datepickerFilterLessIDTOSUBSTITUTE" style="margin-top:3em" ></div>', i18n: {msg: 'Less than'}},
                 {id: 'gte', label: '{%msg}<div class="input-group input-append" onclick="showDatepickerMore(IDTOSUBSTITUTE)" ><input  type="text"  style="width:100%;min-width:15em;" class="form-control datepickerInput datepickerInputMore" name="date" id="datepickerInputMoreIDTOSUBSTITUTE"><span class="input-group-addon  add-on"><span class="glyphicon glyphicon-calendar"></span></span></input> </div> <div class="datepickerFilter" id="datepickerFilterMoreIDTOSUBSTITUTE" style="margin-top:3em" ></div>', i18n: {msg: 'More than'}},
                 {id: 'eq', label: '{%msg} <div class="input-group input-append" onclick="showDatepickerEq(IDTOSUBSTITUTE)" ><input  type="text"  style="width:100%;min-width:15em;" class="form-control datepickerInput datepickerInputEq" name="date" id="datepickerInputEqIDTOSUBSTITUTE"><span class="input-group-addon  add-on"><span class="glyphicon glyphicon-calendar"></span></span></input> </div> <div class="datepickerFilter" id="datepickerFilterEqIDTOSUBSTITUTE" style="margin-top:3em" ></div>', i18n: {msg: 'Equals'}},
                 {id: 'neq', label: '{%msg} <div class="input-group input-append" onclick="showDatepickerNeq(IDTOSUBSTITUTE)" ><input  type="text"  style="width:100%;min-width:15em;" class="form-control datepickerInput datepickerInputNeq" name="date" id="datepickerInputNeqIDTOSUBSTITUTE"><span class="input-group-addon  add-on"><span class="glyphicon glyphicon-calendar"></span></span></input> </div> <div class="datepickerFilter" id="datepickerFilterNeqIDTOSUBSTITUTE" style="margin-top:3em" ></div>', i18n: {msg: 'Not equals'}}
                 ],
            check: function(filterData, value) {

            	let v, values, day,month,year,time,hour,minutes,seconds,valueToCompare,val,resto;
            	  if(value == null)
                  {
                  	value="";
                  }
            	  if(value!=""){
            		let v = value.toString();
            		let values=v.split('/');
            		let day=values[0];
            		let month=values[1];
	              	//resto is yyyy hh:mm:ss
                      let resto = values[2];

            		if(resto.length != 4 && resto.length!=5)
	              	{
            			let values=resto.split(' ');
                        let year = values[0];
                        let time = values[1].split(':');
                        let hour = time[0];
                        let minutes = time[1];
                        let seconds = time[2];
	              	}
            		else
                    let year = resto.trim(); 
            		
                    let valueToCompare = year+month+day;
	              	
	              	
              	if (typeof filterData.eq !== 'undefined') {
              		
              		if(filterData.eq !=""){
              			{
                			filterData.eq=filterData.eq.trim();
              				values=filterData.eq.split('/');
              			}
	              	let day=values[0];
	              	let month=values[1];
	              	let year = values[2];
	              	let val = year+month+day;
              		
              		if(val != valueToCompare)
                      return false;
              		}
                  }
                  if (typeof filterData.neq !== 'undefined') {
                	  if(filterData.neq !=""){
                    		{
                    			filterData.neq=filterData.neq.trim();
                    			values=filterData.neq.split('/');
                    		}
      	              	let day=values[0];
      	              	let month=values[1];
      	              	let year = values[2];
      	              	let val = year+month+day;
      	              	
      	              if(val == valueToCompare)
                          return false;
                  		}
            		}
                  if (typeof filterData.lte !== 'undefined') {
                	  
                	  if(filterData.lte !=""){
                    		{
                    			filterData.lte=filterData.lte.trim();
                    			values=filterData.lte.split('/');
                    		}
      	              	let day=values[0];
      	              	let month=values[1];
      	              	let year = values[2];
      	              	let val = year+month+day;
      	              	
      	              if(val <= valueToCompare)
                          return false;
                  }
                  }
                  if (typeof filterData.gte !== 'undefined') {
                	  
                	  if(filterData.gte !=""){
                    		{
                    			filterData.gte=filterData.gte.trim();
                    			values=filterData.gte.split('/');
                    		}
      	              	let alues[0];
      	              	let month=values[1];
      	              	let year = values[2];
      	              	let val = year+month+day;
                  }
                	  if(val >= valueToCompare)
                          return false;
                  }
        
            	  }else return false;
                  return true;

            }
            
        }, 
        search: {
            search: false,
            rows: [
                {id: 'eq', label: '{%msg} <input class="form-control" type="text">', i18n: {msg: 'Equals'}},
                {id: 'neq', label: '{%msg} <input class="form-control" type="text">', i18n: {msg: 'Not equals'}},
                {id: 'cnt', label: '{%msg} <input class="form-control" type="text">', i18n: {msg: 'Contains'}},
                {id: 'ncnt', label: '{%msg} <input class="form-control" type="text">', i18n: {msg: 'Doesn\'t contain'}},
                {id: 'ept', label: '{%msg}', i18n: {msg: 'Is empty'}},
                {id: 'nept', label: '{%msg}', i18n: {msg: 'Is not empty'}}
            ],
            check: function(filterData, value) {
            	if (typeof filterData._values !== 'undefined' && filterData._values.indexOf('ept') >= 0 && value != null) {
                    return false;
                }    
                if (typeof filterData._values !== 'undefined' && filterData._values.indexOf('nept') >= 0 && value == null) {
                    return false;
                }
                if(value == null)
                {
                	value="";
                }
                
                value = value.toUpperCase();
            	
            	if (typeof filterData.eq !== 'undefined' && value != filterData.eq.toUpperCase()) {
                    return false;
                }
                if (typeof filterData.neq !== 'undefined' && value == filterData.neq.toUpperCase()) {
                    return false;
                }
                
                if (typeof filterData.cnt !== 'undefined' && value.indexOf(filterData.cnt.toUpperCase()) < 0) {
                    return false;
                }
                if (typeof filterData.ncnt !== 'undefined' && value.indexOf(filterData.ncnt.toUpperCase()) >= 0) {
                    return false;
                }
                
                return true;
            }
        },
        searchText: {
            search: true,
            rows: [
            ],
            check: function(filterData, value) {
            	
            	if(value===undefined || value==null)
            		return false;
            	
                value = value.toUpperCase();
                let searchPhrase = $(".search-values").val();
                searchPhrase = searchPhrase.toUpperCase();

                if (value.indexOf(searchPhrase) == -1) {
                    return false;
                }
                
                return true;
            }
        },
        ajaxSelect: {
            search: false,
            rows: [],
            rowsCallback: function(filter, searchPhrase) {
                let that = this;
                //clearTimeout(this.timeoutId_);
                this.timeoutId_ = setTimeout(function() {
                    $.ajax(filter.source, {dataType: 'json', async:true, data: {q: searchPhrase}})
                    .done(function(data) {
                        that.clearFilterOptions(filter.field);
                        that.fillFilterOptions(filter.field, data);
                    });
                }, 300);
            }
        },
        ajaxSelectList: {
            search: false,
            rows: [],
            rowsCallback: function(filter, searchPhrase) {
                let that = this;
                //clearTimeout(this.timeoutId_);
                this.timeoutId_ = setTimeout(function() {
                    $.ajax(filter.source, {dataType: 'json', async:true, data: {q: searchPhrase}})
                    .done(function(data) {
                        that.clearFilterOptionsList(filter.field);
                        that.fillFilterOptionsList(filter.field, data);
                    });
                }, 300);
            },
            check: function(filterData, value) {
            	
            	let ok = false;
            	
            	let i =0;
            	for (i=0;i< filterData._values.length;i++) {
            		let s = "&&&" + filterData._values[i] + "&&&";
            		  if(value.indexOf(s)>-1)
            			  ok = true;
            		}
            	
            	return ok ;           	
            }
        },
        select: {
            search: true,
            rows: [],
            rowsCallback: function(filter, searchPhrase) {
                let vals = filter.values;
                let label;
                if (searchPhrase.length) {
                    vals = vals.filter(function(el) {
                        return rowLabel(el).indexOf(searchPhrase) > -1;
                    });
                }
                this.clearFilterOptions(filter.field);
                this.fillFilterOptions(filter.field, vals.slice(0, 20));
            }
        }
        ,
        selectNoSearch: {
            search: false,
            rows: [],
            rowsCallback: function(filter, searchPhrase) {
                let vals = filter.values;
                let label;
                if (searchPhrase.length) {
                    vals = vals.filter(function(el) {
                        return rowLabel(el).indexOf(searchPhrase) > -1;
                    });
                }
                this.clearFilterOptions(filter.field);
                this.fillFilterOptions(filter.field, vals.slice(0, 20));
            }
        }
        ,
        custom: {
            search: false,
            rows: [],
            customCallback: function(filter, searchPhrase) {
            	 let that = this;
                 //clearTimeout(this.timeoutId_);
                 this.timeoutId_ = setTimeout(function() {
                     $.ajax(filter.source)
                     .done(function(data) {
                         filter.$dropdownList.append("<li>"+data+"</li>");
                     });
                 }, 300);
            	 
            }
        },
        customWithCustomCheck: {
            search: false,
            rows: [],
            customCallback: function(filter, searchPhrase) {
            	 let that = this;
                 //clearTimeout(this.timeoutId_);
                 this.timeoutId_ = setTimeout(function() {
                     $.ajax(filter.source)
                     .done(function(data) {
                         filter.$dropdownList.append("<li>"+data+"</li>");
                     });
                 }, 300);
            	 
            }  ,
            check: function(filterData, value) {
            	//added by nicolo - filtro specifico per CUPSR215
            	let y = filterData._values[filterData._values.length-1];
            	//Estratti a campione tutti (y=0)
            	//Estratti a campione (Controllo in Loco) (y=1)
            	//tutti (y=-1)
            	if(y!==undefined)
            		{
            		
		            	let x = value;
		            	if(y==-1) //tutti -> torno sempre true
		            		return true;
		            	if(y==0)
		            		if(x!=null)
		            			return true; 
		            		else 
		            			return false;
		            	
		            	if(y==1)
		            		if(x!=null && x!=1)
		            			return true;
		            		else
		            			return false; 
            		}
            	return false;
            }
        }
    };

    BootstrapTableFilter.EXTERNALS = [];

    BootstrapTableFilter.prototype.init = function() {
    	this.initContainer();
        this.initMainButton();
        this.initFilters();
        this.initRefreshButton();
        this.initFilterSelector();
        this.initExternals(); 	
    };

    BootstrapTableFilter.prototype.initContainer = function() {
        let that = this;
        this.$toolbar = $([
            '<div class="btn-toolbar">',
                '<div class="btn-group btn-group-filter-main">',
                    '<button type="button" class="btn btn-default dropdown-toggle btn-filter" data-toggle="dropdown">',
                        this.options.filterIcon,
                    '</button>',
                    '<ul class="dropdown-menu" role="menu">',
                    '</ul>',
                '</div>',
                '<div class="btn-group btn-group-filters">',
                '</div>',
                '<div class="btn-group btn-group-filter-refresh">',
                    '<button type="button" class="btn btn-default btn-primary btn-refresh" data-toggle="dropdown">',
                        this.options.refreshIcon,
                    '</button>',
                '</div>',
            '</div>'
        ].join(''));
        this.$toolbar.appendTo(this.$el);
        this.$filters = this.$toolbar.find('.btn-group-filters');

        //avoid the closure of the drop-down menu
        this.$toolbar.delegate('.btn-group-filters li ', 'click', function (e) {
        	 e.stopImmediatePropagation();
        });
        
        //my delegate - hide datepickers on changing date
       this.$toolbar.delegate('div[id^="datepickerFilterLess"]', 'change', function (e) {
       	 let val = $(this).val();
  		let idStr = this.id;
  		let numberOfDateFilter = idStr[idStr.length-1];
       	 $('#datepickerFilterLess'+numberOfDateFilter).hide();
       	 $('#datepickerFilterLess'+numberOfDateFilter).trigger('click');
       	 $('#datepickerInputLess'+numberOfDateFilter).trigger('click');
       	 showDatepickerLess(numberOfDateFilter);
       	 $('#datepickerFilterLess'+numberOfDateFilter).hide();
    	 e.stopImmediatePropagation();
       });

       this.$toolbar.delegate('div[id^="datepickerFilterMore"]', 'change', function (e) {

          	 let val = $(this).val();
       		let idStr = this.id;
      		let numberOfDateFilter = idStr[idStr.length-1];
          	 $('#datepickerFilterMore'+numberOfDateFilter).hide();
          	 $('#datepickerFilterMore'+numberOfDateFilter).trigger('click');
          	 $('#datepickerInputMore'+numberOfDateFilter).trigger('click');
           	 showDatepickerMore(numberOfDateFilter);
          	 $('#datepickerFilterMore'+numberOfDateFilter).hide();
        	 e.stopImmediatePropagation();
          });

       this.$toolbar.delegate('div[id^="datepickerFilterEq"]', 'change', function (e) {

          	 let val = $(this).val();
       		let idStr = this.id;
      		let numberOfDateFilter = idStr[idStr.length-1];
          	 $('#datepickerFilterEq'+numberOfDateFilter).hide();
          	 $('#datepickerFilterEq'+numberOfDateFilter).trigger('click');
          	 $('#datepickerInputEq'+numberOfDateFilter).trigger('click');
           	 showDatepickerEq(numberOfDateFilter);
          	 $('#datepickerFilterEq'+numberOfDateFilter).hide();
        	 e.stopImmediatePropagation();
          });

       this.$toolbar.delegate('div[id^="datepickerFilterNeq"]', 'change', function (e) {

          	 let val = $(this).val();
       		let idStr = this.id;
      		let numberOfDateFilter = idStr[idStr.length-1];
          	 $('#datepickerFilterNeq'+numberOfDateFilter).hide();
          	 $('#datepickerFilterNeq'+numberOfDateFilter).trigger('click');
          	 $('#datepickerInputNeq'+numberOfDateFilter).trigger('click');
           	 showDatepickerNeq(numberOfDateFilter);
          	 $('#datepickerFilterNeq'+numberOfDateFilter).hide();
        	 e.stopImmediatePropagation();
          });
        
        $(document).on('click', '.ui-datepicker-next', function (e) {
         	 	e.stopImmediatePropagation();
        	})
        $(document).on('click', '.ui-datepicker-prev', function (e) {
         	 	e.stopImmediatePropagation();
        	})

        
       this.$toolbar.delegate('.btn-group-filters li .filter-enabled', 'click', function(e) {
        	let $chck = $(this);
            let field = $chck.closest('[data-filter-field]').attr('data-filter-field');
            let $option = $chck.closest('[data-val]');
            let option = $option.attr('data-val');
            
            if ($chck.prop('checked')) {
                let data = getOptionData($option);              
                that.selectFilterOption(field, option, data);
            }
            else {
                that.unselectFilterOption(field, option);
            }
            
                	e.stopImmediatePropagation();
        });


        this.$toolbar.delegate('.btn-group-filters li :input:not(.filter-enabled)', 'click change', function(e) {
            let $inp = $(this);
            let field = $inp.closest('[data-filter-field]').attr('data-filter-field');
            let $option = $inp.closest('[data-val]');
            let option = $option.attr('data-val');
            let $chck = $option.find('.filter-enabled');
            if ($inp.val()) {
                let data = getOptionData($option);
                that.selectFilterOption(field, option, data);
                $chck.prop('checked', true);
            }
            else {
                that.unselectFilterOption(field, option);
                $chck.prop('checked', false);
            }

            e.stopImmediatePropagation();
        });
        this.$toolbar.delegate('.search-values', 'keyup', function(e) {
            let $this = $(this);
            let phrase = $this.val();
            let field = $this.closest('[data-filter-field]').attr('data-filter-field');
            let filter = that.getFilter(field);
            let fType = that.getFilterType(filter);
            if (fType.rowsCallback) {
                fType.rowsCallback.call(that, filter, phrase);
            }
        });
    };

    BootstrapTableFilter.prototype.initMainButton = function() {
        this.$button = this.$toolbar.find('.btn-filter');
        this.$buttonList = this.$button.parent().find('.dropdown-menu');
        this.$button.dropdown();
    };

    BootstrapTableFilter.prototype.initRefreshButton = function() {
        let that = this;
        this.$refreshButton = this.$toolbar.find('.btn-refresh');

        this.$refreshButton.click(function(e) {
            that.trigger('submit', that.getData());
            that.toggleRefreshButton(false);
            that.trigger('submit-click-button');

        });
        this.toggleRefreshButton(false);
    };

    BootstrapTableFilter.prototype.initFilters = function() {
        let that = this;
        this.$buttonList.append('<li class="remove-filters"><a href="javascript:void(0)">' + this.options.clearAllIcon + ' ' + this.options.formatRemoveFiltersMessage() + '</a></li>');
        this.$buttonList.append('<li class="divider"></li>');
        $.each(this.options.filters, function(i, filter) {
            that.addFilter(filter);
        });
        this.$toolbar.delegate('.remove-filters *', 'click', function() {
            that.disableFilters();
            that.trigger('submit', that.getData());
            that.toggleRefreshButton(false);
            that.trigger('refresh-success');

        });
        
        
    };

    BootstrapTableFilter.prototype.initFilterSelector = function() {
        let that = this;
        let applyFilter = function($chck) {
            let filterField = $chck.closest('[data-filter-field]').attr('data-filter-field');
            if ($chck.prop('checked')) {
                that.enableFilter(filterField);
            }
            else {
                that.disableFilter(filterField);
            }
        };
        this.$buttonList.delegate('li :input[type=checkbox]', 'click', function(e) {
            applyFilter($(this));
            e.stopImmediatePropagation();
        });
        this.$buttonList.delegate('li, li a', 'click', function(e) {
            let $chck = $(':input[type=checkbox]', this);
            if ($chck.length) {
                $chck.prop('checked', !$chck.is(':checked'));
                applyFilter($chck);
                e.stopImmediatePropagation();
            }
            let $inp = $(':input[type=text]', this);
            if ($inp.length) {
                $inp.focus();
            }
        });
    };

    BootstrapTableFilter.prototype.initExternals = function() {
        let that = this;
        $.each(BootstrapTableFilter.EXTERNALS, function(i, ext) {
            ext.call(that);
        });
    }

    BootstrapTableFilter.prototype.getFilter = function(field) {
        if (typeof this.filters[field] === 'undefined') {
            throw 'Invalid filter ' + field;
        }
        return this.filters[field];
    };
    BootstrapTableFilter.prototype.getFilterType = function(field, type) {
        if (field) {
            let filter = typeof field === 'object' ? field : this.getFilter(field);
            type = filter.type;
        }
        if (typeof BootstrapTableFilter.FILTER_SOURCES[type] === 'undefined') {
            throw 'Invalid filter type ' + type;
        }
        let ret = BootstrapTableFilter.FILTER_SOURCES[type];
        if (typeof ret.extend !== 'undefined') {
            ret = $.extend({}, ret, this.getFilterType(null, ret.extend));
        }
        return ret;
    };
    BootstrapTableFilter.prototype.checkFilterTypeValue = function(filterType, filterData, value) {
        if (typeof filterType.check === 'function') {
            return filterType.check(filterData, value);
        }
        else {
            if (typeof filterData._values !== 'undefined') {
                return $.inArray("" + value, filterData._values) >= 0;
            }
        }
        return true;
    };

    BootstrapTableFilter.prototype.clearFilterOptions = function(field) {
        let filter = this.getFilter(field);
        filter.$dropdownList.find('li:not(.static)').remove();
        this.toggleRefreshButton(true);
    };


    BootstrapTableFilter.prototype.fillFilterOptions = function(field, data, cls) {
        let that = this;
        let filter = this.getFilter(field);
       
        cls = cls || '';
        let option, checked;
        $.each(data, function(i, row) {
        	
        	//deep copy dell'oggetto row (così non lo modifico e resta la stringa IDTOSUBSTITUTE)
        	let myRow = jQuery.extend(true, {}, row);
        	 if(filter.type=="date")
             	//increment id - change substring
             	{
             		let numberOfDateFilter = $("#numberOfDateFilter").val();
             		//modifico myRow
             		myRow.label=myRow.label.replace(/IDTOSUBSTITUTE/g, numberOfDateFilter.toString());
             	}            
            
            option = rowId(i, myRow);
            checked = that.isSelected(field, option);
        	
            //filter.$dropdownList.append($('<li data-val="' + option + '" class="' + cls + '"><a href="javascript:void(0)"><input type="checkbox" class="filter-enabled"' + (checked ? ' checked' : '') + '> ' + rowLabel(row) + '</a></li>'));
            filter.$dropdownList.append($('<li data-val="' + option + '" class="' + cls + '"><div class="groupdiv"><input type="checkbox" class="filter-enabled"' + (checked ? ' checked' : '') + '> ' + rowLabel(myRow) + '</div></li>'));
        });
    };
/*NICO LIST SELEC AJAX*/
    BootstrapTableFilter.prototype.clearFilterOptionsList = function(field) {
        let filter = this.getFilter(field);
        filter.$dropdownList.find('li:not(.static)').remove();
        this.toggleRefreshButton(true);
    };

    BootstrapTableFilter.prototype.fillFilterOptionsList = function(field, data, cls) {
        let that = this;
        let filter = this.getFilter(field);
        cls = cls || '';
        let option, checked;
        $.each(data, function(i, row) {
            option = rowId(i, row);
            checked = that.isSelected(field, option);
            //filter.$dropdownList.append($('<li data-val="' + option + '" class="' + cls + '"><a href="javascript:void(0)"><input type="checkbox" class="filter-enabled"' + (checked ? ' checked' : '') + '> ' + rowLabel(row) + '</a></li>'));
            filter.$dropdownList.append($('<li data-val="' + option + '" class="' + cls + '"><div class="groupdiv"><input type="checkbox" class="filter-enabled"' + (checked ? ' checked' : '') + '> ' + rowLabel(row) + '</div></li>'));
        });
    };
    
    /*FINE*/
    BootstrapTableFilter.prototype.trigger = function(name) {
        let args = Array.prototype.slice.call(arguments, 1);

        name += '.bs.table.filter';
        if (typeof BootstrapTableFilter.EVENTS[name] === 'undefined') {
            throw 'Unknown event ' + name;
        }
        this.options[BootstrapTableFilter.EVENTS[name]].apply(this.options, args);
        this.$el.trigger($.Event(name), args);

        this.options.onAll(name, args);
        this.$el.trigger($.Event('all.bs.table.filter'), [name, args]);
    };

    // PUBLIC FUNCTION DEFINITION
    // =======================

    BootstrapTableFilter.prototype.resetView = function() {
        this.$el.html();
        this.init();
        this.trigger('reset');
    };

    BootstrapTableFilter.prototype.addFilter = function(filter) {
        this.filters[filter.field] = filter;
        this.$buttonList.append('<li data-filter-field="' + filter.field + '"><a href="javascript:void(0)"><input type="checkbox"> ' + filter.label + '</a></li>');

        this.trigger('add-filter', filter);
        if (typeof filter.enabled !== 'undefined' && filter.enabled) {
            this.enableFilter(filter.field);
        }
    };

    BootstrapTableFilter.prototype.removeFilter = function(field) {
        this.disableFilter(field);
        this.$buttonList.find('[data-filter-field=' + field + ']').remove();
        this.trigger('remove-filter', field);
        $('.input:checkbox').prop('checked','false');
    };


    BootstrapTableFilter.prototype.enableFilter = function(field) {
    
    	let numberOfDateFilter = $("#numberOfDateFilter").val();
    	$("#numberOfDateFilter").val(parseInt(numberOfDateFilter)+1);
    	numberOfDateFilter = $("#numberOfDateFilter").val();
    	
        let filter = this.getFilter(field);
        let $filterDropdown = $([
            '<div class="btn-group" data-filter-field="' + field + '">',
                '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">',
                    filter.label,
                    ' <span class="caret"></span>',
                '</button>',
                '<ul class="dropdown-menu" role="menu">',
                '</ul>',
            '</div>'
        ].join(''));
        $filterDropdown.appendTo(this.$filters);
        filter.$dropdown = $filterDropdown;
        filter.$dropdownList = $filterDropdown.find('.dropdown-menu');
        filter.enabled = true;

        this.$buttonList.find('[data-filter-field=' + field + '] input[type=checkbox]').prop('checked', true);

        let fType = this.getFilterType(filter);
        if (fType.search) {
            filter.$dropdownList.append($('<li class="static"><span><input type="text" class="form-control search-values" placeholder="' + this.options.formatSearchMessage() + '"></span></li>'));
            if(fType.rows!=null && fType.rows.length!=0)
            	filter.$dropdownList.append($('<li class="static divider"></li>'));
        }
        if (fType.rows) {       
            this.fillFilterOptions(field, fType.rows, 'static');
        }
        if (fType.rowsCallback) {
            fType.rowsCallback.call(this, filter, '');
        }
        
        if (fType.customCallback) {
            fType.customCallback.call(this, filter, '');
        }
        this.toggleRefreshButton(true);
        this.trigger('enable-filter', filter);
    };

    BootstrapTableFilter.prototype.disableFilters = function() {
        let that = this;
        $.each(this.filters, function(i, filter) {
            that.disableFilter(filter.field);
        });
    };

    BootstrapTableFilter.prototype.disableFilter = function(field) {
        let filter = this.getFilter(field);
        this.$buttonList.find('[data-filter-field=' + field + '] input[type=checkbox]').prop('checked', false);
        filter.enabled = false;
        if (filter.$dropdown) {
            filter.$dropdown.remove();
            delete filter.$dropdown;
            this.trigger('disable-filter', filter);
        }
        this.toggleRefreshButton(true);
    };

    BootstrapTableFilter.prototype.selectFilterOption = function(field, option, data) {
        let filter = this.getFilter(field);
        if (typeof filter.selectedOptions === 'undefined')
            filter.selectedOptions = {};
        if (data) {
            filter.selectedOptions[option] = data;
        }
        else {
            if (typeof filter.selectedOptions._values === 'undefined') {
                filter.selectedOptions._values = [];
            }
            filter.selectedOptions._values.push(option);
        }
        this.trigger('select-filter-option', field, option, data);
        this.toggleRefreshButton(true);
    };

    BootstrapTableFilter.prototype.unselectFilterOption = function(field, option) {
        let filter = this.getFilter(field);
        if (typeof filter.selectedOptions !== 'undefined' && typeof filter.selectedOptions[option] !== 'undefined') {
            delete filter.selectedOptions[option];
        }
        if (typeof filter.selectedOptions !== 'undefined' && typeof filter.selectedOptions._values !== 'undefined') {
            filter.selectedOptions._values = filter.selectedOptions._values.filter(function(item) {
                return item != option
            });
            if (filter.selectedOptions._values.length == 0) {
                delete filter.selectedOptions._values;
            }
            if ($.isEmptyObject(filter.selectedOptions)) {
                delete filter.selectedOptions;
            }
        }
        this.trigger('unselect-filter-option', field, option);
        this.toggleRefreshButton(true);
    };

    BootstrapTableFilter.prototype.setupFilterFromJSON = function(jsonFields) {
    	let that = this;
    	let jsonObj = jQuery.parseJSON(jsonFields);

    	$.each(jsonObj, function(key, data) {
    		that.setupFilter(key,data);
    	});
    	that.trigger('submit', that.getData());
        that.toggleRefreshButton(false);
        
    }
    
    BootstrapTableFilter.prototype.setupFilter = function(field, options) {
        let that = this;
        if(this.filters[field]!==undefined && this.filters[field]!=null)
        {
	        this.enableFilter(field);
	        
	        if(this.filters[field].type=='searchText')
	        	{
	        		$(".search-values").val(options);
	        	}
	        else
		        $.each(options, function(key, val) {
		            if (key === '_values') {
		                $.each(val, function(i, v) {
		                    that.selectFilterOption(field, v, false);
		                    $('div[data-filter-field="' + field + '"] [data-val="' + v + '"] input.filter-enabled').prop('checked', true);
		                });
		            }
		            else {
		                that.selectFilterOption(field, key, val);
		                $('div[data-filter-field="' + field + '"] [data-val="' + key + '"] input.filter-enabled').prop('checked', true);
		                $('div[data-filter-field="' + field + '"] [data-val="' + key + '"] input[type="text"]:not([data-name])').val(val);
		            }
		        });
        }
    };

    BootstrapTableFilter.prototype.toggleRefreshButton = function(show) {
        this.$refreshButton.toggle(show);
    };

    BootstrapTableFilter.prototype.isSelected = function(field, option, value) {
        let filter = this.getFilter(field);
        if (typeof filter.selectedOptions !== 'undefined') {
            if (typeof filter.selectedOptions[option] !== 'undefined') {
                if (value ? (filter.selectedOptions[option] == value) : filter.selectedOptions[option]) {
                    return true
                }
            }
            if (typeof filter.selectedOptions._values !== 'undefined') {
                if (filter.selectedOptions._values.indexOf(option.toString()) > -1) {
                    return true;
                }
            }
        }
        return false;
    };

    BootstrapTableFilter.prototype.getData = function() {
        let that = this;
        let ret = {};
        $.each(that.filters, function(field, filter) {

            if (filter.enabled) {
                if (typeof filter.selectedOptions !== 'undefined') {
                    ret[field] = filter.selectedOptions;
                }
                if(filter.type=='searchText')
                	{
                		let searchValue=$(".search-values").val();
                		ret[field] = searchValue;
                	}
            }
            
        });
        return ret;
    };

    // BOOTSTRAP FILTER TABLE PLUGIN DEFINITION
    // =======================

    $.fn.bootstrapTableFilter = function(option, _relatedTarget, _param2) {
        BootstrapTableFilter.externals = this.externals;

        let allowedMethods = [
            'addFilter', 'removeFilter',
            'enableFilter', 'disableFilter', 'disableFilters',
            'selectFilterOption', 'unselectFilterOption',
            'setupFilter','setupFilterFromJSON',
            'toggleRefreshButton',
            'getData', 'isSelected',
            'resetView'
        ],
        value;

        this.each(function() {
            let $this = $(this),
                data = $this.data('bootstrap.tableFilter'),
                options = $.extend(
                    {}, BootstrapTableFilter.DEFAULTS, $this.data(),
                    typeof option === 'object' && option
                );

            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw "Unknown method: " + option;
                }

                if (!data) {
                    return;
                }

                value = data[option](_relatedTarget, _param2);

                if (option === 'destroy') {
                    $this.removeData('bootstrap.tableFilter');
                }
            }

            if (!data) {
                $this.data('bootstrap.tableFilter', (data = new BootstrapTableFilter(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };

    $.fn.bootstrapTableFilter.Constructor = BootstrapTableFilter;
    $.fn.bootstrapTableFilter.defaults = BootstrapTableFilter.DEFAULTS;
    $.fn.bootstrapTableFilter.columnDefaults = BootstrapTableFilter.COLUMN_DEFAULTS;
    $.fn.bootstrapTableFilter.externals = BootstrapTableFilter.EXTERNALS;
    $.fn.bootstrapTableFilter.filterSources = BootstrapTableFilter.FILTER_SOURCES;

    // BOOTSTRAP TABLE FILTER INIT
    // =======================

    $(function() {
        $('[data-toggle="tableFilter"]').bootstrapTableFilter();
    });
    
    


}(jQuery);

let l=0,m=0,e=0,n=0;
function showDatepickerLess(numberOfDateFilter){
hideDatepickers(numberOfDateFilter);
$("#datepickerFilterLess"+numberOfDateFilter).show();	
$("#datepickerFilterLess"+numberOfDateFilter).datepicker({
    format: 'dd/MM/yyyy',
    altField: '#datepickerInputLess'+numberOfDateFilter
    	});
}
function showDatepickerMore(numberOfDateFilter){
	hideDatepickers(numberOfDateFilter);
	$("#datepickerFilterMore"+numberOfDateFilter).show();	
	$("#datepickerFilterMore"+numberOfDateFilter).datepicker({
	    format: 'dd/MM/yyyy',
	    altField: '#datepickerInputMore'+numberOfDateFilter	
	    	})
	}
function showDatepickerEq(numberOfDateFilter){
	hideDatepickers(numberOfDateFilter);
	$("#datepickerFilterEq"+numberOfDateFilter).show();
	$("#datepickerFilterEq"+numberOfDateFilter).datepicker({
	    format: 'dd/MM/yyyy',
	    altField: '#datepickerInputEq'+numberOfDateFilter	});
	}
function showDatepickerNeq(numberOfDateFilter){
	hideDatepickers(numberOfDateFilter);
	$("#datepickerFilterNeq"+numberOfDateFilter).show();	
	$("#datepickerFilterNeq"+numberOfDateFilter).datepicker({
	    format: 'dd/MM/yyyy',
	    altField: '#datepickerInputNeq'+numberOfDateFilter	
	    	});
	}

function hideDatepickers(numberOfDateFilter){
	$("#datepickerFilterMore"+numberOfDateFilter).hide();	
	$("#datepickerFilterLess"+numberOfDateFilter).hide();	
	$("#datepickerFilterEq"+numberOfDateFilter).hide();	
	$("#datepickerFilterNeq"+numberOfDateFilter).hide();
}



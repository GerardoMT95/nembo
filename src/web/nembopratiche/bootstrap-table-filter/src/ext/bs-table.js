!function($) {

    'use strict';

    let filterData = {};
    let bootstrapTableFilter;
    let serverUrl;

    let getTypeByValues = function(filter) {
        let typeFloat = true, typeInt = true;
        $.each(filter.values, function(i, val) {
            if (typeInt && (parseInt(val) != val)) {
                typeInt = false;
            }
            if (typeFloat && (parseFloat(val) != val)) {
                typeFloat = false;
            }
        });
        if (typeInt || typeFloat) {
            return {type: 'range'};
        }
        if (serverUrl) {
            let delimiter = serverUrl.indexOf('?') < 0 ? '?' : '&';
            return {
                type: 'search',
                source: serverUrl + delimiter + 'resourceFor=' + filter.field
            };
        }
        return {type: 'select'};
    };
    let getCols = function(cols, data, useAjax) {
        let ret = {};
        $.each(cols, function(i, col) {
            if (col.filterable)
            {
                ret[col.field] = {
                    field: col.field,
                    label: col.title,
                    values: []
                };
            }
        });
        $.each(data, function(i, row) {
            $.each(ret, function(field, filter) {
                if (ret[field].values.indexOf(row[field]) < 0) {
                    ret[field].values.push(row[field]);
                }
            });
        });
        $.each(ret, function(field, def) {
            ret[field] = $.extend(ret[field], getTypeByValues(def));
        });
        return ret;
    };
    let rowFilter = function(item, i) {
        let filterType;
        let filter;
        let ret = true;
        $.each(item, function(field, value) {
            filterType = false;
            try {
                filterType = bootstrapTableFilter.getFilterType(field);
                filter = bootstrapTableFilter.getFilter(field);
                if (typeof filter.values !== 'undefined') {
                    let oldVal = value;
                	value = filter.values.indexOf(value);
                    if(value == -1)
                    {
                    	 //Gestione filtri di tipo select
                    	 ret =  ret && $.inArray(oldVal, filterData[field]._values) >= 0;
                    	 return ret;
                    }
                }
                if (filterType && typeof filterData[field] !== 'undefined') {
                    ret = ret && bootstrapTableFilter.checkFilterTypeValue(filterType, filterData[field], value);
                }
            }
            catch (e) {}
        });
        return ret;
    };

    $.fn.bootstrapTableFilter.externals.push(function() {
        if (this.options.connectTo) {
            bootstrapTableFilter = this;
            let $bootstrapTable = $(this.options.connectTo);
            let data = $bootstrapTable.bootstrapTable('getData');
            let cols = $bootstrapTable.bootstrapTable('getColumns');
            serverUrl = $bootstrapTable.bootstrapTable('getServerUrl');
            let dataSourceServer = false;
            let filters = this.options.filters.length ? [] : getCols(cols, data, dataSourceServer);

            $.each(filters, function(field, filter) {
                bootstrapTableFilter.addFilter(filter);
            });
            if (serverUrl) {
                this.$el.on('submit.bs.table.filter', function() {
                    filterData = bootstrapTableFilter.getData();
                    let delimiter = serverUrl.indexOf('?') < 0 ? '?' : '&';
                    let url = serverUrl + delimiter + 'filter=' + encodeURIComponent(JSON.stringify(filterData));
                    $bootstrapTable.bootstrapTable('refresh', {url: url});
                });
            }
            else {
                $bootstrapTable.bootstrapTable('registerSearchCallback', rowFilter);
                this.$el.on('submit.bs.table.filter', function() {
                    filterData = bootstrapTableFilter.getData();
                    $bootstrapTable.bootstrapTable('updateSearch');
                });
            }
        }
    });

}(jQuery);


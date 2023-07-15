/**
 * @author: Dennis Hernández
 * @webSite: http://djhvscf.github.io/Blog
 * @version: v1.3.0
 */

(function ($) {
    'use strict';

    let flat = function (element, that) {
        let result = {};

        function recurse(cur, prop) {
            if (Object(cur) !== cur) {
                result[prop] = cur;
            } else if ($.isArray(cur)) {
                for (let i = 0, l = cur.length; i < l; i++) {
                    recurse(cur[i], prop ? prop + that.options.flatSeparator + i : "" + i);
                    if (l == 0) {
                        result[prop] = [];
                    }
                }
            } else {
                let isEmpty = true;
                for (let p in cur) {
                    isEmpty = false;
                    recurse(cur[p], prop ? prop + that.options.flatSeparator + p : p);
                }
                if (isEmpty) {
                    result[prop] = {};
                }
            }
        }

        recurse(element, "");
        return result;
    };

    let flatHelper = function (data, that) {
        let flatArray = [];

        $.each(!$.isArray(data) ? [data] : data, function (i, element) {
            flatArray.push(flat(element, that));
        });
        return flatArray;
    };

    $.extend($.fn.bootstrapTable.defaults, {
        flat: false,
        flatSeparator: '.'
    });

    let BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initData = BootstrapTable.prototype.initData;

    BootstrapTable.prototype.initData = function (data, type) {
        if (this.options.flat) {
            data = flatHelper(data ? data : this.options.data, this);
        }
        _initData.apply(this, [data, type]);
    };
})(jQuery);

/**
 * Bootstrap Table Filter Czech translation
 * Author: Massimo Durando
 */
(function ($) {
    'use strict';

    $.extend($.fn.bootstrapTableFilter.defaults, {
        formatRemoveFiltersMessage: function () {
            return 'Rimuovi Filtri';
        },
        formatSearchMessage: function () {
            return 'Cerca';
        }
    });

    $.fn.bootstrapTableFilter.filterSources.range.rows[0].i18n.msg = 'Minore di';
    $.fn.bootstrapTableFilter.filterSources.range.rows[1].i18n.msg = 'Maggiore di';
    $.fn.bootstrapTableFilter.filterSources.range.rows[2].i18n.msg = 'Uguale a';

    $.fn.bootstrapTableFilter.filterSources.date.rows[0].i18n.msg = 'Minore di';
    $.fn.bootstrapTableFilter.filterSources.date.rows[1].i18n.msg = 'Maggiore di';
    $.fn.bootstrapTableFilter.filterSources.date.rows[2].i18n.msg = 'Uguale a';
    $.fn.bootstrapTableFilter.filterSources.date.rows[3].i18n.msg = 'Diverso da';
    
    $.fn.bootstrapTableFilter.filterSources.search.rows[0].i18n.msg = 'Uguale a';
    $.fn.bootstrapTableFilter.filterSources.search.rows[1].i18n.msg = 'Diverso da';
    $.fn.bootstrapTableFilter.filterSources.search.rows[2].i18n.msg = 'Contiene';
    $.fn.bootstrapTableFilter.filterSources.search.rows[3].i18n.msg = 'Non contiene';
    $.fn.bootstrapTableFilter.filterSources.search.rows[4].i18n.msg = 'Vuoto';
    $.fn.bootstrapTableFilter.filterSources.search.rows[5].i18n.msg = 'Non vuoto';

})(jQuery);
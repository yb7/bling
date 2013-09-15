Ext.define('Bling.controller.wms.RegionalAllocationEditor', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.ArticleSearchTabWin'],
    views: ['wms.ArticleSearchTabWin'],
    refs: [
        {
            selector: 'regional-allocation-editor',
            ref: 'mainPanel'
        }
    ],

    init: function() {
        var me = this;
        this.control({
            'regional-allocation-editor #addArticlesBtn': {
                click: function(){
                    this.getView('wms.ArticleSearchTabWin').create().show();
                }
            }
        })
    }
});

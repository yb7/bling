Ext.define('Bling.controller.wms.RegionalAllocationCreator', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.ArticleSearchTabWin'],
    views: ['wms.ArticleSearchTabWin'],
    refs: [
        {
            selector: 'regional-allocation-creator',
            ref: 'mainPanel'
        }
    ],

    init: function() {
        var me = this;
        this.control({
            'regional-allocation-creator #addArticlesBtn': {
                click: function(){
                    this.getView('wms.ArticleSearchTabWin').create().show();
                }
            }
        })
    }
});

Ext.define('Bling.controller.wms.EnteringWarehouseMgmtGrid', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.EnteringWarehouseCreator'],
    views: ['wms.EnteringWarehouseCreator'],
    refs: [
        {
            selector: 'entering-warehouse-mgmt-grid',
            ref: 'mainGrid'
        },
        {
            selector: 'content-panel',
            ref: 'contentPanel'
        }
    ],

    init: function() {
        var me = this;
        this.control({
            'entering-warehouse-mgmt-grid #createBtn': {
                click: function(){
                    this.getContentPanel().add(this.getView('wms.EnteringWarehouseCreator').create());
                }
            }
        })
    }
});

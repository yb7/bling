Ext.define('Bling.controller.wms.RegionalAllocation', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.RegionalAllocationCreator'],
    views: ['wms.RegionalAllocationCreator'],
    refs: [
        {
            selector: 'regional-allocation-grid',
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
            'regional-allocation-grid #createBtn': {
                click: function(){
                    this.application.fireEvent('addtocontentpanel', me.getView('wms.RegionalAllocationCreator').create());
                }
            }
        })
    }
});

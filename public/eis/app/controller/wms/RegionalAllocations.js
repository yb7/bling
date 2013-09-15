Ext.define('Bling.controller.wms.RegionalAllocations', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.RegionalAllocationEditor', 'Bling.model.wms.RegionalAllocation'],
    views: ['wms.RegionalAllocationEditor'],
    models: ['wms.RegionalAllocation'],
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
                click: this.createOrder
            },
            'regional-allocation-grid grid': {
                afterrender: function(cmp) {
                    Ext.data.StoreManager.lookup('wms.RegionalAllocations').load();
                },
                itemdblclick: this.editOrder
            }
        })
    },
    createOrder: function() {
        var me = this;
        this.getModel('wms.RegionalAllocation').create().save({
            success: function(rec, op) {
                me.initOrderEditor(rec.data)
            }
        });
    },
    editOrder: function(view, record, item, index, e, opts) {
        this.initOrderEditor(record.data)
    },
    initOrderEditor: function(data) {

        var store = Ext.create('Ext.data.Store', {
            model: 'Bling.model.article.Article',
            proxy: {
                type: 'rest',
                url : '/eis/wms/regional-allocations/' + data.id + "/articles",
                reader: {
                    type: 'json',
                    root: 'data'
                },
                writer: {
                    type: 'json'
                }
            },
            autoSync: true
        });
        var panel = this.getView('wms.RegionalAllocationEditor').create({articleStore: store});
        store.load();
        panel.down('form').getForm().setValues(data);
        panel.down('form warehouse-combo[name=receiveWarehouseId]').setValue(Ext.create('Bling.model.wms.Warehouse',
            {id: data.warehouseId, name: data.warehouseName}));
        this.application.fireEvent('addtocontentpanel', {view: panel});
    }
});

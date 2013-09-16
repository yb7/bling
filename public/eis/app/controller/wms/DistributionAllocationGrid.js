Ext.define('Bling.controller.wms.DistributionAllocationGrid', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.DistributionAllocationEditor', 'Bling.model.wms.DistributionAllocation'],
    views: ['wms.DistributionAllocationEditor'],
    models: ['wms.DistributionAllocation'],
    refs: [
        {
            selector: 'distribution-allocation-grid',
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
            'distribution-allocation-grid #createBtn': {
                click: this.createOrder
            },
            'distribution-allocation-grid grid': {
                afterrender: function(cmp) {
                    Ext.data.StoreManager.lookup('wms.DistributionAllocations').load();
                },
                itemdblclick: this.editOrder
            }
        })
    },
    createOrder: function() {
        var me = this;
        this.getModel('wms.DistributionAllocation').create().save({
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
                url : '/eis/wms/distribution-allocations/' + data.id + "/articles",
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
        var panel = this.getView('wms.DistributionAllocationEditor').create({articleStore: store});
        store.load();
        panel.down('form').getForm().setValues(data);
        panel.down('form warehouse-combo[name=receiveWarehouseId]').setValue(Ext.create('Bling.model.wms.Warehouse',
            {id: data.receiveWarehouseId, name: data.receiveWarehouseName}));
        this.application.fireEvent('addtocontentpanel', {view: panel});
    }
});

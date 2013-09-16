Ext.define('Bling.controller.wms.OutwardProcessingGrid', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.OutwardProcessingEditor', 'Bling.model.wms.OutwardProcessing'],
    views: ['wms.OutwardProcessingEditor'],
    models: ['wms.OutwardProcessing'],
    refs: [
        {
            selector: 'outward-processing-grid',
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
            'outward-processing-grid #createBtn': {
                click: this.createOrder
            },
            'outward-processing-grid grid': {
                afterrender: function(cmp) {
                    Ext.data.StoreManager.lookup('wms.OutwardProcessings').load();
                },
                itemdblclick: this.editOrder
            }
        })
    },
    createOrder: function() {
        var me = this;
        this.getModel('wms.OutwardProcessing').create().save({
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
                url : '/eis/wms/outward-processings/' + data.id + "/articles",
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
        var panel = this.getView('wms.OutwardProcessingEditor').create({articleStore: store});
        store.load();
        panel.down('form').getForm().setValues(data);
        panel.down('form warehouse-combo[name=receiveWarehouseId]').setValue(Ext.create('Bling.model.wms.Warehouse',
            {id: data.receiveWarehouseId, name: data.receiveWarehouseName}));
        this.application.fireEvent('addtocontentpanel', {view: panel});
    }
});

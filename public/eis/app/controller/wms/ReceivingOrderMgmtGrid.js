Ext.define('Bling.controller.wms.ReceivingOrderMgmtGrid', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.ReceivingOrderEditor',
        'Bling.model.wms.ReceivingOrder'],
    views: ['wms.ReceivingOrderEditor'],
    models: ['wms.ReceivingOrder'],
    refs: [
        {
            selector: 'receiving-order-mgmt-grid',
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
            'receiving-order-mgmt-grid #createBtn': {
                click: this.createOrder
            },
            'receiving-order-mgmt-grid grid': {
                afterrender: function(cmp) {
                    cmp.getStore().load();
                },
                itemdblclick: this.editOrder
            }
        })
    },

    createOrder: function() {
        var me = this;
        var order = this.getModel('wms.ReceivingOrder').create().save({
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
                url : '/eis/wms/receiving-orders/' + data.id + "/articles",
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
        var panel = this.getView('wms.ReceivingOrderEditor').create({articleStore: store});
        store.load();
        panel.down('form').getForm().setValues(data);
        panel.down('form combo[name=warehouseId]').setValue(Ext.create('Bling.model.wms.Warehouse',
            {id: data.warehouseId, name: data.warehouseName}));
        this.application.fireEvent('addtocontentpanel', {view: panel});
    }
});

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
        var order = this.getModel('wms.ReceivingOrder').create().save();
        var panel = this.getView('wms.ReceivingOrderEditor').create();
        panel.getForm().setValues(order.data);
        this.getContentPanel().add(panel);
    },
    editOrder: function(view, record, item, index, e, opts) {
        var panel = this.getView('wms.ReceivingOrderEditor').create();
        panel.getForm().setValues(record.data);
        this.getContentPanel().add(panel);
    }
});

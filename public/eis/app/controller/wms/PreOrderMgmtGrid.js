Ext.define('Bling.controller.wms.PreOrderMgmtGrid', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.PreOrderEditor'],
    views: ['wms.PreOrderEditor'],
    refs: [
        {
            selector: 'pre-order-mgmt-grid',
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
            'pre-order-mgmt-grid #createSellBtn': {
                click: this.createOrder
            },
            'pre-order-mgmt-grid grid': {
                afterrender: function(cmp) {
                    //cmp.getStore().load();
                },
                itemdblclick: this.editOrder
            }
        })
    },

    createOrder: function() {
        var me = this;
				me.initOrderEditor({});
        //var order = this.getModel('wms.ReceivingOrder').create().save({
         //   success: function(rec, op) {
          //      me.initOrderEditor(rec.data)
           // }
       // });
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
       //var panel = this.getView('wms.ReceivingOrderEditor').create({articleStore: store});
        var panel = this.getView('wms.PreOrderEditor').create({articalStore:store});
        store.load();
        //panel.down('form').getForm().setValues(data);
       // panel.down('form combo[name=warehouseId]').setValue(Ext.create('Bling.model.wms.Warehouse',
       //     {id: data.warehouseId, name: data.warehouseName}));
        this.application.fireEvent('addtocontentpanel', {view: panel});
    }
});

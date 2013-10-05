Ext.define('Bling.controller.wms.CashierEditor', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.shared.UploadFileWin'],
    views: ['shared.UploadFileWin'],
    refs: [
        {
            selector: 'content-panel',
            ref: 'contentPanel'
        }
    ],

    init: function() {
        var me = this;
        this.control({
            'cashier-editor #uploadFileBtn': {
                click: this.uploadFile
            },
            'cashier-editor tally-article-grid': {
                afterrender: function(cmp) {
                    cmp.getStore().load();
                },
                selectionchange: function(selModel, selections, opts) {
                    this.getContentPanel().getActiveTab().down('#deleteBtn').setDisabled(selections.length === 0);
                }
            },
            'cashier-editor #deleteBtn': {
                click: this.deleteArticle
            },
            'cashier-editor #executeBtn': {
                click: this.execute
            }
        })
    },

    deleteArticle: function(cmp){
        var grid = cmp.up('tally-article-grid');
        var selected = grid.getSelectionModel().getSelection()[0];
        if (selected) {
            grid.getStore().remove(selected);
        }
    },

    uploadFile: function(cmp, value, opts) {
        var id = this.getOrderId(cmp);
        var postUrl = '/eis/wms/receiving-orders/' + id + '/articles.xls';
        var win = this.getView('shared.UploadFileWin').create({
            postUrl: postUrl
        });
        win.on('upload-file-success', function(values) {
            console.log("upload file success " + values);
            win.close();
            cmp.up('receiving-order-editor').down('tally-article-grid').getStore().load();
        });
        win.show();

    },
    execute: function(btn) {
        var me = this;
        var id = this.getOrderId(btn);
        var executeUrl = '/eis/wms/receiving-orders/' + id + '/execute';
        var formValues = this.getEditor(btn).down('form').getForm().getValues();
        delete formValues.bizCode;
        delete formValues.id;
        Ext.Ajax.request({
            url:executeUrl,
            method:'PUT',
            jsonData:formValues,
            success:function (response) {
                me.getEditor(btn).close();
                Ext.data.StoreManager.lookup('wms.ReceivingOrders').load();
            },
            failure:function(response){
                var e = Ext.decode(response.responseText);
                Ext.Msg.alert('警告', e.message);
            }
        });
    },
    getOrderId: function(cmp) {
        return this.getEditor(cmp).down('form hidden[name=id]').getValue();
    },
    getEditor: function(cmp) {
        return cmp.up('cashier-editor');
    }

});

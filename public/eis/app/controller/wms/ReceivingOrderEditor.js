Ext.define('Bling.controller.wms.ReceivingOrderEditor', {
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
            'receiving-order-editor #uploadFileBtn': {
                click: this.uploadFile
            },
            'receiving-order-editor tally-article-grid': {
                afterrender: function(cmp) {
                    cmp.getStore().load();
                },
                selectionchange: function(selModel, selections, opts) {
//                    console.log(opts);
//                    this.getCompanyMgmtGrid().down('#deleteBtn').setDisabled(selections.length === 0);
                }
            },
            'receiving-order-editor #deleteBtn': {
                click: this.deleteArticle
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
        var id = cmp.up('form').down('hidden[name=id]').getValue();
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

    }
});

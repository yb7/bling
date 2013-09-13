Ext.define('Bling.controller.wms.ReceivingOrderEditor', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.shared.UploadFileWin'],
    views: ['shared.UploadFileWin'],
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
            'receiving-order-editor #uploadFileBtn': {
                click: this.uploadFile
            }
        })
    },

    uploadFile: function(cmp, value, opts) {
        var id = cmp.up('form').down('hidden[name=id]').getValue();
        var postUrl = '/eis/wms/receiving-orders/' + id + '/articles.xls';
        var win = this.getView('shared.UploadFileWin').create({
            postUrl: postUrl
        });
        win.on('upload-file-success', function(values) {
            win.close();
            cmp.up('receiving-order-editor').down('grid').getStore().load();
        });
        win.show();

    }
});

Ext.define('Bling.controller.wms.EnteringWarehouseCreator', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.share.UploadFileWin'],
    views: ['share.UploadFileWin'],
    refs: [
        {
            selector: 'entering-warehouse-mgmt-grid',
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
            'entering-warehouse-creator #uploadFileBtn': {
                click: this.uploadFile
            }
        })
    },

    uploadFile: function(cmp, value, opts) {
        this.getView('share.UploadFileWin').create({postUrl: ''}).show();
    }
});

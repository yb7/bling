Ext.define('Bling.controller.shared.UploadFileWin', {
    extend: 'Ext.app.Controller',


    init: function() {
        var me = this;
        this.control({
            'upload-file-win #uploadBtn': {
                click: this.doUpload
            }
        })
    },

    doUpload: function(cmp, e, opts){
        var me = this;
        var formDom = cmp.up('form');
        var form = formDom.getForm();
        var filepath = formDom.down('[name=file]').getValue();
        if (filepath.indexOf(".xls", filepath.length - 4) === -1) {
            Ext.MessageBox.alert('提醒', '请上传XLS格式文件');
            return;
        }
        var win = cmp.up('upload-file-win');
        var postUrl = cmp.up('upload-file-win').postUrl;
        form.submit({
            url: postUrl,
            waitMsg:'正在上传...',
            success:function(form, action) {
                me.fireEvent('upload-file-success', action.result);
            },
            failure:function(form, action) {
                Ext.Msg.alert("Error", Ext.JSON.decode(this.response.responseText).message);
            }
        });
    }
});

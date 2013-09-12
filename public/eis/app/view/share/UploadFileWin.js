Ext.define('Bling.view.share.UploadFileWin', {
    extend: 'Ext.window.Window',
    requires: ['Ext.form.field.Field'],
    xtype: 'upload-file-win',
    initComponent:function() {
        Ext.apply(this, {
            title:'文件上传',
            modal:true,
            resizable:false,
            width:400,
            items:[
                {
                    xtype:'form',
                    layout:'fit',
                    defaults: {
                        anchor: '100%',
                        allowBlank: false,
//                        msgTarget: 'side',
                        labelWidth: 50
                    },
//                    standardSubmit: false,
                    items:[
                        {
                            xtype:'filefield',
                            emptyText: '请选择文件',
                            fieldLabel: '文件',
                            name: 'file',
                            buttonText: '',
                            buttonConfig: {
                                iconCls: 'upload-icon'
                            }

                        }
                    ],
                    buttons:[
                        {
                            text:'确定',
                            itemId:'uploadBtn'
                        },
                        {
                            text:'取消',
                            handler:function(btn, e) {
                                btn.up('window').close();
                            }
                        }
                    ]
                }
            ]
        });
        this.callParent(arguments);
    }
});
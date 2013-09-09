Ext.define("Bling.view.foundation.EditCompanyWin", {
    extend: 'Ext.window.Window',
    xtype: 'edit-company-win',

    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            title: '修改公司',
            layout:'fit',
            resizable: false,
            modal: true,
            items:[{
                xtype: 'form',
                bodyPadding: 10,
                fieldDefaults: {
                    labelAlign: 'left',
                    labelWidth: 40,
                    labelStyle: 'font-weight:bold'
                },
                items: [

                    {
                        xtype:'textfield',
                        fieldLabel : '名称',
                        name : 'name',
                        allowBlank: false,
                        blankText: '名称必须填写'
                    },
                    {
                        xtype:'textfield',
                        fieldLabel : '编码',
                        name : 'shortCode',
                        allowBlank:false,
                        blankText:'编码必须填写'
                    },
                    {
                        xtype:'hidden',
                        name : 'id'
                    },
                    {
                        xtype:'hidden',
                        name : 'version'
                    }
                ],
                buttons : [
                    {
                        itemId:'saveBtn',
                        formBind: true,
                        text : '确定',
                        cls:'defaultBtn'
                    },
                    {
                        itemId:'cancelBtn',
                        text : '取消'
                    }
                ]
            }]
        });
        me.callParent(arguments);
    }
});
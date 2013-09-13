Ext.define('Bling.view.wms.ReceivingOrderEditor', {
    extend: 'Ext.form.Panel',
    xtype: 'receiving-order-editor',

    requires: ['Ext.form.field.Hidden'],

    bodyPadding: 10,
    title: '入库新增',

    initComponent: function() {
        var me = this;

        Ext.apply(me, {
            layout:'column',
            defaults: {
                margin:'5 5 5 5',
                columnWidth: 0.33
            },
            fieldDefaults: {
                labelWidth:70,
                labelAlign:'left'
            },
            items: [
                {
                    xtype:'hidden',
                    name:'id'
                },
                {
                    xtype:'hidden',
                    name:'version'
                },
                {
                    xtype: 'textfield',
                    name: 'bizCode',
                    fieldLabel: '入库单号'
                },
                {
                    xtype: 'datefield',
                    name: 'receivingDate',
                    fieldLabel: '入库日期'
                },
                {
                    xtype: 'combobox',
                    anchor: '100%',
                    fieldLabel: '入库仓库'
                },
                {
                    columnWidth: 1,
                    xtype: 'tally-article-grid',
                    dockedItems: [{
                        xtype: 'toolbar',
                        dock: 'top',
                        ui:'footer',
                        items: ['->', {
                                xtype: 'button',
                                text: 'Excel模板下载'
                            },
                            {
                                xtype: 'button',
                                text: '从Excel导入',
                                itemId: 'uploadFileBtn'
                            },
                            {
                                xtype: 'button',
                                text: '删除'
                            }
                        ]
                    }, {
                        xtype: 'toolbar',
                        dock: 'bottom',
                        ui:'footer',
                        items: ['->',{
                                text: '执行入库',
                                itemId: 'executeBtn'
                            }
                        ]
                    }]
                }
            ]
        });

        me.callParent(arguments);
    }

});
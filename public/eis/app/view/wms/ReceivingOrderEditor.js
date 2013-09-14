Ext.define('Bling.view.wms.ReceivingOrderEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'receiving-order-editor',

    requires: ['Ext.form.field.Hidden', 'Ext.form.field.File', 'Bling.model.article.Article',
        'Bling.view.shared.WarehouseCombo'],

    bodyPadding: 10,
    title: '入库新增',
    closable:true,

    initComponent: function() {
        var me = this;

        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{
                xtype: 'form',
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
                        xtype: 'warehouse-combo',
                        anchor: '100%',
                        fieldLabel: '入库仓库',
                        name: 'warehouseId'
                    }
                ]
            }, {
                xtype: 'tally-article-grid',
                flex: 1,
                store: me.articleStore,
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
                            itemId: 'deleteBtn',
                            //                                disabled: true,
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
            }]
        });

        me.callParent(arguments);
    }

});
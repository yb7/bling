Ext.define('Bling.view.wms.PreOrderEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'pre-order-editor',

    requires: ['Ext.form.field.Hidden', 'Ext.form.field.File', 'Bling.model.article.Article',
        'Bling.view.shared.WarehouseCombo'],

    bodyPadding: 10,
    title: '预定新增',
    closable:true,

    initComponent: function() {
        var me = this;
        var grid = {
            xtype: 'grid',
            store: 'wms.ReceivingOrders',
            columns: [
                {text: 'ID', dataIndex: 'id', hidden: true, width: 50},
                {text: '预定单号', dataIndex: 'orderCode', width: 150},
                {text: '到期日', dataIndex:'expireTime', width:150,xtype: 'datecolumn', format:'Y-m-d'},
                {text: '货品条码', dataIndex: 'goodsCode', width: 150},
                {text: '货品名称', dataIndex: 'goodsName', width:150},
                {text: '货品品种', dataIndex: 'goodsCategory', width:150},
                {text: '货品重量', dataIndex: 'goodsWeight', width:150},
                {text: '金属名称', dataIndex: 'metalName', width:150},
                {text: '金重', dataIndex: 'metalWeight', width:150},
                {text: '宝石名称', dataIndex: 'jewelName', width:150}
            ],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                ui:'footer',
                items: ['->', {
                        xtype: 'button',
                        itemId: 'deleteBtn',
                        disabled: true,
                        text: '删除'
                    }
                ]
            }],
            flex: 1
        };

        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [
            {
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
                        xtype: 'textfield',
                        name: 'sellCode',
                        disabled:true,
                        fieldLabel: '预定单号'
                    },
                    {
                        xtype:'form',
                        layout:'column',
                        columnWidth:0.66,
                        defaults: {
                            margin:'5 5 5 5',
                            columnWidth:0.33
                        },
                        items: [
                            {
                                xtype: 'button',
                                itemId: 'cancelButton',
                                disabled: true,
                                text: '作废'
                            },
                            {
                                xtype: 'button',
                                itemId: 'saveButton',
                                disabled: true,
                                text: '保存/打印'
                            },
                            {
                                xtype: 'checkbox',
                                itemId: 'printCb',
                                boxLabel: '是否立即打印'
                            },
                        ]
                    }
                ]
            },
            {
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
                        xtype: 'hidden',
                        name: 'sellCode',
                        fieldLabel: '预定单号'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellStore',
                        fieldLabel: '销售门店'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellArea',
                        fieldLabel: '销售区域'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellBar',
                        fieldLabel: '销售柜台'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'seller1',
                        fieldLabel: '营业员1'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'seller2',
                        fieldLabel: '营业员2'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'orderRound',
                        fieldLabel: '班次'
                    },
                    {
                        xtype: 'textfield',
                        name: 'goodsCode',
                        fieldLabel: '条码扫描'
                    },
                    {
                        xtype: 'datefield',
                        name: 'orderDate',
                        format:'Y-m-d',
                        fieldLabel: '预定日期'
                    },
                    {
                        xtype: 'datefield',
                        name: 'expireDate',
                        format:'Y-m-d',
                        fieldLabel: '到期日期'
                    },
                    {
                        xtype: 'numberfield',
                        name: 'totalAmount',
                        disabled:true,
                        fieldLabel: '总金额'
                    },
                    {
                        xtype: 'numberfield',
                        name: 'earnestAmount',
                        fieldLabel: '定金'
                    }
                ]
            }, grid]
        });

        me.callParent(arguments);
    }

});

Ext.define('Bling.view.wms.CounterOrderEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'counter-order-editor',

    requires: ['Ext.form.field.Hidden', 'Ext.form.field.File', 'Bling.model.article.Article',
        'Bling.view.shared.WarehouseCombo'],

    bodyPadding: 10,
    title: '上柜新增',
    closable:true,

    initComponent: function() {
        var me = this;
        var grid = {
            xtype: 'grid',
            store: 'wms.ReceivingOrders',
            columns: [
                {text: 'ID', dataIndex: 'id', hidden: true, width: 50},
                {text: '货品条码', dataIndex: 'goodsCode', width: 150},
                {text: '货品名称', dataIndex: 'goodsName', width:150},
                {text: '货品品种', dataIndex: 'goodsCategory', width:150},
                {text: '货品重量', dataIndex: 'goodsWeight', width:150},
                {text: '金属名称', dataIndex: 'metalName', width:150},
                {text: '金重', dataIndex: 'metalWeight', width:150},
                {text: '宝石名称', dataIndex: 'jewelName', width:150},
                {text: '证书号', dataIndex:'identityCode',width:150}
            ],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                ui:'footer',
                items: ['->', {
                        xtype: 'button',
                        itemId: 'SaveBtn',
                        disabled: true,
                        text: '保存'
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
                        xtype:'hidden',
                        name:'id'
                    },
                    {
                        xtype:'hidden',
                        name:'version'
                    },
                    {
                        xtype: 'textfield',
                        name: 'onCounterCode',
                        disabled:true,
                        fieldLabel: '上柜单号'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellStore',
                        fieldLabel: '销售门店'
                    },
                    {
                        xtype: 'datefield',
                        name: 'onCounterDate',
                        format:'Y-m-d',
                        fieldLabel: '日期'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellArea',
                        fieldLabel: '销售区域'
                    },
                    {
                        xtype: 'textfield',
                        name: 'areaCode',
                        fieldLabel: '区域代码'
                    },
                    {
                        xtype: 'numberfield',
                        name: 'amount',
                        fieldLabel: '合计数量'
                    },
                    {
                        xtype: 'textfield',
                        name: 'comments',
                        fieldLabel: '备注',
                        columnWidth:0.66
                    },
                    {
                        xtype:'panel',
                        height:24
                    },
                    {
                        xtype: 'textfield',
                        name: 'goodsCode',
                        fieldLabel: '条码扫描',
                        columnWidth:0.66
                    }
                ]
            }, grid]
        });

        me.callParent(arguments);
    }

});
